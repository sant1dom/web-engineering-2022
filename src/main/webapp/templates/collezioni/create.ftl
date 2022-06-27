<#include "../outlines/outline_header.ftl">

<div class="page-container page-bg flex justify-center align-items-center">
    <div class="card form-card">
        <div class="card-header">
            <span class="form-title flex justify-center align-items-center">NUOVA COLLEZIONE</span>
        </div>
        <div class="card-body">

            <form method="post" action="create-collezione">
                <#if error??>
                    <div class=text-red style="color: red;">
                        ${error!}
                    </div>
                </#if>

                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" style="width: 100%;">
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
                </div>

                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" style="width: 40%;">
                        <label for="titolo">Titolo</label>
                        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo">
                    </div>
                    <div class="form-group" style="width: 33%;">
                        <label for="privacy">Privacy</label>
                        <select id="privacy" name="privacy" class="selectpicker">
                            <option selected="" value="PRIVATA">PRIVATA</option>
                            <option value="PUBBLICA">PUBBLICA</option>
                            <option value="CONDIVISA">CONDIVISA</option>
                        </select>
                    </div>
                </div>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" id="condivisione" style="width: 40%;">
                        <div class="alert alert-info" >
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <b>AVVISO:</b> Verra' mandata un'email a tutti gli utenti con cui vuoi condividere la collezione.
                        </div>
                        <div style="display: flex; justify-content: space-between">
                            <h4>Condividi con:</h4>
                            <a id="aggiungi-utente"><i class="lni lni-circle-plus"></i></a>
                        </div>
                        <input type="text" class="form-control" id="utente_1" name="utenti[]" placeholder="Username">

                    </div>
                </div>

                <div class="flex justify-center align-items-center pt-3">
                    <button type="submit" class="btn btn-success">
                        SALVA
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>
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

