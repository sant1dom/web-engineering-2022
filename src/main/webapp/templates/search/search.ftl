<#include "../outlines/outline_header.ftl">

<link href="/assets/css/show.css" rel="stylesheet"/>

<div class="page-container">
    <#if (keyword??)>
        <div class="info">
            <h3>Ricerca</h3>
            <div class="labels">
                <span class="label-info">Keyword: </span>
                <br>
            </div>
            <div class="testi">
                <span class="testo-info">${keyword}</span>
            </div>
        </div>
    </#if>
    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <div class="title">FILTRO</div>
            <div class="filtro">
                <input id="input-filtro" type="text" placeholder="Search.." class="input-filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <dt class="filtro-subtitle">GENERE:</dt>

                        <div class="horizontal-separator filtro-horizontal-separator"></div>

                        <dd><span class="filtro-link" onclick="filtro('TUTTI')">TUTTI</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('POP')">POP</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('ROCK')">ROCK</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('JAZZ')">JAZZ</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('CLASSIC')">CLASSIC</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('METAL')">METAL</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('RAP')">RAP</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('BLUES')">BLUES</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('PUNK')">PUNK</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('RAGGAE')">REGGAE</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('COUNTRY')">COUNTRY</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('HIPHOP')">HIPHOP</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('ELECTRONIC')">ELECTRONIC</span></dd>
                        <dd><span class="filtro-link" onclick="filtro('OTHER')">OTHER</span></dd>
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <!-- TABELLA UTENTI -->
            <div class="table-container">
                <div class="title">UTENTI</div>
                <#if (utenti?? && utenti?size > 0)>
                    <div class="table-scrollable">
                        <table class="table table-borderless table-striped overflow-auto">
                            <thead class="table-dark">
                            <tr>
                                <th scope="col">Username</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Cognome</th>
                            </tr>
                            </thead>
                            <tbody id="tbody-dischi">
                            <#list utenti as utente>
                                <tr>
                                    <td><a class="link" href="profilo?id=${utente.getKey()}">${utente.getUsername()}</a>
                                    </td>
                                    <td>${utente.getNome()!"N/A"}</td>
                                    <td>${utente.getCognome()!"N/A"}</td>
                                </tr>
                            </#list>
                        </table>
                    </div>
                <#else>
                    <div class="table-empty">Non ci sono utenti.</div>
                </#if>
            </div>
            <!-- TABELLA COLLEZIONI -->
            <div class="table-container">
                <div class="title">COLLEZIONI</div>
                <#if (collezioni?? && collezioni?size > 0)>
                    <div class="table-scrollable">
                        <table class="table table-borderless table-striped overflow-auto">
                            <thead class="table-dark">
                            <tr>
                                <th scope="col">Titolo</th>
                                <th scope="col">Proprietario</th>
                            </tr>
                            </thead>
                            <tbody id="tbody-dischi">
                            <#list collezioni as collezione>
                                <tr>
                                    <td><a class="link"
                                           href="show-collezione?id=${collezione.getKey()}">${collezione.getTitolo()}</a>
                                    </td>
                                    <td><a class="link"
                                           href="profilo?id=${collezione.getUtente().getKey()}">${collezione.getUtente().getUsername()}</a>
                                    </td>
                                </tr>
                            </#list>
                        </table>
                    </div>
                <#else>
                    <div class="table-empty">Non ci sono collezioni.</div>
                </#if>
            </div>

            <div class="table-container">
                <div class="title">DISCHI</div>
                <#if (dischi?? && dischi?size > 0)>
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
                            <tbody id="tbody-dischi">
                            <#list dischi as disco>
                                <tr>
                                    <td>${disco.getBarCode()}</td>
                                    <td><a class="link" href="show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a>
                                    </td>
                                    <td>${disco.getAnno()}</td>
                                    <td>${disco.getEtichetta()}</td>
                                    <td>${disco.getGenere()}</td>
                                </tr>
                            </#list>
                        </table>
                    </div>
                <#else>
                    <div class="table-empty">Non ci sono dischi.</div>
                </#if>
            </div>
            <div class="table-container">
                <div class="title">TRACCE</div>
                <#if (tracce?? && tracce?size > 0)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">ISWC</th>
                            <th scope="col">Titolo</th>
                            <th scope="col">Durata</th>
                        </tr>
                        </thead>
                        <tbody id="tbody-dischi">
                        <#list tracce as traccia>
                            <tr>
                                <td>${traccia.getISWC()}</td>
                                <td><a class="link"
                                       href="show-traccia?id=${traccia.getKey()}">${traccia.getTitolo()}</a>
                                </td>
                                <td>${traccia.getDurata()}</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono tracce.</div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
