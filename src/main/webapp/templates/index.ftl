<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->

<#include "outlines/outline_header.ftl">

<div class="page-container">
    <#if (utente??)>
        <h3 class="mb-4">Benvenuto: ${utente.getUsername()}</h3>
    </#if>

    <#if (dischi?? && dischi?size>0)>
        <div class="card">
            <div class="card-header">
                <h3>
                    DISCHI PIU' POPOLARI
                </h3>
            </div>
            <div class="card-body"
                 style="padding: 2rem; width: 100%; display: flex; flex-wrap: wrap; justify-content: space-between">
                <#list dischi as disco>
                    <div class="flex justify-between"
                         style="height: 100%; width: 45%; margin: 2rem ; border: 1px solid grey">
                        <#if (disco.getImmagini()?? && disco.getImmagini()?size > 0)>
                            <img style="max-width: 50%; height: auto; "
                                 src="/display-immagine?id_disco=${disco.getKey()}&id_image=${disco.getImmagini()[0].getKey()}"
                                 alt="slide">
                        </#if>
                        <div style="width: 100%; padding: 2rem">
                            <h3><a class="link" href="/show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a>
                            </h3>
                            <p>${disco.getAnno()}</p>
                            <p>${disco.getGenere()}</p>
                            <p>${disco.getFormato()}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </#if>
    <#if (tracce?? && tracce?size>0)>
    <div class="table-container" id="tracce-container">
        <div class="title flex justify-between align-items-center">
            TRACCE
        </div>
        <#if (tracce?? && tracce?size > 0)>
        <div class="table-scrollable">
            <table class="table table-borderless table-striped">
                <thead class="table-dark">
                <tr>
                    <th scope="col">ISWC</th>
                    <th scope="col">Titolo</th>
                    <th scope="col">Durata</th>
                    <th scope="col">Autori</th>
                    <th scope="col"><span class="flex justify-center">Originale</span></th>
                </tr>
                </thead>
                <tbody id="table-tbody-tracce">
                <#list tracce as traccia>
                    <tr>
                        <td>${traccia.getISWC()}</td>
                        <td>${traccia.getTitolo()}</td>
                        <td>${traccia.getDurata()}s</td>
                        <td>
                            <#list traccia.getAutori() as autore>
                                ${autore.getNomeArtistico()},
                            </#list>
                        </td>
                        <td class="flex justify-center">
                            <#if (traccia.getPadre()?has_content)>
                                ${traccia.getPadre().getISWC()}
                            <#else>
                                SI
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <#else>
                <div class="table-empty">Non ci sono tracce.</div>
            </#if>
        </div>
        </#if>
    </div>

</div>