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
            <thead><tr><th colspan="3">Servicios</th></tr>
                <tr><th>Origen</th>
                    <th>Destino</th>
                    <th>Llegada</th>
                    <th>Salida</th>
                    </tr>
                </thead>
            <tbody>
                <#list servicios as item>
                <tr>
                    <td>${item.origen}</td>
                    <td>${item.destino}</td>
                    <td>${item.horaLlegada}</td>
                    <td>${item.usuario.id}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </body>
</html>