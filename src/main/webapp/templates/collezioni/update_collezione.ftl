<#include "../outlines/outline_header.ftl">

<h2 class="ml-5 mt-3">Modifica collezione</h2>

 <!-- NON FUNZIONA -->
<form method="post" action="update-collezione" class="ml-5 mt-3">
<div class="form-group col-7">
    <label for="disco">Dischi</label>
    <input type="hidden" name="k" value="${collezione.key!0}"/>
    <div class="twelve columns"><select name="disco">
            <#list dischi as disco>
                <option value="${disco.key}"
                        <#if (collezione.disco?? && disco.key=collezione.disco.key)>
                            selected="selected"
                        </#if>
                >${disco.titolo}</option>
            </#list>
        </select>
    </div>

    <div class="form-group col-5">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" value="" id="titolo" name="titolo" placeholder="Titolo">
    </div>

    <div class="form-group col-7">
        <label for="privacy">Privacy</label>
        <select id="privacy" name="privacy" class="selectpicker" >
            <option selected="" value="">Scegli un'opzione</option>
            <option value="PRIVATA">PRIVATA</option>
            <option value="PUBBLICA">PUBBLICA</option>
            <option value="CONDIVISA">CONDIVISA</option>
        </select>
    </div>

    <div class="form-group col-3">
        <button type="submit" class="btn btn-primary">Salva</button>
    </div>
</div>

</form>

<script>
    $(window).on('load', function () {
        $('.selectpicker').selectpicker();
    });
</script>