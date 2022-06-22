<#include "../outlines/outline_header.ftl">

<div class="card-body">

    <#if (collezione??)>

        <h2>Titolo</h2>
        ${collezione.titolo}


        <h2>Privacy</h2>
        ${collezione.privacy}

    </#if>
    <br>
    <br>

    <a href="update-collezione?id_collezione=${collezione.key}" class="btn btn-primary">modifica</a>
    <br>
    <h2>Dischi</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Titolo</th>
            <th scope="col">Etichetta</th>
            <th scope="col">Genere</th>
            <th scope="col">Dettagli</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#if (dischi?size>0)>
            <#list dischi as disco>
                <tr>
                    <th scope="row">${disco.titolo}</th>
                    <td>${disco.etichetta}</td>
                    <td>${disco.genere}</td>
                    <td><a href="show-discoCollezione?id_disco=${disco.key}" class="bt-navbar-registrati">visualizza dettagli</a></td>
                    <td><a href="delete-discoCollezione?id_disco=${disco.key}&id_collezione=${collezione.key}" class="bt-navbar-registrati">elimina</a></td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>

    <a href="update-dischiCollezione?id_collezione=${collezione.key}" class="btn btn-primary">aggiungi dischi</a>

</div>