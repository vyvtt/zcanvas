package project.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.crawler.soyn.SoynPageCrawler;
import static project.utils.XMLHelper.getBufferReaderFromURI;

/**
 *
 * @author thuyv
 */
public class CrawlHelper implements Serializable {

    /**
     * Get total product's page of a category
     * @param categoryURL Category first page URL
     * @param beginSign Where info about pagination begin
     * @param endSign Where info about pagination end
     * @param key Sign of which line contain page number
     * @return Number of page count
     */
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

    /**
     * Parse a single line of HTML to get page number and compare to previous count number
     * @param line A Single line in HTML
     * @param key Sign of which line contain page number
     * @param previousCount Previous count number (for comparing)
     * @return 
     */
    private static int updatePageCount(String line, String key, int previousCount) {

        line = line.substring(line.indexOf(key));

        int begin = line.indexOf(">") + 1;
        String countPage = line.substring(begin);
        int end = countPage.indexOf("<");
        countPage = countPage.substring(0, end);
        
        countPage = countPage.toLowerCase();
        countPage = countPage.replace("trang", "").replace("page", "");
        countPage = countPage.trim();

        try {
            int currentCount = Integer.parseInt(countPage);
            return Math.max(currentCount, previousCount);
        } catch (NumberFormatException e) {
            Logger.getLogger(CrawlHelper.class.getName()).log(Level.INFO, "Can not parse page number: {0} -> skip this number", e.getMessage());
            return previousCount;
        }
    }
}
