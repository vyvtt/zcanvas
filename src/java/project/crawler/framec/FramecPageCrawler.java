/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler.framec;

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
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import project.jaxb.Canvas;
import project.jaxb.Categories;
import project.jaxb.Detail;
import project.utils.Constant;
import project.utils.CrawlHelper;
import project.utils.StringHelper;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class FramecPageCrawler {

    private String url;
    private String categoryName;
    private int pageCount;
    private String htmlContent;

    private boolean hasNext;
    private String nextPageURL;

    public FramecPageCrawler(String url, String categoryName) {
        this.url = url;
        this.categoryName = categoryName;

        pageCount = 1;
        htmlContent = "";

        hasNext = true;
        nextPageURL = "";
    }

    public Categories crawlEachPage()
            throws UnsupportedEncodingException, XMLStreamException {
        if (categoryName == null) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, new Exception("NULL categoryName"));
            return null;
        }

        System.out.println("Crawl Category: " + categoryName + " - " + url + " - ");

        // ---------------------------------
        // Crawl products of each page
        Categories category = new Categories();
        category.setName(categoryName);
        List<Canvas> listCanvas = new ArrayList<>();

        while (hasNext) {
            String beginSign = "class=\"page_list_center\"";
            String endSign = "type=\"text/javascript\"";

            System.out.println("Get products from: " + url);

            htmlContent = "";
            htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
            htmlContent = StringHelper.refineHtml(htmlContent);

            hasNext = false;
            listCanvas.addAll(crawlListCanvasEachPage(htmlContent));
            
            if (hasNext) {
                url = nextPageURL;
            }
        }
        
        System.out.println("total: " + listCanvas.size());
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
                        if (attribute.getValue().equals("itemProductHome")) {
                            // bắt đầu 1 product
                            isInside = true;
                            canvas = new Canvas();
                        }
                    }
                } // end if div 

                if ("a".equals(tagName) && isInside) {
                    Attribute att = element.getAttributeByName(new QName("class"));
                    if (att != null) {
                        if (att.getValue().equals("title")) {
                            att = element.getAttributeByName(new QName("href"));
                            if (att != null) {
                                canvas.setUrl(att.getValue());

                                event = reader.nextEvent();
                                if (event.isCharacters()) {
                                    Characters characters = event.asCharacters();
                                    canvas.setName(characters.getData().trim());
                                    isInside = false; // sau khi lấy img thì kết thúc product này, chuyển sang product mới
                                    listCanvas.add(canvas);
                                }

                            }
                        } else if (att.getValue().equals("img")) {
                            att = element.getAttributeByName(new QName("style"));
                            if (att != null) {
                                String tmp = att.getValue();
                                canvas.setImage(tmp.substring(tmp.indexOf("(") + 1, tmp.indexOf(")")));
                            }
                        }
                    }
                } // end if <a> inside
                
                if ("a".equals(tagName) && !isInside) {
                    Attribute att = element.getAttributeByName(new QName("class"));
                    if (att != null) {
                        if (att.getValue().equals("trang-sau")) {
                            hasNext = true;
                            
                            att = element.getAttributeByName(new QName("href"));
                            nextPageURL = att.getValue();
                        }
                    }
                }
            } // end if start
        } // end while reader
        reader.close();
        return listCanvas;
    }
}
