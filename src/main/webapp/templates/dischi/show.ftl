<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->
<#-- @ftlvariable name="disco" type="org.webeng.collector_site.data.model.Disco" -->
<#-- @ftlvariable name="tracce" type="org.webeng.collector_site.data.model.Traccia[]" -->
<#-- @ftlvariable name="autori" type="org.webeng.collector_site.data.model.Autore[]" -->
<#-- @ftlvariable name="tracceAdd" type="org.webeng.collector_site.data.model.Traccia[]" -->
<#-- @ftlvariable name="autoriAdd" type="org.webeng.collector_site.data.model.Autore[]" -->
<#-- @ftlvariable name="immagini" type="org.webeng.collector_site.data.model.Image[]" -->


<#include "../outlines/outline_header.ftl">

<link href="/assets/css/page.css" rel="stylesheet"/>

<div class="page-container">
    <#if (disco??)>
        <div class="info">
            <h3>${disco.getTitolo()}</h3>
            <div class="labels">
                <span class="label-info">Barcode: </span>
                <br>
                <span class="label-info">Anno: </span>
                <br>
                <span class="label-info">Genere: </span>
                <br>
                <span class="label-info">Etichetta: </span>
                <br>
                <span class="label-info">Formato: </span>
                <#if (!(disco.getFormato() == 'DIGITALE'))>
                    <br>
                    <span class="label-info">Stato di conservazione: </span>
                </#if>
                <#if (disco.getPadre()??)>
                    <br>
                    <span class="label-info">Disco originale: </span>
                </#if>
            </div>
            <div class="testi">
                <span class="testo-info">${disco.getBarCode()}</span>
                <br>
                <span class="testo-info">${disco.getAnno()}</span>
                <br>
                <span class="testo-info">${disco.getGenere()?lower_case?cap_first}</span>
                <br>
                <span class="testo-info">${disco.getEtichetta()}</span>
                <br>
                <span class="testo-info">${disco.getFormato()?lower_case?cap_first}</span>
                <#if (!(disco.getFormato() == 'DIGITALE'))>
                    <br>
                    <span class="testo-info">${disco.getStatoConservazione()?lower_case?cap_first}</span>
                </#if>
                <#if (disco.getPadre()??)>
                    <br>
                    <span class="testo-info"><a href="show-disco?id=${disco.getPadre().getKey()}">
                            ${disco.getPadre().getTitolo()}</a>
                    </span>
                </#if>
            </div>
            <#if (utente?? && utente.getKey() == disco.getUtente().getKey())>
                <div class="actions">
                    <a class="btn btn-warning btn-wd-fixed"
                       href="edit-disco?id=${disco.getKey()}">
                        Modifica informazioni</a>
                </div>
            </#if>
        </div>
    </#if>

    <div class="horizontal-separator"></div>
    <div class="tabelle-filtro-container">
        <div class="side-bar-container">
            <#if (utente?? && utente.getKey() == disco.getUtente().getKey())>
                <div class="title">AZIONI</div>
                <div class="filtro mb-5">
                    <div style="width: 100%; padding: 1rem">
                        <button class="btn btn-success" id="add-tracce-btn" style="width: 100%;">Aggiungi tracce
                        </button>
                        <form method="post" action="add-tracce?id=${disco.getKey()}" id="add-tracce">
                            <div class="horizontal-separator filtro-horizontal-separator"></div>
                            <label for="tracce">Seleziona le tracce:</label>
                            <#if (tracceAdd?? && tracceAdd?size>0)>
                                <select id="tracce" name="tracce[]" class="selectpicker" multiple
                                        data-live-search="true">
                                    <#list tracceAdd as traccia>
                                        <option value="${traccia.key}">${traccia.titolo} | ${traccia.durata}
                                            | ${traccia.ISWC}</option>
                                    </#list>
                                </select>
                                <div class="flex justify-center mt-3" style="width: 100%;">
                                    <button type="submit" class="btn btn-warning">Aggiungi</button>
                                </div>
                            <#else>
                                <p>Non ci sono tracce</p>
                            </#if>
                        </form>
                    </div>

                    <div class="horizontal-separator filtro-horizontal-separator"></div>

                    <div style="width: 100%; padding: 1rem">
                        <button class="btn btn-success" id="add-autori-btn" style="width: 100%;">Aggiungi autori
                        </button>
                        <form method="post" action="add-autori?id=${disco.getKey()}" id="add-autori">
                            <div class="horizontal-separator filtro-horizontal-separator"></div>
                            <label for="autori">Seleziona le autori:</label>
                            <#if (autoriAdd?? && autoriAdd?size>0)>
                                <select id="autori" name="autori[]" class="selectpicker" multiple
                                        data-live-search="true">
                                    <#list autoriAdd as autore>
                                        <option value="${autore.key}">${autore.nome} ${autore.cognome}</option>
                                    </#list>
                                </select>
                                <div class="flex justify-center mt-3" style="width: 100%;">
                                    <button type="submit" class="btn btn-warning">Aggiungi</button>
                                </div>
                            <#else>
                                <p>Non ci sono autori</p>
                            </#if>
                        </form>
                    </div>

                    <div class="horizontal-separator filtro-horizontal-separator"></div>

                    <div style="width: 100%; padding: 1rem">
                        <button class="btn btn-success" id="add-immagini-btn" style="width: 100%;">Aggiungi
                            immagini
                        </button>
                        <form method="post" action="add-immagini?id=${disco.getKey()}" enctype="multipart/form-data"
                              id="add-immagini">
                            <div class="horizontal-separator filtro-horizontal-separator"></div>
                            <label for="immagini" class="form-label">Scegli le foto da caricare:</label>
                            <input type="file" id="immagini" name="immagini" multiple/>
                            <div class="flex justify-center mt-3" style="width: 100%;">
                                <button type="submit" class="btn btn-warning">Aggiungi</button>
                            </div>
                        </form>
                    </div>
                    <#if (immagini?? && immagini?size > 0) >
                        <div class="horizontal-separator filtro-horizontal-separator"></div>

                        <div style="width: 100%; padding: 1rem">
                            <button class="btn btn-danger" id="remove-immagini-btn" style="width: 100%;">Rimuovi
                                immagini
                            </button>
                            <div id="remove-immagini">
                                <div class="horizontal-separator filtro-horizontal-separator"></div>
                                <dl>
                                    <#list immagini as immagine>
                                        <dd>
                                            <div class="flex justify-around">

                                                <a class="btn btn-danger"
                                                   href="/delete-immagine?d=${disco.key}&i=${immagine.key}"><i
                                                            class="lni lni-trash-can"></i></a>
                                                <div style="width: 8rem;">
                                                    <img class="center" style="height: 2rem; width: auto"
                                                         src="/display-immagine?id_disco=${disco.key}&id_image=${immagine.key}"
                                                         alt="slide">
                                                </div>
                                            </div>
                                        </dd>
                                    </#list>
                                </dl>
                            </div>
                        </div>
                    </#if>
                </div>
            </#if>

            <div class="title">FILTRO</div>
            <div class="filtro">
                <div class="filtro-list">
                    <dl class="filtro-info list-group list-group-flush">
                        <#include "../outlines/filtro/outline_durata.ftl">
                        <#include "../outlines/filtro/outline_tipologia.ftl">
                    </dl>
                </div>
            </div>
        </div>

        <div class="tables-container">
            <#if (immagini?? && immagini?size > 0)>
                <div id="carouselIndicators" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                        <#list immagini as immagine>
                            <div class="carousel-item">
                                <img class="center" style="max-height: 25rem; height: 100%; width: auto;"
                                     src="/display-immagine?id_disco=${disco.key}&id_image=${immagine.key}"
                                     alt="slide">
                            </div>
                        </#list>
                    </div>
                    <a class="carousel-control-prev" href="#carouselIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </#if>

            <div class="table-container" id="tracce-container">
                <div class="title flex justify-between align-items-center">
                    TRACCE
                    <input onkeyup="ricerca(this.value, '#table-tbody-tracce')" type="text"
                           placeholder="Search.." class="input-filtro inner-table">
                </div>
                <#if (tracce?? && tracce?size > 0)>
                <div class="table-scrollable">
                    <table class="table table-borderless table-striped">
                        <thead class="table-dark">
                        <tr>
                            <th scope="col">ISWC</th>
                            <th scope="col">Titolo</th>
                            <th scope="col">Durata</th>
                            <#if (utente?? && utente.getKey() == disco.getUtente().getKey())>
                                <th scope="col" style="text-align: center">Azioni</th>
                            </#if>
                        </tr>
                        </thead>
                        <tbody id="table-tbody-tracce">
                        <#list tracce as traccia>
                            <tr>
                                <td>${traccia.getISWC()}</td>
                                <td>${traccia.getTitolo()}</td>
                                <td>${traccia.getDurata()}s</td>
                                <#if (utente?? && utente.getKey() == disco.getUtente().getKey())>
                                    <td style="text-align: center">
                                        <a href="remove-traccia?d=${disco.getKey()}&t=${traccia.getKey()}"
                                           class="btn btn-danger">Rimuovi</a>
                                    </td>
                                </#if>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                    <#else>
                        <div class="table-empty">Non ci sono tracce.</div>
                    </#if>
                </div>

                <div class="table-container" id="autori-container">
                    <div class="title flex justify-between align-items-center">
                        AUTORI
                        <input onkeyup="ricerca(this.value, '#table-tbody-autori')" type="text"
                               placeholder="Search.." class="input-filtro inner-table">
                    </div>
                    <#if (autori?? && autori?size > 0)>
                    <div class="table-scrollable">
                        <table class="table table-borderless table-striped">
                            <thead class="table-dark">
                            <tr>
                                <th scope="col">Nome e cognome</th>
                                <th scope="col">Nome d'arte</th>
                                <th scope="col">Tipologia</th>
                                <#if (utente?? && utente.getKey() == disco.getUtente().getKey())>
                                    <th scope="col" style="text-align: center">Azioni</th>
                                </#if>
                            </tr>
                            </thead>
                            <tbody id="table-tbody-autori">
                            <#list autori as autore>
                                <tr>
                                    <td><a class="link"
                                           href="show-autore?id=${autore.getKey()}">${autore.getNome() + " "+ autore.getCognome()}</a></td>
                                    <td>${autore.getNomeArtistico()}
                                    </td>
                                    <td>${autore.getTipologia()?lower_case?cap_first}</td>
                                    <#if (utente?? && utente.getKey() == disco.getUtente().getKey())>
                                        <td style="text-align: center">
                                            <a href="remove-autore?d=${disco.getKey()}&a=${autore.getKey()}"
                                               class="btn btn-danger">Rimuovi</a>
                                        </td>
                                    </#if>
                                </tr>
                            </#list>

                            </tbody>
                        </table>
                        <#else>
                            <div class="table-empty">Non ci sono autori.</div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>