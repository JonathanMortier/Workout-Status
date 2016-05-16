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

        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Machine</th>
                            <th>Poids</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list userView.viewList as mw>
                            <tr>
                                <td>${mw.machine.name}</td>
                                <td>${mw.weight.weight}</td>
                                <td>
                                    <a href="/weights/${userView.user.id}/${mw.machine.id}/-2"><button type="button" class="btn btn-sm btn-danger">--</button></a>
                                    <a href="/weights/${userView.user.id}/${mw.machine.id}/-1"><button type="button" class="btn btn-sm btn-danger">--</button></a>
                                    <a href="/weights/${userView.user.id}/${mw.machine.id}/1"><button type="button" class="btn btn-sm btn-success">+</button></a>
                                    <a href="/weights/${userView.user.id}/${mw.machine.id}/2"><button type="button" class="btn btn-sm btn-success">++</button></a>
                                </td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>