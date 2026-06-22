/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.descriptor.AnimalDescriptor;
import models.descriptor.PlantDescriptor;
import models.utils.DataPool;
import repository.DBConnector;

/**
 *
 * @author USER
 */
@WebServlet(name = "MasterDataServlet", urlPatterns = {"/MasterDataServlet"})
public class MasterDataServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("[MasterDataController] Booting up and seeding master data...");

        Connection conn = DBConnector.getConnection();
        if (conn == null) {
            System.err.println("[MasterDataController] Critical Error: Cannot connect to DB to load master data");
            return;
        }

        loadAnimalMasterData(conn);
        loadPlantMasterData(conn);
    }

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
            out.println("<title>Servlet MasterDataServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MasterDataServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void loadAnimalMasterData(Connection conn) {
        String sql = "SELECT * FROM animal_descriptors";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            List<AnimalDescriptor> localAnimalList = new ArrayList<>();
            while (rs.next()) {
                AnimalDescriptor descriptor = new AnimalDescriptor(rs);
                localAnimalList.add(descriptor);
            }
            DataPool.retrieveAnimalCache(localAnimalList);
            System.out.println("[MasterDataController] Animal Database Initiated");

        } catch (SQLException e) {
            System.err.println("[MasterDataController] Error loading animal data: " + e.getMessage());
        }
    }

    private void loadPlantMasterData(Connection conn) {
        String sql = "SELECT * FROM plant_descriptors";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            List<PlantDescriptor> localPlantList = new ArrayList<>();
            while (rs.next()) {
                PlantDescriptor descriptor = new PlantDescriptor(rs);
                localPlantList.add(descriptor);
            }
            DataPool.retrievePlantCache(localPlantList);
            System.out.println("[MasterDataController] Botany Database Initiated");

        } catch (SQLException e) {
            System.err.println("[MasterDataController] Error loading botany data: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Endpoint ini bisa dipakai oleh Vue.js untuk mengecek apakah data sudah terisi atau belum
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        
        // Kembalikan status sukses ke front-end bahwa sistem engine backend siap dijalankan
        out.print("{\"status\": \"ready\", \"message\": \"Master data fully loaded into engine contexts.\"}");
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
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

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
