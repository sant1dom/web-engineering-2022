<#include "../outlines/outline_header.ftl">
<link href="/assets/css/auth.css" rel="stylesheet"/>
<h2 class="ml-5 mt-3">Crea una nuova collezione</h2>

<form method="post" action="create-collezione" class="ml-5 mt-3">
    <#if error??>
        <div class=text-red>
            ${error!}
        </div>
    </#if>

    <div class="form-group col-7">
        <label for="disco">Dischi</label>
        <#if dischi??>
            <select id="disco" name="disco" class="selectpicker" required multiple data-live-search="true">
                <#list dischi as disco>
                    <option value="${disco.key}">${disco.titolo} | ${disco.anno} | ${disco.etichetta} | ${disco.genere}  </option>
                </#list>
            </select>
        <#else>
            <p>
                Non ci sono dischi
            </p>
        </#if>
    </div>
    <div class="form-group col-7">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo">
    </div>

    <div class="form-group col-7">
        <label for="privacy">Privacy</label>
        <select id="privacy" name="privacy" class="selectpicker" required>
            <option selected="" value="">Scegli un'opzione</option>
            <option value="PRIVATA">PRIVATA</option>
            <option value="PUBBLICA">PUBBLICA</option>
            <option value="CONDIVISA">CONDIVISA</option>
        </select>
    </div>
    <div class="form-group" id="condivisione">
        <div style="display: flex; justify-content: space-between">
            <h4>Condividi con:</h4>
            <a id="aggiungi-utente"><i class="lni lni-circle-plus"></i></a>
        </div>
        <input type="text" class="form-control" id="utente_1" name="utenti[]" placeholder="Username">

    </div>

    <div class="form-group col-3">
        <button type="submit" class="btn btn-primary">Salva</button>
    </div>

</form>

<script>
    let id = 1;
    $(window).on('load', function () {
        $('.selectpicker').selectpicker();


        $('#privacy').on('change', function () {
            if ($(this).val() === 'CONDIVISA') {
                $('#condivisione').show();
            } else {
                $('#condivisione').hide();
            }
        });

    });
    $('#aggiungi-utente').on('click', function () {
        $('#condivisione').append(
            '<input type="text" class="form-control" id="utente_' + ++id + '" name="utenti[]" placeholder="Username">').append($('<a>',
            {
                id: 'rimuovi-utente_' + id,
                onclick: "rimuovi('" + id + "')",
            }
        ).html('<i class="lni lni-circle-minus"></i>'));


    });

    function rimuovi(id_u) {
        $('#utente_' + id_u).remove();
        $('#rimuovi-utente_' + id_u).remove();
        id--;
    }
</script>

<style>
    #condivisione {
        display: none;
    }
</style>

