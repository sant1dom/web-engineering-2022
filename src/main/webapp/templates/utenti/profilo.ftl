<#-- @ftlvariable name="collezioni" type="org.webeng.collector_site.data.model.Collezione[]" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="utente_generico" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="keyword" type="String" -->

<#include "../outlines/outline_header.ftl">
<#setting date_format="dd-MM-yyyy">
<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="page-container">
    <div class="info">
        <#if (utente_generico??)>
            <h3>${utente_generico.getUsername()}</h3>
        <#elseif (utente??)>
            <h3>${utente.getUsername()}</h3>
        </#if>

        <div class="labels">
            <#if (utente_generico?? && utente_generico.getNome()?? && utente_generico.getCognome()??)>
                <span class="label-info">Nome completo: </span>
                <br>
            <#elseif (utente?? && utente.getNome()?? && utente.getCognome()??)>
                <span class="label-info">Nome completo: </span>
                <br>
            </#if>
            <span class="label-info">Email: </span>
            <br>
        </div>
        <div class="testi">
            <#if (utente_generico?? && utente_generico.getNome()?? && utente_generico.getCognome()??)>
                <span class="testo-info">${utente_generico.getNome()} ${utente_generico.getCognome()}</span>
                <br>
            <#elseif (utente?? && utente.getNome()?? && utente.getCognome()??)>
                <span class="testo-info">${utente.getNome()} ${utente.getCognome()}</span>
                <br>
            </#if>
            <#if (utente_generico??)>
                <span class="testo-info">${utente_generico.getEmail()}</span>
            <#elseif (utente??)>
                <span class="testo-info">${utente.getEmail()}</span>
            </#if>
            <br>
        </div>

        <#if (utente?? && utente_generico?? && utente.getKey() == utente_generico.getKey())>
            <div class="actions">
                <a class="btn btn-success btn-wd-fixed"
                   href="create-collezione">
                    Aggiungi collezione</a>
                <a class="btn btn-success btn-wd-fixed"
                   href="create-disco">
                    Aggiungi disco</a>
                <a class="btn btn-warning btn-wd-fixed"
                   href="edit-utente?id=${utente.getKey()}">
                    Modifica profilo</a>
            </div>
        </#if>
    </div>

    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <div class="title">FILTRO</div>
            <div class="filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <#include "../outlines/filtro/outline_privacy.ftl">
                        <div class="horizontal-separator filtro-horizontal-separator"></div>
                        <#include "../outlines/filtro/outline_generi.ftl">
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <div class="table-container" id="collezioni-container">
                <div class="title flex justify-between align-items-center">
                    COLLEZIONI
                    <input id="input-filtro" onkeyup="ricerca(this.value, '#table-tbody-collezioni')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
                <#if (collezioni?? && collezioni?size > 0)>
                    <div class="table-scrollable">
                        <table class="table table-borderless table-striped overflow-auto">
                            <thead class="table-dark">
                            <tr>
                                <th scope="col">Titolo</th>
                                <th scope="col">Data creazione</th>
                                <#if (utente?? && utente.getKey() == collezioni[0].getUtente().getKey())>
                                    <th scope="col">Privacy</th>
                                    <th scope="col" style="text-align: center">Azioni</th>
                                </#if>
                            </tr>
                            </thead>
                            <tbody id="table-tbody-collezioni">
                            <#list collezioni as collezione>
                                <tr>
                                    <td><a class="link"
                                           href="show-collezione?id=${collezione.getKey()}">${collezione.getTitolo()}</a>
                                    </td>
                                    <td>${collezione.dataCreazione?date("yyyy-MM-dd")?string("dd-MM-yyyy")}</td>
                                    <#if (utente?? && utente.getKey() == collezioni[0].getUtente().getKey())>
                                        <td>${collezione.getPrivacy()?lower_case?cap_first}</td>
                                        <td class="table-actions">
                                            <a href="show?id=${collezione.getKey()}"
                                               class="btn btn-success">visualizza</a>
                                            <a href="delete-collezione?id=${collezione.getKey()}"
                                            class="btn btn-danger"><i class="lni lni-trash-can"></i></a>
                                        </td>
                                    </#if>
                                </tr>
                            </#list>
                        </table>
                    </div>
                <#else>
                    <div class="table-empty">Non ci sono collezioni.</div>
                </#if>
            </div>

            <div class="table-container" id="dischi-container">
                <div class="title flex justify-between align-items-center">
                    DISCHI
                    <input id="input-filtro" onkeyup="ricerca(this.value, '#table-tbody-dischi')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
                <#if (dischi?? && dischi?size > 0)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">Titolo</th>
                            <th scope="col">Anno</th>
                            <th scope="col">Barcode</th>
                            <th scope="col">Etichetta</th>
                            <th scope="col">Genere</th>
                            <th scope="col">Formato</th>
                            <th scope="col" style="text-align: center">Stato di conservazione</th>
                            <#if (utente?? && utente.getKey() == collezioni[0].getUtente().getKey())>
                                <th scope="col" style="text-align: center">Azioni</th>
                            </#if>
                        </tr>
                        </thead>
                        <tbody id="table-tbody-dischi">
                        <#list dischi as disco>
                            <tr>
                                <#if (disco.getBarCode()??)>
                                    <td>${disco.getBarCode()}</td>
                                <#else>
                                    <td><i class="fa-solid fa-xmark"></i></td>
                                </#if>
                                <td><a class="link"
                                       href="show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a>
                                </td>
                                <td>${disco.anno}</td>
                                <td>${disco.etichetta?cap_first}</td>
                                <td>${disco.genere?lower_case?cap_first}</td>
                                <td>${disco.formato?lower_case?cap_first}</td>
                                <td style="text-align: center">
                                    <#if (disco.getStatoConservazione()??)>
                                        ${disco.statoConservazione?lower_case?cap_first}
                                    <#else>
                                        <i class="fa-solid fa-xmark"></i>
                                    </#if>
                                </td>
                                <td style="text-align: center">
                                    <#if (utente?? && utente.getKey() == collezioni[0].getUtente().getKey())>
                                        <a href="delete-disco?id=${disco.getKey()}"
                                           class="btn btn-danger"><i class="lni lni-trash-can"></i></a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono dischi.</div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
