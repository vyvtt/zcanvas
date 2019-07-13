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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import project.jaxb.Canvases;
import project.jaxb.Spotlight;
import project.miscellaneous.PalatteData;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class InitPalatteServlet extends HttpServlet {

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

            if (PalatteData.isReady()) {
                Canvases c = new Canvases();
                c.setCanvases(PalatteData.topCanvas);

                Spotlight s = new Spotlight();
                s.setCanvases(c);
                s.setPaletteColor(PalatteData.palatteColor);
                s.setPaletteImg(PalatteData.palatteImage);
                
                s.setImgName(PalatteData.imgName);
                s.setImgAuth(PalatteData.imgAuth);
                s.setImgLink(PalatteData.imgLink);

                String spotlightStr = XMLHelper.parseToXMLString(s);
                System.out.println(spotlightStr);

                response.setContentType("text/xml; charset=UTF-8");
                response.getWriter().write(spotlightStr);
            }

        } catch (IOException | JAXBException e) {
            Logger.getLogger(InitPalatteServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);
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
