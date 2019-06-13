/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.util.Map;

/**
 *
 * @author thuyv
 */
public class SoynThread extends BaseThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                SoynCategoriesCrawler categoriesCrawler = new SoynCategoriesCrawler();
                Map<String, String> categories = categoriesCrawler.getCategories();
            } catch (Exception e) {
            }

        }
    }

}
