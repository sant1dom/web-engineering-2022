<#-- @ftlvariable name="autori" type="org.webeng.collector_site.data.model.Autore[]" -->
<#-- @ftlvariable name="generi" type="org.webeng.collector_site.data.model.Genere[]" -->
<#-- @ftlvariable name="etichette" type="String[]" -->
<#-- @ftlvariable name="stats" type="Integer[]" -->
<#-- @ftlvariable name="numero_collezioni_private" type="Integer" -->
<#-- @ftlvariable name="numero_collezioni_totali" type="Integer" -->

<#include "../outlines/outline_header.ftl">
<div class="page-container">
    <div class="info">
        <h3>Statistiche del sito <#if utente??>e personali</#if></h3>
    </div>

    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <div class="title">FILTRO</div>
            <div class="filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <dt class="filtro-subtitle"></dt>
                        <dd>
                            <label for="autore">Autori</label>
                            <select id="autore" name="autore" class="selectpicker" data-live-search="true">
                                <option value="">Scegli un autore</option>
                                <#list autori as author>
                                    <option value="${author.key}">${author.nomeArtistico}</option>
                                </#list>
                            </select>
                        </dd>
                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <dd>
                            <label for="genere">Generi</label>
                            <select id="genere" name="genere" class="selectpicker" data-live-search="true">
                                <option value="">Scegli un genere</option>
                                <#list generi as genre>
                                    <option value="${genre}">${genre}</option>
                                </#list>
                            </select>
                        </dd>
                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <dd>
                            <label for="etichetta">Etichette</label>
                            <select id="etichetta" name="etichetta" class="selectpicker" data-live-search="true">
                                <option value="">Scegli un'etichetta</option>
                                <#list etichette as etichetta>
                                    <option value="${etichetta}">${etichetta}</option>
                                </#list>
                            </select>
                        </dd>
                        <dd class="horizontal-separator filtro-horizontal-separator"></dd>
                        <dd>
                            <label for="anno">Anno</label>
                            <input type="number" id="anno" name="anno" class="form-control-md" placeholder="Anno"
                                   min="1900" max="2099">
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
        <div class="tables-container">
            <div class="table-container" id="dischi-container">
                <div class="title flex justify-between align-items-center">
                    STATISTICHE
                </div>
                <div style="border: 1px solid grey; border-top: none; padding: 1rem">
                    <h4 id="nda"></h4>
                    <h4 id="ndg"></h4>
                    <h4 id="nde"></h4>
                    <h4 id="ndanno"></h4>
                    <div class="horizontal-separator filtro-horizontal-separator"></div>
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
    </div>
</div>

<script src="/assets/js/stats.js"></script>