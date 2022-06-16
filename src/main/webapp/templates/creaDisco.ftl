<#include "outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Crea un nuovo disco</h2>
<form method="post" action="crea-disco" class="ml-5 mt-3">
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
        <label for="collezione_id">Collezione</label>
        <#if collezioni??>
            <select id="collezione_id" name="collezione_id" class="selectpicker" required data-live-search="true">
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
        <label for="padre">Controlla se il disco è presente:</label>
        <#if dischiPadre??>
            <select id="padre" name="padre" class="selectpicker" data-live-search="true">
                <option selected value="">Scegli un disco</option>
                <#list dischiPadri as disco>
                    <option value="${disco.key}">${disco.titolo}</option>
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
            <select id="genere" name="genere" class="selectpicker" required >
                <option selected value="">Scegli un genere</option>
                <#list generi as genere>
                    <option value="${genere}">${genere}</option>
                </#list>
            </select>
    </div>
    <div class="form-group col-7">
        <label for="formato">Formato:</label>
        <select id="formato" name="genere" class="selectpicker" required >
            <option selected value="">Scegli un formato</option>
            <#list formati as formato>
                <option value="${formato}">${formato}</option>
            </#list>
        </select>
    </div>
    <label for="titolo">Titolo:</label><br>
    <input type="text" id="titolo" name="titolo"><br>

    <label for="anno">Anno:</label><br>
    <input type="text" id="anno" name="anno"><br>

    <label for="barcode">Barcode:</label><br>
    <input type="text" id="barcode" name="barcode"><br>

    <label for="etichetta">Etichetta:</label><br>
    <input type="text" id="etichetta" name="etichetta"><br>

    <label for="genere">Genere:</label><br>
    <select name="genere" id="genere">
        <option value="POP">Pop</option>
        <option value="ROCK">Rock</option>
        <option value="JAZZ">Jazz</option>
        <option value="CLASSIC">Classic</option>
        <option value="METAL">Metal</option>
        <option value="RAP">Rap</option>
        <option value="BLUES">Blues</option>
        <option value="PUNK">Punk</option>
        <option value="RAGGAE">Raggae</option>
        <option value="COUNTRY">Country</option>
        <option value="HIPHOP">Hip Hop</option>
        <option value="ELECTRONIC">Electronic</option>
        <option value="OTHER">Other</option>
    </select><br>

    <label for="formato">Formato:</label><br>
    <select name="formato" id="formato">
        <option value="CD">CD</option>
        <option value="VINILE">Vinile</option>
        <option value="DIGITALE">Digitale</option>
    </select><br>

    <label for="stato_conservazione">Stato di conservazione:</label><br>
    <select name="stato_conservazione" id="stato_conservazione">
        <option value="NUOVO">Nuovo</option>
        <option value="BUONO">Buono</option>
        <option value="USATO">Usato</option>
        <option value="SCARSO">Scarso</option>
        <option value="ROTTO">Rotto</option>
    </select><br>
    <input type="submit" value="Crea"><br>
</form>