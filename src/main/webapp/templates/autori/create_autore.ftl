<#-- @ftlvariable name="tipologie" type="org.webeng.collector_site.data.model.TipologiaAutore[]" -->

<#include "../outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Crea un nuovo autore</h2>

<form method="post" action="create-autore" class="ml-5 mt-3">
    <div class="form-group col-7">
        <label for="nome">Nome</label>
        <input type="text" class="form-control" required id="nome" name="nome" placeholder="Nome">
    </div>
    <div class="form-group col-7">
        <label for="cognome">Cognome</label>
        <input type="text" class="form-control" required id="cognome" name="cognome" placeholder="Cognome">
    </div>
    <div class="form-group col-7">
        <label for="nome_artistico">Nome d'arte</label>
        <input type="text" class="form-control" required id="nome_artistico" name="nome_artistico">
    </div>
    <div class="form-group col-7">
        <label for="tipologia_autore">Tipologia</label>
        <select class="form-control" id="tipologia_autore" name="tipologia_autore">
            <#list tipologie as tipologia>
                <option value="${tipologia}">${tipologia}</option>
            </#list>
        </select>
    </div>
    <div class="form-group col-3">
        <button type="submit" class="btn btn-primary">Salva</button>
    </div>
</form>
