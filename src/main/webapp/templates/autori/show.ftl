<#include "../outlines/outline_header.ftl">
<div class="page_container">
    <div class="autore_bg">
        <#if (autore??)>
            <div class="info_autore">
                <h3>${autore.getNomeArtistico()}</h3>
                <div class="labels">
                    <span class="label_info">Vero nome: </span> <br>
                    <span class="label_info">Tipologia: </span>
                    <br>
                </div>
                <div class="testi">
                    <span class="testo_info">${autore.getNome() + " " + autore.getCognome()}</span> <br>
                    <span class="testo_info">${autore.getTipologia()}</span>
                </div>
            </div>
        </#if>
    </div>
    <div class="horizontal_separator"></div>
    <div class="table_container">
        <div class="table_title">DISCHI</div>
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
                <tbody>
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
                <div class="table_empty">Non ci sono dischi.</div>
            </#if>
        </div>

        <div class="table_title">TRACCE</div>
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
                <tbody>
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
                <div class="table_empty">Non ci sono dischi.</div>
            </#if>
        </div>
    </div>
</div>
