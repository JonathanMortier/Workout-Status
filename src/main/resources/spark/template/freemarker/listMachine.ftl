<!DOCTYPE html>
<html>
<head>
<#include "header.ftl">
</head>

<body>
<#include "nav.ftl">

<div class="container">

    <h1>Liste des Machines</h1>
    <ul>
    <#list machines as x>
        <li><a href="/machines/${x.id}">${x.name} (id : ${x.id})</a></li>
    </#list>
    </ul>

</div>
</body>
</html>
