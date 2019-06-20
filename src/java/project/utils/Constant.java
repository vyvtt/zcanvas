/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

/**
 *
 * @author thuyv
 */
public class Constant {
    
    
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    public static String REAL_PATH = "";
    
    public static String SCHEMA_PAINTING = "/WEB-INF/document/painting.xsd";
    
    public static String OUTPUT_XML_SOYN = "/WEB-INF/document/output_soyn.xml";
    public static String OUTPUT_XML_MOPI = "/WEB-INF/document/output_mopi.xml";
    
    public static String XML_CONFIG_SOYN = "/WEB-INF/document/soyn.xml";
    public static String XML_CONFIG_MOPI = "/WEB-INF/document/mopi.xml";
    
    public static void updateRealPath(String realPath) {
        
        REAL_PATH = realPath;
        
        SCHEMA_PAINTING = REAL_PATH + SCHEMA_PAINTING;
        
        OUTPUT_XML_SOYN = REAL_PATH + OUTPUT_XML_SOYN;
        OUTPUT_XML_MOPI = REAL_PATH + OUTPUT_XML_MOPI;
        
        XML_CONFIG_SOYN = REAL_PATH + XML_CONFIG_SOYN;
        XML_CONFIG_MOPI = REAL_PATH + XML_CONFIG_MOPI;
    }

}
