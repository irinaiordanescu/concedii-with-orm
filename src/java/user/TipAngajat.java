/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import dao.TipAngajatDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class TipAngajat extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        JSONObject json = new JSONObject();
        //List<String> tipuriAngajati = new ArrayList<String>();

        //se verifica daca are un id in sesiune
        String idUser = (String) request.getSession().getAttribute("id");
        if (idUser == null) {
            return;
        }
        try {
            //se selecteaza angajatul si id-ul, se pune intr-un ob de tip JSON si se returneaza
            System.out.println("-----Show-----");
            List<models.TipAngajat> tipuriAngajati = TipAngajatDao.getAllTipAngajat();
            for (models.TipAngajat tip : tipuriAngajati) {
                JSONObject tipAngajat = new JSONObject();
                tipAngajat.put("tip_angajat", tip.getDenumire());
                tipAngajat.put("id", tip.getId()+"");
                json.append("tipuriAngajati", tipAngajat.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{success: false}");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
