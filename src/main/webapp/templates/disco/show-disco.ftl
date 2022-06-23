<#include "../outlines/outline_header.ftl">


<div class="card-body">

    <#if (disco??)>
        <h2>Titolo</h2>
        ${disco.titolo}

        <h2>Anno</h2>
        ${disco.anno}

        <h2>Formato</h2>
        ${disco.formato}

        <h2> Genere</h2>
        ${disco.genere}

        <h2>Stato conservazione</h2>
        ${disco.statoConservazione}

        <h2>Barcode</h2>
        <#if (disco.getBarCode()??)>
            ${disco.getBarCode()}
            <#else>
               <p> Nessun barcode inserito</p>
            </#if>
        <h2>Etichetta</h2>
        ${disco.etichetta}
    </#if>
    <a href="update-disco?id_disco=${disco.key}">Modifica le informazioni disco </a>

    <h2>Tracce disco</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Titolo</th>
            <th scope="col">Durata</th>
            <th scope="col">ISWC</th>
        </tr>
        </thead>
        <tbody>
        <#if (tracce?size>0)>
            <#list tracce as traccia>
                <tr>
                    <th scope="row">${traccia.titolo}</th>
                    <td>${traccia.durata}</td>
                    <td>${traccia.ISWC}</td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <a href="update-TracceDisco" >Modifica le tracce del disco </a>
    <h2>Autori disco</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Nome</th>
            <th scope="col">Cognome</th>
            <th scope="col">Nome d'arte</th>
            <th scope="col">Tipologia</th>
        </tr>
        </thead>
        <tbody>
        <#if (autoriDisco?size>0)>
            <#list autoriDisco as autore>
                <tr>
                    <td>${autore.nome}</td>
                    <td>${autore.cognome}</td>
                    <td>${autore.nomeArtistico}</td>
                    <td>${autore.getTipologia()}</td>
                </tr>
            </#list>
        </#if>
        <a href="update-AutoriDisco" >Modifica gli autori del disco </a>
        </tbody>
    </table>
</div>