<#include "outlines/outline_header.ftl">
<#include "outlines/outline_sidebar.ftl">

<!-- GLOABAL LOADER -->
<div id="global-loader">
    <img src="../assets/images/loader.svg" class="loader-img" alt="Loader">
</div>
<!-- End GLOABAL LOADER -->

<!-- PAGE -->
<div class="page">
    <div class="page-main">

        <!--app-content open-->
        <div class="main-content app-content mt-0">
            <div class="side-app">

                <!-- CONTAINER -->
                <div class="main-container container-fluid">

                    <!-- PAGE-HEADER -->
                    <div class="page-header">
                        <h1 class="page-title">Dashboard 01</h1>
                        <div>
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="/">Home</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Dashboard 01</li>
                            </ol>
                        </div>
                    </div>
                    <!-- PAGE-HEADER END -->

                    <!-- ROW-1 -->
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xl-12">
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-12 col-xl-3">
                                    <div class="card overflow-hidden">
                                        <div class="card-body">
                                            <div class="d-flex">
                                                <#if utente??>
                                                    ${utente.getNome()!}
                                                    ${utente.getCognome()!}
                                                <#else>
                                                    CIAO
                                                </#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-12 col-xl-3">
                                    <div class="card overflow-hidden">
                                        <div class="card-body">
                                            <div class="d-flex">
                                                VUOTO
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- ROW-1 END -->

                    <!-- ROW-2 -->
                    <div class="row">
                        <div class="col-sm-12 col-md-12 col-lg-12 col-xl-9">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">CARD VUOTA</h3>
                                </div>
                                <div class="card-body">

                                </div>
                            </div>
                        </div>
                        <!-- COL END -->
                    </div>
                    <!-- ROW-2 END -->
                </div>
                <!-- CONTAINER END -->
            </div>
        </div>
        <!--app-content close-->
    </div>
</div>
<#include "outlines/outline_footer.ftl">