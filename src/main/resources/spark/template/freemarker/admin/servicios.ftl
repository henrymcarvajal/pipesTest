<#setting datetime_format="yyyy/MM/dd HH:mm:ss">
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Ayax admin</title>
        <link rel="stylesheet" type="text/css" href="/css/admin/admin.css">
    </head>
    <body>
        <table border="0">
            <thead>
                <tr><th colspan="9">SERVICIOS</th></tr>
                <tr>
                    <th>Detalle</th>
                    <th>Usuario</th>
                    <th>Fecha<br/>Creaci&oacute;n</th>
                    <th>Origen</th>
                    <th>Destino</th>
                    <th>Llegada</th>
                    <th>Salida</th>
                    <th>N&uacute;mero pasajeros</th>
                    <th>Ofertas</th>
                </tr>
            </thead>
            <tbody>
                <#list servicios as item>
                <tr>
                    <td><a href="/admin/servicio/${item.id}">*</a></td>
                    <td><a href="/admin/usuario/${item.usuario.buzonElectronico}">${item.usuario.buzonElectronico}</a></td>
                    <td>${item.fechaCreacion?datetime}</td>
                    <td>${item.origen}</td>
                    <td>${item.destino}</td>
                    <td>${item.horaSalida?datetime}</td>
                    <td>${item.horaLlegada?datetime}</td>
                    <td>${item.numeroPasajeros}</td>
                    <td>
                    <#if item.ofertas??>
                        <#assign ofertas=item.ofertas?size>
                        <#if (ofertas > 0)>
                            <a href="/admin/servicio/${item.id}/ofertas">${ofertas}</a>
                        <#else>
                            0
                        </#if>
                    <#else>
                        0
                    </#if>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </body>
</html>