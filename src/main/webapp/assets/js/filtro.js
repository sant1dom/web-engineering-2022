function filtro(val){
    let value = val.toLowerCase();
    if (value === "tutti") {
        value = "";
    }
    $("#tbody-dischi tr").filter(function() {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
}

$(document).ready(function(){
    $("#input-filtro").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#tbody-dischi tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});