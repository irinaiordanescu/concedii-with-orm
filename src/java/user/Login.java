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
import javax.servlet.http.HttpSession;
import org.json.*;
import models.User;

public class Login extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String username = "";
        String password = "";

        try {
            username = request.getParameter("username"); //ia val din browser(campurile unde introduc datele)
            password = request.getParameter("password");

            //cu functia login verific ca in BD exista username si password
            //daca exista returneaza true
            boolean exista = login(username, password);

            if (exista) {
                //daca exista user si parola introduse apeleaza fct getUserData
                JSONObject user = getUserData(username, password);
                HttpSession session = request.getSession(true); //deschid o sesiune 
                
                //pt fiecare atribut returnat de fct getUserData, il salvez in sesiune
                //user.keySet returneaza fiecare val din JSON
                for (String key : user.keySet()) {
                    session.setAttribute(key, user.get(key));
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(user.toString());
                response.setStatus(HttpServletResponse.SC_OK); //daca OK-ul este trimis se apeleaza codul din succes din paginaLogin.js, daca nu se apeleaza codul din error
                System.out.println("login reusit");
            } else {
                System.out.println("login esuat");
            }
        } catch (Exception e) {

        }
    }

    public boolean login(String username, String parola) {
        //creez o noua conexiune cu BD
        User user = UserDao.getUserWithUsernameAndPassword(username, parola);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public JSONObject getUserData(String username, String password) { //fct returneaza un obiect cu toate val din BD
        JSONObject userJson = new JSONObject();

        User user = UserDao.getUserWithUsernameAndPassword(username, password);
        if (user == null) {
            return null;
        }

        //adaug val ob curent intr-un ob JSON
        userJson.put("id", user.getId() + "");
        userJson.put("prioritate", user.getTipAngajat().getPrioritate() + "");
        userJson.put("id_departament", user.getDepartament().getId() + "");
        userJson.put("id_tip_angajat", user.getTipAngajat().getId() + "");
        userJson.put("este_admin", user.getEsteAdmin() + "");
        userJson.put("username", username);
        userJson.put("password", password);

        return userJson;
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
