<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
    //verific ca userul este logat in sistem(= sa existi cu uesr si parola in baza de date)
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
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
        <script type="text/javascript" src="js/formularConcediu.js"></script>
    </head>

    <body class='container-login100' style="background-image: url('images/beach-sunset.jpg');">
    <selection class="ng-scope" ng-app="fabmin" role="main">
        <div id="page-content" class="frame-margine">
            <div class="col-lg-9 timeoff">
                <div class="form-group radio_buttons required time_off_time_off_type">
                    <div class="col-md-3" style="padding-left: 0px">
                        <label class="radio_buttons required control-label">
                            Selecteaza tipul: 
                        </label>
                    </div>

                    <div class="col-md-9">
                        <span class="radio">
                            <label for="time_off_time_off_type_entitled">
                                <input class="radio_buttons required" value="concediu" name="time_off[time_off_type]" id="time_off_time_off_type_entitled" type="radio">
                                Concediu
                            </label>
                        </span>
                        <span class="radio">
                            <label for="time_off_time_off_type_sickness">
                                <input class="radio_buttons required" value="medical" name="time_off[time_off_type]" id="time_off_time_off_type_sickness" type="radio">
                                Medical
                            </label>
                        </span>
                        <span class="radio">
                            <label for="time_off_time_off_type_discretionary">
                                <input class="radio_buttons required" value="altceva" name="time_off[time_off_type]" id="time_off_time_off_type_discretionary" type="radio">
                                Altceva
                            </label>
                        </span>
                    </div>

                    <div class="form-group radio_buttons required time_off_time_off_type">
                        <div class="col-md-3">
                            <label class="radio_buttons required control-label">
                                Descriere: 
                            </label>
                        </div>
                        <div class="col-md-9" style="margin-bottom: 20px">
                            <input placeholder=". . . . ." class="string required form-control" autofocus="autofocus" name="time_off[name]" id="time_off_name" type="text">
                        </div>
                    </div>

                    <div class="form-group date required time_off_occurrence_start_date">
                        <div class="col-md-3">
                            <label class="date required control-label" for="time_off_occurrence_attributes_start_date">
                                De la data:
                            </label>
                        </div>
                        <div class="col-md-9" style="margin-bottom: 20px">
                            <div id="calendar-de-la" class="input-group">
                                <span class="input-group-addon" data-target="#time_off_start" style="width:10%">
                                    <i class="material-icons">date_range</i>
                                </span>                                                                               
                                <input class="string required form-control datepicker time-off-date-selector hasDatepicker" value="" id="time_off_start" autocomplete="off" name="time_off[occurrence_attributes][start_date]" min="NaN-NaN-NaN" max="NaN-NaN-NaN" type="date">
                            </div>
                        </div>
                    </div>

                    <div class="form-group date required time_off_occurrence_start_date">
                        <div class="col-md-3">
                            <label class="date required control-label" for="time_off_occurrence_attributes_start_date">
                                Pana la data:
                            </label>
                        </div>
                        <div class="col-md-9" style="margin-bottom: 20px">
                            <div id="calendar-pana-la" class="input-group">
                                <span class="input-group-addon" data-target="#time_off_start" style="width:10%">
                                    <i class="material-icons">date_range</i>
                                </span>                                                                               
                                <input class="string required form-control datepicker time-off-date-selector hasDatepicker" value="" id="time_off_end" autocomplete="off" name="time_off[occurrence_attributes][start_date]" min="NaN-NaN-NaN" max="NaN-NaN-NaN" type="date">
                            </div>
                        </div>
                    </div> 

                    <button id="save_button" class="btn btn-success" name="button" type="submit" onclick="saveFormular()"> Save </button>
                    <button id="submit_button" class="btn btn-success" name="button" type="submit" onclick="cancelFormular()"> Cancel </button>
                </div>
            </div>
        </div>
    </selection>
</body>
</html>
