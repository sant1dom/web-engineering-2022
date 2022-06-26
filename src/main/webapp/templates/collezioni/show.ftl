<#-- @ftlvariable name="collezione" type="org.webeng.collector_site.data.model.Collezione" -->
<#-- @ftlvariable name="proprietario" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
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
                    <div class="actions">
                        <a class="btn btn-success btn-wd-fixed"
                           href="update-collezione?id_collezione=${collezione.getKey()}">
                            Modifica</a>
                        <a class="btn btn-warning btn-wd-fixed"
                           href="add-dischiCollezione?id_collezione=${collezione.getKey()}">
                            Aggiungi disco</a>
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

    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">

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
                                           href="delete-discoCollezione?id_disco=${disco.getKey()}&id_collezione=${collezione.getKey()}">
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
            </div>

            <#if (collezione.getPrivacy() = "CONDIVISA")>
                <div class="table-container">
                    <#if (proprietario??)>
                    <#if (utente?? && proprietario.getKey() == utente.getKey())>
                    <div class="title">CONDIVISIONE</div>
                    <#if (utenti_condivisi?? && utenti_condivisi?size>0)>
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
                                    <td><a class="link" href="profilo?id=${user.getKey()}">${user.getUsername()}</a></td>
                                    <td class="table-actions">
                                        <a class="btn btn-danger"
                                           href="delete-utenteCondiviso?id_utenteCondiviso=${user.getKey()}&id_collezione=${collezione.getKey()}">
                                            rimuovi utente</a>
                                    </td>
                                </tr>
                            </#list>
                        </table>
                        <#else>
                            <div class="table-empty">Collezione condivisa con nessun utente</div>
                        </#if>
                        </#if>
                        </#if>

                    </div>
                </div>
                </#if>
            </div>
        </div>
    </div>
