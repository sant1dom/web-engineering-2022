<div class="row" id="footer">
    <div class="sixteen columns">Copyright &copy; 2014 ${defaults.author}. Page compiled on ${compiled_on?datetime}</div>   
    <#if latest_issue??>
    <div class="sixteen columns"><em>Latest issue published: <a href="issue?n=${latest_issue.key}">${latest_issue.number}</a></em></div>
    </#if>
</div>
