<#setting datetime_format="yyyy/MM/dd HH:mm:ss">
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Ayax admin</title>
        <link rel="stylesheet" type="text/css" href="/css/admin/admin.css">
        <link rel="stylesheet" type="text/css" href="/css/admin/graphics.css">
    </head>
    <body>
<div class="pie" data-start="0" data-value="30"></div>
<div class="pie highlight" data-start="30" data-value="30"></div>
<div class="pie" data-start="60" data-value="40"></div>
<div class="pie big" data-start="100" data-value="260"></div>
<div>
        <table border="0">
            <thead>
                <tr>
                    <th colspan="9">DETALLE DE USUARIO</th>
                </tr>
                <tr>
                    <th>Fecha<br>Creaci&oacute;n</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Empresa</th>
                    <th>Identificaci&oacute;n</th>
                    <th>Telefono</th>
                    <th>Servicios completados</th>
                    <th>Servicios calificados</th>
                    <th>Servicios</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${item.fechaCreacion?datetime}</td>
                    <td>${item.nombre}</td>
                    <td>${item.buzonElectronico}</td>
                    <td>
                        <#if item.empresa??>
                            ${item.empresa}
                        <#else>
                            &nbsp;
                        </#if>
                    </td>
                    <td>${item.identificacion}</td>
                    <td>${item.telefono?c}</td>
                    <td>
                        <#if item.serviciosCompletados??>
                            ${item.serviciosCompletados}
                        <#else>
                            &nbsp;
                        </#if>
                    </td>
                    <td>
                        <#if item.serviciosCalificados??>
                            ${item.serviciosCalificados}
                        <#else>
                            &nbsp;
                        </#if>
                    </td>
                    <td>
                    <#if item.servicios??>
                        <#assign servicios=item.servicios?size>
                        <#if (servicios > 0)>
                            <a href="/admin/usuario/${item.id}/servicios/">${servicios}</a>
                        <#else>
                            0
                        </#if>
                    <#else>
                        0
                    </#if>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    </body>
</html>