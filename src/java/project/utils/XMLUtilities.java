/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import static project.crawler.Crawler.pageCount;
import project.jaxb.Painting;

/**
 *
 * @author thuyv
 */
public class XMLUtilities implements Serializable{
    
    public static BufferedReader getBufferReaderFromURI(String uri) 
            throws MalformedURLException, IOException {
        URL url = new URL(uri);
        URLConnection con = url.openConnection();
        con.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
        InputStream is = con.getInputStream();
        return new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }
    
    public static XMLEventReader getXMLEventReaderFromString(String document) 
            throws UnsupportedEncodingException, XMLStreamException {
        
        byte[] byteArray = document.getBytes("UTF-8");
        InputStream is = new ByteArrayInputStream(byteArray);
        XMLInputFactory factory = XMLInputFactory.newFactory();
        return factory.createXMLEventReader(is);
    }
    
    public void saveToXML(String filePath, Painting painting) {
        try {
            JAXBContext context = JAXBContext.newInstance(painting.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(painting, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
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
            // TODO remove printStackTrace
            e.printStackTrace();
        }
        return htmlContent;
    }
}
