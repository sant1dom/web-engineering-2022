<#-- @ftlvariable name="collezioni" type="org.webeng.collector_site.data.model.Collezione[]" -->
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
                <input id="input-filtro" type="text" onkeyup="ricerca(this.value, '#table-tbody-tracce')"
                       placeholder="Search.." class="input-filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <#include "../outlines/filtro/outline_privacy.ftl">
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <div class="table-container" id="collezioni-container">
                <div class="title flex justify-between align-items-center">
                    COLLEZIONI
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
                                            <a href="delete-collezione?id_collezione=${collezione.getKey()}"
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
        </div>
    </div>
</div>
