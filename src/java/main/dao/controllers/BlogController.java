/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package main.dao.controllers;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import main.dao.PostDao;
import main.dto.Post;

/**
 *
 * @author dotra
 */
public class BlogController extends HttpServlet {

    private static final String SUCCESS_JSP = "blog.jsp";
    private static final String BLOG_DETAIL_JSP = "blogDetail.jsp";

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
        String url = SUCCESS_JSP;
        try {

            String id = request.getParameter("id");
            if (id != null) {
                Post post = PostDao.getPostById(Integer.parseInt(id));
                url = BLOG_DETAIL_JSP;
                request.setAttribute("post", post);
            } else {
                String parPage = request.getParameter("page");
                if (parPage == null || parPage.isEmpty()) {
                    parPage = "1";
                }
                int page = Integer.parseInt(parPage);
                int size = 3;
                ArrayList<Post> listPosts = PostDao.getAllPosts(page, size);
                int count =  PostDao.countAllPosts();
                int totalPage = (int) Math.ceil((double) count / (double) size);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("listPosts", listPosts);
            }

        } catch (Exception e) {
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
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
