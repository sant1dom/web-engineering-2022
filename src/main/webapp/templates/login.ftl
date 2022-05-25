<h1>Site Access</h1>
<#if logininfo??>
    <p>Logged in as ${logininfo.username} from address ${logininfo.ip}</p>
    <p><a href="logout">Logout</a></p>
<#else>
    <form method="post" action="login">
        <#if (referrer??)>
            <input name="referrer" type="hidden" value="${referrer}"/>
        </#if>
        <div class="container">
            <div class="row">
                <div class="four columns clabel">Username:</div>
                <div class="ten columns"><input name="u" type="text"/></div>
            </div>
            <div class="row">
                <div class="four columns clabel">Password:</div>
                <div class="ten columns"><input name="p" type="password"/></div>
            </div>
            <div class="row"><input value="login" name="login" type="submit"/></div>
        </div>
    </form>
</#if>





