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
            <th scope="col">Elimina traccia</th>
        </tr>
        </thead>
        <tbody>
        <#if (tracce?size>0)>
            <#list tracce as traccia>
                <tr>
                    <th scope="row">${traccia.titolo}</th>
                    <td>${traccia.durata}</td>
                    <td>${traccia.ISWC}</td>
                    <td><a href="delate-tracciaDisco?id_traccia=${traccia.key}&id_disco=${disco.key}" class="btn btn-danger"> elimina traccia</a></td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <script type="text/javascript">
        function showTracce() {
            oDiv = document.getElementById('tracceSelect');
            oDiv.style.display='block';
            return false;
        }
    </script>
    <style >
    #tracceSelect {
    display:none;
    }
    </style>
    <div class="form-group">
        <button onclick="showTracce()" class="btn btn-primary">Aggiungi tracce</button>
    </div>
    <div class="form-group col-7"  id="tracceSelect">
        <form method="post" action="aggiungiTracce-disco" class="ml-5 mt-3">
        <label for="tracce">Tracce:</label>
        <#if tracceDaAggiungere??>
            <select id="tracce" name="tracce" class="selectpicker" multiple data-live-search="true">
                <#list tracceDaAggiungere as traccia>
                    <option value="${traccia.key}">${traccia.titolo} | ${traccia.durata} | ${traccia.ISWC}</option>
                </#list>
            </select>
        <#else>
            <p>
                Non ci sono tracce
            </p>
        </#if>
        <input type="hidden" name="id_disco" value="${disco.key}">
        <button type="submit" class="btn btn-primary">Aggiungi tracce</button>

        </form>
    </div>
    <div class="form-group col-7">
    <h2>Autori disco</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Nome</th>
            <th scope="col">Cognome</th>
            <th scope="col">Nome d'arte</th>
            <th scope="col">Tipologia</th>
            <th scope="col">Elimina Autore</th>
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
                    <td><a href="delate-autoreDisco?id_autore=${autore.key}&id_disco=${disco.key}" class="btn btn-danger"> elimina autore</a></td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
        <script type="text/javascript">
            function showAutori() {
                oDiv = document.getElementById('autoriSelect');
                oDiv.style.display='block';
                return false;
            }
        </script>
        <style >
            #autoriSelect {
                display:none;
            }
        </style>
        <div class="form-group">
            <button onclick="showAutori()" class="btn btn-primary">Aggiungi autori al disco</button>
        </div>
        <div class="form-group col-7"  id="autoriSelect">
        <form method="post" action="aggiungiAutore-disco" class="ml-5 mt-3">
                <label for="autore">Autore:</label>
                <#if autoriDaAggiungere??>
                    <select id="autori" name="autori" class="selectpicker"  multiple data-live-search="true" >
                        <#list autoriDaAggiungere as author>
                            <option value="${author.key}">${author.nome} ${author.cognome}</option>
                        </#list>
                    </select>
                </#if>
            <input type="hidden" name="id_disco" value="${disco.key}">
            <button type="submit" class="btn btn-primary">Aggiungi autori</button>
        </form>
        </div>
</div>
</div>