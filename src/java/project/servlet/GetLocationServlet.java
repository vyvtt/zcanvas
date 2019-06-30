/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import project.dao.CategoryDAO;
import project.dao.LocationDAO;
import project.utils.Constant;

/**
 *
 * @author thuyv
 */
public class GetLocationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        try {
            
            LocationDAO locationDAO = new LocationDAO();
            String xmlLocation = locationDAO.getAllLocationCategories();

//            CategoryDAO categoryDAO = new CategoryDAO();
//            String xmlCategory = categoryDAO.getAllCategoriesAsXML();

            request.setAttribute("XML_LOCATION", xmlLocation);
//            request.setAttribute("XML_CATEGORY", xmlCategory);
//
//            try (OutputStreamWriter writer
//                    = new OutputStreamWriter(new FileOutputStream(Constant.REAL_PATH + "/document/categories.xml"), StandardCharsets.UTF_8)) {
//                // do stuff
//                writer.write(xmlCategory);
//                System.out.println("write done!!!!");
//                System.out.println(Constant.REAL_PATH + "/document/categories.xml");
//            }

//            StringBuilder contentBuilder = new StringBuilder();
//            try (BufferedReader br = new BufferedReader(new FileReader(Constant.REAL_PATH + "/WEB-INF/document/admin.xsl"))) {
//
//                String sCurrentLine;
//                while ((sCurrentLine = br.readLine()) != null) {
//                    contentBuilder.append(sCurrentLine.trim());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////            contentBuilder.toString();
//            System.out.println("admin xsl: " + contentBuilder.toString());
//
//            request.setAttribute("XSL_ADMIN", contentBuilder.toString());
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
////            BufferedWriter writer = new BufferedWriter(new FileWriter(Constant.REAL_PATH + "/WEB-INF/document/test.xml"));
//            writer.write(xmlCategory);
//            writer.close();
            RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
