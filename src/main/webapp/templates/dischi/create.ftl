<#-- @ftlvariable name="error" type="String" -->
<#-- @ftlvariable name="dischiPadri" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="authors" type="org.webeng.collector_site.data.model.Autore[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->
<#-- @ftlvariable name="collezioni" type="org.webeng.collector_site.data.model.Collezione[]" -->
<#-- @ftlvariable name="generi" type="String[]" -->
<#-- @ftlvariable name="formati" type="String[]" -->
<#-- @ftlvariable name="statoConservazione" type="String[]" -->

<#include "../outlines/outline_header.ftl">

<div class="page-container page-bg flex justify-center align-items-center">
    <div class="card form-card">
        <div class="card-header">
            <span class="form-title flex justify-center align-items-center">NUOVO DISCO</span>
        </div>
        <div class="card-body">
            <form method="post" action="create-disco">
                <#if error??>
                    <div class="text-red" style="color: red;">
                        ${error!}
                    </div>
                </#if>
                <span class="warning-msg"></span>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" style="width: 100%;">
                        <label for="padre">Cerca tra i dischi esistenti:</label>
                        <#if (dischiPadri?? && dischiPadri?size > 0)>
                            <select id="padre" name="padre" class="selectpicker" data-live-search="true">
                                <option selected value="">Scegli un disco</option>
                                <#list dischiPadri as disco>
                                    <option value="${disco.key}">${disco.titolo} | ${disco.anno} | ${disco.etichetta}
                                        | ${disco.genere}</option>
                                </#list>
                            </select>
                        <#else>
                            <p>
                                Non ci sono dischi già presenti.
                            </p>
                        </#if>
                    </div>
                </div>

                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" style="width: 40%;">
                        <label for="titolo">Titolo:</label>
                        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo">
                    </div>

                    <div class="form-group" style="width: 20%;">
                        <label for="anno">Anno:</label>
                        <input type="number" class="form-control" required id="anno" name="anno" placeholder="anno">
                    </div>

                    <div class="form-group" style="width: 20%;">
                        <label for="barcode">Barcode:</label>
                        <input type="text" class="form-control" value="" id="barcode" name="barcode"
                               placeholder="Barcode">
                    </div>

                    <div class="form-group" style="width: 20%;">
                        <label for="etichetta">Etichetta:</label>
                        <input type="text" class="form-control" required id="etichetta" name="etichetta"
                               placeholder="Etichetta">
                    </div>
                </div>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group" style="width: 33%;">
                        <label for="genere">Genere:</label>
                        <select id="genere" name="genere" class="selectpicker" data-live-search="true" required>
                            <option selected value="">Scegli un genere</option>
                            <#list generi as genere>
                                <option value="${genere}">${genere}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group" style="width: 33%;">
                        <label for="formato">Formato:</label>
                        <select id="formato" name="formato" class="selectpicker" data-live-search="true" required>
                            <option selected value="">Scegli un formato</option>
                            <#list formati as formato>
                                <option value="${formato}">${formato}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group" style="width: 33%;">
                        <label for="statoConservazione">Stato di conservazione:</label>
                        <select id="statoConservazione" name="statoConservazione" class="selectpicker"
                                data-live-search="true" required>
                            <option value="">Scegli uno stato di conservazione</option>
                            <#list statoConservazione as stato_conservazione>
                                <option value="${stato_conservazione}">${stato_conservazione}</option>
                            </#list>
                        </select>
                    </div>
                </div>

                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group">
                        <label for="autore">Autori:</label>
                        <#if (authors?? && authors?size > 0)>
                            <select id="autore" name="autore" class="selectpicker" multiple data-live-search="true"
                                    required>
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
                    <div class="form-group">
                        <label for="tracce">Tracce:</label>
                        <#if (tracce?? && tracce?size > 0) >
                            <select id="tracce" name="tracce" class="selectpicker" multiple data-live-search="true"
                                    required>
                                <#list tracce as traccia>
                                    <option value="${traccia.key}">${traccia.titolo} | ${traccia.durata}
                                        | ${traccia.ISWC}</option>
                                </#list>
                            </select>
                        <#else>
                            <p>
                                Non ci sono tracce
                            </p>
                        </#if>
                    </div>
                    <div class="form-group" style="width: 100%;">
                        <label for="collezioni">Collezione:</label>
                        <#if (collezioni?? && collezioni?size > 0)>
                            <select id="collezioni" name="collezioni" class="selectpicker" multiple
                                    data-live-search="true">
                                <#list collezioni as collezione>
                                    <option value="${collezione.key}">${collezione.titolo}</option>
                                </#list>
                            </select>
                        <#else>
                            <p>
                                Non hai collezioni, prima di aggiungere un disco devi prima crearne una.
                            </p>
                        </#if>
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
