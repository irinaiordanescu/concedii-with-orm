<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
    //verific ca userul este logat in sistem
    if(((String) session.getAttribute("id") == null) || ((String) session.getAttribute("este_admin") == "0")){
        response.setStatus(HttpServletResponse.SC_FOUND);//302
        response.setHeader("Location", "http://localhost:8080/Concedii/paginaLogin.html/");
    }
%>

<html>
    <head>
        <title>Pagina admin</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
        
        <script type="text/javascript" src="js/paginaAdmin.js"></script>
    </head>
    
    <body>
        <div class="card-deck mb-3 text-center">
            <div class="card mb-4 box-shadow">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal">Site concedii</h4>
                </div>
                <div class="card-body">
                    <ul class="list-unstyled mt-3 mb-4">
                        <li><img src="images/holidays.png"> </li>
                    </ul>
                    <button type="button" class="btn btn-lg btn-block btn-primary" onclick="redirectioneazaPaginaConcedii()">Accesare</button>
                </div>
            </div>
            <div class="card mb-4 box-shadow">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal">Site administrare utilizatori</h4>
                </div>
                <div class="card-body">
                    <ul class="list-unstyled mt-3 mb-4">
                        <li><img src="images/users.png"> </li>
                    </ul>
                    <button type="button" class="btn btn-lg btn-block btn-primary" onclick="redirectioenazaPaginaAdministrare()">Accesare</button>
                </div>
            </div>
        </div>
    </body>
</html>
