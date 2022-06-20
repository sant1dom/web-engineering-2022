<#include "../outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Modifica collezione</h2>

 <!-- DA FINIRE -->

<form method="post" action="write">

    <div class="container">
        <input type="hidden" name="k" />
        <div class="row">
            <div class="four columns clabel">Disco</div>
            <div class="twelve columns"><select name="disco">
                    <#list dischi as disco>
                        <option value="${disco.key}"
                                <#if (collezione.disco?? && disco.key=collezione.disco.key)>
                                    selected="selected"
                                </#if>
                        >${disco.titolo} ${disco.etichetta}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="four columns clabel">Titolo</div>
            <div class="twelve columns"><input type="text" name="titolo" value="${collezione.titolo}"/> </div>
        </div>

        <div class="form-group col-7">
            <label for="privacy">Privacy</label>
            <select id="privacy" name="privacy" class="selectpicker" required>
                <option selected="" value="">${collezione.privacy}</option>
                <option value="PRIVATA">PRIVATA</option>
                <option value="PUBBLICA">PUBBLICA</option>
                <option value="CONDIVISA">CONDIVISA</option>
            </select>
        </div>
        <div class="row"><input type="submit" name="update" value="Update"/></div>
    </div>
</form>