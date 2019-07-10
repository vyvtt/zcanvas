/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler.framec;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import project.utils.Constant;
import project.utils.StringHelper;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class FramecCategoriesCrawler implements Serializable {
    private String beginSign = "class=\"pl_menu\"";
    private String endSign = "class=\"pl_menu_title\"";

    public FramecCategoriesCrawler() {
    }
    
    public Map<String, String> crawlCategories()
            throws XMLStreamException, UnsupportedEncodingException {
        
        String url = Constant.HOST_FRAMEC + "danh-sach/tranh-khung-kinh";
        
        String htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
        htmlContent = StringHelper.refineHtml(htmlContent);
        Map<String, String> categories = new HashMap<>();
        
        XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                String tagName = element.getName().getLocalPart();

                if ("a".equals(tagName)) {
                    Attribute attHref = element.getAttributeByName(new QName("href"));
                    String linkCategory = attHref.getValue();
                    event = reader.nextEvent();
                    Characters c = event.asCharacters();
                    categories.put(linkCategory, c.getData());
                }
            } // end if start
        } // end while reader
        
        reader.close();

        return categories;
    }
}
