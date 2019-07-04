/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
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
public class SoynMainCrawler implements Serializable {

    public static void crawl() {
        try {
            // Crawl list categories
            SoynCategoriesCrawler categoriesCrawler = new SoynCategoriesCrawler();
            Map<String, String> categories = categoriesCrawler.crawlCategories();
            categories.remove("https://soyncanvas.vn/tranh-treo-tuong");

            categories.entrySet().forEach((category) -> {
                System.out.println(category.getKey() + " - " + category.getValue());
            });

            Painting painting = new Painting();

            for (Map.Entry<String, String> category : categories.entrySet()) {

                System.out.println(category.getKey() + " - " + category.getValue());
                SoynPageCrawler crawler = new SoynPageCrawler(category.getKey(), category.getValue());

                List<Categories> c = new ArrayList<>();
                Categories crawlCategory = crawler.crawlEachPage();
                c.add(crawlCategory);
                painting.setCategories(c);
                
                XMLHelper.saveToXML(Constant.OUTPUT_XML_SOYN, painting);
                XMLHelper.validateXMLBeforeSaveToDatabase(Constant.OUTPUT_XML_SOYN, painting);
                System.out.println("----------------");

                try {
                    MainCrawler.saveToDB(c);
                } catch (IOException | SQLException | NamingException e) {
                    Logger.getLogger(SoynMainCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
                
                painting = new Painting();
            }

        } catch (UnsupportedEncodingException | XMLStreamException e) {
            Logger.getLogger(SoynMainCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
