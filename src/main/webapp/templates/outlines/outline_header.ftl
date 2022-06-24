<#-- @ftlvariable name="error" type="String" -->
<#-- @ftlvariable name="utente" type="org.webeng.collector_site.data.model.Utente" -->

<nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="z-index: 100; position: sticky; top: 0">
    <a class="navbar-brand" href="home">Collector Site</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <form method="GET" action="resultdispatcher" id="search-form" name="search-form">
            <div class="dropdown form-inline">
                <input class="search-bar pr-5" type="search" placeholder="Search" aria-label="Search"
                       id="search-box" name="keyword" autocomplete="off" form="search-form"
                <#if (keyword??)>
                    value="${keyword}"
                </#if>
                >
                <input type="hidden" name="item_id" id="item_id" form="search-form">
                <input type="hidden" name="item_type" id="item_type" form="search-form">
                <button class="btn-search" type="submit"><i class="fa fa-search"></i></button>
                <div class="dropdown-menu" id="suggestion-box"></div>
            </div>
        </form>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item nav-square">
                <a class="nav-link">Bottone</a>
            </li>
            <li class="nav-item nav-square dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Esplora
                </a>
                <div class="dropdown-menu scripted show" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="index-collezione">Collezioni</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="index-disco">Dischi</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="index-traccia">Tracce</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="index-autore">Autori</a>
                </div>
            </li>
        </ul>
        <#if (!(utente??))>
            <div class="nav-square-accedi">
                <a href="login" class="bt-navbar-accedi">Accedi</a>
                <a href="register" class="bt-navbar-registrati hidden">Registrati</a>
            </div>
            <a href="register" class="bt-navbar-registrati resp991">Registrati</a>
        <#else>
            <div class="nav-square hidden aligned">
                <a href="profilo" class=" profile hidden"><img class="user_logo" src="/assets/images/users/user_icon.png" alt=""></a>
                <a href="logout" class="bt-navbar-registrati hidden">Logout</a>
            </div>
            <a href="profilo" class="resp991"><img class="user_logo" src="/assets/images/users/user_icon.png" alt=""></a>
            <a href="logout" class="bt-navbar-registrati resp991">Logout</a>

        </#if>
    </div>
</nav>