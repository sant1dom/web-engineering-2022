<#include "outlines/outline_header.ftl">
<form >
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