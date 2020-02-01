$(function() {
    //initializare calendar(adaugare in pagina)
    $('#calendar').fullCalendar({
        header: {
            left: '',
            center: 'title',
            right: 'today,month,agendaWeek,prev,next'
        },
        height: 650,
        defaultView: 'month'
    });

    $.ajax({
        type: "GET",
        url: "CalendarPersonal",
        data: "id=1",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: 'json',

        success: function (data, textStatus) {
            populeazaCalendar(data);
        }
        ,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("EROARE");
            console.log(textStatus);
        }
    });

    //pt fiecare concediu din BD il adaug in calendar
    function populeazaCalendar(data) {
        console.log(data);
        data.concedii.forEach((c) => {
            //pe pozitia 3 e data de inceput si pe 4 cea de sfarsit al conceidului
            $('#calendar').fullCalendar('renderEvent', {title: 'Concediu', start: c[3], end: c[4]}, true);
        });
    }  
});
