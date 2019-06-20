/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
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
//        SoynMainCrawler.crawl();
        MopiMainCrawler.crawl();
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

                System.out.println(canvas.getName());

                canvas.setColorPalatte(ImageHelper.getColorPaletteFromImage(canvas.getImage()));
                int canvasId = canvasDAO.insert(canvas);

                if (canvasDAO.isIsExisted()) {
                    // Skip insert Detail
                    System.out.println("Skip existed canvas");
                } else {
                    // Insert Detail
                    detailDAO.insert(canvas.getDetail(), canvasId);
                }

                canvasDAO.insertCanvasCategory(categoryId, canvasId);
            }
        }
    }
}
