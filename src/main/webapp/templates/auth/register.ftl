<!-- BACKGROUND-IMAGE -->
<div class="bg-image">
    <div class="container register_container">
        <div class="card bg-dark">
            <div class="card-header">
                <span class="titolo">REGISTRATI</span>
            </div>
            <div class="card-body">
                <form method="post" action="register" class="validate-form">
                    <#if error??>
                        <div class=text-red>
                            ${error!}
                        </div>
                    </#if>
                    <div class="form-one-line">
                        <label class="label mt-1" for="nome">Nome</label>
                        <label class="label mt-1" for="cognome">Cognome</label>
                    </div>
                    <div class="form-one-line">
                        <input class="input100 border-start-0 ms-0 form-control mr-1" type="text"
                               placeholder="Nome" name="nome" id="nome">

                        <input class="input100 border-start-0 form-control ms-0 ml-1" type="text"
                               placeholder="Cognome" name="cognome" id="cognome">
                    </div>

                    <label class="label mt-1" for="username">Username<span class="text-red">*</span></label>
                    <input class="input100 border-start-0 ms-0 form-control" type="text"
                           placeholder="Username" name="username" id="username">

                    <label class="label mt-1" for="email">Email<span class="text-red">*</span></label>
                    <input class="input100 border-start-0 ms-0 form-control" type="email"
                           placeholder="Email" name="email" id="email">

                    <label class="label mt-1" for="password">Password<span class="text-red">*</span></label>
                    <input class="input100 border-start-0 form-control ms-0" type="password"
                           placeholder="Password" name="password" id="password">

                    <div class="login-btn-container mt-3">
                        <button type="submit" class="btn btn-primary">
                            Registrati
                        </button>
                    </div>
                    <div class="text-center pt-3">
                        <p class="label">Non sei gi&agrave; registrato? <a href="login"
                                                                           class="text-primary ms-1">Accedi!</a>
                        </p>
                    </div>
                </form>
            </div>
        </div>
        <img class="vinile register_img" src="/assets/images/vinile_form.png">
    </div>
</div>