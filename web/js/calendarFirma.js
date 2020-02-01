$(function () {
    var colorArray = ['#FF6633', '#FFB399', '#FF33FF', '#FFFF99', '#00B3E6',
        '#E6B333', '#3366E6', '#999966', '#99FF99', '#B34D4D',
        '#80B300', '#809900', '#E6B3B3', '#6680B3', '#66991A',
        '#FF99E6', '#CCFF1A', '#FF1A66', '#E6331A', '#33FFCC',
        '#66994D', '#B366CC', '#4D8000', '#B33300', '#CC80CC',
        '#66664D', '#991AFF', '#E666FF', '#4DB3FF', '#1AB399',
        '#E666B3', '#33991A', '#CC9999', '#B3B31A', '#00E680',
        '#4D8066', '#809980', '#E6FF80', '#1AFF33', '#999933',
        '#FF3380', '#CCCC00', '#66E64D', '#4D80CC', '#9900B3',
        '#E64D66', '#4DB380', '#FF4D4D', '#99E6E6', '#6666FF'];
    var culoriUseri = new Object();
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
        url: "CalendarFirma",
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

    function populeazaCalendar(data) {
        //returneaza din vector cate un JSON si il pune in alt vector
        if (data.concedii) {
            data.concedii.map((c) => {
                return JSON.parse(c);
            }).forEach((c) => {
                //pt fiecare val JSON din noul vector se populeaza calendarul
                var username = Object.keys(c)[0];
                var titlu = "Concediul angajatului: " + username;
                var perioadaStart = c[username][0];
                var perioadaEnd = c[username][1];
                var culoare = generateColor(username);
                $('#calendar').fullCalendar('renderEvent', {title: titlu, start: perioadaStart, end: perioadaEnd, color: culoare}, true);
            });
        }
    }

    function generateColor(username) {
        var useri = Object.keys(culoriUseri); //se aleg culorile pt useri
        var exista = useri.filter((u) => u === username).length > 0; //se verifica daca culorile sunt deja folosite sau nu
        if (exista) { //dc exista foloseste culoarea
            return culoriUseri[username];
        } else { //daca nu exista alege o culoare la intamplare(nefolosita)
            var culoare = colorArray.shift();
            culoriUseri[username] = culoare;
            return culoare;
        }
    }
});
