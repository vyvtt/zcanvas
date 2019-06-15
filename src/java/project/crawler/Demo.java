/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thuyv
 */
public class Demo {

    public static void main(String[] args) {
        
//        String s = "30x45cm - 210.000₫";
//        
//        String size = s.substring(0, s.indexOf("-")).trim();
//        String unit = size.substring(size.length() - 2, size.length());
//        String length = size.substring(size.indexOf("x") + 1, size.length() - 2);
//        String width = size.substring(0, size.indexOf("x"));
//        
//        System.out.println(size);
//        System.out.println(unit);
//        System.out.println(length);
//        System.out.println(width);
//        
//        String price = s.substring(s.indexOf("-") + 2, s.length() - 1);
//        
//        System.out.println(price);
        

        Thread thread = new Thread(new SoynThread());
        thread.start();

//        String uri = "https://www.sephora.com/shop/facial-treatments";
//        String uri = "https://www.paulaschoice.com/ingredient-dictionary";
//        String uri = "https://soyncanvas.vn/tranh-treo-tuong";
//        String uri = "https://soyncanvas.vn";
//        String beginSign = "class=\"products-view products-view-grid\"";
//        String endSign = "class=\"text-xs-center\"";
//        String beginSign = "href=\"/tranh-treo-tuong\"";
//        String endSign = "</ul>";
//
//        Crawler.parseHTML_getPageCount(uri, beginSign, endSign);
//        String oldContent = Crawler.htmlContent;
//        String newContent = StringUtilities.fixString(oldContent);
//        try {
//            Map<String,String> listCategories = Crawler.getCategories(newContent);
//            System.out.println(listCategories);
//        System.out.println("-------------------------------------");
//        int pageCount = Crawler.pageCount;
//        System.out.println(pageCount);
//        
//        System.out.println(Crawler.htmlContent);
//        
//        System.out.println("-------------------------------------");
//        
//        String oldContent = Crawler.htmlContent;
//        String newContent = StringUtilities.fixString(oldContent);
//        System.out.println(newContent);
//        Crawler.parseHTML(uri, beginSign, endSign);
//        System.out.println(Crawler.htmlContent);
//        } catch (XMLStreamException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
