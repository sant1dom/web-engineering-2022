<#-- @ftlvariable name="collezioni" type="org.webeng.collector_site.data.model.Collezione[]" -->
<#-- @ftlvariable name="keyword" type="String" -->

<#include "../outlines/outline_header.ftl">
<#setting date_format="dd-MM-yyyy">

<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="page-container">
    <div class="info">
        <h3>Lista delle collezioni</h3>
        <#if (keyword?? && keyword != "")>
            <div class="labels">
                <span class="label-info">Hai cercato: </span>
                <br>
            </div>
            <div class="testi">
                <span class="testo-info">${keyword}</span>
            </div>
        </#if>
        <#if (utente??)>
            <div class="actions">
                <a class="btn btn-success btn-wd-fixed"
                   href="create-collezione">
                    Aggiungi collezione</a>
            </div>
        </#if>
    </div>

    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <div class="title">FILTRO</div>
            <div class="filtro">
                <input id="input-filtro" type="text" onkeyup="ricerca(this.value, '#table-tbody-collezioni')"
                       placeholder="Search.." class="input-filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <dt class="filtro-subtitle">PRIVACY COLLEZIONE:</dt>

                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>

                        <dd><span class="filtro-link" onclick="ricerca('', '#table-tbody-collezioni')">TUTTI</span></dd>
                        <dd><span class="filtro-link" onclick="ricerca('PUBBLICO', '#table-tbody-collezioni')">PUBBLICO</span></dd>
                        <dd><span class="filtro-link" onclick="ricerca('CONDIVISO', '#table-tbody-collezioni')">CONDIVISE CON ME</span></dd>
                        <dd><span class="filtro-link" onclick="ricerca('PRIVATO', '#table-tbody-collezioni')">PRIVATO</span></dd>
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <div class="table-container" id="collezioni-container">
                <div class="title flex justify-between align-items-center">
                    COLLEZIONI
                </div>
                <#if (collezioni ?? && collezioni?size>0)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">Titolo</th>
                            <th scope="col">Data creazione</th>
                            <th scope="col">Proprietario</th>
                            <th scope="col">Privacy</th>
                        </tr>
                        </thead>
                        <tbody id="table-tbody-collezioni">
                        <#list collezioni as collezione>
                            <tr>
                                <td><a class="link" href="show-collezione?id=${collezione.key}">${collezione.titolo}</a>
                                </td>
                                <td>${collezione.dataCreazione?date("yyyy-MM-dd")?string("dd-MM-yyyy")}</td>
                                <td><a class="link"
                                       href="profilo?id=${collezione.getUtente().getKey()}">${collezione.getUtente().getUsername()}</a>
                                </td>
                                <td>${collezione.getPrivacy()}</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono collezioni.</div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>