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
                <tr>
                    <th colspan="11">DETALLE DE USUARIO</th>
                </tr>
                <tr>
                    <th>Fecha<br>Creaci&oacute;n</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Empresa</th>
                    <th>Identificaci&oacute;n</th>
                    <th>Telefono</th>
                    <th>Servicio completados</th>
                    <th>Servicios calificados</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${item.fechaCreacion?datetime}</td>
                    <td>${item.nombre}</td>
                    <td>${item.buzonElectronico}</td>
                    <td>${item.empresa}</td>
                    <td>${item.identificacion}</td>
                    <td>${item.telefono}</td>
                    <td>${item.serviciosCompletados}</td>
                    <td>${item.serviciosCalificados}</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>