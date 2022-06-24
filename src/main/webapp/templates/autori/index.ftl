<#-- @ftlvariable name="autore" type="org.webeng.collector_site.data.model.Autore" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->

<#include "../outlines/outline_header.ftl">

<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="page-container">
    <#if (autore??)>
        <div class="info">
            <h3>${autore.getNomeArtistico()}</h3>
            <div class="labels">
                <span class="label-info">Vero nome: </span> <br>
                <span class="label-info">Tipologia: </span>
                <br>
            </div>
            <div class="testi">
                <span class="testo-info">${autore.getNome() + " " + autore.getCognome()}</span> <br>
                <span class="testo-info">${autore.getTipologia()}</span>
            </div>
        </div>
    </#if>
    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <div class="title">FILTRO</div>
            <div class="filtro">
                <input id="input-filtro" type="text" onkeyup="ricerca(this.value, '#table-tbody-autori')"
                       placeholder="Search.." class="input-filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <#include "../outlines/filtro/outline_tipologia.ftl">
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <div class="table-container" id="autori-container">
                <div class="title flex justify-between align-items-center">
                    AUTORI
                </div>
                <#if (autori?? && autori?size > 0)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">Nome</th>
                            <th scope="col">Cognome</th>
                            <th scope="col">Tipologia</th>
                        </tr>
                        </thead>
                        <tbody id="table-tbody-autori">
                        <#list autori as autore>
                            <tr>
                                <td><a class="link"
                                       href="show-autore?id=${autore.getKey()}">${autore.getNomeArtistico()}</a>
                                </td>
                                <td>${autore.getNome() + autore.getCognome()}</td>
                                <td>${autore.getTipologia()?lower_case?cap_first}</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono autori.</div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
