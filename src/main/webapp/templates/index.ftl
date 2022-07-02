<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="dischi" type="org.webeng.collector_site.data.model.Disco[]" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->
<#-- @ftlvariable name="utenti" type="org.webeng.collector_site.data.model.Utente[]" -->

<#include "outlines/outline_header.ftl">

<div class="page-container">
    <div class="jumbotron jumbotron-fluid home-jumbotron">
        <img class="home-jumbotron-img" src="/assets/images/vinile.png" alt="">
        <div class="container home-jumbotron-desc">
            <h1 class="display-4 home-jumbotron-text">Collector Site</h1>
            <p class="lead home-jumbotron-text">Condividi la tua passione per la musica con la community!</p>
        </div>
    </div>

<style>
    .home-jumbotron-img {
        margin-left: 10rem;
        height: 15rem;
        width: auto;
    }

    .home-jumbotron {
        background-image: url("/assets/images/home_bg.jpg");
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        height: 70vh;

        display: flex;
        align-items: center;
    }
    .home-jumbotron-text {
        background: #343a40;
        color: #fff;
        font-weight: bold;
        font-family: 'Roboto', sans-serif;
    }

    .home-jumbotron-desc {
        margin-left: 20rem;
        position: absolute;
        z-index: 2;

    }
</style>




    <#if (utente??)>
        <h3 class="mb-4">Benvenuto: ${utente.getUsername()}</h3>
    </#if>

    <#if (dischi?? && dischi?size>0)>
        <div class="card">
            <div class="card-header">
                <h3>
                    DISCHI PIU' POPOLARI
                </h3>
            </div>
            <div class="card-body"
                 style="padding: 2rem; width: 100%; display: flex; flex-wrap: wrap; justify-content: space-between">
                <#list dischi as disco>
                    <div class="flex justify-between"
                         style="height: 100%; width: 45%; margin: 2rem ; border: 1px solid grey">
                        <#if (disco.getImmagini()?? && disco.getImmagini()?size > 0)>
                            <img style="max-width: 50%; height: auto; "
                                 src="/display-immagine?id_disco=${disco.getKey()}&id_image=${disco.getImmagini()[0].getKey()}"
                                 alt="slide">
                        </#if>
                        <div style="width: 100%; padding: 2rem">
                            <h3><a class="link" href="/show-disco?id=${disco.getKey()}">${disco.getTitolo()}</a>
                            </h3>
                            <p>${disco.getAnno()}</p>
                            <p>${disco.getGenere()}</p>
                            <p>${disco.getFormato()}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </#if>

    <#if (utenti?? && utenti?size>0)>
        <div class="card">
            <div class="card-header">
                <h3>
                    UTENTI PIU' ATTIVI
                </h3>
            </div>
            <div class="card-body"
                 style="padding: 2rem; width: 100%; display: flex; flex-wrap: wrap; justify-content: space-between">
                <#list utenti as user>
                    <div class="flex justify-between"
                         style="height: 100%; width: 100%; margin: 2rem ; border: 1px solid grey">

                        <div class="table-container" id="collezioni-container" STYLE="width: 50%">
                            <div class="title flex justify-between align-items-center">
                                COLLEZIONI
                            </div>
                            <div class="table-scrollable" style="margin-bottom: 0;">
                                <#if (user.getCollezioni()?? && user.getCollezioni()?size > 0)>
                                    <table class="table table-borderless table-striped">
                                        <thead class="table-dark">
                                        <tr>
                                            <th scope="col">Titolo</th>
                                            <th scope="col">Data creazione</th>
                                            <th scope="col">Proprietario</th>
                                            <th scope="col">Privacy</th>
                                        </tr>
                                        </thead>
                                        <tbody id="table-tbody-collezioni">
                                        <#list user.getCollezioni() as collezione>
                                            <tr>
                                                <td><a class="link"
                                                       href="show-collezione?id=${collezione.key}">${collezione.titolo}</a>
                                                </td>
                                                <td>${collezione.dataCreazione?date("yyyy-MM-dd")?string("dd-MM-yyyy")}</td>
                                                <td><a class="link"
                                                       href="profilo?id=${collezione.getUtente().getKey()}">${collezione.getUtente().getUsername()}</a>
                                                </td>
                                                <td>${collezione.getPrivacy()}</td>
                                            </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                <#else>
                                    <div class="table-empty">Non ci sono collezioni.</div>
                                </#if>
                            </div>
                        </div>
                        <div class="" style="width: 50%; padding: 2rem">
                            ${user.getUsername()}
                        </div>

                    </div>
                </#list>
            </div>
        </div>
    </#if>


    <#if (tracce?? && tracce?size>0)>
    <div class="table-container" id="tracce-container">
        <div class="title flex justify-between align-items-center">
            TRACCE
        </div>
        <#if (tracce?? && tracce?size > 0)>
        <div class="table-scrollable">
            <table class="table table-borderless table-striped">
                <thead class="table-dark">
                <tr>
                    <th scope="col">ISWC</th>
                    <th scope="col">Titolo</th>
                    <th scope="col">Durata</th>
                    <th scope="col">Autori</th>
                    <th scope="col"><span class="flex justify-center">Originale</span></th>
                </tr>
                </thead>
                <tbody id="table-tbody-tracce">
                <#list tracce as traccia>
                    <tr>
                        <td>${traccia.getISWC()}</td>
                        <td>${traccia.getTitolo()}</td>
                        <td>${traccia.getDurata()}s</td>
                        <td>
                            <#list traccia.getAutori() as autore>
                                ${autore.getNomeArtistico()},
                            </#list>
                        </td>
                        <td class="flex justify-center">
                            <#if (traccia.getPadre()?has_content)>
                                ${traccia.getPadre().getISWC()}
                            <#else>
                                SI
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <#else>
                <div class="table-empty">Non ci sono tracce.</div>
            </#if>
        </div>
        </#if>
    </div>
</div>