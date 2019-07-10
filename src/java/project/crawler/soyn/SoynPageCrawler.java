/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler.soyn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
import project.utils.Constant;
import project.utils.CrawlHelper;
import project.utils.StringHelper;
import project.utils.XMLHelper;
import static project.utils.XMLHelper.getBufferReaderFromURI;

/**
 *
 * @author thuyv
 */
public class SoynPageCrawler implements Serializable {

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
            throws UnsupportedEncodingException, XMLStreamException {
        if (categoryName == null) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, new Exception("NULL categoryName"));
            return null;
        }

        // ---------------------------------
        // Get total pages of this category 
        String beginSign = "class=\"pagination clearfix \"";
        String endSign = "</ul>";
        String keyOfLineContainPageCount = "page-link";
        pageCount = CrawlHelper.getPageCount(url, beginSign, endSign, keyOfLineContainPageCount);

        System.out.println("Crawl Category: " + categoryName + " - " + url + " - " + pageCount);

        // ---------------------------------
        // Crawl products of each page
        Categories category = new Categories();
        category.setName(categoryName);
        List<Canvas> listCanvas = new ArrayList<>();

        for (int i = 0; i < pageCount; i++) {

            String pageUrl = url + "?page=" + (i + 1);
            beginSign = "class=\"products-view products-view-grid\"";
            endSign = "class=\"sidebar left left-content col-lg-3 col-lg-pull-9\"";

            System.out.println("Get products from: " + pageUrl);

            htmlContent = "";
            htmlContent = XMLHelper.parseHTML(pageUrl, beginSign, endSign);
            htmlContent = StringHelper.refineHtml(htmlContent);

            listCanvas.addAll(crawlListCanvasEachPage(htmlContent));
        } // end for page

//        System.out.println("--- Done getEachPage - Begin get detail of " + listCanvas.size());
        // ---------------------------------
        // Crawl each product to get detail
//        listCanvas = crawlCanvasDetail(listCanvas);
        
        
        
//        for (int i = 0; i < listCanvas.size(); i++) {
//            List<Detail> listDetail = new ArrayList<>();
//            Canvas currentCanvas = listCanvas.get(i);
//            String price = "";
//
//            url = currentCanvas.getUrl();
//            System.out.println((i + 1) + " - " + url);
//
//            htmlContent = "";
//            beginSign = "class=\"price-box\"";
//            endSign = "class=\"form-group form-groupx form-detail-action \"";
//
//            htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
//            htmlContent = StringHelper.refineHtml(htmlContent);
//            XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);
//
//            boolean isInsideOption = false;
//            boolean isCollection = false;
//
//            if (currentCanvas.getName().contains("Bộ tranh")) {
//                isCollection = true;
//            }
//
//            while (reader.hasNext()) {
//
//                XMLEvent event = reader.nextEvent();
//
//                if (event.isStartElement()) {
//                    StartElement element = event.asStartElement();
//                    String tagName = element.getName().getLocalPart().trim();
//
//                    if ("span".equals(tagName)) {
//                        Attribute attribute = element.getAttributeByName(new QName("class"));
//                        if (attribute != null) {
//                            if (attribute.getValue().equals("price product-price")) {
//                                event = reader.nextEvent();
//                                if (event.isCharacters()) {
//                                    Characters characters = event.asCharacters();
//                                    price = characters.getData().trim();
//                                    price = price.substring(0, price.length() - 1);
//                                    price = price.replace(".", "").replace(",", "");
//
//                                    if (isCollection) { // "Bộ tranh" => chỉ lấy price => break
//                                        listDetail.add(new Detail(StringHelper.convertStringToBigInteger(price)));
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    if ("select".equals(tagName)) {
//                        Attribute attribute = element.getAttributeByName(new QName("id"));
//                        if (attribute != null) {
//                            if (attribute.getValue().equals("product-selectors")) {
//                                isInsideOption = true;
//                            }
//                        }
//                    }
//
//                    if ("option".equals(tagName)) {
//                        event = reader.nextEvent();
//                        if (event.isCharacters()) {
//                            Characters characters = event.asCharacters();
//                            listDetail.add(extractDetailFromString(characters.getData().trim()));
//                        }
//                    }
//                } // end startElement
//
//                if (event.isEndElement() && isInsideOption) {
//                    EndElement element = event.asEndElement();
//                    String tagName = element.getName().getLocalPart().trim();
//                    if ("select".equals(tagName)) {
//                        isInsideOption = false;
//                        break;
//                    }
//                }
//            } // end while reader
//
//            // update canvas field
//            currentCanvas.setDetail(listDetail);
//            String designer = getDesignerFromName(currentCanvas.getName());
//            if (!designer.isEmpty()) {
//                currentCanvas.setDesigner(designer);
//            }
//        } // end for each canvas
        category.setCanvas(listCanvas);

        return category;
    }

    private List<Canvas> crawlListCanvasEachPage(String htmlContent)
            throws UnsupportedEncodingException, XMLStreamException {

        XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);
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
                            // bắt đầu 1 product
                            isInside = true;
                            canvas = new Canvas();
                        } else if (attribute.getValue().equals("text-xs-center")) {
                            // kết thúc hết tất cả các product trong page này => thoát
                            break;
                        }
                    }
                } // end if div 

                if ("a".equals(tagName) && isInside) {
                    Attribute att = element.getAttributeByName(new QName("href"));
                    if (att != null) {
                        canvas.setUrl(Constant.HOST_SOYN + att.getValue());
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
                    isInside = false; // sau khi lấy img thì kết thúc product này, chuyển sang product mới
                    listCanvas.add(canvas);
                }
            } // end if start
        } // end while reader
        reader.close();
        return listCanvas;
    }

    private List<Canvas> crawlCanvasDetail(List<Canvas> listCanvas) 
            throws UnsupportedEncodingException, XMLStreamException{
        for (int i = 0; i < listCanvas.size(); i++) {
            List<Detail> listDetail = new ArrayList<>();
            Canvas currentCanvas = listCanvas.get(i);
            String price = "";

            url = currentCanvas.getUrl();
            System.out.println((i + 1) + " - " + url);

            htmlContent = "";
            String beginSign = "class=\"price-box\"";
            String endSign = "class=\"form-group form-groupx form-detail-action \"";

            htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
            htmlContent = StringHelper.refineHtml(htmlContent);
            XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);

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
                                        listDetail.add(new Detail(StringHelper.convertStringToBigInteger(price)));
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
        
        return listCanvas;
    }

    private Detail extractDetailFromString(String s) {
        String size = s.substring(0, s.indexOf("-")).trim();
        String unit = size.substring(size.length() - 2, size.length());
        String length = size.substring(size.indexOf("x") + 1, size.length() - 2);
        String width = size.substring(0, size.indexOf("x"));
        String price = s.substring(s.indexOf("-") + 2, s.length() - 1);
        price = price.replace(".", "").replace(",", "");
        return new Detail(
                StringHelper.convertStringToBigInteger(width),
                StringHelper.convertStringToBigInteger(length),
                unit,
                StringHelper.convertStringToBigInteger(price)
        );
    }

    private String getDesignerFromName(String name) {
        if (name.contains("-")) {
            return name.substring(name.lastIndexOf("-") + 1).trim();
        } else if (name.contains("/")) {
            return name.substring(name.lastIndexOf("/") + 1).trim();
        }
        return "";
    }

}
