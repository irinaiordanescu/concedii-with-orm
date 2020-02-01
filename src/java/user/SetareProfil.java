/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import dao.UserDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import org.json.JSONObject;

public class SetareProfil extends HttpServlet {

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

        JSONObject utilizator = new JSONObject();
        String id = (String) request.getSession().getAttribute("id");
        if (id == null) {
            return;
        }
        try {

            User user = UserDao.getUserWithId(Integer.parseInt(id));

            JSONObject utilizatorJson = new JSONObject();
            utilizatorJson.put("username", user.getUsername());
            utilizatorJson.put("prioritate", user.getTipAngajat().getPrioritate());
            utilizatorJson.put("tip_angajat", user.getTipAngajat().getDenumire());
            utilizatorJson.put("departament", user.getDepartament().getDenumire());
            utilizatorJson.put("este_admin", +user.getEsteAdmin());
            utilizatorJson.put("id", user.getId());

            utilizator.put("utilizator", utilizatorJson);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(utilizator.toString());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        String id = (String) request.getSession().getAttribute("id");
        if (id == null) {
            return;
        }
 
        try {
           String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = UserDao.updateUsernameAndPassword(Integer.parseInt(id), username, password);
            request.getSession(true).setAttribute("username", user.getUsername());
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
