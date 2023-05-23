/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dao.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author hd
 */
public class MainController extends HttpServlet {

    private static final String HOME = "Home";
    private static final String HOME_CONTROLLER = "HomeController";
    private static final String SHOPPING = "Shopping";
    private static final String SHOPPING_CONTROLLER = "ShoppingController";
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String GET_LIST_CARS = "GetListCars";
    private static final String GET_LIST_CARS_CONTROLLER = "ListCarsController";
    private static final String SEARCH_CARS = "SearchCars";
    private static final String SEARCH_CARS_CONTROLLER = "SearchCarsController";
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String DELETE = "Delete";
    private static final String DELETE_CONTROLLER = "DeleteController";
    private static final String UPDATE_CAR = "Update";
    private static final String UPDATE_CAR_CONTROLLER = "UpdateCarsController";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String url = ERROR;

            try {
                String action = request.getParameter("action");
                switch (action) {
                    case HOME: {
                        url = HOME_CONTROLLER;
                        break;
                    }
                    case SHOPPING: {
                        url = SHOPPING_CONTROLLER;
                        break;
                    }
                    case LOGIN: {
                        url = LOGIN_CONTROLLER;
                        break;
                    }
                    case GET_LIST_CARS: {
                        url = GET_LIST_CARS_CONTROLLER;
                        break;
                    }
                    case SEARCH_CARS: {
                        url = SEARCH_CARS_CONTROLLER;
                        break;
                    }
                    case LOGOUT: {
                        url = LOGOUT_CONTROLLER;
                        break;
                    }
                    case DELETE: {
                        url = DELETE_CONTROLLER;
                        break;
                    }
                    case UPDATE_CAR: {
                        url = UPDATE_CAR_CONTROLLER;
                        break;
                    }
                }
            } catch (Exception e) {
                log("Error at MainController: " + e.toString());
            } finally {
                request.getRequestDispatcher(url).forward(request, response);
            }
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
