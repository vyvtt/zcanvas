/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

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
import project.utils.StringUtilities;
import project.utils.XMLUtilities;

/**
 *
 * @author thuyv
 */
public class SoynMainCrawler {

    public static void crawl() {
        try {
            // Get host from config file
            StringUtilities.HOST_SOYN = StringUtilities.getHostFromConfigFile(Constant.XML_CONFIG_SOYN);
            // Crawl list categories
            SoynCategoriesCrawler categoriesCrawler = new SoynCategoriesCrawler();
            Map<String, String> categories = categoriesCrawler.crawlCategories();
            categories.remove("https://soyncanvas.vn/tranh-treo-tuong");

            categories.entrySet().forEach((category) -> {
                System.out.println(category.getKey() + " - " + category.getValue());
            });
            System.out.println("Done categories ----");

            Painting painting = new Painting();
            List<Categories> listCategoriesCrawl = new ArrayList<>();

            int test = 0;
            for (Map.Entry<String, String> category : categories.entrySet()) {
                System.out.println(category.getKey() + " - " + category.getValue());
                SoynPageCrawler crawler = new SoynPageCrawler(category.getKey(), category.getValue());

                // test -------------------
                Painting p = new Painting();
                List<Categories> c = new ArrayList<>();
                Categories crawlCategory = crawler.crawlEachPage();
                c.add(crawlCategory);
                p.setCategories(c);
                XMLUtilities.saveToXML("test" + test + ".xml", p);
                XMLUtilities.validateXMLBeforeSaveToDatabase("test" + test + ".xml", painting);
                test++;
                // done test --------------

                listCategoriesCrawl.add(crawlCategory);
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

            XMLUtilities.saveToXML(Constant.OUTPUT_XML_SOYN, painting);
            XMLUtilities.validateXMLBeforeSaveToDatabase(Constant.OUTPUT_XML_SOYN, painting);
        } catch (UnsupportedEncodingException | XMLStreamException e) {
            Logger.getLogger(SoynMainCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
