<#-- @ftlvariable name="keyword" type="String" -->
<#-- @ftlvariable name="utenti" type="org.webeng.collector_site.data.model.Utente[]" -->
<#-- @ftlvariable name="collezioni" type="org.webeng.collector_site.data.model.Collezione[]" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->
<#-- @ftlvariable name="autori" type="org.webeng.collector_site.data.model.Autore[]" -->

<#include "../outlines/outline_header.ftl">

<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="page-container">

    <div class="info">
        <h3>Ricerca</h3>
        <#if (keyword?? && keyword != "")>
            <div class="labels">
                <span class="label-info">Hai cercato: </span>
                <br>
            </div>
            <div class="testi">
                <span class="testo-info">${keyword}</span>
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
                        <#include "../outlines/filtro/outline_tipo.ftl">
                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <#include "../outlines/filtro/outline_generi.ftl">

                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <#include "../outlines/filtro/outline_formato.ftl">
                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <#include "../outlines/filtro/outline_stato_conservazione.ftl">

                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <#include "../outlines/filtro/outline_tipologia.ftl">
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <!-- TABELLA UTENTI -->
            <div class="table-container" id="utenti-container">
                <div class="title flex justify-between align-items-center">
                    UTENTI
                    <input onkeyup="ricerca(this.value, '#table-tbody-utenti')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
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
                            <tbody id="table-tbody-utenti">
                            <#list utenti as user>
                                <tr>
                                    <td><a class="link" href="profilo?id=${user.getKey()}">${user.getUsername()}</a>
                                    </td>
                                    <td>${user.getNome()!"N/A"}</td>
                                    <td>${user.getCognome()!"N/A"}</td>
                                </tr>
                            </#list>
                        </table>
                    </div>
                <#else>
                    <div class="table-empty">Non ci sono utenti.</div>
                </#if>
            </div>
            <!-- TABELLA COLLEZIONI -->
            <div class="table-container" id="collezioni-container">
                <div class="title flex justify-between align-items-center">
                    COLLEZIONI
                    <input onkeyup="ricerca(this.value, '#table-tbody-collezioni')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
                <#if (collezioni?? && collezioni?size > 0)>
                    <div class="table-scrollable">
                        <table class="table table-borderless table-striped overflow-auto">
                            <thead class="table-dark">
                            <tr>
                                <th scope="col">Titolo</th>
                                <th scope="col">Proprietario</th>
                            </tr>
                            </thead>
                            <tbody id="table-tbody-collezioni">
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
            <div class="table-container" id="dischi-container">
                <div class="title flex justify-between align-items-center">
                    DISCHI
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
                            <th scope="col">Stato di conservazione</th>
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
                                <#if (disco.getStatoConservazione()??)>
                                    <td>${disco.statoConservazione?lower_case?cap_first}</td>
                                <#else>
                                    <td><i class="fa-solid fa-xmark"></i></td>
                                </#if>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono dischi.</div>
                    </#if>
                </div>
            </div>
            <div class="table-container" id="tracce-container">
                <div class="title flex justify-between align-items-center">
                    TRACCE
                    <input onkeyup="ricerca(this.value, '#table-tbody-tracce')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
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
                        <tbody id="table-tbody-tracce">
                        <#list tracce as traccia>
                            <tr>
                                <td>${traccia.getISWC()}</td>
                                <td>${traccia.getTitolo()}</td>
                                <td>${traccia.getDurata()}s</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono tracce.</div>
                    </#if>
                </div>
            </div>

            <div class="table-container" id="autori-container">
                <div class="title flex justify-between align-items-center">
                    AUTORI
                    <input onkeyup="ricerca(this.value, '#table-tbody-autori')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
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
