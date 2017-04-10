<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax - Detalle de Veh&iacute;culo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <table style="margin-left: auto; margin-right: auto;" border="1">
            <thead>
                <tr>
                    <td colspan="3">Datos del Veh&iacute;culo</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Nombres conductor</td>
                    <td>:</td>
                    <td>${vehiculo.transportador.nombres}</td>
                </tr>
                <tr>
                    <td>Apellidos conductor</td>
                    <td>:</td>
                    <td>${vehiculo.transportador.apellidos}</td>
                </tr>
                <tr>
                    <td>Placa</td>
                    <td>:</td>
                    <td>${vehiculo.placa}</td>
                </tr>
                <tr>
                    <td>Ciudad</td>
                    <td>:</td>
                    <td>${vehiculo.ciudad}</td>
                </tr>
                <tr>
                    <td>Marca</td>
                    <td>:</td>
                    <td>${vehiculo.marca}</td>
                </tr>
                <tr>
                    <td>Modelo</td>
                    <td>:</td>
                    <td>${vehiculo.modelo}</td>
                </tr>
                <tr>
                    <td>Pasajeros</td>
                    <td>:</td>
                    <td>${vehiculo.numeroPasajeros}</td>
                </tr>
                <tr>
                    <td>Acondicionado</td>
                    <td>:</td>
                    <td>${vehiculo.acondicionado?string("Si", "No")}</td>
                </tr>
                <tr>
                    <td>SOAT</td>
                    <td>:</td>
                    <td><img alt="soat" src="${soat}" /></td>
                </tr>
                <tr>
                    <td>Revision Tecnomecanica</td>
                    <td>:</td>
                    <td><img alt="revisionTecnomecanica" src="${revisionTecnomecanica}" /></td>
                </tr>
                <tr>
                    <td>Seguro</td>
                    <td>:</td>
                    <td><img alt="seguroContractual" src="${seguroContractual}" /></td>
                </tr>
                <tr>
                    <td>Seguro Extra</td>
                    <td>:</td>
                    <td><img alt="seguroExtracontractual" src="${seguroExtracontractual}" /></td>
                </tr>
                <tr>
                    <td>Tarjeta</td>
                    <td>:</td>
                    <td><img alt="tarjetaPropiedad" src="${tarjetaPropiedad}" /></td>
                </tr>
                <tr>
                    <td>Foto</td>
                    <td>:</td>
                    <td><img alt="fotoVehiculo" src="${fotoVehiculo}" /></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>