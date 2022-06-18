<#include "../outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Crea una nuova collezione</h2>

<form method="post" action="create-collezione" class="ml-5 mt-3">
    <div class="form-group col-7">
        <label for="disco">Dischi</label>
        <#if dischi??>
            <select id="disco" name="disco" class="selectpicker" required multiple data-live-search="true">
                <#list dischi as disco>
                    <option value="${disco.key}">${disco.titolo} | ${disco.anno} | ${disco.etichetta} | ${disco.genere}  </option>
                </#list>
            </select>
        <#else>
            <p>
                Non ci sono dischi
            </p>
        </#if>
    </div>
    <div class="form-group col-5">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" required id="titolo" name="titolo" placeholder="Titolo">
    </div>

    <div class="form-group col-7">
        <label for="privacy">Privacy</label>
        <select id="privacy" name=privacy" class="selectpicker" data-live-search="true" required>
            <option value="PRIVATA">PRIVATA</option>
            <option value="CONDIVISA">CONDIVISA</option>
        </select>
    </div>

    <div class="form-group col-3">
        <button type="submit" class="btn btn-primary">Salva</button>
    </div>
</form>