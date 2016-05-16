<!DOCTYPE html>
<html>
<head>
<#include "header.ftl">
</head>

<body>
<#include "nav.ftl">

<div class="container">

    <h1>Détail de l'utilisateur</h1>
    <ul>
        <li>Nom : ${userView.user.lastname}</li>
        <li>Prénom : ${userView.user.firstname}</li>
        <li>Mail : ${userView.user.mail}</li>
    </ul>

    <h2>Poids par machine (en kg)</h2>
    <ul>
        <#list userView.viewList as mw>
            <li>${mw.machine.name} ${mw.weight.weight} :
                <a href="/weights/${userView.user.id}/${mw.machine.id}/-2">--</a>  <a href="/weights/${userView.user.id}/${mw.machine.id}/-1">-</a>
                <a href="/weights/${userView.user.id}/${mw.machine.id}/1">+</a>  <a href="/weights/${userView.user.id}/${mw.machine.id}/2">++</a></li>
        </#list>
    </ul>

</div>
</body>
</html>