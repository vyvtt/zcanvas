/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.crawler.SoynPageCrawler;
import static project.utils.XMLHelper.getBufferReaderFromURI;

/**
 *
 * @author thuyv
 */
public class CrawlHelper implements Serializable {

    public static int getPageCount(String categoryURL, String beginSign, String endSign, String key) {

        int result = 1;
        try {

            boolean isInside = false;
            int count = 0;

            try (BufferedReader reader = getBufferReaderFromURI(categoryURL)) {
                String line = null;
                while ((line = reader.readLine()) != null) {

                    if (line.contains(beginSign)) {
                        if (count == 0) {
                            isInside = true;
                        }
                        count++;
                    }
                    if (line.contains(endSign)) {
                        if (isInside) {
                            break;
                        }
                    }
                    if (isInside) {
                        if (line.contains(key)) {
                            result = updatePageCount(line, key, result);
                        }
                    }
                } // end while
            }
        } catch (IOException e) {
            Logger.getLogger(SoynPageCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }

    private static int updatePageCount(String line, String key, int previousCount) {

        line = line.substring(line.indexOf(key));

        int begin = line.indexOf(">") + 1;
        String countPage = line.substring(begin);
        int end = countPage.indexOf("<");
        countPage = countPage.substring(0, end);

        try {
            int currentCount = Integer.parseInt(countPage);
            return Math.max(currentCount, previousCount);
        } catch (NumberFormatException e) {
            Logger.getLogger(CrawlHelper.class.getName()).log(Level.INFO, "Can not parse page number: {0}", e.getMessage());
            return previousCount;
        }

//        if (!countPage.equals("...")) {
//            try {
//                int currentCount = Integer.parseInt(countPage);
//                return Math.max(currentCount, previousCount);
//            } catch (NumberFormatException e) {
//                Logger.getLogger(CrawlHelper.class.getName()).log(Level.INFO, "Can not parse page number: {0}", e.getMessage());
//                return previousCount;
//            }
//
//        }
//        return previousCount;
    }
}
