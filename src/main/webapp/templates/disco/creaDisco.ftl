<#include "../outlines/outline_header.ftl">


<h2 class="ml-5 mt-3">Crea un nuovo disco</h2>
<form method="post" action="crea-disco" class="ml-5 mt-3">
    <div class="form-group col-7">
        <label for="autore">Autore:</label>
        <#if authors??>
            <select id="autore" name="autore" class="selectpicker" multiple data-live-search="true" required>
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
        <label for="tracce">Tracce:</label>
        <#if tracce??>
            <select id="tracce" name="tracce" class="selectpicker" multiple data-live-search="true" required>
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
        <label for="collezioni">Collezione:</label>
        <#if collezioni??>
            <select id="collezioni" name="collezioni" class="selectpicker" multiple data-live-search="true">
                <#list collezioni as collezione>
                    <option value="${collezione.key}">${collezione.titolo}</option>
                </#list>
            </select>
        <#else>
            <p>
                Non hai collezioni, prima di aggiungere un disco devi creare una collezione
            </p>
        </#if>
    </div>
    <div class="form-group col-7">
        <label for="padre">Controlla se presente:</label>
        <#if dischiPadri??>
            <select id="padre" name="padre" class="selectpicker" data-live-search="true">
                <option selected value="">Scegli un disco</option>
                <#list dischiPadri as disco>
                    <option value="${disco.key}">${disco.titolo} | ${disco.anno} | ${disco.etichetta} | ${disco.genere}  </option>
                </#list>
            </select>
        <#else>
            <p>
                Non ci sono dischi
            </p>
        </#if>
    </div>

    <div class="form-group col-7">
        <label for="genere">Genere:</label>
            <select id="genere" name="genere" class="selectpicker" data-live-search="true" required >
                <option selected value="">Scegli un genere</option>
                <#list generi as genere>
                    <option value="${genere}">${genere}</option>
                </#list>
            </select>
    </div>
    <div class="form-group col-7">
        <label for="statoConservazione">Stato di conservazione:</label>
        <select id="statoConservazione" name="statoConservazione" class="selectpicker" data-live-search="true" required >
            <#list statoConservazione as stato_conservazione>
                <option value="${stato_conservazione}">${stato_conservazione}</option>
            </#list>
        </select>
    </div>
    <div class="form-group col-7">
        <label for="formato">Formato:</label>
        <select id="formato" name="formato" class="selectpicker" data-live-search="true" required >
            <option selected value="">Scegli un formato</option>
            <#list formati as formato>
                <option value="${formato}">${formato}</option>
            </#list>
        </select>
    </div>
    <div class="form-group col-7">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo">
    </div>

    <div class="form-group col-7">
        <label for="anno">Anno</label>
        <input type="text" class="form-control" required id="anno" name="anno" placeholder="anno">
    </div>

    <div class="form-group col-7">
        <label for="barcode">Barcode</label>
        <input type="text" class="form-control" value="" id="barcode" name="barcode" placeholder="Barcode">
    </div>

    <div class="form-group col-7">
        <label for="etichetta">Etichetta</label>
        <input type="text" class="form-control" required id="etichetta" name="etichetta" placeholder="Etichetta">
    </div>
    <div class="form-group col-7">
        <input type="submit" value="Crea"><br>
    </div>
</form>

<script>
    $(window).on('load', function () {
        $('.selectpicker').selectpicker();

        $('#padre').on('change', function () {
            let selected = $('#padre option:selected');
            $("input[name='titolo']").val(selected.text().split(' | ')[0])
            $("input[name='titolo']").prop('readonly', true);
            $("input[name='anno']").val(selected.text().split(' | ')[1])
            $("input[name='anno']").prop('readonly', true);
            $("input[name='etichetta']").val(selected.text().split(' | ')[2])
            $("input[name='etichetta']").prop('readonly', true);
            $("select[name='genere']").prop('disabled', true);
            $("select[name='tracce']").prop('disabled', true);
            $("select[name='autore']").prop('disabled', true);
            $("select[name='genere']").prop('requiered', false);
            $("select[name='tracce']").prop('required', false);
            $("select[name='autore']").prop('required', false);
        });
    });
</script>
