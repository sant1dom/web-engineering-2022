<#-- @ftlvariable name="autori" type="org.webeng.collector_site.data.model.Autore[]" -->
<#-- @ftlvariable name="generi" type="org.webeng.collector_site.data.model.Genere[]" -->
<#-- @ftlvariable name="stats" type="Integer[]" -->
<#-- @ftlvariable name="numero_collezioni_private" type="Integer" -->
<#-- @ftlvariable name="numero_collezioni_totali" type="Integer" -->
<#include "../outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Statistiche del sito <#if utente??>e personali</#if> </h2>
<div class="row ml-5 mt-3">
    <div class="col">
        <h3>Autori</h3>
        <select id="autore" name="autore" class="selectpicker" data-live-search="true">
            <option value="">Scegli un autore</option>
            <#list autori as author>
                <option value="${author.key}">${author.nomeArtistico}</option>
            </#list>
        </select>
    </div>
    <div class="col">
        <h3>Generi</h3>
        <select id="genere" name="genere" class="selectpicker" data-live-search="true">
            <option value="">Scegli un genere</option>
            <#list generi as genre>
                <option value="${genre}">${genre}</option>
            </#list>
        </select>
    </div>
</div>
<div class="row ml-5 mt-3">
    <div class="col">
        <h3>Etichette</h3>
        <select id="etichetta" name="etichetta" class="selectpicker" data-live-search="true">
            <option value="">Scegli un'etichetta</option>
            <#list etichette as etichetta>
                <option value="${etichetta}">${etichetta}</option>
            </#list>
        </select>
    </div>
    <div class="col">
        <h3>Anno</h3>
        <input type="number" id="anno" name="anno" class="form-control-md" placeholder="Anno" min="1900" max="2099">
    </div>
</div>
<div class="row ml-5 mt-3">
    <div class="col">
        <h3>Statistiche</h3>
        <div class="ml-3">
            <h4 id="nda"></h4>
            <h4 id="ndg"></h4>
            <h4 id="nde"></h4>
            <h4 id="ndanno"></h4>
            <h4>Numero di dischi totali nel sistema: ${stats[0]}</h4>
            <h4>Numero di tracce totali nel sistema: ${stats[1]}</h4>
            <h4>Numero di etichette totali nel sistema: ${stats[2]}</h4>
            <h4>Numero di generi totali nel sistema: ${stats[3]}</h4>
            <#if numero_collezioni_private??>
                <h4>Numero di tue collezioni private nel sistema: ${numero_collezioni_private}</h4>
            </#if>
            <#if numero_collezioni_totali??>
                <h4>Numero di tue collezioni totali nel sistema: ${numero_collezioni_totali}</h4>
            </#if>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#autore').selectpicker();
        $('#genere').selectpicker();
        $('#etichetta').selectpicker();

        $('#autore').change(function () {
            if($(this).val() !== "") {
                let autore = $("#autore option:selected").text();
                $.ajax({
                    url: 'http://localhost:8080/stats?action=autore&autore=' + autore,
                    type: 'GET',
                    success: function (data) {
                        $("#nda").text("Numero di dischi per l'autore selezionato: " + data);
                        $("#nda").css("display", "");
                    }
                });
            } else {
                $("#nda").css("display", "none");
            }
        });

        $('#genere').change(function () {
            if($(this).val() !== "") {
                let genere = $("#genere option:selected").text();
                $.ajax({
                    url: 'http://localhost:8080/stats?action=genere&genere=' + genere,
                    type: 'GET',
                    success: function (data) {
                        $("#ndg").text("Numero di dischi per il genere selezionato: " + data);
                        $("#ndg").css("display", "");
                    }
                });
            } else {
                $("#ndg").css("display", "none");
            }
        });

        $('#etichetta').change(function () {
            if($(this).val() !== "") {
                let etichetta = $("#etichetta option:selected").text();
                $.ajax({
                    url: 'http://localhost:8080/stats?action=etichetta&etichetta=' + etichetta,
                    type: 'GET',
                    success: function (data) {
                        $("#nde").text("Numero di dischi per l'etichetta selezionata: " + data);
                        $("#nde").css("display", "");
                    }
                });
            } else {
                $("#nde").css("display", "none");
            }
        });

        $('#anno').change(function () {
            if($(this).val() !== "") {
                let anno = $("#anno").val();
                $.ajax({
                    url: 'http://localhost:8080/stats?action=anno&anno=' + anno,
                    type: 'GET',
                    success: function (data) {
                        $("#ndanno").text("Numero di dischi per l'anno selezionato: " + data);
                        $("#ndanno").css("display", "");
                    }
                });
            } else {
                $("#ndanno").css("display", "none");
            }
        });
    });

</script>