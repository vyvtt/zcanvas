/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import project.utils.CrawlHelper;
import project.utils.StringHelper;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class MopiPageCrawler implements Serializable {

    private String url;
    private String categoryName;
    private int pageCount;
    private String htmlContent;

    public MopiPageCrawler(String url, String categoryName) {
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
        // Get page count of this category
        String beginSign = "class='page-numbers'"; // dấu nháy đơn
        String endSign = "</ul>";
        String keyOfLineContainPageCount = "<a class='page-numbers'";
        pageCount = CrawlHelper.getPageCount(url, beginSign, endSign, keyOfLineContainPageCount);

        System.out.println("In MopiPageCrawler " + categoryName + " - " + url + " - " + pageCount);

        // ---------------------------------
        // Crawl products of each page
        Categories category = new Categories();
        category.setName(categoryName);
        List<Canvas> listCanvas = new ArrayList<>();

        for (int i = 0; i < pageCount; i++) {
            String pageUrl = url + "page/" + (i + 1) + "/";
            beginSign = " class=\"products\"";
            endSign = "after-loop-wrapper";

            System.out.println("Get all products from: " + pageUrl);

            htmlContent = "";
            htmlContent = XMLHelper.parseHTML(pageUrl, beginSign, endSign);
            htmlContent = StringHelper.refineHtml(htmlContent);
            listCanvas.addAll(crawlListCanvasEachPage(htmlContent));
        } // end get all products of category


        System.out.println("--- Done get all products of category - Begin get detail of " + listCanvas.size());
        // ---------------------------------
        // Read each product page to get detail
        List<Integer> invalidProductPosition = new ArrayList<>();
        for (int i = 0; i < listCanvas.size(); i++) {

            List<Detail> listDetail = new ArrayList<>();
            Canvas currentCanvas = listCanvas.get(i);
            Map<String, String> sizeAndPrice = new HashMap<>();
            Map<String, String> sizeAndString = new HashMap<>();

            boolean isInsideOptionSize = false;
            boolean isEnding = false;
            boolean isValid = true;

            url = currentCanvas.getUrl();
            System.out.println((i + 1) + " - " + url);

            htmlContent = "";
            beginSign = "class=\"variations_form cart\"";
            endSign = "class=\"woocommerce-tabs wc-tabs-wrapper\"";

            htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
            htmlContent = StringHelper.refineHtml(htmlContent);
            XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    String tagName = element.getName().getLocalPart().trim();

                    if ("form".equals(tagName)) {
                        Attribute attribute = element.getAttributeByName(new QName("data-product_variations"));
                        if (attribute != null) {
                            String rawJson = attribute.getValue();
                            sizeAndPrice = getSizeAndPriceFromJSON(rawJson);

                            if (sizeAndPrice == null) {
                                isValid = false; // set listDetail = null về sau
                            }

                        }
                    } else if ("select".equals(tagName) && isValid) {
                        Attribute attribute = element.getAttributeByName(new QName("data-attribute_name"));
                        if (attribute != null) {
                            String attValue = attribute.getValue().trim();
                            if ("attribute_pa_size".equals(attValue)) {
                                isInsideOptionSize = true;
                            } else if ("attribute_kch-thc".equals(attValue)) {
                                isInsideOptionSize = true;
                            } else if ("attribute_kich-thuoc".equals(attValue)) {
                                isInsideOptionSize = true;
                            }
                        }
                    } else if ("option".equals(tagName) && isInsideOptionSize && isValid) {
                        Attribute attribute = element.getAttributeByName(new QName("value"));
                        if (attribute != null) {
                            String value = attribute.getValue().trim();
                            if (!value.isEmpty()) {
                                event = reader.nextEvent();
                                if (event.isCharacters()) {
                                    Characters characters = event.asCharacters();
                                    sizeAndString.put(value, characters.getData().trim());
                                }
                            }
                        }
                    } else if ("div".equals(tagName)) {
                        if (!isEnding) {
                            Attribute attribute = element.getAttributeByName(new QName("class"));
                            if (attribute != null) {
                                if ("tags-link".equals(attribute.getValue())) {
                                    isEnding = true;
                                }
                            }
                        } else { // <div>Thiết kế bởi: Thúy Nguyễn</div>
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                Characters characters = event.asCharacters();
                                String designer = characters.getData().trim();
                                try {
                                    currentCanvas.setDesigner(designer.substring(designer.indexOf(":") + 2));
                                } catch (Exception e) {
                                    // Exception trường hợp "Thúy Nguyễn" là link aka thẻ <a> nằm trong -> loại bỏ
                                    Logger.getLogger(MopiPageCrawler.class.getName()).log(Level.SEVERE, "Exception ---> Set DESIGNER of product to null and continue");
                                    Logger.getLogger(MopiPageCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                                    currentCanvas.setDesigner(null);
                                }
                                isEnding = false; // reset cờ
                            }
                        }
                    }
                } // end if event asStartElement
                else if (event.isEndElement()) {
                    EndElement element = event.asEndElement();
                    String tagName = element.getName().getLocalPart().trim();

                    if (isInsideOptionSize && tagName.equals("select")) {
                        // tắt biến cờ cho tag <select> <option> về size
                        isInsideOptionSize = false;
                    }
                }
            } // end while reader

            if (!isValid) { // parse sản phẩm bị lỗi
                currentCanvas.setDetail(null);
            } else {
                try {
//                    System.out.println(sizeAndPrice);
//                    System.out.println(sizeAndString);
                    for (Map.Entry<String, String> entry : sizeAndString.entrySet()) {
                        // {4560=45x60cm, 6090=60x90cm, 3040=30x40cm}
                        Detail detail = extractDetailFromString(entry.getValue());
                        String price = sizeAndPrice.get(entry.getKey());
                        detail.setPrice(StringHelper.convertStringToBigInteger(price));
                        listDetail.add(detail);
                    }
                    currentCanvas.setDetail(listDetail);
                } catch (Exception e) {
                    Logger.getLogger(MopiPageCrawler.class.getName()).log(Level.SEVERE, "Exception ---> Set DETAIL of product to null and continue");
                    Logger.getLogger(MopiPageCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                    currentCanvas.setDetail(null);
                }

            }
        } // end for each product

        category.setCanvas(listCanvas);

        return category;
    }

    private Detail extractDetailFromString(String s) {
        String unit = s.substring(s.length() - 2);
        String length = s.substring(s.indexOf("x") + 1, s.length() - 2);
        String width = s.substring(0, s.indexOf("x"));
        return new Detail(
                StringHelper.convertStringToBigInteger(width),
                StringHelper.convertStringToBigInteger(length),
                unit,
                null
        );
    }

    private Map<String, String> getSizeAndPriceFromJSON(String rawJson) {
//        System.out.println("in get price size ---------------");
//        System.out.println(to);
        
        rawJson = rawJson.replace("&amp;", "&")
                .replace("&quot;", "\"");

//        System.out.println(to);
        String key = "attribute_pa_size";
        if (!rawJson.contains(key)) {
            key = "attribute_kch-thc";
        }
        if (!rawJson.contains(key)) {
            key = "attribute_kich-thuoc";
        }
        if (rawJson.contains("\"attributes\":[]")) {
            // Exception: sản phẩm ko có size cụ thể -> ko thể lấy đc giá
            return null;
        }

        Map<String, String> sizeAndPrice = new HashMap<>();
        while (rawJson.contains(key)) {
            rawJson = rawJson.substring(rawJson.indexOf(key));
//            System.out.println("1 " + to);
            rawJson = rawJson.replaceFirst(key + "\":\"", "");
//            System.out.println("2 " + to);

            String size = rawJson.substring(0, rawJson.indexOf("\""));

            if (size == null || size.isEmpty()) {
                return null;
            }

            rawJson = rawJson.substring(rawJson.indexOf("display_price"));
//            System.out.println("3 " + to);
            rawJson = rawJson.replaceFirst("display_price\":", "");
//            System.out.println("4 " + to);

            String price = rawJson.substring(0, rawJson.indexOf(","));
            sizeAndPrice.put(size, price);
//            System.out.println("size: " + size);
//            System.out.println("price: " + price);
        }
        return sizeAndPrice;
    }

    private List<Canvas> crawlListCanvasEachPage(String htmlContent)
            throws UnsupportedEncodingException, XMLStreamException {

        XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);
        List<Canvas> listCanvas = new ArrayList<>();

        boolean isInside = false;
        boolean hasImg = false;
        boolean hasLink = false;
        Canvas canvas = null;
        
        while (reader.hasNext()) {

            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                String tagName = element.getName().getLocalPart().trim();

                if ("div".equals(tagName)) {
                    Attribute attribute = element.getAttributeByName(new QName("class"));
                    if (attribute != null) {
                        if (attribute.getValue().equals("product-wrapper")) {
                            // bắt đầu 1 product
                            isInside = true;
                            canvas = new Canvas();
                        }
                    }
                } else if ("a".equals(tagName) && isInside && !hasLink) {
                    // thẻ <a> đầu tiên sau khi isInside -> link
                    Attribute att = element.getAttributeByName(new QName("href"));
                    if (att != null) {
                        canvas.setUrl(att.getValue());
                        hasLink = true;
                    }
                } else if ("img".equals(tagName) && isInside && !hasImg) {
                    // thẻ <a> đầu tiên sau khi isInside -> img, chỉ lấy img đầu tiên
                    Attribute att = element.getAttributeByName(new QName("data-src"));
                    if (att != null) {
                        canvas.setImage(att.getValue());
                        hasImg = true;
                    }
                } else if ("h".equals(tagName)) {
                    event = reader.nextEvent(); // <a>
                    event = reader.nextEvent();

                    if (event.isCharacters()) {
                        Characters c = event.asCharacters();
                        canvas.setName(c.getData());
                        // reset các biến cờ
                        isInside = false;
                        hasImg = false;
                        hasLink = false;
                        listCanvas.add(canvas);
                    }
                }

//                // thẻ <a> đầu tiên sau khi isInside -> link
//                if ("a".equals(tagName) && isInside) {
//                    
//                }
//
//                // thẻ <a> đầu tiên sau khi isInside -> img, chỉ lấy img đầu tiên
//                if ("img".equals(tagName) && isInside && !hasImg) {
//                    
//                }
//                
//                if ("h3".equals(tagName)) {
//                    
//                }
            } // end if start
        } // end while reader
        reader.close();
        return listCanvas;
    }

}
