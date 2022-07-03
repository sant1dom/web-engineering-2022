$(document).ready(function () {
    $('#autore').selectpicker();
    $('#genere').selectpicker();
    $('#etichetta').selectpicker();

    $('#autore').change(function () {
        if ($(this).val() !== "") {
            let autore = $("#autore option:selected").text();
            $.ajax({
                url: 'http://localhost:8080/stats?action=autore&autore=' + autore,
                type: 'GET',
                success: function (data) {
                    $("#nda").text("Numero di dischi per l'autore selezionato: " + data);
                    $("#nda").css("display", "");
                }
            });
        } else {
            $("#nda").css("display", "none");
        }
    });

    $('#genere').change(function () {
        if ($(this).val() !== "") {
            let genere = $("#genere option:selected").text();
            $.ajax({
                url: 'http://localhost:8080/stats?action=genere&genere=' + genere,
                type: 'GET',
                success: function (data) {
                    $("#ndg").text("Numero di dischi per il genere selezionato: " + data);
                    $("#ndg").css("display", "");
                }
            });
        } else {
            $("#ndg").css("display", "none");
        }
    });

    $('#etichetta').change(function () {
        if ($(this).val() !== "") {
            let etichetta = $("#etichetta option:selected").text();
            $.ajax({
                url: 'http://localhost:8080/stats?action=etichetta&etichetta=' + etichetta,
                type: 'GET',
                success: function (data) {
                    $("#nde").text("Numero di dischi per l'etichetta selezionata: " + data);
                    $("#nde").css("display", "");
                }
            });
        } else {
            $("#nde").css("display", "none");
        }
    });

    $('#anno').change(function () {
        if ($(this).val() !== "") {
            let anno = $("#anno").val();
            $.ajax({
                url: 'http://localhost:8080/stats?action=anno&anno=' + anno,
                type: 'GET',
                success: function (data) {
                    $("#ndanno").text("Numero di dischi per l'anno selezionato: " + data);
                    $("#ndanno").css("display", "");
                }
            });
        } else {
            $("#ndanno").css("display", "none");
        }
    });
});