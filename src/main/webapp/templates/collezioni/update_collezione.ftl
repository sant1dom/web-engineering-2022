<#include "../outlines/outline_header.ftl">
<h2 class="ml-5 mt-3">Modifica collezione</h2>

 <!-- DA FINIRE -->

<form method="post" action="write">

    <div class="container">
        <input type="hidden" name="k" value="${collezione.key!0}"/>
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
    </div>
</form>