<#include "../outlines/outline_header.ftl">

<h2 class="ml-5 mt-3">Modifica collezione</h2>


<form method="post" action="update-collezione?id_collezione=${collezione.key}" class="ml-5 mt-3">
<div class="form-group col-7">
    <div class="form-group col-5">
        <label for="titolo">Titolo</label>
        <input type="text" class="form-control" value="${collezione.titolo}" id="titolo" name="titolo">
    </div>

    <div class="form-group col-7">
        <label for="privacy">Privacy</label>
        <select id="privacy" name="privacy" class="selectpicker" >

            <#if (collezione.privacy="PRIVATA")>
            <option selected="selected" value="PRIVATA">PRIVATA</option>
            <#else>
                <option value="PRIVATA">PRIVATA</option>
            </#if>
            <#if (collezione.privacy="PUBBLICA")>
                <option selected="selected" value="PUBBLICA">PUBBLICA</option>
            <#else>
                <option value="PUBBLICA">PUBBLICA</option>
            </#if>
            <#if (collezione.privacy="CONDIVISA")>
                <option selected="selected" value="CONDIVISA">CONDIVISA</option>
            <#else>
                <option value="CONDIVISA">CONDIVISA</option>
            </#if>

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