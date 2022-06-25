<#include "../outlines/outline_header.ftl">


<h2 class="ml-5 mt-3">Modifica il disco</h2>
<form method="post" action="update-disco" class="ml-5 mt-3">
    <div class="form-group col-7">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="${disco.titolo}" value="${disco.titolo}">
    </div>

    <div class="form-group col-7">
        <label for="anno">Anno</label>
        <input type="text" class="form-control" required id="anno" name="anno" placeholder="${disco.anno}" value="${disco.anno}">
    </div>
    <input type="hidden" id="id_disco" name="id_disco" value="${disco.getKey()}">
    <div class="form-group col-7">
        <label for="barcode">Barcode</label>
        <input type="text" class="form-control" required id="barcode" name="barcode" placeholder="${disco.getBarCode()}" value="${disco.getBarCode()}">
    </div>

    <div class="form-group col-7">
        <label for="etichetta">Etichetta</label>
        <input type="text" class="form-control" required id="etichetta" name="etichetta" placeholder="${disco.getEtichetta()}" value="${disco.getEtichetta()}">
    </div>

    <div class="form-group col-7">
        <label for="genere">Genere:</label>
        <select id="genere" name="genere" class="selectpicker" data-live-search="true" required  >
            <option selected value="${disco.getGenere()}">${disco.getGenere()}</option>
            <#list generi as genere>
                <option value="${genere}">${genere}</option>
            </#list>
        </select>
    </div>
    <div class="form-group col-7">
        <label for="statoConservazione">Stato di conservazione:</label>
        <select id="statoConservazione" name="statoConservazione" class="selectpicker" data-live-search="true" required >
            <option selected value="${disco.getStatoConservazione()}">${disco.getStatoConservazione()}</option>
            <#list statoConservazione as stato_conservazione>
                <option value="${stato_conservazione}">${stato_conservazione}</option>
            </#list>
        </select>
    </div>
    <div class="form-group col-7">
        <label for="formato">Formato:</label>
        <select id="formato" name="formato" class="selectpicker" data-live-search="true" required >
            <option selected value="${disco.getFormato()}">${disco.getFormato()}</option>
            <#list formati as formato>
                <option value="${formato}">${formato}</option>
            </#list>
        </select>
    </div>

    <div class="form-group col-7">
        <input type="submit" value="Modifica"><br>
    </div>
</form>

