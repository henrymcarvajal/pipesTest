<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax - Inicio de Servicio</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <#if test>
        <table bgcolor="#FF0000" width="100%">
          <tr>
            <th><p style="color:white">Prueba</p></th>
          </tr>
        </table>
        </#if>
        <p><img style="display: block; margin-left: auto; margin-right: auto;" src="${url_ayax}/img/ayax-logo.jpg" alt="Ayax" width="230" height="220" /></p>
        <p>A continuaci&oacute;n encontrar&aacute;s los datos del servicio y la informaci&oacute;n de contacto del solicitante:</p>
        <p>&nbsp;</p>
        <table style="margin-left: auto; margin-right: auto;" border="1">
            <thead>
                <tr>
                    <th colspan="3">Solicitante</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Nombre</td>
                    <td>:</td>
                    <td>${servicio.usuario.nombre}</td>
                </tr>
                <tr>
                    <td>Usuario</td>
                    <td>:</td>
                    <td>${servicio.usuario.buzonElectronico}</td>
                </tr>
				<tr>
                    <td>Tel&eacute;fono</td>
                    <td>:</td>
                    <td>${servicio.usuario.telefono?string.computer}</td>
                </tr>
                <tr>
                    <td>Origen</td>
                    <td>:</td>
                    <td>${servicio.origen}</td>
                </tr>
                <tr>
                    <td>Destino</td>
                    <td>:</td>
                    <td>${servicio.destino}</td>
                </tr>
                <tr>
                    <td>Hora salida</td>
                    <td>:</td>
                    <td>${servicio.horaSalida?datetime}</td>
                </tr>
                <tr>
                    <td>Hora Llegada</td>
                    <td>:</td>
                    <td>${servicio.horaLlegada?datetime}</td>
                </tr>
                <tr>
                    <td>N&uacute;mero pasajeros</td>
                    <td>:</td>
                    <td>${servicio.numeroPasajeros}</td>
                </tr>
                <tr>
                    <td>Ida y vuelta</td>
                    <td>:</td>
                <#if servicio.redondo>
                    <td>S&iacute;</td>
                <#else>
                    <td>No</td>
                </#if>
                </tr>
                <tr>
                    <td>Disponibilidad</td>
                    <td>:</td>
                <#if servicio.disponibilidad>
                    <td>S&iacute;</td>
                <#else>
                    <td>No</td>
                </#if>
                </tr>
            </tbody>
        </table>
        <#if !docsAlDia>
        <p><b>Es necesario poner al d&iacute;a los documentos del veh&iacute;culo para prestar un excelente servicio.</b></p>
        </#if>
        <p>&nbsp;</p>
        <p>Cordialmente,</p>
        <p>&nbsp;</p>
        <p>El equipo de Ayax.co,</p>
        <p><img src="${url_ayax}/img/ayax-letra.png" alt="Ayax" width="100" height="90" /></p>
    </body>
</html>