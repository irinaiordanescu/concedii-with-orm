/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import dao.UserDao;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.FormularConcediu;
import models.User;
import org.json.JSONObject;

public class CalendarFirma extends HttpServlet {

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
    //afiseaza toate concediile tuturor angajatilor crae sunt in acelasi departament in funtie de gradul lor
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        JSONObject json = new JSONObject();

        try {
            int userId = Integer.parseInt((String) request.getSession().getAttribute("id"));
            User user = UserDao.getUserWithId(userId);

            SimpleDateFormat formatulDatelor = new SimpleDateFormat("yyyy-MM-dd");
            
            //selectez toti angajatii care sunt in acelasi departament si afisez concediile doar in fct de prioritate
            List<User> useriDinDepartament = UserDao.getUsersFromSameDepartment(userId);
            for (User userDinDepartament : useriDinDepartament) {
                if (userDinDepartament.getTipAngajat().getPrioritate() < user.getTipAngajat().getPrioritate()) {
                    for (FormularConcediu fc : userDinDepartament.getFormulareConcedii()) {
                        List<String> concedii = new ArrayList<String>();
                        JSONObject newJson = new JSONObject();
                        concedii.add(formatulDatelor.format(fc.getPrimaZiConcediu()));
                        concedii.add(formatulDatelor.format(fc.getUltimaZiConcediu()));
                        newJson.put(userDinDepartament.getUsername(), concedii.toArray());
                        json.append("concedii", newJson.toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.setStatus(HttpServletResponse.SC_OK);
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
