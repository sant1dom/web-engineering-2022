<#include "../outlines/outline_header.ftl">

<div class="card-body">
    <h2>I tuoi dischi</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Titolo</th>
            <th scope="col">Anno</th>
            <th scope="col">Barcode</th>
            <th scope="col">Etichetta</th>
            <th scope="col">Genere</th>
            <th scope="col">Formato</th>
            <th scope="col">Stato di conservazione</th>
            <th scope="col">Doppioni</th>
        </tr>
        </thead>
        <tbody>
        <#if (dischiByUtente?size>0)>
            <#list dischiByUtente as disco>
                <tr>
                    <td>${disco.titolo}</td>
                    <td>${disco.anno}</td>
                    <#if (disco.barcode??)>
                    <td>${disco.barcode}</td>
                    <#else>
                    <td><i class="fa-solid fa-xmark"></i></td>
                    </#if>
                    <td>${disco.etichetta}</td>
                    <td>${disco.genere}</td>
                    <td>${disco.formato}</td>
                    <td>${disco.statoConservazione}</td>
                    <td>${disco.doppioni}</td>
                    <td><a href="update-disco?id_disco=${disco.key}">modifica</a></td>

            </#list>
        </#if>
        </tbody>
    </table>
    <a href="crea-disco" >Crea un nuovo disco </a>

</div>

