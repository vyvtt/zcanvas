/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler.framec;

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
import project.crawler.MainCrawler;
import project.crawler.soyn.SoynCategoriesCrawler;
import project.crawler.soyn.SoynMainCrawler;
import project.crawler.soyn.SoynPageCrawler;
import project.jaxb.Categories;
import project.jaxb.Painting;
import project.utils.Constant;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class FramecMainCrawler implements Serializable {

    public static void crawl() {
        try {
            // Crawl list categories
            FramecCategoriesCrawler categoriesCrawler = new FramecCategoriesCrawler();
            Map<String, String> categories = categoriesCrawler.crawlCategories();

            Painting painting = new Painting();

            for (Map.Entry<String, String> category : categories.entrySet()) {
                
                FramecPageCrawler crawler = new FramecPageCrawler(category.getKey(), category.getValue());

                List<Categories> c = new ArrayList<>();
                Categories crawlCategory = crawler.crawlEachPage();
                c.add(crawlCategory);
                painting.setCategories(c);

                XMLHelper.object2XMLFile(Constant.OUTPUT_XML_FRAMEC, painting);
                XMLHelper.validateXMLBeforeSaveToDatabase(Constant.OUTPUT_XML_FRAMEC, painting);
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
