<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->
<#-- @ftlvariable name="utenti" type="org.webeng.collector_site.data.model.Utente[]" -->

<#include "outlines/outline_header.ftl">
<link href="/assets/css/home.css" rel="stylesheet"/>

<div class="page-container">
    <div class="jumbotron jumbotron-fluid home-jumbotron justify-center">
        <div class="flex flex-wrap justify-center align-items-center">
            <img class="home-jumbotron-img resp991" src="/assets/images/vinile.png" alt="">
            <div class="home-jumbotron-desc">
                <h1 class="display-4 home-jumbotron-text">Collector Site</h1>
                <p class="lead home-jumbotron-text">Condividi la tua passione per la musica con la community!</p>
            </div>
        </div>
    </div>

    <div class="container-home">
        <#if (dischi?? && dischi?size>0)>
            <div class="card disco-card" style="">
                <div class="card-header text-center">
                    <strong class="titolo-dischi-home">DISCHI PIU' POPOLARI</strong>
                </div>
                <div class="card-body flex flex-wrap justify-evenly">
                    <#list dischi as disco>
                        <div class="disco-card-body flex justify-start align-items-center">
                            <#if (disco.getImmagini()?? && disco.getImmagini()?size > 0)>
                                <img class="disco-card-img"
                                     src="/display-immagine?id_disco=${disco.getKey()}&id_image=${disco.getImmagini()[0].getKey()}"
                                     alt="slide">
                            <#else>
                                <img class="disco-card-img"
                                     src="/assets/images/vinile.png"
                                     alt="slide">
                            </#if>
                            <div>
                                <h4><a class="link" href="/show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a>
                                </h4>
                                <p>${disco.getAnno()}, ${disco.getGenere()}, ${disco.getFormato()}</p>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>
    </div>
</div>