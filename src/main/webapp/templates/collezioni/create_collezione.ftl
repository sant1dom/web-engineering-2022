<#include "../outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Crea una nuova collezione</h2>

<form method="post" action="create-collezione" class="ml-5 mt-3">
    <div class="form-group col-7">
        <label for="disco">Dischi</label>
        <#if dischi??>
            <select id="disco" name="disco" class="selectpicker" required multiple data-live-search="true">
                <#list dischi as disco>
                    <option value="${disco.key}">${disco.titolo}</option>
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
        <div class="custom-control custom-switch">
            <input type="checkbox" class="custom-control-input" id="condivisa">
            <label class="custom-control-label" for="condivisa">Rendi la collezione condivisa</label>
        </div>
    </div>


    <div class="form-group col-3">
        <button type="submit" class="btn btn-primary">Salva</button>
    </div>
</form>