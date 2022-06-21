$(document).ready(function () {
    $('#suggestion-box').hide();
});

$('#search-box').keyup(function () {
        let suggestion_box = $('#suggestion-box');
        let search_box = $('#search-box');
        if (search_box.val().length >= 3) {
            $.ajax({
                url: "result",
                method: "GET",
                data: {keyword: search_box.val()},
                success: function (data) {
                    if (data.length > 0) {
                        let obj = $.parseJSON(data);
                        suggestion_box.children().remove();
                        $.each(obj, function (key) {
                            $.each(obj[key], function (nomeArray, conenutoArray) {
                                suggestion_box.append($('<h3 class="dropdown-header">' + nomeArray + '</h3>'));
                                suggestion_box.append($('<div class="dropdown-divider"></div>'));
                                console.log(obj)
                                if (nomeArray === "AUTORI") {
                                    $.each(conenutoArray, function (id, dati) {
                                        suggestion_box.append($('<a>',
                                            {
                                                class: "dropdown-item",
                                                onclick: "selectSuggestion('" + id + ", " + nomeArray + ", " + dati[0] + "')",
                                                value: id,
                                                text: dati[0] + " " + dati[1],
                                            }));
                                    });
                                } else {
                                    $.each(conenutoArray, function (chiave, valore) {
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
                            suggestion_box.show();
                        });
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
    }
)
;

function selectSuggestion(id, nomeArray, val) {
    $("#search-box").val(val);
    $("#suggestion-box").hide();
    $("#item_id").val(id);
    $("#item_type").val(nomeArray);
    $("#search-form").submit()
}