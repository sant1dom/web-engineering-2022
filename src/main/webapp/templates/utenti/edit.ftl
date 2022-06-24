<#-- @ftlvariable name="error" type="String" -->
<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->

<#include "../outlines/outline_header.ftl">

<div class="page-container page-bg flex justify-center align-items-center">
    <div class="card form-card">
        <div class="card-header">
            <span class="form-title flex justify-center align-items-center">MODIFICA PROFILO</span>
        </div>
        <div class="card-body">
            <form method="post" action="edit-utente">
                <#if error??>
                    <div style="color: red;">
                        ${error!}
                    </div>
                </#if>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="Nome"
                               value="${utente.getNome()!""}">
                    </div>
                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" class="form-control" id="cognome" name="cognome"
                               placeholder="Cognome" value="${utente.getCognome()!""}">
                    </div>
                </div>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group">
                        <label for="email">Email<span class="text-red" style="color: red">*</span></label>
                        <input type="text" class="form-control" required id="email" name="email" placeholder="Email"
                               value="${utente.getEmail()!""}">
                    </div>
                    <div class="form-group">
                        <label for="username">Username<span class="text-red" style="color: red">*</span></label>
                        <input type="text" class="form-control" required id="username" name="username"
                               value="${utente.getUsername()!""}">
                    </div>
                </div>
                <div class="form-container flex justify-between flex-wrap">
                    <div class="form-group">
                        <label for="vecchia_password">Vecchia password<span class="text-red" style="color: red">*</span></label>
                        <input type="password" class="form-control" required id="vecchia_password" name="vecchia_password">
                    </div>

                    <div class="form-group">
                        <label for="password">Nuova password<span class="text-red" style="color: red">*</span></label>
                        <input type="password" class="form-control" required id="password" name="password">
                    </div>
                </div>
                <div class="flex justify-center align-items-center pt-3">
                    <button type="submit" class="btn btn-success">
                        SALVA
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
