/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    private static final String KEY_B_CATEGORY = "bCate";
    private static final String KEY_E_CATEGORY = "eCate";
    private static final String KEY_B_COUNT = "bCount";
    private static final String KEY_E_COUNT = "eCount";
    private static final String KEY_B_PAGE = "bPage";
    private static final String KEY_E_PAGE = "ePage";

    public static void configHost() {
        // Get host from config file
        HOST_SOYN = getHostConfigFromFile(XML_CONFIG_SOYN);
        HOST_MOPI = getHostConfigFromFile(XML_CONFIG_MOPI);
        HOST_FRAMEC = getHostConfigFromFile(XML_CONFIG_FRAMEC);
    }

    public static void configImage() {
        getImageConfigFromFile(XML_CONFIG_IMAGE);
    }

    public static void configCrawl() {
        Map<String, String> map = getCrawlConfigFromFile(XML_CONFIG_SOYN);
        SOYN_BEGIN_CATEGORY = map.get(KEY_B_CATEGORY);
        SOYN_END_CATEGORY = map.get(KEY_E_CATEGORY);
        SOYN_BEGIN_COUNT = map.get(KEY_B_COUNT);
        SOYN_END_COUNT = map.get(KEY_E_CATEGORY);
        SOYN_BEGIN_PAGE = map.get(KEY_B_PAGE);
        SOYN_END_PAGE = map.get(KEY_E_PAGE);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("-------------");

        map = getCrawlConfigFromFile(XML_CONFIG_MOPI);
        MOPI_BEGIN_CATEGORY = map.get(KEY_B_CATEGORY);
        MOPI_END_CATEGORY = map.get(KEY_E_CATEGORY);
        MOPI_BEGIN_COUNT = map.get(KEY_B_COUNT);
        MOPI_END_COUNT = map.get(KEY_E_CATEGORY);
        MOPI_BEGIN_PAGE = map.get(KEY_B_PAGE);
        MOPI_END_PAGE = map.get(KEY_E_PAGE);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public static void configRealPath(String realPath) {
        REAL_PATH = realPath;

        SCHEMA_PAINTING = REAL_PATH + SCHEMA_PAINTING;

        OUTPUT_XML_SOYN = REAL_PATH + OUTPUT_XML_SOYN;
        OUTPUT_XML_MOPI = REAL_PATH + OUTPUT_XML_MOPI;
        OUTPUT_XML_FRAMEC = REAL_PATH + OUTPUT_XML_FRAMEC;

        XML_CONFIG_SOYN = REAL_PATH + XML_CONFIG_SOYN;
        XML_CONFIG_MOPI = REAL_PATH + XML_CONFIG_MOPI;
        XML_CONFIG_FRAMEC = REAL_PATH + XML_CONFIG_FRAMEC;
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
            String expression = "/config/host";
            String host = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            return host.trim();

        } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
            Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    private static Map<String, String> getCrawlConfigFromFile(String filePath) {
        try {

            // parse file to DOM
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            // get XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            Map<String, String> map = new HashMap<>();
            String tmp = "";
            String expression = "";

            // get config
            expression = "/config/begin-category";
            tmp = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            map.put(KEY_B_CATEGORY, tmp.trim());

            expression = "/config/end-category";
            tmp = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            map.put(KEY_E_CATEGORY, tmp.trim());

            expression = "/config/begin-count";
            tmp = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            map.put(KEY_B_COUNT, tmp.trim());

            expression = "/config/end-count";
            tmp = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            map.put(KEY_E_COUNT, tmp.trim());

            expression = "/config/begin-page";
            tmp = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            map.put(KEY_B_PAGE, tmp.trim());

            expression = "/config/end-page";
            tmp = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            map.put(KEY_E_PAGE, tmp.trim());

            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println("-------------");

            return map;

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
            IMG_DIMENTION_MAX = ((Number) xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();

            expression = "/config/bucket-per-dimension";
            IMG_BUCKET_PER_DIMENSION = ((Number) xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();

            expression = "/config/skip-pixel";
            IMG_SKIP_PIXEL = ((Number) xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();

            expression = "/config/palette-size";
            IMG_PALETTE_SIZE = ((Number) xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();

            expression = "/config/delta-E-tolerance";
            IMG_DELTA_E_TOLERANCE = ((Number) xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();

            expression = "/config/delta-E-tolerance-single";
            IMG_DELTA_E_TOLERANCE_SINGLE = ((Number) xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();

            expression = "/config/background-tolerance";
            IMG_BG_TOLERANCE = ((Number) xPath.evaluate(expression, document, XPathConstants.NUMBER)).intValue();

            IMG_BUCKET_SIZE = IMG_DIMENTION_MAX / IMG_BUCKET_PER_DIMENSION;

        } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
            Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
