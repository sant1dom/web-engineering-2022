<#-- @ftlvariable name="authors" type="org.webeng.collector_site.data.model.Autore[]" -->
<#include "../outlines/outline_header.ftl">
<h2>Crea una traccia</h2>

<form method="post" action="create-traccia">
    <div class="form-group">
        <label for="autore">Autore</label>
        <#if authors??>
            <#list authors as author>
                <input class="form-control" id="autore" type="checkbox" name="autore" value="${author.key}"/>${author.nome} ${author.cognome}<br/>
            </#list>
        <#else>
            <p>Nessun autore presente</p>
        </#if>
    </div>
    <div class="form-group">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" id="titolo" placeholder="Titolo">
    </div>
    <div class="form-group">
        <label for="durata">Durata</label>
        <input type="number" class="form-control" id="durata" placeholder="30">
    </div>
    <div class="form-group">
        <label for="iswc">ISWC</label>
        <input type="text" class="form-control" id="iswc" placeholder="T-123.456.789-C">
    </div>
    <button type="submit" class="btn btn-primary">Salva</button>
</form>