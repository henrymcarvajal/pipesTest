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
            <thead><tr><th colspan="5">Servicios</th></tr>
                <tr>
                    <th>Creaci&oaacute;</th>
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
                    <td>${item.fechaCreacion?datetime}</td>
                    <td>${item.origen}</td>
                    <td>${item.destino}</td>
                    <td>${item.horaSalida?datetime}</td>
                    <td>${item.horaLlegada?datetime}</td>
                    <td>${item.numeroPasajeros}</td>
                    <td><#if item.ofertas??><#assign x=item.ofertas.count><#else><#assign x=0></#if>${x}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </body>
</html>