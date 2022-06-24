<#-- @ftlvariable name="error" type="String" -->
<#-- @ftlvariable name="authors" type="org.webeng.collector_site.data.model.Autore[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->

<#include "../outlines/outline_header.ftl">

<div class="page-container page-bg flex justify-center align-items-center">
    <div class="card form-card">
        <div class="card-header">
            <span class="form-title flex justify-center align-items-center">NUOVA TRACCIA</span>
        </div>
        <div class="card-body">
            <form method="post" action="create-traccia">
                <#if error??>
                    <div class=text-red>
                        ${error!}
                    </div>
                </#if>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group">
                        <label for="autore">Autore</label>
                        <div>
                        <#if authors??>
                            <select id="autore" name="autore" class="selectpicker" required multiple
                                    data-live-search="true">
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
                    </div>
                    <div class="form-group">
                        <label for="padre">Controlla se presente:</label>
                        <div>
                        <#if tracce??>
                            <select id="padre" name="padre" class="selectpicker" data-live-search="true">
                                <option selected value="">Scegli una traccia</option>
                                <#list tracce as traccia>
                                    <option value="${traccia.key}">${traccia.titolo} | ${traccia.durata} | ${traccia.ISWC}
                                    </option>
                                </#list>
                            </select>
                        <#else>
                            <p>
                                Non ci sono tracce
                            </p>
                        </#if>
                        </div>
                    </div>
                </div>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group cw-25">
                        <label for="iswc">ISWC</label>
                        <input type="text" class="form-control" required id="iswc" name="iswc"
                               placeholder="T-123.456.789-C">
                    </div>
                    <div class="form-group">
                        <label for="titolo">Titolo</label>
                        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo">
                    </div>
                    <div class="form-group cw-25">
                        <label for="durata">Durata</label>
                        <input type="number" class="form-control" required id="durata" name="durata" placeholder="30">
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