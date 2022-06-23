<#include "../outlines/outline_header.ftl">

<link href="/assets/css/show.css" rel="stylesheet"/>

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
                           href="update-dischiCollezione?id_collezione=${collezione.getKey()}">
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
                        <tbody id="tbody-dischi">
                        <#list dischi as disco>
                            <tr>
                                <td>${disco.getBarCode()}</td>
                                <td><a class="link" href="show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a></td>
                                <td>${disco.getAnno()}</td>
                                <td>${disco.getEtichetta()}</td>
                                <td>${disco.getGenere()}</td>
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
        </div>
    </div>
</div>
