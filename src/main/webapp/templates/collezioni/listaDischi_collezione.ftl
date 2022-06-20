<#include "../outlines/outline_header.ftl">

<div class="card-body">
    <h2>Dischi</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Titolo</th>
            <th scope="col">Etichetta</th>
            <th scope="col">Genere</th>
        </tr>
        </thead>
        <tbody>
        <#if (dischi?size>0)>
            <#list dischi as disco>
                <tr>
                    <th scope="row">${disco.titolo}</th>
                    <td>${disco.etichetta}</td>
                    <td>${disco.genere}</td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>