<#include "../outlines/outline_header.ftl">

<div class="card-body">
    <h2>Le tue collezioni</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID Collezione</th>
            <th scope="col">Titolo</th>
            <th scope="col">Data ultima modifica</th>
            <th scope="col">Dettagli dischi</th>
            <th scope="col">Gestisci collezione</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#if (collezioni?size>0)>
            <#list collezioni as collezione>
                <tr>
                    <th scope="row">${collezione.key}</th>
                    <td>${collezione.titolo}</td>
                    <td>${collezione.dataCreazione}</td>
                    <td><a href="listaDischi-collezione?id_collezione=${collezione.key}">visualizza dischi</a></td>
                    <td><a href="update-collezione?id_collezione=${collezione.key}">modifica</a></td>
                    <td><a href="delete-collezione?id_collezione=${collezione.key}" >elimina</a></td>
                </tr>
            </#list>
        </#if>
        </tbody>
        </table>
        <a href="create-collezione" >Crea una nuova collezione</a>

</div>

