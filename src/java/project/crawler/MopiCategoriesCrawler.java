package project.crawler;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import project.utils.StringHelper;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class MopiCategoriesCrawler implements Serializable{

    private String beginSign = "id=\"menu-chon-chu-de\"";
    private String endSign = "</nav>";

    public MopiCategoriesCrawler() {
    }

    public Map<String, String> crawlCategories()
            throws XMLStreamException, UnsupportedEncodingException {
        String tmpUrl = "";
        Map<String, String> categories = new HashMap<>();

        String htmlContent = XMLHelper.parseHTML(StringHelper.HOST_MOPI, beginSign, endSign);
        htmlContent = StringHelper.refineHtml(htmlContent);
        
        XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                String tagName = element.getName().getLocalPart();

                if ("a".equals(tagName)) {
                    Attribute attHref = element.getAttributeByName(new QName("href"));
                    tmpUrl = attHref.getValue();
                    System.out.println("tmp: " + tmpUrl);
                    break;
                }
            } // end if start
        } // end while reader

        reader.close();

        beginSign = "class=\"product-categories\"";
        endSign = "id=\"ts_product_filter_by_color-3\"";
        htmlContent = "";
        htmlContent = XMLHelper.parseHTML(tmpUrl, beginSign, endSign);
        htmlContent = StringHelper.refineHtml(htmlContent);
        
        reader = XMLHelper.getXMLEventReaderFromString(htmlContent);
        
        boolean isParent = true; // chỉ lấy parent, ko lấy các url children bên trong
        String categoryName, categoryLink = "";
        
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                String tagName = element.getName().getLocalPart();
                
                if ("ul".equals(tagName)) {
                    Attribute att = element.getAttributeByName(new QName("class"));
                    if (att != null) {
                        if ("children".equals(att.getValue().trim())) {
                            isParent = false;
                        }
                    }
                }

                if ("a".equals(tagName) && isParent) {
                    Attribute attHref = element.getAttributeByName(new QName("href"));
                    categoryLink = attHref.getValue();
                    event = reader.nextEvent(); // chuyển đến thân thẻ a
                    Characters c = event.asCharacters();
                    categoryName = c.getData();
                    
                    categories.put(categoryLink, categoryName.substring(0, categoryName.indexOf("(")).trim());
                }
            } // end if start
            
            if (event.isEndElement()) {
                EndElement element = event.asEndElement();
                String tagName = element.getName().getLocalPart();
                
                if ("ul".equals(tagName)) {
                    isParent = true;
                }
            }
        } // end while reader
        reader.close();
        
        categories.remove("https://mopi.vn/bo-suu-tap/mopi-home-category/");
        categories.remove("https://mopi.vn/bo-suu-tap/mopi-life-category/");
        return categories;
    }
}