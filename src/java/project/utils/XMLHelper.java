package project.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import project.jaxb.Painting;

/**
 *
 * @author thuyv
 */
public class XMLHelper implements Serializable {

    /**
     * Get a UTF-8 BufferedReader from URI; setting System "https.protocols" property
     * @param uri
     * @return
     * @throws MalformedURLException
     * @throws IOException 
     */
    public static BufferedReader getBufferReaderFromURI(String uri)
            throws MalformedURLException, IOException {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("User-Agent", Constant.USER_AGENT);
        con.connect();
        InputStream is = con.getInputStream();
        return new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }

    /**
     * Get XMLEventReader (StAX) from a well-formed HTML String; set "javax.xml.stream.isCoalescing" = true
     * @param document
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException 
     */
    public static XMLEventReader getXMLEventReaderFromString(String document)
            throws UnsupportedEncodingException, XMLStreamException {

        byte[] byteArray = document.getBytes("UTF-8");
        InputStream is = new ByteArrayInputStream(byteArray);
        XMLInputFactory factory = XMLInputFactory.newFactory();
        // Khi event là Chracters có chứa "&" -> decode thành 1 String duy nhất
        // http://rama-palaniappan.blogspot.com/2011/05/be-careful-using-stax-parser.html
        factory.setProperty(XMLInputFactory.IS_COALESCING, true); 
        return factory.createXMLEventReader(is);
    }

    public static void validateXMLBeforeSaveToDatabase(String xmlFilePath, Painting painting) {
        try {
            JAXBContext context = JAXBContext.newInstance(painting.getClass());
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(Constant.SCHEMA_PAINTING));
            Validator validator = schema.newValidator();

            InputSource inputFile = new InputSource(new BufferedReader(new FileReader(xmlFilePath)));

            validator.validate(new SAXSource(inputFile));
            System.out.println("Done validate");
            
        } catch (IOException | JAXBException | SAXException e) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static String parseHTML(String uri, String beginSign, String endSign) {
        String htmlContent = "";
        boolean isInside = false;
        int count = 0;

        try {
            BufferedReader reader = getBufferReaderFromURI(uri);

            String line = null;
            while ((line = reader.readLine()) != null) {
                
                if (line.contains(beginSign)) {
                    if (count == 0) {
                        isInside = true;
                    }
                    count++;
                }
                if (line.contains(endSign)) {
                    isInside = false;
                }
                if (isInside) {
                    line = line.replaceAll("\\$", "");
                    line = line.replace("value=\"\">", ">");
                    line = line.replace("selected='selected'", "");
                    
                    StringBuffer result = new StringBuffer();
                    Matcher m = Pattern
                            .compile("(\\s+[\\w:.-]+=\")([^\"]*(?:\"(?!\\s+[\\w:.-]+=\"|\\s*(?:/?|\\?)>)[^\"]*)*)\"")
                            .matcher(line);
                    while (m.find()) {
                        m.appendReplacement(result, m.group(1) + m.group(2).replace("\"", "&quot;") + "\"");
                    }
                    m.appendTail(result);
                    String removeTab = result.toString().trim().replace("\t", "");
                    htmlContent = htmlContent + removeTab;
                }
            }
            reader.close();
        } catch (IOException e) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return htmlContent;
    }

    public static String parseHTML(String uri, String beginSign, String endSign, String filePath) {
        String htmlContent = "";
        boolean isInside = false;
        int count = 0;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            BufferedReader reader = getBufferReaderFromURI(uri);

            String line = null;
            while ((line = reader.readLine()) != null) {
                writer.append(line + '\n');
                if (line.contains(beginSign)) {
                    if (count == 0) {
                        isInside = true;
                    }
                    count++;
                }
                if (line.contains(endSign)) {
                    isInside = false;
                }
                if (isInside) {
                    htmlContent = htmlContent + line;
                }

                writer.close();
                reader.close();
            }
        } catch (IOException e) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return htmlContent;
    }
    
    public static <T> String object2XMLString(T obj) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller mar = jc.createMarshaller();
        StringWriter sw  = new StringWriter();
        mar.marshal(obj, sw);
        return sw.toString();
    }
    
    public static <T> void object2XMLFile(String filePath, T obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            marshaller.marshal(obj, new File(filePath));
        } catch (JAXBException e) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
