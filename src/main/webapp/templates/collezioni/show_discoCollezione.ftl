<div class="card-body">

    <#if (disco??)>
        <h2>Titolo</h2>
        ${disco.titolo}

        <h2>Anno</h2>
        ${disco.anno}

        <h2>Formato</h2>
        ${disco.formato}

        <h2>Stato conservazione</h2>
        ${disco.statoConservazione}

    </#if>

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
</div>