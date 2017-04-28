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
    <body id="top" data-spy="scroll" data-target=".navbar" data-offset="260">
        <table border="0">
            <thead>
                <tr><th colspan="7">Servicios</th></tr>
                <tr>
                    <th>Usuario</th>
                    <th>Creaci&oacute;n</th>
                    <th>Origen</th>
                    <th>Destino</th>
                    <th>Llegada</th>
                    <th>Salida</th>
                    <th>N&uacute;mero pasajeros</th>
                    <th>Ofertas</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><a href="/admin/usuario/${item.usuario.buzonElectronico}">${item.usuario.buzonElectronico}</a></td>
                    <td>${item.fechaCreacion?datetime}</td>
                    <td>${item.origen}</td>
                    <td>${item.destino}</td>
                    <td>${item.horaSalida?datetime}</td>
                    <td>${item.horaLlegada?datetime}</td>
                    <td>${item.numeroPasajeros}</td>
                    <td>${item.distancia}</td>
                    <td>${item.redondo?string}</td>
                    <td>${item.disponibilidad?string}</td>
                    <td>xxx
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
                <tr>
                    <td>${item.detalle}</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>