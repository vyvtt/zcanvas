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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import project.dao.CategoryDAO;
import project.dao.LocationDAO;
import project.listener.MyContextServletListener;
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

            String url = Constant.JSP_ADMIN;
            HttpSession session = request.getSession(false);
            boolean auth = false;
            if (session != null) {
                try {
                    auth = (boolean) session.getAttribute("AUTH");
                } catch (Exception e) {
                    auth = false;
                }
            }
            if (auth) {

                // Prepare file categories.xml
                CategoryDAO categoryDAO = new CategoryDAO();
                String xmlCategory = categoryDAO.getAllCategoriesAsXML();

                if (xmlCategory == null || xmlCategory.isEmpty()) {
                    xmlCategory = "<categories></categories>";
                }

                try (OutputStreamWriter writer
                        = new OutputStreamWriter(new FileOutputStream(Constant.REAL_PATH + "/WEB-INF/document/categories.xml"), StandardCharsets.UTF_8)) {
                    writer.write(xmlCategory);
                } catch (IOException e) {
                    Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }

                LocationDAO locationDAO = new LocationDAO();
                String xmlLocation = locationDAO.getAllLocationCategories();

                request.setAttribute("XML_LOCATION", xmlLocation);
                request.setAttribute("XML_CATEGORIES", xmlCategory);
                System.out.println(xmlLocation);
                System.out.println(xmlCategory);

            } else {
                url = Constant.HTML_LOGIN;
            }

            RequestDispatcher rd = request.getRequestDispatcher(url);
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
