<#include "../outlines/outline_header.ftl">

<h2 class="ml-5 mt-3">Aggiungi dischi alla collezione</h2>
<form method="post" action="update-dischiCollezione?id_collezione=${collezione.key}" class="ml-5 mt-3">
    <div class="form-group col-7">
        <label for="disco">Dischi</label>
        <#if dischi??>
            <select id="disco" name="disco" class="selectpicker" required multiple data-live-search="true">
                <#list dischi as disco>
                    <option value="${disco.key}">${disco.titolo} | ${disco.etichetta} | ${disco.genere}  </option>
                </#list>
            </select>
        <#else>
            <p>
                Non ci sono dischi
            </p>
        </#if>
    </div>

    <div class="form-group col-3">
        <button type="submit" class="btn btn-primary">Salva</button>
    </div>

</form>

<h2 class="ml-5 mt-3">Crea un nuovo disco per la collezione</h2>
<div class="ml-5 mt-3">
    <a href="crea-disco" class="btn btn-primary">crea nuovo disco</a>
</div>

<script>
    $(window).on('load', function () {
        $('.selectpicker').selectpicker();
    });
</script>