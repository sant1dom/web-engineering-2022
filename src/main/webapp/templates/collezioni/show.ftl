<#-- @ftlvariable name="collezione" type="org.webeng.collector_site.data.model.Collezione" -->
<#-- @ftlvariable name="proprietario" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="dischiAdd" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="utenti_condivisi" type="org.webeng.collector_site.data.model.Utente[]" -->

<#include "../outlines/outline_header.ftl">

<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="page-container">
    <#if (collezione??)>
        <div class="info">
            <h3>${collezione.getTitolo()}</h3>
            <#if (proprietario??)>
                <#if (utente?? && proprietario.getKey() == utente.getKey())>
                    <div class="labels">
                        <span class="label-info">Privacy: </span>
                        <br>
                    </div>
                    <div class="testi">
                        <span class="testo-info">${collezione.getPrivacy()}</span>
                    </div>
                <#else>
                    <div class="labels">
                        <span class="label-info">Proprietario: </span>
                        <br>
                    </div>
                    <div class="testi">
                    <span class="testo-info">
                        <a class="link" href="profilo?id=${proprietario.getKey()}">${proprietario.getUsername()}</a>
                    </span>
                    </div>
                </#if>
            </#if>
        </div>
    </#if>
    <#if error??>
        <div class=text-red>
            ${error!}
        </div>
    </#if>
    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <#if (utente?? && utente.getKey() == collezione.getUtente().getKey())>
                <div class="title">AZIONI</div>
                <div class="filtro mb-5">
                    <div style="width: 100%; padding: 1rem">
                        <button class="btn btn-success" id="edit-collezione-btn" style="width: 100%;">Aggiorna
                            collezione
                        </button>
                        <form method="post" action="edit-collezione?id=${collezione.getKey()}" id="edit-collezione">
                            <div class="horizontal-separator filtro-horizontal-separator"></div>
                            <label for="privacy">Modifica le informazioni:</label><br>
                            <label for="titolo">Titolo</label>
                            <input type="text" class="form-control" value="${collezione.getTitolo()}" id="titolo"
                                   name="titolo">
                            <label for="privacy" class="mt-2">Modifica le informazioni:</label>
                            <select id="privacy" name="privacy" class="selectpicker">
                                <#if (collezione.getPrivacy()="PRIVATA")>
                                    <option selected="selected" value="PRIVATA">PRIVATA</option>
                                <#else>
                                    <option value="PRIVATA">PRIVATA</option>
                                </#if>
                                <#if (collezione.getPrivacy()="PUBBLICA")>
                                    <option selected="selected" value="PUBBLICA">PUBBLICA</option>
                                <#else>
                                    <option value="PUBBLICA">PUBBLICA</option>
                                </#if>
                                <#if (collezione.getPrivacy()="CONDIVISA")>
                                    <option selected="selected" value="CONDIVISA">CONDIVISA</option>
                                <#else>
                                    <option value="CONDIVISA">CONDIVISA</option>
                                </#if>
                            </select>
                            <div class="flex justify-center mt-3" style="width: 100%;">
                                <button type="submit" class="btn btn-warning">Aggiorna</button>
                            </div>
                        </form>
                    </div>
                    <div class="horizontal-separator filtro-horizontal-separator"></div>
                    <div style="width: 100%; padding: 1rem">
                        <button class="btn btn-success" id="add-dischi-btn" style="width: 100%;">Aggiungi dischi
                        </button>
                        <form method="post" action="add-dischi?id=${collezione.getKey()}" id="add-dischi">
                            <div class="horizontal-separator filtro-horizontal-separator"></div>
                            <label for="dischi">Seleziona i dischi:</label>
                            <#if (dischiAdd?? && dischiAdd?size>0)>
                                <select id="dischi" name="dischi[]" class="selectpicker" multiple
                                        data-live-search="true">
                                    <#list dischiAdd as disco>
                                        <option value="${disco.key}">${disco.titolo} | ${disco.anno}</option>
                                    </#list>
                                </select>
                                <div class="flex justify-center mt-3" style="width: 100%;">
                                    <button type="submit" class="btn btn-warning">Aggiungi</button>
                                </div>
                            <#else>
                                <p>Non ci sono dischi</p>
                            </#if>
                        </form>
                    </div>
                    <#if (collezione.getPrivacy() == "CONDIVISA")>
                        <div class="horizontal-separator filtro-horizontal-separator"></div>
                        <div style="width: 100%; padding: 1rem">
                            <button class="btn btn-success" id="share-collezione-btn" style="width: 100%;">Condividi
                            </button>
                            <form method="post" action="edit-collezione?id=${collezione.getKey()}"
                                  id="share-collezione">
                                <div class="horizontal-separator filtro-horizontal-separator"></div>
                                <label for="user_share">Inserire Username utente:</label>
                                <input type="text" class="form-control" id="user_share"
                                       name="user_share" required>
                                <div class="flex justify-center mt-3" style="width: 100%;">
                                    <button type="submit" class="btn btn-warning">Condividi</button>
                                </div>
                            </form>
                        </div>
                    </#if>
                </div>
            </#if>

            <div class="title">FILTRO</div>
            <div class="filtro">
                <input id="input-filtro" type="text" onkeyup="ricerca(this.value, '#table-tbody-dischi')"
                       placeholder="Search.." class="input-filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <div class="resp991">
                            <div class="horizontal-separator filtro-horizontal-separator"></div>
                            <#include "../outlines/filtro/outline_generi.ftl">
                        </div>
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container" id="dischi-container">
            <div class="table-container">
                <div class="title">DISCHI</div>
                <#if (dischi??)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped overflow-auto">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">Barcode</th>
                            <th scope="col">Titolo</th>
                            <th scope="col">Anno</th>
                            <th scope="col">Etichetta</th>
                            <th scope="col">Genere</th>
                            <#if (utente?? && proprietario.getKey() == utente.getKey())>
                                <th scope="col" style="text-align: center">Azioni</th>
                            </#if>
                        </tr>
                        </thead>
                        <tbody id="table-tbody-dischi">
                        <#list dischi as disco>
                            <tr>
                                <td>${disco.getBarCode()}</td>
                                <td><a class="link" href="show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a></td>
                                <td>${disco.getAnno()}</td>
                                <td>${disco.getEtichetta()}</td>
                                <td>${disco.getGenere()?lower_case?cap_first}</td>
                                <#if (utente?? && proprietario.getKey() == utente.getKey())>
                                    <td class="table-actions">
                                        <a class="btn btn-danger"
                                           href="remove-disco?c=${collezione.getKey()}&d=${disco.getKey()}">
                                            <i class="lni lni-trash-can"></i></a>
                                    </td>
                                </#if>
                            </tr>
                        </#list>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono dischi.</div>
                    </#if>
                </div>

                <#if (utente?? && proprietario.getKey() == utente.getKey() && collezione.getPrivacy() = "CONDIVISA")>
                    <div class="table-container">
                        <div class="title">UTENTI CONDIVISI</div>
                        <#if (utenti_condivisi?? && utenti_condivisi?size > 0)>
                        <div class="table-scrollable">
                            <table class="table table-borderless table-striped overflow-auto">
                                <thead class="table-dark">
                                <tr>
                                    <th scope="col">Username</th>
                                    <th scope="col" style="text-align: center">Azioni</th>
                                </tr>
                                </thead>
                                <tbody id="table-tbody-dischi">
                                <#list utenti_condivisi as user>
                                    <tr>
                                        <td><a class="link"
                                               href="profilo?id=${user.getKey()}">${user.getUsername()}</a>
                                        </td>
                                        <td class="table-actions">
                                            <a class="btn btn-danger"
                                               href="remove-condivisione?c=${collezione.getKey()}&u=${user.getKey()}">
                                                <i class="lni lni-trash-can"></i></a>
                                        </td>
                                    </tr>
                                </#list>
                            </table>
                            <#else>
                                <div class="table-empty">Non ci sono utenti condivisi.</div>
                            </#if>
                        </div>
                    </div>
                </#if>
            </div>
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