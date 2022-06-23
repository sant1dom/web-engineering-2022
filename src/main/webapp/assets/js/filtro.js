/**
 * Script per il filtro nelle tabelle.
 * @type {jQuery}
 * @Author: Davide De Acetis
 */

//filtro per il tipo di tabella
function filtroTipo(tabella){
    let utenti = $('#utenti-container')
    let collezioni = $("#collezioni-container");
    let dischi = $("#dischi-container");
    let tracce = $("#tracce-container");
    let autori = $("#autori-container");

    tabella = tabella.toLowerCase();

    switch (tabella) {
        case "utenti":
            utenti.show();
            collezioni.hide();
            dischi.hide();
            tracce.hide();
            autori.hide();
            break;
        case "collezioni":
            utenti.hide();
            collezioni.show();
            dischi.hide();
            tracce.hide();
            autori.hide();
            break;
        case "dischi":
            utenti.hide();
            collezioni.hide();
            dischi.show();
            tracce.hide();
            autori.hide();
            break;
        case "tracce":
            utenti.hide();
            collezioni.hide();
            dischi.hide();
            tracce.show();
            autori.hide();
            break;
        case "artisti":
            utenti.hide();
            collezioni.hide();
            dischi.hide();
            tracce.hide();
            autori.show();
            break;
        default:
            utenti.show();
            collezioni.show();
            dischi.show();
            tracce.show();
            autori.show();
            break;
    }
}

//filtro per i dischi
function filtroDischi(val){
    let value = val.toLowerCase();
    if (value === "tutti") {
        value = "";
    }
    $("#table-tbody-dischi tr").filter(function() {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
}

//barra di ricerca
function ricerca (tabella) {
    $("#input-filtro").on("keyup", function (tabella) {
        var value = $(this).val().toLowerCase();
        $(tabella + " tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
}
