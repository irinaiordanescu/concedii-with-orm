//adauga tabelul cu concedii pentru a le putea vedea, sterge
//daca exista perioade de concedii => apar nr de zile ocupate si ramase

$(function () {
    $.ajax({
        type: "GET",
        url: "CalendarPersonal",
        data: "id=1",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: 'json',

        success: function (data, textStatus) {
            var zileOcupate = 0;
            //pt fiecare concediu (f) din vector am 5 variabile
            //f[2], f[3],f[0], f[1], f[4] => asta este ordinea lor din baza de date, tabela formular_concediu
            data.concedii.forEach((f) => {
                var id = f[0];
                var descriere = f[1];
                var tipConcediu = f[2];
                var deLaData = f[3];
                var panaLaData = f[4];

                zileOcupate += (new Date(panaLaData) - new Date(deLaData)) / (24 * 60 * 60 * 1000); //calculez zilele ocupate din toate concediile
                var element = `<tr>`; //creez elem in html cu datele mele; daca nu il creez nu pot vedea randul in tabel
                element += "<td>" + tipConcediu + "</td>";
                element += "<td>" + descriere + "</td>";
                element += "<td>" + deLaData + "</td>";
                element += "<td>" + panaLaData + "</td>";
                element += `"<td><a class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="" data-original-title="Delete" onClick="stergeFormularConcediu(` + id + `); ">&#xE872;</i></a></td></tr>`; //buton de stergere
                $("#concedii").append(element); //adaug elementul(ca si rand) in tabel
            })
            $("#zileLibereOcupate").text("Zile libere ocupate: " + zileOcupate); //afisez zilele ocupate in pagina
            $("#zileLibereRamase").text("Zile libere ramase: " + (30 - zileOcupate)); // afisez zilele ramasa in pagina
        }
        ,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("EROARE");
            console.log(textStatus);
        }
    });
})

function stergeFormularConcediu(id) {
    if (confirm("Sunteti sigur ca stergeti acest concediu?")) {
        $.ajax({
            type: "POST",
            url: "CalendarPersonal",
            data: "id=" + id + "&type=DELETE",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: 'json',

            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("error");
            }
        });
    } else {
        // Do nothing!
    }
    window.location.href = "administrareConcedii.jsp";
}