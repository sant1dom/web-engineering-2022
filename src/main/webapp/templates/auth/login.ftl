<#-- @ftlvariable name="error" type="String" -->
<#-- @ftlvariable name="referrer" type="String" -->
<link href="/assets/css/auth.css" rel="stylesheet"/>
<!-- BACKGROUND-IMAGE -->
<div class="bg-image">
    <div class="container login_container">
        <div class="card bg-dark">
            <div class="card-header">
                <span class="titolo">ACCEDI</span>
            </div>
            <div class="card-body">
                <form method="post" action="login" class="login100-form validate-form">
                    <#if referrer??>
                        <input type="hidden" value="${referrer!}" name="referrer">
                    </#if>
                    <#if error??>
                        <div class=text-red>
                            ${error!}
                        </div>
                    </#if>
                    <label class="label mt-1" for="username">Username</label>
                    <input class="input100 border-start-0 ms-0 form-control" type="text"
                           placeholder="Username" name="username" id="username">
                    <label class="label mt-1" for="password">Password</label>
                    <input class="input100 border-start-0 form-control ms-0" type="password"
                           placeholder="Password" name="password" id="password">
                    <div class="login-btn-container mt-3">
                        <button type="submit" class="btn btn-primary">
                            Login
                        </button>
                    </div>
                    <div class="text-center pt-3">
                        <p class="label">Non sei registrato?
                            <a href="register" class="text-primary ms-1">Registrati!</a>
                        </p>
                    </div>
                </form>
            </div>
        </div>
        <img class="vinile login_img" src="/assets/images/vinile.png" alt="">
    </div>
</div>