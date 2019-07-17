/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import project.dao.LocationDAO;
import project.utils.Constant;

/**
 *
 * @author thuyv
 */
@MultipartConfig
public class AddLocationServlet extends HttpServlet {

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

            String locationName = request.getParameter("txtName");
            String categories[] = request.getParameterValues("rbCategory");
            Part filePart = request.getPart("imgIcon");
            InputStream is = filePart.getInputStream();
            BufferedImage image = ImageIO.read(is);

            if (image != null) {
                String fileName = filePart.getSubmittedFileName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

                String filePath = Constant.REAL_PATH + "/image/" + fileName;

                File outputfile = new File(filePath);
                ImageIO.write(image, fileExtension, outputfile);

                filePath = "D:\\Study-Resource\\Semester_8_Summer2019\\PRX301_Web Development (XML)\\Code\\ZCanvas\\web\\image\\" + fileName;
                System.out.println(filePath);
                outputfile = new File(filePath);
                ImageIO.write(image, fileExtension, outputfile);

                String locationImage = "image/" + fileName;
                // parse các id của categories mới
                List<Integer> categoryIds = new ArrayList<>();
                for (int i = 0; i < categories.length; i++) {
                    categoryIds.add(Integer.parseInt(categories[i]));
                }

                LocationDAO locationDAO = new LocationDAO();
                int locationId = locationDAO.addNewLocation(locationName, locationImage);
                locationDAO.addLocationCategory(locationId, categoryIds);
            }

            String url = Constant.SERVLET_GET_LOCATION_CATEGORY;
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
