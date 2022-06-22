<#include "outlines/outline_header.ftl">

<#if (utente??)>
    Benvenuto ${utente.getNome()} ${utente.getCognome()}
    <br>
    <a href="crea-disco" >Aggiungi un disco</a>

    <br>
    <a href="create-collezione" >Aggiungi una collezione</a>

    <br>
    <a href="show-collezioni" >Visualizza le tue collezioni</a>
<#else>
    Accedi!
</#if>