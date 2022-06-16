<#-- @ftlvariable name="authors" type="org.webeng.collector_site.data.model.Autore[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->

<#include "../outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Crea una nuova traccia</h2>

<form method="post" action="create-traccia" class="ml-5 mt-3">
    <div class="form-group col-7">
        <label for="autore">Autore</label>
        <#if authors??>
            <select id="autore" name="autore" class="selectpicker" required multiple data-live-search="true">
                <#list authors as author>
                    <option value="${author.key}">${author.nome} ${author.cognome}</option>
                </#list>
            </select>
        <#else>
            <p>
                Non ci sono autori
            </p>
        </#if>
    </div>
    <div class="form-group col-7">
        <label for="padre">Controlla se presente:</label>
        <#if tracce??>
            <select id="padre" name="padre" class="selectpicker" data-live-search="true">
                <option selected value="">Scegli una traccia</option>
                <#list tracce as traccia>
                    <option value="${traccia.key}">${traccia.titolo} | ${traccia.durata} | ${traccia.ISWC}</option>
                </#list>
            </select>
        <#else>
            <p>
                Non ci sono tracce
            </p>
        </#if>
    </div>
    <div class="form-group col-7">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo">
    </div>
    <div class="form-group col-7">
        <label for="durata">Durata</label>
        <input type="number" class="form-control" required id="durata" name="durata" placeholder="30">
    </div>
    <div class="form-group col-7">
        <label for="iswc">ISWC</label>
        <input type="text" class="form-control" required id="iswc" name="iswc" placeholder="T-123.456.789-C">
    </div>
    <div class="form-group col-3">
        <button type="submit" class="btn btn-primary">Salva</button>
    </div>
</form>

<script>
    $(window).on('load', function () {
        $('.selectpicker').selectpicker();

        $('#padre').on('change', function () {
            let selected = $('#padre option:selected');
            $("input[name='titolo']").val(selected.text().split(' | ')[0]);
            $("input[name='durata']").val(selected.text().split(' | ')[1]);
            $("input[name='iswc']").val(selected.text().split(' | ')[2]);
        });
    });
</script>