package project.crawler;

import project.crawler.mopi.MopiMainCrawler;
import project.crawler.soyn.SoynMainCrawler;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.naming.NamingException;
import project.crawler.framec.FramecMainCrawler;
import project.dao.CanvasDAO;
import project.dao.CategoryDAO;
import project.dao.DetailDAO;
import project.jaxb.Canvas;
import project.jaxb.Categories;
import project.miscellaneous.CategoriesHelper;
import project.utils.ImageHelper;

/**
 *
 * @author thuyv
 */
public class MainCrawler {

    public static void crawl() {
        long startTime = System.nanoTime();

        SoynMainCrawler.crawl();
        MopiMainCrawler.crawl();
        FramecMainCrawler.crawl();

        long endTime = System.nanoTime() - startTime;

        System.out.println("Crawl time: " + TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS) + " seconds");
    }

    public static void saveToDB(List<Categories> categories)
            throws IOException, NamingException, SQLException {

        CategoryDAO categoryDAO = new CategoryDAO();
        CanvasDAO canvasDAO = new CanvasDAO();
        DetailDAO detailDAO = new DetailDAO();

        for (Categories category : categories) {

            category.setName(CategoriesHelper.mappingCategoryName(category.getName()));
            int categoryId = categoryDAO.insert(category);

            List<Canvas> listCanvas = category.getCanvas();
            for (Canvas canvas : listCanvas) {
                try {
                    BufferedImage image = ImageIO.read(new URL(canvas.getImage()));
                    canvas.setColorPalatte(ImageHelper.getColorPaletteFromImage(image, false));
                } catch (IOException e) {
                    Logger.getLogger(MainCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                    canvas.setColorPalatte("");
                }

                int canvasId = canvasDAO.insert(canvas);

//                if (canvasDAO.isIsExisted()) {
//                    // Skip insert Detail
//                    System.out.println("DB ---> SKIP EXISTED: " + canvas.getName());
//                } else {
//                    // Insert Detail
//                    System.out.println("DB : " + canvas.getName());
//                    detailDAO.insert(canvas.getDetail(), canvasId);
//                }
                canvasDAO.insertCanvasCategory(categoryId, canvasId);
            }
        }
    }
}
