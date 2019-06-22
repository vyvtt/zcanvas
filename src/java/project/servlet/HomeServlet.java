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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import project.dao.CanvasDAO;
import project.jaxb.Canvas;
import project.utils.Constant;
import project.utils.ImageHelper;

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

        PrintWriter out = response.getWriter();
        String url = Constant.JSP_HOME;

        System.out.println("in HomeServlet");

        try {
            Part filePart = request.getPart("file");
            InputStream is = filePart.getInputStream();
            BufferedImage image = ImageIO.read(is);

            String colorPalette = ImageHelper.getColorPaletteFromImage(image);
            List<String> inputPalatte = Arrays.asList(colorPalette.split("\\s*;\\s*"));
            System.out.println(colorPalette);

            CanvasDAO dao = new CanvasDAO();
            List<Canvas> listCanvas = dao.getAllCanvasByCategory(15);

            List<Canvas> result = new ArrayList<>();

            for (Canvas canvas : listCanvas) {
//                image = ImageIO.read(new URL(url1));
//                String currentPaletteString = ImageHelper.getColorPaletteFromImage(image);
                List<String> currentPalette = Arrays.asList(canvas.getColorPalatte().split("\\s*;\\s*"));

                if (ImageHelper.comparePalette(inputPalatte, currentPalette)) {
                    result.add(canvas);
                }
            }

            System.out.println("total canvas " + listCanvas.size());
            System.out.println("total result " + result.size());

            for (Canvas canvas : result) {
                System.out.println(canvas.getName());
                System.out.println(canvas.getImage());
                System.out.println("---");
            }

            request.setAttribute("CANVAS", result);
//            response.setContentType("image/png");
//            ImageIO.write(image, "png", response.getOutputStream());

//            Base64.Encoder encoder = Base64.getEncoder();
//            ImageIO.write(image, "png", new File("tmp.png"));
//            String base64Str = "data:image/png;base64," + encoder.encode(Files.readAllBytes(Paths.get("tmp.png")));
//            request.setAttribute("IMAGE", base64Str);
            String realPath = request.getServletContext().getRealPath("/");
            if (Constant.REAL_PATH.isEmpty()) {
                Constant.updateRealPath(realPath);
            }
            ImageIO.write(image, "png", new File(Constant.REAL_PATH + "/WEB-INF/image/tmpImage.png"));
            byte[] imageBytes = Files.readAllBytes(Paths.get(Constant.REAL_PATH + "/WEB-INF/image/tmpImage.png"));
            Base64.Encoder encoder = Base64.getEncoder();
            String encoding = "data:image/png;base64," + encoder.encodeToString(imageBytes);
            request.setAttribute("IMAGE", encoding);

//            CanvasDAO canvasDAO = new CanvasDAO();
//            List<Canvas> result = canvasDAO.getAllCanvasByCategory(15);
//            
//            for (Canvas canvas : result) {
//                ImageHelper.comparePalette(colorPalette, canvas.getColorPalatte());
//            }
        } catch (IOException | ServletException e) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);
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
