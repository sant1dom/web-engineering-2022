<#include "../outlines/outline_header.ftl">

<div class="profile_bg"></div>
<div class="name_line">
    <#if (autore??)>
        ${autore.getNome() + " " + autore.getCognome() + autore.getNomeArtistico()}
    </#if>
</div>








<#if (autore??)>
    Autore ${autore.getNome()}<br>
<#else>
    niente autore<br>
</#if>

<#if (dischi??)>
    DISCHI:<br>
    <#list dischi as d>
        <#if (d.getTitolo()??)>
            ${d.getTitolo()}<br>
        <#else>
            niente titolo<br>
        </#if>
    </#list>
<#else>
    niente dischi<br>
</#if>

<#if (tracce??)>
    TRACCE:<br>
    <#list tracce as t>
        <#if (t.getTitolo()??)>
            ${t.getTitolo()}<br>
        <#else>
            niente tracce<br>
        </#if>
    </#list>
<#else>
    niente tracce<br>
</#if>