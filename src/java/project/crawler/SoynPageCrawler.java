/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import project.jaxb.Canvas;
import project.jaxb.Categories;
import project.jaxb.Detail;
import project.jaxb.Painting;
import project.utils.Constant;
import project.utils.StringUtilities;
import project.utils.XMLUtilities;
import static project.utils.XMLUtilities.getBufferReaderFromURI;

/**
 *
 * @author thuyv
 */
public class SoynPageCrawler {

    private String url;
    private String categoryName;
    private int pageCount;
    private String htmlContent;

    public SoynPageCrawler(String url, String categoryName) {
        this.url = url;
        this.categoryName = categoryName;

        pageCount = 1;
        htmlContent = "";
    }

    public Categories crawlEachPage()
            throws UnsupportedEncodingException, XMLStreamException, XMLStreamException {
        if (categoryName == null) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, new Exception("NULL categoryName"));
            return null;
        }

        // get total page count of this category
        getPageCount(url);

        System.out.println("In SoynCrawler " + categoryName + " - " + url + " - " + pageCount);

        Categories category = new Categories();
        category.setName(categoryName);
        List<Canvas> listCanvas = new ArrayList<>();

        for (int i = 0; i < pageCount; i++) {
            String pageUrl = url + "?page=" + (i + 1);
            String beginSign = "class=\"products-view products-view-grid\"";
            String endSign = "class=\"sidebar left left-content col-lg-3 col-lg-pull-9\"";
            
            htmlContent = "";
            htmlContent = XMLUtilities.parseHTML(pageUrl, beginSign, endSign);
            htmlContent = StringUtilities.refineHtml(htmlContent);
            
            listCanvas.addAll(crawlListCanvasEachPage(htmlContent));
        } // end for page

//        listCanvas.forEach((canvas) -> {
//            System.out.println(canvas.getName() + " - " + canvas.getUrl());
//        });
        
        System.out.println("--- Done getEachPage - Begin get detail of " + listCanvas.size());

        // Read each product page to get detail
        for (int i = 0; i < listCanvas.size(); i++) {
            List<Detail> listDetail = new ArrayList<>();
            Canvas currentCanvas = listCanvas.get(i);
            String price = "";

            url = currentCanvas.getUrl();
            System.out.println((i+1) + " - " + url);

            htmlContent = "";
            String beginSign = "class=\"price-box\"";
            String endSign = "class=\"form-group form-groupx form-detail-action \"";

            htmlContent = XMLUtilities.parseHTML(url, beginSign, endSign);
            htmlContent = StringUtilities.refineHtml(htmlContent);
            XMLEventReader reader = XMLUtilities.getXMLEventReaderFromString(htmlContent);

            boolean isInsideOption = false;
            boolean isCollection = false;

            if (currentCanvas.getName().contains("Bộ tranh")) {
                isCollection = true;
            }

            while (reader.hasNext()) {

                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    String tagName = element.getName().getLocalPart().trim();

                    if ("span".equals(tagName)) {
                        Attribute attribute = element.getAttributeByName(new QName("class"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("price product-price")) {
                                event = reader.nextEvent();
                                if (event.isCharacters()) {
                                    Characters characters = event.asCharacters();
                                    price = characters.getData().trim();
                                    price = price.substring(0, price.length() - 1);
                                    price = price.replace(".", "").replace(",", "");
                                    
                                    if (isCollection) { // "Bộ tranh" => chỉ lấy price => break
                                        listDetail.add(new Detail(StringUtilities.convertStringToBigInteger(price)));
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if ("select".equals(tagName)) {
                        Attribute attribute = element.getAttributeByName(new QName("id"));
                        if (attribute != null) {
                            if (attribute.getValue().equals("product-selectors")) {
                                isInsideOption = true;
                            }
                        }
                    }

                    if ("option".equals(tagName)) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            Characters characters = event.asCharacters();
                            listDetail.add(extractDetailFromString(characters.getData().trim()));
                        }
                    }
                } // end startElement

                if (event.isEndElement() && isInsideOption) {
                    EndElement element = event.asEndElement();
                    String tagName = element.getName().getLocalPart().trim();
                    if ("select".equals(tagName)) {
                        isInsideOption = false;
                        break;
                    }
                }
            } // end while reader
            
            // update canvas field
            currentCanvas.setDetail(listDetail);
            String designer = getDesignerFromName(currentCanvas.getName());
            if (!designer.isEmpty()) {
                currentCanvas.setDesigner(designer);
            }
        } // end for each canvas

        category.setCanvas(listCanvas);
        
        return category;

//        ArrayList<Categories> listCategorieses = new ArrayList();
//        listCategorieses.add(category);
//
//        Painting painting = new Painting();
//        painting.setCategories(listCategorieses);
//       
//
//        XMLUtilities.saveToXML("test.xml", painting);
//        XMLUtilities.validateXMLBeforeSaveToDatabase("test.xml", painting);
//        
//        System.out.println("Category: " + categoryName + " has " + listCanvas.size() + " canvas");
//        System.out.println("--------------------------------------------------");
    }
    
    private String getDesignerFromName(String name) {
        if (name.contains("-")) {
            return name.substring(name.lastIndexOf("-") + 1).trim();
        } else if (name.contains("/")) {
            return name.substring(name.lastIndexOf("/") + 1).trim();
        }
        return "";
    }

    private List<Canvas> crawlListCanvasEachPage(String htmlContent) 
            throws UnsupportedEncodingException, XMLStreamException {
//            try {
//                BufferedWriter writer = new BufferedWriter(new FileWriter("forDebug.txt"));
//                writer.append(htmlContent);
//                writer.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        XMLEventReader reader = XMLUtilities.getXMLEventReaderFromString(htmlContent);
        boolean isInside = false;
        List<Canvas> listCanvas = new ArrayList<>();
        Canvas canvas = null;

        while (reader.hasNext()) {

            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                String tagName = element.getName().getLocalPart().trim();

                if ("div".equals(tagName)) {
                    Attribute attribute = element.getAttributeByName(new QName("class"));
                    if (attribute != null) {
                        if (attribute.getValue().equals("product-thumbnail")) {
                            isInside = true;
                            canvas = new Canvas();
                        } else if (attribute.getValue().equals("text-xs-center")) {
                            break;
                        }
                    }
                } // end if div 

                if ("a".equals(tagName) && isInside) {
                    Attribute att = element.getAttributeByName(new QName("href"));
                    if (att != null) {
                        canvas.setUrl(StringUtilities.HOST_SOYN + att.getValue());
                    }
                    att = element.getAttributeByName(new QName("title"));
                    if (att != null) {
                        canvas.setName(att.getValue());
                    }
                }

                if ("img".equals(tagName) && isInside) {
                    Attribute att = element.getAttributeByName(new QName("src"));
                    if (att != null) {
                        canvas.setImage("https:" + att.getValue());
                    }
                    isInside = false;
                    listCanvas.add(canvas);
                }
            } // end if start
        } // end while reader
        reader.close();
        return listCanvas;
    }

    private void getPageCount(String url) {
        try {
            String beginSign = "class=\"pagination clearfix \"";
            String endSign = "</ul>";

            boolean isInside = false;
            int count = 0;

            try (BufferedReader reader = getBufferReaderFromURI(url)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    updatePageCount(line);
                    if (line.contains(beginSign)) {
                        if (count == 0) {
                            isInside = true;
                        }
                        count++;
                    }
                    if (line.contains(endSign)) {
                        if (isInside) {
                            break;
                        }
                    }
                    if (isInside) {
                        updatePageCount(line);
                    }
                } // end while
            }
        } catch (IOException e) {
            Logger.getLogger(SoynPageCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void updatePageCount(String line) {
        String key = "page-link";

        if (line.contains(key)) {
            line = line.substring(line.indexOf(key));

            int begin = line.indexOf(">") + 1;
            String countPage = line.substring(begin);
            int end = countPage.indexOf("<");
            countPage = countPage.substring(0, end);

            if (!countPage.equals("...")) {
                int currentCount = Integer.parseInt(countPage);
                this.pageCount = Math.max(currentCount, pageCount);
            }
        }
    }

    private Detail extractDetailFromString(String s) {
        String size = s.substring(0, s.indexOf("-")).trim();
        String unit = size.substring(size.length() - 2, size.length());
        String length = size.substring(size.indexOf("x") + 1, size.length() - 2);
        String width = size.substring(0, size.indexOf("x"));
        String price = s.substring(s.indexOf("-") + 2, s.length() - 1);
        price = price.replace(".", "").replace(",", "");
        return new Detail(
                StringUtilities.convertStringToBigInteger(width), 
                StringUtilities.convertStringToBigInteger(length), 
                unit, 
                StringUtilities.convertStringToBigInteger(price)
        );
    }

}
