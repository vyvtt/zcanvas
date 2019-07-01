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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import project.dao.LocationDAO;
import project.jaxb.Location;
import project.utils.Constant;

/**
 *
 * @author thuyv
 */
@MultipartConfig
public class HomeServlet extends HttpServlet {

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
        
        LocationDAO locationDAO = new LocationDAO();
        String locationXML = locationDAO.getAllLocationXML();
        System.out.println("location xml: " + locationXML);

        response.setContentType("text/xml; charset=UTF-8");
        response.getWriter().write(locationXML);

//        try (PrintWriter out = response.getWriter()) {
//
//            Location l1 = new Location(1, "Tranh treo cửa hàng", "image/shop.svg", null);
//            Location l2 = new Location(2, "Tranh treo văn phòng", "image/office.svg", null);
//            Location l3 = new Location(3, "Tranh trang trí nhà cửa", "image/house.svg", null);
//            List<Location> list = new ArrayList<>();
//            list.add(l1);
//            list.add(l2);
//            list.add(l3);
//            
//            HttpSession session = request.getSession();
//            session.setAttribute("LOCATION", list);
//            
//            RequestDispatcher rs = request.getRequestDispatcher(Constant.JSP_HOME);
//            rs.forward(request, response);
//        }
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
