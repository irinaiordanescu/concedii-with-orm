<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    //verific ca userul este logat in sistem
    if ((String) session.getAttribute("id") == null) {
        response.setStatus(HttpServletResponse.SC_FOUND);//302
        response.setHeader("Location", "http://localhost:8080/Concedii/paginaLogin.html/");
    }
%>

<html>
    <head>
        <title>Setari profil</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link href="css/fabmin.css" rel="stylesheet" type="text/css" /> 
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" /> 

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script type="text/javascript" src="js/setariProfil.js"></script>
    </head>

    <body class='container-login100' style="background-image: url('images/beach-sunset.jpg');">
        <div id="addEmployeeModal">
            <div class="frame-margine">
                <div class="col-lg-9 timeoff">
                    <form>
                        <div>					
                            <div class="form-group">
                                <label>Username</label>
                                <input type="text" id="editeazaUsername" class="form-control">
                            </div>
                            
                            <div class="form-group">
                                <label>Parola noua</label>
                                <input type="password" id="editeazaParola" class="form-control">
                            </div>

                            <div class="form-group">
                                <label>Verifica Parola noua</label>
                                <input type="password" id="editeazaVerificareParola" class="form-control">
                            </div>

                        </div>
                        <div >
                            <input type="submit" class="btn btn-success" value="Salveaza schimbarile" onclick="salveazaSchimbari()">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>    
</html>
