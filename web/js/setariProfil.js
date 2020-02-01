$(function () {
    $.ajax({
        type: "GET",
        url: "SetareProfil",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: 'json',
        success: function (data) {
            if(data.utilizator) {
                var username = data.utilizator.username;
                $("#editeazaUsername").val(username);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
})

function salveazaSchimbari() {
    if (confirm("Doriti sa salvati schimbarile?")) {
        var username = $("#editeazaUsername").val();
        var parola = $("#editeazaParola").val();
        var verificareParola = $("#editeazaVerificareParola").val();

        if (parola != verificareParola) {
            alert("Cele 2 parole nu coincid");
            return;
        }

        $.ajax({
            type: "POST",
            url: "SetareProfil",
            data: "username=" + username + "&password=" + parola,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: 'json',
            success: function () {
                alert("Modificat cu success");
            }
            ,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}