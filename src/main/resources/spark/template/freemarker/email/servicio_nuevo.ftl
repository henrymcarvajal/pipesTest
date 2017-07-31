<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax - Nueva Solicitud de Servicio</title>
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
        <p>Has solicitado un servicio de transporte especial en <a href="${url_ayax}">Ayax.co</a>, con las siguientes caracter&iacute;sticas:</p>
        <p>&nbsp;</p>
        <table style="margin-left: auto; margin-right: auto;" border="1">
            <tbody>
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
        <p>&nbsp;</p>
		<p>&nbsp;Si deseas ver o editar la solicitud que acabas de hacer, puedes hacerlo entrando al siguiente enlace:</p><br/>
					<center>
                        <a href="${url_ayax}/EditarSolicitud.html?id=${servicio.id}">Editar Solicitud</a>
                    </center>
					<center>
                        <a href="${url_ayax}/detalleServicio.html?id=${servicio.id}">Ver Solicitud</a>
                    </center>
        <p>&nbsp;Por correo recibir&aacute;s ofertas de nuestros transportadores. Est&aacute; atento/atenta a su llegada.</p>
        <p>&nbsp;</p>
        <table style="margin-left: auto; margin-right: auto;" border="1">
            <tbody>
                <tr>
                    <td align="center">Tu C&oacute;digo de Autorizaci&oacute;n, para acceso r&aacute;pido</td>
                </tr>
                <tr>
                    <td align="center">${servicio.usuario.id[0..7]}</td>
                </tr>
            </tbody>
        </table>
        <p>&nbsp;</p>
        <p>Cordialmente,</p>
        <p>&nbsp;</p>
        <p>El equipo de Ayax.co,</p>
        <p><img src="${url_ayax}/img/ayax-letra.png" alt="Ayax" width="100" height="90" /></p>
    </body>
</html>