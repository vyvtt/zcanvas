/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;
import project.utils.Constant;
import project.utils.StringUtilities;
import project.utils.XMLUtilities;

/**
 *
 * @author thuyv
 */
public class SoynCategoriesCrawler extends BaseCrawler {

    private String beginSign = "href=\"/tranh-treo-tuong\"";
    private String endSign = "</ul>";
    private XMLEventAllocator allocator;

    public SoynCategoriesCrawler() {
    }

    public Map<String, String> getCategories()
            throws XMLStreamException, UnsupportedEncodingException {
        
        String htmlContent = XMLUtilities.parseHTML(Constant.HOST_SOYN, beginSign, endSign, "soyn_categories.txt");
        htmlContent = StringUtilities.fixString(htmlContent);
        
        htmlContent = htmlContent.trim();
        Map<String, String> categories = new HashMap<>();
        
        // Kết hợp cursor và iterator để duyệt
//        XMLInputFactory factory = XMLInputFactory.newFactory();
//        factory.setEventAllocator(new XMLEventAllocatorImpl());
//        allocator = factory.getEventAllocator();
//        byte[] byteArray = htmlContent.getBytes("UTF-8");
//        InputStream is = new ByteArrayInputStream(byteArray);
//        XMLStreamReader reader = factory.createXMLStreamReader(is);
//        int eventType = reader.getEventType();
//        
//        while (reader.hasNext()) {
//            eventType = reader.next();
//            if (eventType == XMLStreamConstants.START_ELEMENT) {
//                StartElement element = getXMLEvent(reader).asStartElement();
//                
//                String tagName = element.getName().getLocalPart();
//
//                if ("a".equals(tagName)) {
//                    Attribute attHref = element.getAttributeByName(new QName("href"));
//                    String linkCategory = Constant.HOST_SOYN + attHref.getValue();
//                    eventType = reader.next();
//                    Characters c = getXMLEvent(reader).asCharacters();
//                    categories.put(linkCategory, c.getData());
//                }
//            }
//        }
        
        XMLEventReader reader = XMLUtilities.getXMLEventReaderFromString(htmlContent);

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                String tagName = element.getName().getLocalPart();

                if ("a".equals(tagName)) {
                    Attribute attHref = element.getAttributeByName(new QName("href"));
                    String linkCategory = Constant.HOST_SOYN + attHref.getValue();
                    event = reader.nextEvent();
                    Characters c = event.asCharacters();
                    categories.put(linkCategory, c.getData());
                }
            } // end if start
        } // end while reader

        return categories;
    }
    
    private XMLEvent getXMLEvent(XMLStreamReader reader) throws XMLStreamException {
        return allocator.allocate(reader);
    }
}
