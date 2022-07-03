$(document).ready(function () {
    const autore = $('#autore');
    const genere = $('#genere');
    const etichetta = $('#etichetta')
    const anno = $('#anno');

    autore.selectpicker();
    genere.selectpicker();
    anno.selectpicker();

    autore.change(function () {
        let nda = $("#nda");
        if ($(this).val() !== "") {
            let autore = $("#autore option:selected").text();
            $.ajax({
                url: 'stats?action=autore&autore=' + autore,
                type: 'GET',
                success: function (data) {
                    nda.text("Numero di dischi per l'autore selezionato: " + data);
                    nda.css("display", "");
                }
            });
        } else {
            nda.css("display", "none");
        }
    });

    genere.change(function () {
        let nda = $("#ndg");
        if ($(this).val() !== "") {
            let genere = $("#genere option:selected").text();
            $.ajax({
                url: 'stats?action=genere&genere=' + genere,
                type: 'GET',
                success: function (data) {
                    nda.text("Numero di dischi per il genere selezionato: " + data);
                    nda.css("display", "");
                }
            });
        } else {
            nda.css("display", "none");
        }
    });

    etichetta.change(function () {
        let nde = $("#nde");
        if ($(this).val() !== "") {
            let etichetta = $("#etichetta option:selected").text();
            $.ajax({
                url: 'stats?action=etichetta&etichetta=' + etichetta,
                type: 'GET',
                success: function (data) {
                    nde.text("Numero di dischi per l'etichetta selezionata: " + data);
                    nde.css("display", "");
                }
            });
        } else {
            nde.css("display", "none");
        }
    });

    anno.change(function () {
        let ndanno = $("#ndanno");
        if ($(this).val() !== "") {
            let anno = $("#anno").val();
            $.ajax({
                url: 'stats?action=anno&anno=' + anno,
                type: 'GET',
                success: function (data) {
                    ndanno.text("Numero di dischi per l'anno selezionato: " + data);
                    ndanno.css("display", "");
                }
            });
        } else {
            ndanno.css("display", "none");
        }
    });
});