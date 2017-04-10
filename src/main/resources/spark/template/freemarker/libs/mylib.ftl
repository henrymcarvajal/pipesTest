<#function xxx param>
    <#if (param?has_content)>
        <#return "yes">
    <#else>
        <#return "no">
    </#if>
</#function>