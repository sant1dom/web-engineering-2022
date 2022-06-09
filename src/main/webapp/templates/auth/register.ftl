 <!-- BACKGROUND-IMAGE -->
    <div class="login-img">

        <!-- GLOABAL LOADER -->
        <div id="global-loader">
            <img src="../../assets/images/loader.svg" class="loader-img" alt="Loader">
        </div>
        <!-- /GLOABAL LOADER -->

        <!-- PAGE -->
        <div class="page">
            <div class="">

                <!-- CONTAINER OPEN -->
                <div class="col col-login mx-auto mt-7">
                    <div class="text-center">
                        <img src="../../assets/images/brand/logo-white.png" class="header-brand-img m-0" alt="">
                    </div>
                </div>
                <div class="container-login100">
                    <div class="wrap-login100 p-6">
                        <form method="post" action="register" class="login100-form validate-form">
                            <span class="login100-form-title">
									Registrazione
								</span>
                            <#if error??>
                                <div class=text-red>
                                    ${error!}
                                </div>
                            </#if>
                            <div class="wrap-input100 validate-input input-group">
                                <a href="javascript:void(0)" class="input-group-text bg-white text-muted">
                                    <i class="mdi mdi-account" aria-hidden="true"></i>
                                </a>
                                <label for="nome"></label><input class="input100 border-start-0 ms-0 form-control" type="text" placeholder="Nome" name="nome" id="nome">
                            </div>
                            <div class="wrap-input100 validate-input input-group">
                                <a href="javascript:void(0)" class="input-group-text bg-white text-muted">
                                    <i class="mdi mdi-account" aria-hidden="true"></i>
                                </a>
                                <label for="cognome"></label><input class="input100 border-start-0 ms-0 form-control" type="text" placeholder="Cognome" name="cognome" id="cognome">
                            </div>
                            <div class="wrap-input100 validate-input input-group" data-bs-validate="E' richiesta una Username valida">
                                <a href="javascript:void(0)" class="input-group-text bg-white text-muted">
                                    <i class="mdi mdi-account" aria-hidden="true"></i>
                                </a>
                                <label for="username" class="text-red">*</label><input class="input100 border-start-0 ms-0 form-control" type="text" placeholder="Username" name="username" id="username">
                            </div>
                            <div class="wrap-input100 validate-input input-group" data-bs-validate="E' richiesta una email valida: ex@abc.xyz">
                                <a href="javascript:void(0)" class="input-group-text bg-white text-muted">
                                    <i class="zmdi zmdi-email" aria-hidden="true"></i>
                                </a>
                                <label for="email" class="text-red">*</label><input class="input100 border-start-0 ms-0 form-control" type="email" placeholder="Email" name="email" id="email">
                            </div>
                            <div class="wrap-input100 validate-input input-group" id="Password-toggle">
                                <a href="javascript:void(0)" class="input-group-text bg-white text-muted">
                                    <i class="zmdi zmdi-eye" aria-hidden="true"></i>
                                </a>
                                <label for="password" class="text-red">*</label><input class="input100 border-start-0 ms-0 form-control" type="password" placeholder="Password" name="password" id="password">
                            </div>
                            <div class="container-login100-form-btn">
                                <button type="submit" class="login100-form-btn btn-primary">
										Registrati
									</button>
                            </div>
                            <div class="text-center pt-3">
                                <p class="text-dark mb-0">Hai gi&agrave; un account?<a href="login" class="text-primary ms-1">Accedi</a></p>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- CONTAINER CLOSED -->
            </div>
        </div>
        <!-- END PAGE -->
    </div>
    <!-- BACKGROUND-IMAGE CLOSED -->
