/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import dao.FormularConcediuDao;
import dao.UserDao;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.FormularConcediu;
import models.User;

public class SaveFormularConcediu extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String tipConcediu = null;
        String medical = null;
        String altceva = null;
        String descriere = null;
        String deLaData = null;
        String panaLaData = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            //date care provin din browser si care se duc in baza de date
            tipConcediu = request.getParameter("tipConcediu");
            medical = request.getParameter("medical");
            descriere = request.getParameter("descriere");
            deLaData = request.getParameter("deLaData");
            panaLaData = request.getParameter("panaLaData");

            //verifica ca userul e logat
            String idUser = (String) request.getSession().getAttribute("id");
            if (idUser == null) {
                return;
            }

            if (formularulEsteValid(tipConcediu, descriere, deLaData, panaLaData, idUser)) { //verifica daca formularul poate fi creat
                //creeaza formularul in FormularConcediuDao
                FormularConcediuDao.createFormularConcediu(tipConcediu, descriere, format.parse(deLaData), format.parse(panaLaData), Integer.parseInt(idUser));
                //raspunde browser-ului la cerere
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{success: true}");
                response.setStatus(HttpServletResponse.SC_OK);
            } 
            else {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{success: false}");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{success: false}");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    //Particularitati
    public static boolean formularulEsteValid(String tipConcediu, String descriere, String deLaData, String panaLaData, String idUser) 
            throws ParseException, SQLException {
        if (!stringulEsteValid(tipConcediu)) {
            return false;
        }

        if (!stringulEsteValid(descriere)) {
            return false;
        }
        if (!stringulEsteValid(deLaData)) {
            return false;
        }
        if (!stringulEsteValid(panaLaData)) {
            return false;
        }

        return calendarulRespectaRegulileFirmei(deLaData, panaLaData, idUser);
    }

    public static boolean stringulEsteValid(String valoare) {
        if (valoare == null) {
            return false;
        }
        if (valoare.equals("")) {
            return false;
        }
        return true;
    }

    public static boolean calendarulRespectaRegulileFirmei(String deLaData, String panaLaData, String idUser) 
            throws ParseException, SQLException {
        ArrayList<ArrayList<String>> toateConcediile = toateConcediileUtilizatorului(idUser);

        if (!perioadaEsteCorectIntrodusa(deLaData, panaLaData)) {
            return false;
        }
        if (!utilizatorulRespecta30zileAnual(toateConcediile, deLaData, panaLaData)) {
            return false;
        }

        if (!concediulEsteImpartitCorect(toateConcediile)) {
            return false;
        }

        if (!concediulEsteAlocatCorectVara(deLaData, panaLaData)) {
            return false;
        }

        if (!concediulNuRespectaRegulaDe50LaSuta(deLaData, panaLaData, idUser)) {
            return false;
        }

        return true;
    }

    //verifica daca perioada este introdusa corect
    public static boolean perioadaEsteCorectIntrodusa(String nouaPerioadaDeLa, String nouaPerioadaPanaLa) throws ParseException {
        int anCurent = Calendar.getInstance().get(Calendar.YEAR); //Calendar este un obiect care salveaza date(de ex zile, luni, ani)
        Calendar deLa = stringToCalendar(nouaPerioadaDeLa); //creez un obiect de tip Calendar cu string-ul obtinut din introducerea datei de pe pagina "formularConcediu.htlm"
        Calendar panaLa = stringToCalendar(nouaPerioadaPanaLa); //creez un obiect de tip Calendar cu string-ul obtinut din introducerea datei de pe pagina "formularConcediu.htlm"

        if (deLa.get(Calendar.YEAR) != anCurent || panaLa.get(Calendar.YEAR) != anCurent) { //cu .get obtin anul curent
            return false;
        }

        //trebuie sa ma asigur ca perioada e introdusa in anul curent si pt asta am nevoie de 
        //if(deLa.after(panaLa)) => verfica daca perioada de inceput e inainte de cea de final (ex: 5aug-7aug corect, 5aug-3aug gresit)
        if (deLa.after(panaLa)) {
            return false;
        }

        return true;
    }

    //numara cate zile de concediu a avut persoana in total(din maxim 30 zile)
    public static boolean utilizatorulRespecta30zileAnual(ArrayList<ArrayList<String>> toateConcediile, String nouaPerioadaDeLa, String nouaPerioadaPanaLa) throws ParseException {
        int zileConcediuUtilizate = 0;

        //toate concediile sunt stocate intr-o lista numita toateConcediile, iar pentru  fiecare lista numita concediu din lista mea de liste se executa
        for (ArrayList<String> concediu : toateConcediile) {
            //lista mea de liste este de fapt o Lista de (Lista cu 2 elemente)
            //cele 2 elemente sunt perioadele de inceput si de final
            //creez un calendar din stringul stringToCalnedar
            Calendar deLa = stringToCalendar(concediu.get(0)); //concediu.get(0) = perioada de inceput
            Calendar panaLa = stringToCalendar(concediu.get(1)); //concediu.get(1) = perioada de final

            //zileConcediuUtilizate = zileConcediuUtilizate + daysBetween(deLa.getTime(), panaLa.getTime());
            zileConcediuUtilizate += daysBetween(deLa.getTime(), panaLa.getTime());
        }

        int zileConcediuCerute = daysBetween(stringToCalendar(nouaPerioadaDeLa).getTime(), stringToCalendar(nouaPerioadaPanaLa).getTime());
        return zileConcediuUtilizate + zileConcediuCerute <= 30;
    }

    //returneaza toate concediile utilizatorului folosind FormularConcediuDao apeland baza de date
    public static ArrayList<ArrayList<String>> toateConcediileUtilizatorului(String id) throws SQLException {
        //apelez baza de date
        List<FormularConcediu> formulareConcedii = FormularConcediuDao.getFormulareConcediiFromUserId(Integer.parseInt(id));
        //creez o lista locala pentru un singur concediu dupa care adaug lista in lista cu toate concediile
        ArrayList<ArrayList<String>> toateConcediile = new ArrayList<ArrayList<String>>(); //ArrayList<ArrayList<String>> = Lista de lista de string

        //in baza de date creez toate concediile si pt fiecare formular de concediu adaug perioada de inceput si perioada de sfarsit in lista
        //am o lista numita 'concedii' care are 2 val: 0 = perioada de inceput, 1 = perioada de final
        //concedii.add(rs.getString(1)); = in lista concedii adaug valoarea obtinuta din apelarea bazei de date pt a vedea data finala
        // => in lista de concedii adaug perioada de final
        // 0 si 1 sunt din lista, iar 1 si 2 sunt din PeparedStatement
        for (FormularConcediu fc : formulareConcedii) {
            ArrayList<String> concedii = new ArrayList<String>();
            concedii.add(fc.getPrimaZiConcediu().toString());
            concedii.add(fc.getUltimaZiConcediu().toString());
            toateConcediile.add(concedii);
        }
        return toateConcediile;
    }

    //returnez daca numarul concediilor este max 3(numara toate concediile din lista concediu)
    public static boolean concediulEsteImpartitCorect(ArrayList<ArrayList<String>> toateConcediile) {
        int numarConcediiUtilizate = 0;

        //toate concediile sunt stocate intr-o lista numita toateConcediile, iar pentru fiecare lista numita concediu din lista mea de liste se executa 
        for (ArrayList<String> concediu : toateConcediile) {
            numarConcediiUtilizate++;
        }

        return numarConcediiUtilizate <= 3;
    }

    //returneaza daca concediul de vara este intre luna 5 si 7 si daca are max 21 de zile libere
    public static boolean concediulEsteAlocatCorectVara(String deLaData, String panaLaData) throws ParseException {
        Calendar deLa = stringToCalendar(deLaData);
        Calendar panaLa = stringToCalendar(panaLaData);

        int lunaDeLa = deLa.get(Calendar.MONTH);
        int lunaPanaLa = panaLa.get(Calendar.MONTH);
        int numarZileConcediu = daysBetween(deLa.getTime(), panaLa.getTime());

        //concediu de vara(max 21zile libere)
        if ((lunaDeLa >= 5 && lunaDeLa <= 7) || (lunaPanaLa >= 5 && lunaDeLa <= 7)) {
            return numarZileConcediu <= 21;
        }
        return true;
    }

    //regula de 50%
    public static boolean concediulNuRespectaRegulaDe50LaSuta(String deLaData, String panaLaData, String id) throws ParseException {
        Calendar deLa = stringToCalendar(deLaData);
        Calendar panaLa = stringToCalendar(panaLaData);

        List<User> useriDinDepartament = UserDao.getUsersFromSameDepartment(Integer.parseInt(id));
        int numarConcedii = FormularConcediuDao.numarFormulareConcediiDinDepartamentInPerioadaCeruta(Integer.parseInt(id), deLaData, panaLaData);
        
        return numarConcedii <= (useriDinDepartament.size() / 2);
    }

    //ia ca parametru un string "09-08-1996",   fac un obiect Calendar din el si returnez noul obiect
    public static Calendar stringToCalendar(String data) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(data));
        return cal;
    }

    //returneaza diferenta dintre doua date/zile
    //pt fieare calendar am doua numere mari, scazandu-le trebuie sa obtin o valoare => trebuie sa fac dif matematica pt a obtine zilele
    //(d2.getTime() - d1.getTime()) = [ms]
    //(d2.getTime() - d1.getTime()) / (1000) = [s]
    //(d2.getTime() - d1.getTime()) / (1000 * 60) = [min]
    //(d2.getTime() - d1.getTime()) / (1000 * 60 * 60) = [h]
    //(d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24) = [zile]
    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
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
