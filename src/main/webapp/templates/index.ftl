<#include "outlines/outline_header.ftl">

<#if (utente??)>
    Benvenuto ${utente.getNome()} ${utente.getCognome()}
<#else>
    Accedi!
</#if>