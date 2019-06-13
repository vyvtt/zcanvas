/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author thuyv
 */
public class BaseCrawler {

//    protected BufferedReader getBufferReaderFromURI(String uri) 
//            throws MalformedURLException, IOException {
//        URL url = new URL(uri);
//        URLConnection con = url.openConnection();
//        con.setRequestProperty("User-Agent",
//                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//        con.connect();
//        InputStream is = con.getInputStream();
//        return new BufferedReader(new InputStreamReader(is, "UTF-8"));
//    }
//    
//    protected XMLEventReader getXMLEventReaderFromString(String document) 
//            throws UnsupportedEncodingException, XMLStreamException {
//        
//        byte[] byteArray = document.getBytes("UTF-8");
//        InputStream is = new ByteArrayInputStream(byteArray);
//        XMLInputFactory factory = XMLInputFactory.newFactory();
//        return factory.createXMLEventReader(is);
//    }

}
