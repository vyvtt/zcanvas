/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import static project.utils.Constant.*;

/**
 *
 * @author thuyv
 */
public class ConfigHelper {

    public static void configHost() {
        // Get host from config file
        HOST_SOYN = getHostConfigFromFile(XML_CONFIG_SOYN);
        HOST_MOPI = getHostConfigFromFile(XML_CONFIG_MOPI);
    }
    
    public static void configImage() {
        getImageConfigFromFile(XML_CONFIG_IMAGE);
    }
    
    public static void configRealPath(String realPath) {  
        REAL_PATH = realPath;
        
        SCHEMA_PAINTING = REAL_PATH + SCHEMA_PAINTING;
        
        OUTPUT_XML_SOYN = REAL_PATH + OUTPUT_XML_SOYN;
        OUTPUT_XML_MOPI = REAL_PATH + OUTPUT_XML_MOPI;
        
        XML_CONFIG_SOYN = REAL_PATH + XML_CONFIG_SOYN;
        XML_CONFIG_MOPI = REAL_PATH + XML_CONFIG_MOPI;
        XML_CONFIG_IMAGE = REAL_PATH + XML_CONFIG_IMAGE;
    }

    private static String getHostConfigFromFile(String filePath) {
        try {

            // parse file to DOM
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            // get XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            // get config
            String expression = "/host";
            String host = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            return host.trim();

        } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
            Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    private static void getImageConfigFromFile(String filePath) {
        try {
            // parse file to DOM
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            // get XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            
            // get config
            String expression = "/config/dimension-max";
            IMG_DIMENTION_MAX = ((Number)xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();
            
            expression = "/config/bucket-per-dimension";
            IMG_BUCKET_PER_DIMENSION = ((Number)xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();
            
            expression = "/config/skip-pixel";
            IMG_SKIP_PIXEL = ((Number)xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();
            
            expression = "/config/palette-size";
            IMG_PALETTE_SIZE = ((Number)xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();
            
            expression = "/config/delta-E-tolerance";
            IMG_DELTA_E_TOLERANCE = ((Number)xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();
            
            expression = "/config/background-tolerance";
            IMG_BG_TOLERANCE = ((Number)xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();
            
            IMG_BUCKET_SIZE = IMG_DIMENTION_MAX / IMG_BUCKET_PER_DIMENSION;
            
        } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
            Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
