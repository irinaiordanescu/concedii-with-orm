function doLogin() {
    // alert("dologin");
    var userName = $('#username').val();
    var password = $('#password').val();

    //alert("username " + userName + " password " + password);
    $.ajax({
        type: "POST",
        url: "Login",
        data: "username=" + userName + "&password=" + password,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: 'json',

        success: function (data, textStatus) {
            var user = data;
            if (user.este_admin == "1") {
                window.location.href = "paginaAdmin.jsp";
                return;
            }
            window.location.href = "frame.jsp";
        }
        ,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("EROARE");
            console.log(textStatus);
        }
    });
}

$(document).keypress(function (e) { //dupa ce scriu in pagina de login si dau enter(=echivalent cu a apasa pe butonul de login) ma duce mai departe
    if (e.which == 13) {
        doLogin();
    }
});

function cleanSession() {
    $.ajax({
        type: "post",
        url: "Logout",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: 'json',
        success: function (data, textStatus) {

        }}
    )
    top.location.href = "http://localhost:8080/Concedii/paginaLogin.html/"; //redirectionez catre pag principala
}
