/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import dao.UserDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import org.json.JSONObject;

public class Utilizatori extends HttpServlet {

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

        String esteAdmin = (String) request.getSession().getAttribute("este_admin");

        //verifica daca e admin deoarece doar el are acces la aceasta pagina 
        if (esteAdmin == null || esteAdmin.equals("0")) {
            response.setStatus(404);
            return;
        }

        //daca nu exista un id se returneaza toti
        //altfel in request se returneaza doar user-ul cu id-ul corespunzator
        String id = request.getParameter("id");
        if (id == null) {
            returneazaTotiUtilizatorii(request, response);
        } else {
            returneazaUnSingurAngajat(id, request, response);
        }

    }

    protected void returneazaTotiUtilizatorii(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //este o lista cu toti utilizatorii
        JSONObject utilizatori = new JSONObject();
        List<User> useri = null;
        try {
            useri = UserDao.getAllUsers();
            for (User user : useri) {
                JSONObject utilizatorJson = new JSONObject(); //contine toate datele pt un sg utilizator
                utilizatorJson.put("username", user.getUsername());
                utilizatorJson.put("tip_angajat", user.getTipAngajat().getDenumire());
                utilizatorJson.put("departament", user.getDepartament().getDenumire());
                utilizatorJson.put("este_admin", user.getEsteAdmin());
                utilizatorJson.put("id", user.getId());

                utilizatori.append("utilizatori", utilizatorJson); //se adauga utilizatorul in json-ul de oe linia 75
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(utilizatori.toString());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void returneazaUnSingurAngajat(String id, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject utilizator = new JSONObject();

        try {
            User user = UserDao.getUserWithId(Integer.parseInt(id));

            JSONObject utilizatorJson = new JSONObject();
            utilizatorJson.put("username", user.getUsername());
            utilizatorJson.put("tip_angajat", user.getTipAngajat().getDenumire());
            utilizatorJson.put("departament", user.getDepartament().getDenumire());
            utilizatorJson.put("este_admin", user.getEsteAdmin());

            utilizator.put("utilizator", utilizatorJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(utilizator.toString());
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

        //se verific din nou daca este admin pt ca doar ei pot sterge/edita/creea
        String esteAdmin = (String) request.getSession().getAttribute("este_admin");

        if (esteAdmin == null || esteAdmin.equals("0")) {
            response.setStatus(404);
            return;
        }

        //type:
        String type = request.getParameter("type");

        if (type != null) {
            if (type.equals("DELETE")) {
                stergeUnUtilizator(request, response);
            }
            if (type.equals("EDIT")) {
                editeazaUnUtilizator(request, response);
            }
        } else {
            creazaUnUtilizator(request, response);
        }

    }

    protected void creazaUnUtilizator(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = null;
        String parola = null;
        String idDepartament = null;
        String idTipangajat = null;
        int admin = 0;

        try {
            username = request.getParameter("username");
            parola = request.getParameter("password");
            idDepartament = request.getParameter("departament");
            idTipangajat = request.getParameter("tipAngajat");
            admin = request.getParameter("esteAdmin").equals("true") ? 1 : 0;
            UserDao.createUser(username, parola, Integer.parseInt(idDepartament), Integer.parseInt(idTipangajat), admin);

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

    protected void stergeUnUtilizator(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String esteAdmin = (String) request.getSession().getAttribute("este_admin");

        if (esteAdmin == null || esteAdmin.equals("0")) {
            response.setStatus(404);
            return;
        }

        try {
            String id = request.getParameter("id");
            UserDao.removeUser(id);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void editeazaUnUtilizator(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String esteAdmin = (String) request.getSession().getAttribute("este_admin");

        if (esteAdmin == null || esteAdmin.equals("0")) {
            response.setStatus(404);
            return;
        }
        String username = null;
        String parola = null;
        String idDepartament = null;
        String idTipangajat = null;
        int admin = 0;

        try {
            username = request.getParameter("username");
            idDepartament = request.getParameter("departament");
            idTipangajat = request.getParameter("tipAngajat");
            admin = request.getParameter("esteAdmin").equals("true") ? 1 : 0;
            String id = request.getParameter("id");

            UserDao.updateAllInformation(Integer.parseInt(id), username, Integer.parseInt(idDepartament), Integer.parseInt(idTipangajat), admin);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
