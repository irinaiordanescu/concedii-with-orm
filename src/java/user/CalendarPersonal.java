/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import dao.FormularConcediuDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.FormularConcediu;
import org.json.JSONObject;

public class CalendarPersonal extends HttpServlet {
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
    
    //returneaza toate concediile unei pers
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String idUser = null;
        JSONObject json = new JSONObject(); //pt a trimite inappoi date; JSON arata un format

        try {
            idUser = (String) request.getSession().getAttribute("id");
            if (idUser == null) {
                return;
            }
            
            int userId = Integer.parseInt(idUser);
            List<FormularConcediu> formulareConcedii = FormularConcediuDao.getFormulareConcediiFromUserId(userId);

            //pt fiecare rezultat se populeaza/se adauga in JSON
            for(FormularConcediu formularConcediu: formulareConcedii){
                List<String> concediu = new ArrayList<String>();
                concediu.add(formularConcediu.getId()+"");
                concediu.add(formularConcediu.getDescriere());
                concediu.add(formularConcediu.getTipConcediu());
                concediu.add(formularConcediu.getPrimaZiConcediu().toString());
                concediu.add(formularConcediu.getUltimaZiConcediu().toString());
                json.append("concedii", concediu.toArray());
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
    
    //sterge un singur concediu al unei pers
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String idUser = (String) request.getSession().getAttribute("id");
        if (idUser == null) {
            return;
        }
        
        String type = request.getParameter("type");
        
        if (type.equals("DELETE")) {
            stergeConcediul(request, response);
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

    public void stergeConcediul(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null) {
            return;
        }
        try {
            FormularConcediuDao.deleteFormularConcediuWithId(Integer.parseInt(id));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
