<#include "../outlines/outline_header.ftl">
<#setting date_format="dd-MM-yyyy">
<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="card-body">

        <#if (collezioni?size>0)>
        <h2>Le tue collezioni</h2>
        <table class="table table-bordered">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Titolo</th>
                <th scope="col">Data creazione</th>
                <th scope="col">Dettagli</th>
                <th scope="col">Azioni</th>
            </tr>
            </thead>
            <tbody>
            <#list collezioni as collezione>
                <tr>
                    <th scope="row">${collezione.titolo}</th>
                    <td>${collezione.dataCreazione?date("yyyy-MM-dd")?string("dd-MM-yyyy")}</td>
                    <td><a href="show-collezione?id=${collezione.key}" class="btn btn-success">visualizza</a></td>
                    <td><a href="delete-collezione?id_collezione=${collezione.key}" class="btn btn-danger">elimina collezione</a></td>
                </tr>
            </#list>
            </tbody>
            </table>
            <#else>
                <div class="table-empty">Non hai ancora nessuna collezione!</div>
        </#if>
        <a href="create-collezione" class="btn btn-warning">Crea una nuova collezione</a>
</div>
    <div class="card-body">
        <h2>Collezioni condivise con te</h2>
    <#if (collezioniCondivise?size>0)>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Titolo</th>
            <th scope="col">Data creazione</th>
            <th scope="col">Dettagli</th>
        </tr>
        </thead>
        <tbody>
        <#list collezioniCondivise as collezioneCondivisa>
            <tr>
                <th scope="row">${collezioneCondivisa.titolo}</th>
                <td>${collezioneCondivisa.dataCreazione?date("yyyy-MM-dd")?string("dd-MM-yyyy")}</td>
                <td><a href="show-collezione?id=${collezioneCondivisa.key}" class="btn btn-success">visualizza</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
    <#else>
        <div class="table-empty">Nessuna collezione condivisa con te!</div>
    </#if>
    </div>



