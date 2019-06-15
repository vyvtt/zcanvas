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
import project.utils.XMLUtilities;

/**
 *
 * @author thuyv
 */
public class SoynThread extends BaseThread implements Runnable {

    @Override
    public void run() {

        try {
            while (true) {
                SoynCategoriesCrawler categoriesCrawler = new SoynCategoriesCrawler();
                Map<String, String> categories = categoriesCrawler.getCategories();
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
                    SoynCrawler crawler = new SoynCrawler(category.getKey(), category.getValue());

                    // test -------------------
                    Painting p = new Painting();
                    List<Categories> c = new ArrayList<>();
                    Categories crawlCategory = crawler.crawl();
                    c.add(crawlCategory);
                    p.setCategories(c);
                    XMLUtilities.saveToXML("test" + test + ".xml", p);
                    test++;
                    // done test --------------
                    
                    listCategoriesCrawl.add(crawlCategory);
                }
                painting.setCategories(listCategoriesCrawl);

                int total = 0;
                for (int i = 0; i < listCategoriesCrawl.size(); i++) {
                    total += listCategoriesCrawl.get(i).getCanvas().size();
                    System.out.println("Category: " + listCategoriesCrawl.get(i).getName());
                    System.out.println("Canvas  : " + listCategoriesCrawl.get(i).getCanvas().size());
                }

                System.out.println("Total " + total + " canvas in " + listCategoriesCrawl.size());

                XMLUtilities.saveToXML("test.xml", painting);
                XMLUtilities.validateXMLBeforeSaveToDatabase("test.xml", painting);

                return;
            }
        } catch (UnsupportedEncodingException | XMLStreamException e) {
            Logger.getLogger(SoynThread.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

//        while (true) {
//            try {
//                SoynCategoriesCrawler categoriesCrawler = new SoynCategoriesCrawler();
//                Map<String, String> categories = categoriesCrawler.getCategories();
//                
//                System.out.println("Done categories ----");
//                categories.entrySet().forEach((category) -> {
//                    System.out.println(category.getKey() + " - " + category.getValue());
//                });
//               
//                System.out.println("Bggin page ---");
//                for (Map.Entry<String,String> category : categories.entrySet()) {
//                    System.out.println(category.getKey() + " - " + category.getValue());
//                    SoynCrawler crawler = new SoynCrawler(category.getKey(), category.getValue());
//                    crawler.proccess();
//
//                    break;
//                }
//                return;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
    }

}
