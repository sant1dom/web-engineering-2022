
$(document).ready(function () {
    $('#suggestion-box').hide();
});

$('#search-box').keyup(function () {
    let suggestion_box = $('#suggestion-box');
    if ($("#search-box").val().length >= 3) {
        $.ajax({
            url: "result",
            method: "GET",
            data: {keyword: $('#search-box').val()},
            success: function (data) {
                if (data.length > 0) {
                    let obj = $.parseJSON(data);
                    suggestion_box.children().remove();
                    suggestion_box.append($('<h3 class="dropdown-header">UTENTI</h3>'));
                    suggestion_box.append($('<div class="dropdown-divider"></div>'));
                    $.each(obj, function (key, value) {
                        suggestion_box.append($('<a>',
                            {
                                class: "dropdown-item",
                                onclick: "selectSuggestion('" + value + "')",
                                value: key,
                                text: value
                            }));
                    });
                    suggestion_box.append($('<div class="dropdown-divider"></div>'));
                    suggestion_box.show();
                } else {
                    suggestion_box.hide();
                }
            },
            minLength: 2,
            cache: false
        });
    } else {
        suggestion_box.children().remove();
        suggestion_box.hide();
    }
});

function selectSuggestion(val) {
    $("#search-box").val(val);
    $("#suggestion-box").hide();
}