<#include "../outlines/outline_header.ftl">
<#if id??>

    <body>
    <form method="post" action="add-immagini" class="ml-5 mt-3" enctype="multipart/form-data" >
    <div class="form-group col-7">
        <label for="immagini" class="form-label">Scegli le foto da caricare:</label>
        <input class="form-control" type="file" id="immagini" name="immagini" multiple />
    </div>
        <div class="form-group col-7">
            <input type="submit" value="Aggiungi foto"><br>
        </div>

    </form>
    </body>
<#else>
<h2>Id vuoto</h2>
</#if>
