/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import project.dao.CategoryDAO;
import project.utils.ConfigHelper;
import project.utils.Constant;

/**
 * Web application lifecycle listener.
 *
 * @author thuyv
 */
public class MyContextServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // init REAL_PATH
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        ConfigHelper.configRealPath(realPath);

//        // Prepare file categories.xml
//        CategoryDAO categoryDAO = new CategoryDAO();
//        String xmlCategory = categoryDAO.getAllCategoriesAsXML();
//        
//        if (xmlCategory == null || xmlCategory.isEmpty()) {
//            xmlCategory = "<categories></categories>";
//        }
//        
//        try (OutputStreamWriter writer
//                = new OutputStreamWriter(new FileOutputStream(Constant.REAL_PATH + "/WEB-INF/document/categories.xml"), StandardCharsets.UTF_8)) {
//            writer.write(xmlCategory);
//        } catch (IOException e) {
//            Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, e.getMessage(), e);
//        }
        
        // read config host
        ConfigHelper.configHost();
        ConfigHelper.configImage();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
