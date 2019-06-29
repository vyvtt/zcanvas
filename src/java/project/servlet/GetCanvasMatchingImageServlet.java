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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import project.dao.CanvasDAO;
import project.dao.LocationDAO;
import project.jaxb.Canvas;
import project.jaxb.Categories;
import project.utils.Constant;
import project.utils.ImageHelper;

/**
 *
 * @author thuyv
 */
public class GetCanvasMatchingImageServlet extends HttpServlet {

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
        String url = Constant.JSP_HOME;

        try {
            int locationId = Integer.parseInt(request.getParameter("rbLocation"));

            Part filePart = request.getPart("file");
            InputStream is = filePart.getInputStream();
            BufferedImage image = ImageIO.read(is);

            String colorPalette = ImageHelper.getColorPaletteFromImage(image);
            List<String> inputPalatte = Arrays.asList(colorPalette.split("\\s*;\\s*"));
            
            System.out.println(colorPalette);

            CanvasDAO canvasDAO = new CanvasDAO();
            LocationDAO locationDAO = new LocationDAO();
            
            List<Categories> listCategory = locationDAO.getCategoriesByLocation(locationId);

            List<Canvas> listCanvasInLocation = canvasDAO.getAllCanvasByCategory(listCategory);
            List<Canvas> result = new ArrayList<>();
            

            for (Canvas canvas : listCanvasInLocation) {
                List<String> currentPalette = Arrays.asList(canvas.getColorPalatte().split("\\s*;\\s*"));

                double deltaE = ImageHelper.comparePalette(inputPalatte, currentPalette);
                
                if (deltaE != -1) {
                    canvas.setDeltaE(deltaE);
                    List<String> currentCanvasColor = new ArrayList<>();

                    for (String colorInt : currentPalette) {
                        currentCanvasColor.add(ImageHelper.convertColorInt2Hex(Integer.parseInt(colorInt)));
                    }
                    canvas.setListColor(currentCanvasColor);
                    result.add(canvas);
                }
            }

            Collections.sort(result, (c1, c2) -> {
                return ((Comparable) c1.getDeltaE()).compareTo(c2.getDeltaE());
            });

            for (Canvas curCanvas : result) {
                System.out.println(curCanvas.getDeltaE());
            }

            List<String> colorHex = new ArrayList<>();
            for (String colorInt : inputPalatte) {
                colorHex.add(ImageHelper.convertColorInt2Hex(Integer.parseInt(colorInt)));
            }

            ImageIO.write(image, "png", new File(Constant.REAL_PATH + "/image/tmpImage.png"));
            byte[] imageBytes = Files.readAllBytes(Paths.get(Constant.REAL_PATH + "/image/tmpImage.png"));
            Base64.Encoder encoder = Base64.getEncoder();
            String encoding = "data:image/png;base64," + encoder.encodeToString(imageBytes);

            request.setAttribute("IMAGE", encoding);
            request.setAttribute("COLOR", colorHex);
            request.setAttribute("CANVAS", result);
            request.setAttribute("LOCATIONVALUE", locationId);
            request.setAttribute("TOTAL", result.size());

        } catch (IOException | ServletException e) {
            Logger.getLogger(GetCanvasMatchingImageServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
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