/**
 * Script per il filtro nelle tabelle.
 * @type {jQuery}
 * @Author: Davide De Acetis
 */

//filtro per il tipo di tabella
function filtroTipo(tabella) {
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
        case "autori":
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

//filtro per le tabelle
function ricerca(val, tabella_id) {
    val = val.toLowerCase();
    $(tabella_id + " tr").filter(function () {
        $(this).toggle($(this).text().toLowerCase().indexOf(val) > -1)
    });
}
