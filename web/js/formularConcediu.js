function saveFormular() {
    var concediu = $('#time_off_time_off_type_entitled:checked').val();
    var medical = $('#time_off_time_off_type_sickness:checked').val();
    var altceva = $('#time_off_time_off_type_discretionary:checked').val();
    var tipConcediu = concediu || medical || altceva; // una din valorile selectate
    var descriere = $('#time_off_name').val();
    var deLaData = $('#time_off_start').val();
    var panaLaData = $('#time_off_end').val();

    $.ajax({
        type: "POST",
        url: "SaveFormularConcediu",
        data: "tipConcediu=" + tipConcediu + "&descriere=" + descriere + "&deLaData=" + deLaData + "&panaLaData=" + panaLaData,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: 'json',

        success: function (data, textStatus) {
            //alert('success');
            window.location.href = "frame2.jsp";
        }
        ,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("EROARE");
            console.log(textStatus);
        }
    });
}

function cancelFormular() {
    //resetare valori
    document.getElementById("time_off_time_off_type_entitled").checked = false;
    document.getElementById("time_off_time_off_type_sickness").checked = false;
    document.getElementById("time_off_time_off_type_discretionary").checked = false;
    document.getElementById("time_off_name").value = "";
    document.getElementById("time_off_working_units").value = "";
    document.getElementById("time_off_start").value = "";
    document.getElementById("time_off_end").value = "";
}

//in loc sa apas pe butonul save, pot apasa tasta enter
$(document).keypress(function (e) {
    if (e.which == 13) {
        saveFormular();
    }
});
