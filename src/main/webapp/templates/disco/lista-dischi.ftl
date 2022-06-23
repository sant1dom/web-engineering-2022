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
        </tr>
        </thead>
        <tbody>
        <#if (dischiByUtente?size>0)>
            <#list dischiByUtente as disco>
                <tr>
                    <td><a href="show-disco?id_disco=${disco.key}">${disco.titolo}</a></td>
                    <td>${disco.anno}</td>
                    <#if (disco.getBarCode()??)>
                        <td>${disco.getBarCode()}</td>
                    <#else>
                        <td><i class="fa-solid fa-xmark"></i></td>
                    </#if>
                    <td>${disco.etichetta?cap_first}</td>
                    <td>${disco.genere?lower_case?cap_first}</td>
                    <td>${disco.formato?lower_case?cap_first}</td>
                    <td>${disco.statoConservazione?lower_case?cap_first}</td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <a href="crea-disco">Crea un nuovo disco </a>

</div>

