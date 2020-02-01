<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    //verific ca userul este logat in sistem
    if ((String) session.getAttribute("id") == null) {
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

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
        <script type="text/javascript" src="js/paginaLogin.js"></script>
    </head>
    
    <body>
        <div id="sidebar" class="sidebar-offcanvas left-sidebar-nav">
            <header> 
                <div class="well sidebar-nav"> 
                    <div class="quick-profile-user-control">
                        <div class="quick-profile">
                            <menu>
                                <div id="quickProfileToggle">
                                    <img src="images/Avatar.png" alt="Avatar" class="center-block" style="border-radius:50%; height:185px; width:175px; margin-bottom:10px">
                                    <a class="user-full-name" style="text-align: center;padding-left: 0px">
                                        <%
                                            //numele de sub poza se schimba in funtie de utiliator
                                            String name = (String) session.getAttribute("username");
                                            out.print(name);
                                        %> 
                                    </a>
                                </div>
                            </menu>
                        </div>
                    </div>

                    <nav>
                        <ul class="level0" role="navigation">
                            <li class="nav-btn-members"> 
                                <a id="members_nav" href="formularConcediu.jsp" target="frame_02">
                                    <i class="fa fa-use" data-original-title="" title=""> </i>
                                    FORMULAR CONCEDIU
                                    <span class="indicator"> </span>
                                </a>
                            </li>
                            
                            <li class="nav-btn-members">
                                <a id="time_off_nav" href="administrareConcedii.jsp" target="frame_02" >
                                    <i class="fa fa-use" data-original-title="" title=""> </i>
                                    ADMINISTRARE CONCEDII
                                    <span class="indicator"> </span>
                                </a>
                            </li>
                            
                            <li class="nav-btn-members">
                                <a id="time_off_nav" href="calendarPersonal.jsp" target="frame_02" >
                                    <i class="fa fa-use" data-original-title="" title=""> </i>
                                    CALENDAR PERSONAL
                                    <span class="indicator"> </span>
                                </a>
                            </li>

                            <%
                                //doar persoanele autorizate au dreptul sa vada CALENDAR FIRMA
                                //directorii au prioritatea 1, iar directori adjuncti au prioritatea 2
                                if (!(((String) session.getAttribute("prioritate")).equals("1") || ((String) session.getAttribute("prioritate")).equals("2"))) {  
                            %>
                            <li class="nav-btn-members">
                                <a id="time_off_nav" href="calendarFirma.jsp" target="frame_02" >
                                    <i class="fa fa-use" data-original-title="" title=""> </i>
                                    CALENDAR FIRMA
                                    <span class="indicator"> </span>
                                </a>
                            </li>
                            <% }
                            %>

                            <%
                                //verific daca utilizatorul este admin 
                                if (((String) session.getAttribute("este_admin")).equals("1")) {
                            %>                           
                            <li class="nav-btn-members"> 
                                <a id="members_nav" href="membriiFirma.jsp" target="frame_02" >
                                    <i class="fa fa-use" data-original-title="" title=""> </i>
                                    MEMBRII FIRMA 
                                    <span class="indicator"> </span>
                                </a>
                            </li>
                            <% }
                            %>
                            
                            <li class="nav-btn-members">
                                <a id="time_off_nav" href="setariProfil.jsp" target="frame_02" >
                                    <i class="fa fa-use" data-original-title="" title=""> </i>
                                    SETARI PROFIL
                                    <span class="indicator"> </span>
                                </a>
                            </li>
                            
                            <li class="nav-btn-members"> 
                                <a id="members_nav" class="active log-out" href="#" onclick="cleanSession()" style="background-color: #b96c88;" >
                                    <i class="fa fa-use" data-original-title="" title=""> </i>
                                    LOG OUT 
                                    <span class="indicator"> </span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </header>
        </div>
    </body>
</html>
