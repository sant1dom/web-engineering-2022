<#-- @ftlvariable name="error" type="String" -->
<#-- @ftlvariable name="disco" type="org.webeng.collector_site.data.model.Disco" -->
<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="generi" type="String[]" -->
<#-- @ftlvariable name="formati" type="String[]" -->
<#-- @ftlvariable name="statoConservazione" type="String[]" -->

<#include "../outlines/outline_header.ftl">

<div class="page-container page-bg flex justify-center align-items-center">
    <div class="card form-card">
        <div class="card-header">
            <span class="form-title flex justify-center align-items-center">AGGIORNA DISCO</span>
        </div>
        <div class="card-body">
            <form method="post" action="edit-disco?id=${disco.getKey()}">
                <#if error??>
                    <div class="text-red" style="color: red;">
                        ${error!}
                    </div>
                </#if>
                <span class="warning-msg"></span>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" style="width: 40%;">
                        <label for="titolo">Titolo:</label>
                        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo"
                               value="${disco.getTitolo()}">
                    </div>

                    <div class="form-group" style="width: 20%;">
                        <label for="anno">Anno:</label>
                        <input type="number" class="form-control" required id="anno" name="anno" placeholder="anno"
                               value="${disco.getAnno()}">
                    </div>

                    <div class="form-group" style="width: 20%;">
                        <label for="barcode">Barcode:</label>
                        <input type="text" class="form-control" id="barcode" name="barcode"
                               placeholder="Barcode" value="${disco.getBarCode()!}">
                    </div>

                    <div class="form-group" style="width: 20%;">
                        <label for="etichetta">Etichetta:</label>
                        <input type="text" class="form-control" required id="etichetta" name="etichetta"
                               placeholder="Etichetta" value="${disco.getBarCode()}">
                    </div>
                </div>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" style="width: 33%;">
                        <label for="genere">Genere:</label>
                        <select id="genere" name="genere" class="selectpicker" data-live-search="true" required>
                            <#list generi as genere>
                                <#if (disco.getGenere() == genere)>
                                    <option selected value="${genere}">${genere}</option>
                                <#else>
                                    <option value="${genere}">${genere}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group" style="width: 33%;">
                        <label for="formato">Formato:</label>
                        <select id="formato" name="formato" class="selectpicker" data-live-search="true" required>
                            <option selected value="">Scegli un formato</option>
                            <#list formati as formato>
                                <#if (disco.getFormato() == formato)>
                                    <option selected value="${formato}">${formato}</option>
                                <#else>
                                    <option value="${formato}">${formato}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group" style="width: 33%;">
                        <label for="statoConservazione">Stato di conservazione:</label>
                        <select id="statoConservazione" name="statoConservazione" class="selectpicker"
                                data-live-search="true" required>
                            <option value="">Scegli uno stato di conservazione</option>
                            <#list statoConservazione as stato_conservazione>
                                <#if (disco.getStatoConservazione()?? && disco.getStatoConservazione() == stato_conservazione)>
                                    <option selected value="${stato_conservazione}">${stato_conservazione}</option>
                                <#else>
                                    <option value="${stato_conservazione}">${stato_conservazione}</option>
                                </#if>
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