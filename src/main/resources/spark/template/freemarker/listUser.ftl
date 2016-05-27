<!DOCTYPE html>
<html>
<head>
<#include "header.ftl">
</head>

<body>

<#include "nav.ftl">

<div class="container">

    <h1>Liste des utilisateurs</h1>
    <ul>
    <#list users as x>
        <li><a href="/weights/${x.id}">${x.firstname} ${x.lastname}</a></li>
    </#list>
    </ul>

</div>

</body>
</html>
