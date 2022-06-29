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
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <dt class="filtro-subtitle">TIPO:</dt>

                        <div class="horizontal-separator filtro-horizontal-separator"></div>

                        <dd><span class="filtro-link" onclick="filtroTipo('TUTTI')">TUTTI</span></dd>
                        <dd><span class="filtro-link" onclick="filtroTipo('DISCHI' )">DISCHI</span></dd>
                        <dd><span class="filtro-link" onclick="filtroTipo('TRACCE' )">TRACCE</span></dd>

                        <div class="resp991">
                            <div class="horizontal-separator filtro-horizontal-separator"></div>
                            <#include "../outlines/filtro/outline_generi.ftl">
                        </div>
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <div class="table-container" id="dischi-container">
                <div class="title flex justify-between align-items-center">
                    DISCHI
                    <input id="input-filtro" onkeyup="ricerca(this.value, '#table-tbody-dischi')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
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
                        </tr>
                        </thead>
                        <tbody id="table-tbody-dischi">
                        <#list dischi as disco>
                            <tr>
                                <td>${disco.getBarCode()}</td>
                                <td><a class="link" href="show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a></td>
                                <td>${disco.getAnno()}</td>
                                <td>${disco.getEtichetta()}</td>
                                <td>${disco.getGenere()}</td>
                            </tr>
                        </#list>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono dischi.</div>
                    </#if>
                </div>
            </div>
            <div class="table-container" id="tracce-container">
                <div class="title flex justify-between align-items-center">
                    TRACCE
                    <input id="input-filtro" onkeyup="ricerca(this.value, '#table-tbody-tracce')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
                <#if (tracce??)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">ISWC</th>
                            <th scope="col">Titolo</th>
                            <th scope="col">Durata</th>
                        </tr>
                        </thead>
                        <tbody id="table-tbody-tracce">
                        <#list tracce as traccia>
                            <tr>
                                <td>${traccia.getISWC()}</td>
                                <td>${traccia.getTitolo()}</td>
                                <td>${traccia.getDurata()}</td>
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
