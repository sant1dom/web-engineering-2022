<#include "outlines/outline_header.ftl">

<#if (utente??)>
    Benvenuto ${utente.getNome()} ${utente.getCognome()}
    <a href="crea-disco" >Aggiungi un disco</a>
<#else>
    Accedi!
</#if>