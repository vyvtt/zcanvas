/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import project.dao.CanvasDAO;
import project.dao.LocationDAO;
import project.jaxb.Canvas;
import project.jaxb.Canvases;
import project.jaxb.Categories;
import project.jaxb.Info;
import project.utils.ColorHelper;
import project.utils.ImageHelper;
import project.utils.XMLHelper;

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

        try {
            int locationId = Integer.parseInt(request.getParameter("rbLocation"));
            Part filePart = request.getPart("mFile");
            InputStream is = filePart.getInputStream();
            BufferedImage image = ImageIO.read(is);

            // init
            String colorPalette = ImageHelper.getColorPaletteFromImage(image, true);
            List<String> inputPalatte = Arrays.asList(colorPalette.split("\\s*;\\s*"));
            System.out.println("input palette: " + colorPalette);
            CanvasDAO canvasDAO = new CanvasDAO();
            LocationDAO locationDAO = new LocationDAO();

            // get list categories by location
            List<Categories> listCategory = locationDAO.getCategoriesByLocation(locationId);
            Map<Integer, Integer> mapCategoryCount = new HashMap<>();
            listCategory.forEach((category) -> {
                mapCategoryCount.put(category.getId(), 0);
            });

            // get canvas by category
            List<Canvas> listCanvasInLocation = canvasDAO.getAllCanvasByCategory(listCategory);
            List<Canvas> result = new ArrayList<>();

            for (Canvas canvas : listCanvasInLocation) {
                List<String> currentPalette = Arrays.asList(canvas.getColorPalatte().split("\\s*;\\s*"));

                double deltaE = ImageHelper.comparePalette2Palette(inputPalatte, currentPalette);

                if (deltaE != -1) {
                    canvas.setDeltaE(deltaE);
                    List<String> currentCanvasColor = new ArrayList<>();

                    for (String colorInt : currentPalette) {
                        currentCanvasColor.add(ColorHelper.convertInt2Hex(Integer.parseInt(colorInt)));
                    }
                    canvas.setCanvasColors(currentCanvasColor);

                    // count result by category
                    for (Integer keyId : canvas.getCanvasCategories()) {
                        mapCategoryCount.put(keyId, mapCategoryCount.get(keyId) + 1);
                    } 
                    result.add(canvas);
                }
            }

            Collections.sort(result, (c1, c2) -> {
                return ((Comparable) c1.getDeltaE()).compareTo(c2.getDeltaE());
            });

            for (Categories category : listCategory) {
                category.setCount(mapCategoryCount.get(category.getId()));
            }

            List<String> colorHex = new ArrayList<>();
            for (String colorInt : inputPalatte) {
                colorHex.add(ColorHelper.convertInt2Hex(Integer.parseInt(colorInt)));
            }

            Canvases canvases = new Canvases();
            canvases.setCanvases(result);

            Info info = new Info();
            info.setTotal(result.size());
            info.setCategories(listCategory);
            info.setInputColors(colorHex);
            info.setCanvases(canvases);

            String infoStr = XMLHelper.parseToXMLString(info);
            System.out.println(infoStr);

            response.setContentType("text/xml; charset=UTF-8");
            response.getWriter().write(infoStr);

        } catch (IOException | ServletException | JAXBException e) {
            Logger.getLogger(GetCanvasMatchingImageServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
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
