/**
 * Script per i suggerimenti di ricerca nella barra di ricerca.
 * @type {jQuery}
 * @Author: Davide De Acetis
 */

const suggestion_box = $('#suggestion-box');
const search_box = $('#search-box');
const search_form = $('#search-form');
const item_id = $("#item_id");
const item_type = $("#item_type");

//nasconde il box di suggerimenti al caricamento della pagina
$(document).ready(function () {
    suggestion_box.hide();
});

//nasconde il box di suggerimenti quando si clicca fuori dalla barra di ricerca
$('.page_container').click(function () {
    suggestion_box.hide();
});

//mostra il box di suggerimenti quando si clicca sul campo di ricerca e il contenuto del box ha più di 3 caratteri
search_box.focusin(function () {
    if (search_box.val().length >= 3) {
        search_box.trigger('keyup');
        suggestion_box.show();
    }
});

//permette di selezionare il suggerimento, impostare i campi di ricerca e richiamare la ricerca
function selectSuggestion(id, nomeArray, input) {
    search_box.val(input);
    suggestion_box.hide();
    item_id.val(id);
    item_type.val(nomeArray);
    search_form.submit();
}

//permette di selezionare tutti gli elementi di un tipo suggeriti, impostare i campi di ricerca e richiamare la ricerca
function seeAll(nomeArray, input) {
    search_box.val(input);
    suggestion_box.hide();
    item_type.val(nomeArray);
    search_form.submit();
}

/**
 * esegue la chiamata ajax per ottenere i suggerimenti.
 * Se la ricerca è vuota, nasconde il box di suggerimenti.
 * Ogni tipologia di elemento è gestita da un ciclo differente che ne imposta i campi nella suggestion-box.
 * Se ci sono più di 5 suggerimenti, mostra una label che permette di visionare tutti i risultati di quel tipo.
 */

search_box.keyup(function () {
    if (search_box.val().length >= 3) {
        $.ajax({
            url: "result",
            method: "GET",
            data: {keyword: search_box.val()},
            success: function (data) {
                console.log(data[0] + " " + data[1]);
                if (!((data[0] + data[1]) === "[]")) {
                    let obj = $.parseJSON(data);
                    suggestion_box.children().remove();
                    $.each(obj, function (key) {
                        $.each(obj[key], function (nomeArray, contenutoArray) {
                            suggestion_box.append($('<h3 class="dropdown-header">' + nomeArray + '</h3>'));
                            suggestion_box.append($('<div class="dropdown-divider"></div>'));
                            let counter = 0;
                            if (nomeArray === "UTENTI") {
                                $.each(contenutoArray, function (id, dati) {
                                    if (counter < 3) {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item",
                                                onclick: "selectSuggestion('" + id + "', '" + nomeArray + "', '" + dati[0] + "')",
                                                value: id,
                                                text: dati[0],
                                            }));
                                        counter++;
                                    } else {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item dropdown-item-more",
                                                onclick: "seeAll('" + nomeArray + "')",
                                                text: "Vedi tutti i risultati per utenti...",
                                            }));
                                        return false;
                                    }
                                });
                            } else if (nomeArray === "COLLEZIONI") {
                                    $.each(contenutoArray, function (id, dati) {
                                        if (counter < 3) {
                                            suggestion_box.append($('<a>',
                                                {
                                                    class: "dropdown-item",
                                                    onclick: "selectSuggestion('" + id + "', '" + nomeArray + "', '" + dati[0] + "')",
                                                    value: id,
                                                    text: dati,
                                                }));
                                            counter++;
                                        } else {
                                            suggestion_box.append($('<a>',
                                                {
                                                    class: "dropdown-item dropdown-item-more",
                                                    onclick: "seeAll('" + nomeArray + "')",
                                                    text: "Vedi tutti i risultati per collezioni...",
                                                }));
                                            return false;
                                        }
                                    });
                            } else if (nomeArray === "DISCHI") {
                                $.each(contenutoArray, function (id, dati) {
                                    if (counter < 3) {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item",
                                                onclick: "selectSuggestion('" + id + "', '" + nomeArray + "', '" + dati[0] + "')",
                                                value: id,
                                                text: dati[0],
                                            }));
                                        counter++;
                                    } else {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item dropdown-item-more",
                                                onclick: "seeAll('" + nomeArray + "')",
                                                text: "Vedi tutti i risultati per dischi...",
                                            }));
                                        return false;
                                    }
                                });
                            } else if (nomeArray === "TRACCE") {
                                $.each(contenutoArray, function (id, dati) {
                                    if (counter < 3) {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item",
                                                onclick: "selectSuggestion('" + id + "', '" + nomeArray + "', '" + dati + "')",
                                                value: id,
                                                text: dati,
                                            }));
                                        counter++;
                                    } else {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item dropdown-item-more",
                                                onclick: "seeAll('" + nomeArray + "')",
                                                text: "Vedi tutti i risultati per tracce...",
                                            }));
                                        return false;
                                    }
                                });
                            } else if (nomeArray === "AUTORI") {
                                $.each(contenutoArray, function (id, dati) {
                                    if (counter < 3) {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item",
                                                onclick: "selectSuggestion('" + id + "', '" + nomeArray + "', '" + dati[0] + "')",
                                                value: id,
                                                text: dati[0],
                                            }));
                                        counter++;
                                    } else {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item dropdown-item-more",
                                                onclick: "seeAll('" + nomeArray + "')",
                                                text: "Vedi tutti i risultati per autori...",
                                            }));
                                        return false;
                                    }
                                });
                            } else {
                                $.each(contenutoArray, function (chiave, valore) {
                                    suggestion_box.append($('<a>',
                                        {
                                            class: "dropdown-item",
                                            onclick: "selectSuggestion('" + valore + "')",
                                            value: chiave,
                                            text: valore
                                        }));
                                });
                            }
                        });
                        suggestion_box.append($('<div class="dropdown-divider"></div>'));
                    });
                    suggestion_box.show();
                } else {
                    suggestion_box.hide();
                }
            },
            minLength: 2,
            cache: false,
        });
    } else {
        suggestion_box.children().remove();
        suggestion_box.hide();
    }
});

