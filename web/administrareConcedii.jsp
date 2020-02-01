<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    //verific ca userul este logat in sistem
    if (((String) session.getAttribute("id") == null)) {
        response.setStatus(HttpServletResponse.SC_FOUND);//302
        return;
    }
%>

<html>
    <head>
        <title>Administrare Concedii</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="js/administrareConcedii.js" type="text/javascript"></script>
    </head>

    <body class='container-login100' style="background-image: url('images/beach-sunset.jpg'); background-size: cover; background-repeat: no-repeat; background-position: center center;">
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row" style='margin-bottom: 30px'>
                        <div class="col-sm-6">
                            <h2>Administrare Concedii</h2>
                        </div>
                    </div>
                      <div style="margin-bottom:15px">
                        <h5 id="zileLibereOcupate"> </h3>
                    </div>
                    <div style="margin-bottom:15px">
                        <h5 id="zileLibereRamase"> </h3>
                    </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Tip concediu</th>
                            <th>Descriere</th>
                            <th>De la data</th>
                            <th>Pana la data</th>
                            <th>Actiuni</th>
                        </tr>
                    </thead>
                    <tbody id="concedii"></tbody>
                </table>
            </div>
        </div>
    </body>
</html>
