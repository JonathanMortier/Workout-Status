<!DOCTYPE html>
<html>
<head>
<#include "header.ftl">
</head>

<body>

<#include "nav.ftl">

<div class="container">

    <h1>List Users</h1>
    <ul>
    <#list users as x>
        <li><a href="/users/${x.id}">${x.firstname} ${x.lastname}</a></li>
    </#list>
    </ul>

</div>

</body>
</html>
