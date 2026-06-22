/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.utils.DataPool;

/**
 *
 * @author USER
 */
@WebServlet(name = "SimulatorRenderer", urlPatterns = {"/SimulatorRenderer"})
public class SimulatorRendererServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SimulatorRenderer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SimulatorRenderer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); 

        PrintWriter out = response.getWriter();

        var herbivores = DataPool.getCachedHerbivoreData();
        var carnivores = DataPool.getCachedCarnivoreData();
        var plants = DataPool.getCachedPlantData();

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"herbivores\": [");
        for (int i = 0; i < herbivores.size(); i++) {
            json.append("{");
            json.append("\"name\":\"").append(herbivores.get(i).getBreedName()).append("\"");
            json.append("}");
            if (i < herbivores.size() - 1) json.append(",");
        }
        json.append("],");
        
        json.append("{");
        json.append("\"carnivores\": [");
        for (int i = 0; i < herbivores.size(); i++) {
            json.append("{");
            json.append("\"name\":\"").append(carnivores.get(i).getBreedName()).append("\"");
            json.append("}");
            if (i < herbivores.size() - 1) json.append(",");
        }
        json.append("],");

        json.append("\"plants\": [");
        for (int i = 0; i < plants.size(); i++) {
            json.append("{");
            json.append("\"name\":\"").append(plants.get(i).getBotanicName()).append("\"");
            json.append("}");
            if (i < plants.size() - 1) json.append(",");
        }
        json.append("]");
        json.append("}");

        out.print(json.toString());
        out.flush();
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
