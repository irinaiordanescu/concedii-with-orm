<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
    //verific ca userul este logat in sistem
    if((String) session.getAttribute("id") == null){
        response.setStatus(HttpServletResponse.SC_FOUND);//302
        return;
    }
%>

<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link href="css/fabmin.css" rel="stylesheet" type="text/css" /> 
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" /> 

        <link rel='stylesheet' href='css/fullcalendar.css' />
        <script src='lib/jquery.min.js'></script>
        <script src='lib/moment.min.js'></script>
        <script src='js/fullcalendar.js'></script>
        <script type="text/javascript" src="js/calendarFirma.js"></script>
    </head>

    <body class='container-login100' style="background-image: url('images/beach-sunset.jpg');" style="opacity: 20%">
        <div id="calendar" class="calendar"></div>
    </body>
</html>