<#-- @ftlvariable name="tipologie" type="org.webeng.collector_site.data.model.TipologiaAutore[]" -->

<#include "../outlines/outline_header.ftl">

<div class="page-container flex justify-center align-items-center">
    <div class="card form-card">
        <div class="card-header">
            <span class="form-title flex justify-center align-items-center">NUOVO AUTORE</span>
        </div>
        <div class="card-body">
            <form method="post" action="create-autore">
                <#if error??>
                    <div class=text-red>
                        ${error!}
                    </div>
                </#if>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" class="form-control" required id="nome" name="nome" placeholder="Nome">
                    </div>
                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" class="form-control" required id="cognome" name="cognome"
                               placeholder="Cognome">
                    </div>
                </div>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group">
                        <label for="nome_artistico">Nome d'arte</label>
                        <input type="text" class="form-control" required id="nome_artistico" name="nome_artistico">
                    </div>
                    <div class="form-group">
                        <label for="tipologia_autore">Tipologia</label>
                        <select class="form-control" id="tipologia_autore" name="tipologia_autore">
                            <#list tipologie as tipologia>
                                <option value="${tipologia}">${tipologia}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="flex justify-center align-items-center pt-3">
                    <button type="submit" class="btn btn-success">
                        SALVA
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>