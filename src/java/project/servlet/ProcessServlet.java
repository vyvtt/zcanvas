/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static project.utils.Constant.*;

/**
 *
 * @author thuyv
 */
@MultipartConfig
public class ProcessServlet extends HttpServlet {

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

        String url = JSP_HOME;
        String btn = request.getParameter("btAction");

        try {
            if (btn == null) {
                // do nothing
            } else if (btn.equals("match")) {
                String type = request.getParameter("rbType");
                
                if (type.equals("typeImage")) {
                    url = SERVLET_GET_CANVAS_MATCHING_IMG;
                } else {
                    url = SERVLET_GET_CANVAS_MATCHING_COLOR;
                }
            } else if (btn.equals("crawl")) {
                url = SERVLET_CRAWL;
            } else if (btn.equals("updateLocation")) {
                url = SERVLET_UPDATE_LOCATION;
            } else if (btn.equals("admin")) {
                url = SERVLET_GET_LOCATION_CATEGORY;
            } else if (btn.equals("initLocation")) {
                url = SERVLET_HOME;
            } else if (btn.equals("addLocation")) {
                url = SERVLET_ADD_LOCATION;
            } else if (btn.equals("deleteLocation")) {
                url = SERVLET_DELETE_LOCATION;
            }

        } catch (Exception e) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {

            System.out.println("URL: " + url);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

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
