<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
    //verific ca userul este logat in sistem
    if((String) session.getAttribute("id") == null){
        response.setStatus(HttpServletResponse.SC_FOUND);//302
        response.setHeader("Location", "http://localhost:8080/Concedii/paginaLogin.html/");
    }
%>

<html>
    <head>
        <title>Planificare Concedii</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link href="css/fabmin.css" rel="stylesheet" type="text/css" /> 
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" /> 

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
        <script type="text/javascript" src="js/paginaLogin.js"></script>
    </head>

    <frameset cols="15%, 85%" name="frameset_00">
        <frame src="frame1.jsp" name="frame_01" scrolling="NO" marginwidth="0" marginheight="0" noresize="" frameborder="0"/>       
        <frame src="frame2.jsp" name="frame_02" scrolling="YES" marginwidth="0" marginheight="0" noresize="" frameborder="0"/>
    </frameset>
</html>




