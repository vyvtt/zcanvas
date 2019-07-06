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
    
    // Schema
    public static String SCHEMA_PAINTING = "/WEB-INF/document/painting.xsd";
    // XML output
    public static String OUTPUT_XML_SOYN = "/WEB-INF/document/output_soyn.xml";
    public static String OUTPUT_XML_MOPI = "/WEB-INF/document/output_mopi.xml";
    // Config file
    public static String XML_CONFIG_SOYN = "/WEB-INF/document/config-soyn.xml";
    public static String XML_CONFIG_MOPI = "/WEB-INF/document/config-mopi.xml";
    public static String XML_CONFIG_IMAGE = "/WEB-INF/document/config-image.xml";
    
    // Config HOST constant
    public static String HOST_SOYN = "";
    public static String HOST_MOPI = "";
    // Config IMAGE constant
    public static int IMG_DIMENTION_MAX;
    public static int IMG_BUCKET_PER_DIMENSION;
    public static int IMG_BUCKET_SIZE;
    public static int IMG_SKIP_PIXEL;
    public static int IMG_PALETTE_SIZE;
    public static int IMG_DELTA_E_TOLERANCE;
    public static int IMG_BG_TOLERANCE;
    
    // Servlet
    public static final String SERVLET_CRAWL = "CrawlServlet";
    public static final String SERVLET_HOME = "HomeServlet";
    public static final String SERVLET_PROCESS = "ProcessServlet";
    public static final String SERVLET_GET_CANVAS_MATCHING_IMG = "GetCanvasMatchingImageServlet";
    public static final String SERVLET_GET_LOCATION_CATEGORY = "GetLocationServlet";
    public static final String SERVLET_UPDATE_LOCATION = "UpdateLocationServlet";
    public static final String SERVLET_ADD_LOCATION = "AddLocationServlet";
    // JSP
    public static final String JSP_HOME = "home.jsp";
    
    

}
