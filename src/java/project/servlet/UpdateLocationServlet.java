/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import project.dao.LocationDAO;
import project.utils.Constant;

/**
 *
 * @author thuyv
 */
public class UpdateLocationServlet extends HttpServlet {

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
        
        try (PrintWriter out = response.getWriter()) {
            
            String locationName = request.getParameter("txtLocationName");
            String oldLocationName = request.getParameter("txtOldLocationName");
            String categories[] = request.getParameterValues("category");
            int locationId = Integer.parseInt(request.getParameter("txtLocationId"));
            
            // parse các id của categories mới
            List<Integer> categoryIds = new ArrayList<>();
            for (int i = 0; i < categories.length; i++) {
                categoryIds.add(Integer.parseInt(categories[i]));
            }
            
            // update categories của location
            LocationDAO locationDAO = new LocationDAO();
            locationDAO.updateLocationCategory(locationId, categoryIds);
            
            // update tên location nếu thay đổi
            System.out.println(oldLocationName);
            System.out.println(locationName);
            if (!locationName.equals(oldLocationName)) {
                locationDAO.updateLocationName(locationId, locationName);
            }
            
            String xmlLocation = locationDAO.getAllLocationCategories();
            request.setAttribute("XML_LOCATION", xmlLocation);
            
            String url = "admin.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            
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
