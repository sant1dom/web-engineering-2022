<#include "../outlines/outline_header.ftl">
<h2>Le tue collezioni</h2>
    <table class="table">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Titolo</th>
        <th scope="col">Dettagli</th>
    </tr>
    </thead>
    <tbody>
    <#if (collezioni?size>0)>
        <#list collezioni as collezione>
            <tr>
                <th scope="row">${collezione.key}</th>
                <td>${collezione.titolo}</td>
                <td><a href="issue?n=${collezione.key}">[visualizza dettagli]</a></td>
            </tr>
        </#list>
    </#if>
    </tbody>
    </table>

