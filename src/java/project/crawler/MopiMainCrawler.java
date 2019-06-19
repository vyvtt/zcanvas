/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import project.jaxb.Categories;
import project.jaxb.Painting;
import project.utils.Constant;
import project.utils.StringHelper;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class MopiMainCrawler implements Serializable{
    public static void crawl() {
        try {
            
            // Get host from config file
            StringHelper.HOST_MOPI = StringHelper.getHostFromConfigFile(Constant.XML_CONFIG_MOPI);
            
            // Crawl list categories
            MopiCategoriesCrawler categoriesCrawler = new MopiCategoriesCrawler();
            Map<String, String> categories = categoriesCrawler.crawlCategories();
            
            Painting painting = new Painting();
            List<Categories> listCategoriesCrawl = new ArrayList<>();

            int test = 0;
            for (Map.Entry<String, String> category : categories.entrySet()) {
                
//                if (test==0 || test == 1 || test == 2) {
//                    test++;
//                    continue;
//                }
                System.out.println(category.getKey() + " - " + category.getValue());
                
                MopiPageCrawler crawler = new MopiPageCrawler(category.getKey(), category.getValue());
                
                // test -------------------
                Painting p = new Painting();
                List<Categories> c = new ArrayList<>();
                Categories crawlCategory = crawler.crawlEachPage();
                c.add(crawlCategory);
                p.setCategories(c);
                XMLHelper.saveToXML("test_mopi_" + test + ".xml", p);
                XMLHelper.validateXMLBeforeSaveToDatabase("test_mopi_" + test + ".xml", p);
                System.out.println("////////////////////////////");
                // done test --------------
                listCategoriesCrawl.add(crawlCategory);
                
                test++;
            }
            
            painting.setCategories(listCategoriesCrawl);

            // test -------------------
            int total = 0;
            for (int i = 0; i < listCategoriesCrawl.size(); i++) {
                total += listCategoriesCrawl.get(i).getCanvas().size();
                System.out.println("Category: " + listCategoriesCrawl.get(i).getName());
                System.out.println("Canvas  : " + listCategoriesCrawl.get(i).getCanvas().size());
            }
            System.out.println("Total " + total + " canvas in " + listCategoriesCrawl.size());
            // done test --------------

            XMLHelper.saveToXML(Constant.OUTPUT_XML_MOPI, painting);
            XMLHelper.validateXMLBeforeSaveToDatabase(Constant.OUTPUT_XML_MOPI, painting);

//            Painting painting = new Painting();
//            List<Categories> listCategoriesCrawl = new ArrayList<>();
//
//            int test = 0;
//            for (Map.Entry<String, String> category : categories.entrySet()) {
//                System.out.println(category.getKey() + " - " + category.getValue());
//                MopiPageCrawler crawler = new MopiPageCrawler(category.getKey(), category.getValue());
//
//                // test -------------------
//                Painting p = new Painting();
//                List<Categories> c = new ArrayList<>();
//                Categories crawlCategory = crawler.crawlEachPage();
//                c.add(crawlCategory);
//                p.setCategories(c);
//                XMLUtilities.saveToXML("test" + test + ".xml", p);
//                XMLUtilities.validateXMLBeforeSaveToDatabase("test" + test + ".xml", painting);
//                test++;
//                // done test --------------
//
//                listCategoriesCrawl.add(crawlCategory);
//            }
//            painting.setCategories(listCategoriesCrawl);
//
//            // test -------------------
//            int total = 0;
//            for (int i = 0; i < listCategoriesCrawl.size(); i++) {
//                total += listCategoriesCrawl.get(i).getCanvas().size();
//                System.out.println("Category: " + listCategoriesCrawl.get(i).getName());
//                System.out.println("Canvas  : " + listCategoriesCrawl.get(i).getCanvas().size());
//            }
//            System.out.println("Total " + total + " canvas in " + listCategoriesCrawl.size());
//            // done test --------------
//
//            XMLUtilities.saveToXML(Constant.OUTPUT_XML_SOYN, painting);
//            XMLUtilities.validateXMLBeforeSaveToDatabase(Constant.OUTPUT_XML_SOYN, painting);
        } catch (UnsupportedEncodingException | XMLStreamException e) {
            Logger.getLogger(SoynMainCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
