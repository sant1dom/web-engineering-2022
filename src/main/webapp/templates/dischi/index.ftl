<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="keyword" type="String" -->

<#include "../outlines/outline_header.ftl">

<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="page-container">
    <div class="info">
        <h3>Lista dei dischi</h3>
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
                   href="create-disco">
                    Aggiungi disco</a>
            </div>
        </#if>
    </div>

    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <div class="title">FILTRO</div>
            <div class="filtro">
                <input id="input-filtro" type="text" onkeyup="ricerca(this.value, '#table-tbody-dischi')"
                       placeholder="Search.." class="input-filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <#include "../outlines/filtro/outline_generi.ftl">
                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <#include "../outlines/filtro/outline_formato.ftl">
                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <#include "../outlines/filtro/outline_stato_conservazione.ftl">
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <div class="table-container" id="dischi-container">
                <div class="title flex justify-between align-items-center">
                    DISCHI
                </div>
                <#if (dischi?? && dischi?size > 0)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">Barcode</th>
                            <th scope="col">Titolo</th>
                            <th scope="col">Anno</th>
                            <th scope="col">Etichetta</th>
                            <th scope="col">Genere</th>
                            <th scope="col">Formato</th>
                            <th scope="col" style="text-align: center">Stato di conservazione</th>
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
