/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static project.utils.XMLUtilities.getBufferReaderFromURI;

/**
 *
 * @author thuyv
 */
public class SoynCrawler implements Runnable {

    private String url;
    private String categoryName;

    @Override
    public void run() {
        if (categoryName == null) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, new Exception("NULL categoryName"));
            return;
        }

        try {
//            String htmlContent = "";
            String beginSign = "href=\"/tranh-treo-tuong\"";
            String endSign = "</ul>";

            boolean isInside = false;
            int count = 0;
            BufferedReader reader = getBufferReaderFromURI(url);

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(beginSign)) {
                    if (count == 0) {
                        isInside = true;
                    }
                    count++;
                }
                if (line.contains(endSign)) {
                    isInside = false;
                }
                if (isInside) {
//                    htmlContent = htmlContent + line;
                    getPageCount(line);
                }
                reader.close();
            }
        } catch (Exception e) {
        }

        // lấy tổng số trang của category hiện tại
    }

    private int getPageCount(String line) {
        String key = "page-link";
        int pageCount = -1;

        if (line.contains(key)) {
            line = line.substring(line.indexOf(key));

            int begin = line.indexOf(">") + 1;
            String countPage = line.substring(begin);
            int end = countPage.indexOf("<");
            countPage = countPage.substring(0, end);

            if (!countPage.equals("...")) {
                int currentCount = Integer.parseInt(countPage);
                pageCount = Math.max(currentCount, pageCount);
            }
        }
        return pageCount;
    }

}
