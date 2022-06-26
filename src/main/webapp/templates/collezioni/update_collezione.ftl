<#-- @ftlvariable name="collezione" type="org.webeng.collector_site.data.model.Collezione" -->
<#-- @ftlvariable name="utenti_condivisi" type="org.webeng.collector_site.data.model.Utente[]" -->

<#include "../outlines/outline_header.ftl">
<link href="/assets/css/auth.css" rel="stylesheet"/>

<h2 class="ml-5 mt-3">Modifica collezione</h2>


<form method="post" action="update-collezione?id_collezione=${collezione.key}" class="ml-5 mt-3">
    <#if error??>
        <div class=text-red>
            ${error!}
        </div>
    </#if>

    <div class="form-group col-7">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" value="${collezione.titolo}" id="titolo" name="titolo">
      </div>
        <div class="form-group col-7">
            <label for="privacy">Privacy</label>
            <select id="privacy" name="privacy" class="selectpicker">
                <#if (collezione.privacy="PRIVATA")>
                    <option selected="selected" value="PRIVATA">PRIVATA</option>
                <#else>
                    <option value="PRIVATA">PRIVATA</option>
                </#if>
                <#if (collezione.privacy="PUBBLICA")>
                    <option selected="selected" value="PUBBLICA">PUBBLICA</option>
                <#else>
                    <option value="PUBBLICA">PUBBLICA</option>
                </#if>
                <#if (collezione.privacy="CONDIVISA")>
                    <option selected="selected" value="CONDIVISA">CONDIVISA</option>
                <#else>
                    <option value="CONDIVISA">CONDIVISA</option>
                </#if>

            </select>
           </div>
          <div class="form-group col-7">
              <#if collezione.privacy="CONDIVISA">
                  <h4>Collezione condivisa con:</h4>
                    <#if (utenti_condivisi??)>
                    <#list utenti_condivisi as user>
                        ${user.username}
                    </#list>
                    <#else>
                     <p>nessun utente</p>
                    </#if>
                </#if>
          </div>

            <div class="form-group" id="condivisione">
                <div style="display: flex; justify-content: space-between">
                    <h4>Aggiungi utenti per la condivisione:</h4>
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
        if ($('#privacy').val() === 'CONDIVISA') {
            $('#condivisione').show();
        }


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