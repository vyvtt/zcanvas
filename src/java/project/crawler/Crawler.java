/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import project.utils.Constant;

/**
 *
 * @author thuyv
 */
public class Crawler extends BaseCrawler {

    public static String htmlContent = "";
    public static int pageCount = 0;

//    public static void parseHTML(String uri, String beginSign, String endSign) {
//        htmlContent = "";
//        boolean isInside = false;
//        int count = 0;
//
//        try {
//            URL url = new URL(uri);
//            URLConnection con = url.openConnection();
//            con
//                    .setRequestProperty("User-Agent",
//                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//            con.connect();
//
//            InputStream is = con.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//
//            String line = null;
//
//            while ((line = br.readLine()) != null) {
//                if (line.contains(beginSign)) {
//                    if (count == 0) {
//
//                        isInside = true;
//                    }
//                    count++;
//                }
//                if (line.contains(endSign)) {
//                    isInside = false;
//                }
//                if (isInside) {
//                    htmlContent = htmlContent + line;
//                }
//
//            }
//            is.close();
//        } catch (Exception e) {
//        }
//    }

//    public static void updatePageCount(String line) {
//        String key = "page-link";
//
//        if (line.contains(key)) {
//            line = line.substring(line.indexOf(key));
//
//            int begin = line.indexOf(">") + 1;
//            String countPage = line.substring(begin);
//            int end = countPage.indexOf("<");
//            countPage = countPage.substring(0, end);
//
//            if (!countPage.equals("...")) {
//                int currentCount = Integer.parseInt(countPage);
//                pageCount = Math.max(currentCount, pageCount);
//            }
//        }
//    }

//    public void parseHTML_getPageCount(String uri, String beginSign, String endSign, String filePath) {
//        htmlContent = "";
//        boolean isInside = false;
//        int count = 0;
//        BufferedReader reader = null;
//        BufferedWriter writer = null;
//
//        try {
//            writer = new BufferedWriter(new FileWriter(filePath, true));
//            reader = getBufferReaderFromURI(uri);
//            String line = null;
//
//            while ((line = reader.readLine()) != null) {
//                
//                writer.append(line + '\n');
//                updatePageCount(line);
//
//                if (line.contains(beginSign)) {
//                    if (count == 0) {
//                        isInside = true;
//                    }
//                    count++;
//                }
//                if (line.contains(endSign)) {
//                    isInside = false;
//                }
//                if (isInside) {
//                    htmlContent = htmlContent + line.trim();
//                }
//            }
//            
//            
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                reader.close();
//            }
//            if (writer != null) {
//                writer.close();
//            }
//        }
//    }

//    public Map<String, String> getCategories(String document)
//            throws XMLStreamException, UnsupportedEncodingException {
//        
//        document = document.trim();
//        XMLEventReader reader = getXMLEventReaderFromString(document);
//        Map<String, String> categories = new HashMap<>();
//
//        while (reader.hasNext()) {
//            XMLEvent event = reader.nextEvent();
//
//            if (event.isStartElement()) {
//                StartElement element = event.asStartElement();
//                System.out.println("startElement: " + element.getName());
//                String tagName = element.getName().getLocalPart();
//
//                if ("a".equals(tagName)) {
//                    Attribute attHref = element.getAttributeByName(new QName("href"));
//                    String linkCategory = Constant.HOST_SOYN + attHref.getValue();
//                    event = reader.nextEvent();
//                    Characters c = event.asCharacters();
//                    categories.put(linkCategory, c.getData());
//                }
//            } // end if start
//        } // end while reader
//
//        return categories;
//    }
}
