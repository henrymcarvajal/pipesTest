<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax - Nuevo comentario</title>
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
        <p>Nuestros transportadores tienen una inquietud sobre tu servicio en <a href="${url_ayax}">Ayax.co</a>:</p>
        <p>&nbsp;</p>
        <table style="margin-left: auto; margin-right: auto;" border="1">
            <tbody>
                <tr>
                    <td>Origen</td>
                    <td>:</td>
                    <td>${conversacion.servicio.origen}</td>
                </tr>
				<tr>
                    <td>Destino</td>
                    <td>:</td>
                    <td>${conversacion.servicio.destino}</td>
                </tr>
				<tr>
                    <td>Hora salida</td>
                    <td>:</td>
                    <td>${conversacion.servicio.horaSalida?datetime}</td>
                </tr>
				<tr>
                    <td>N&uacute;mero pasajeros</td>
                    <td>:</td>
                    <td>${conversacion.servicio.numeroPasajeros}</td>
                </tr>
            </tbody>
        </table>
		
		<center>
                <a href="${url_ayax}/ResponderPregunta.html?id=${conversacion.id}">Responder Pregunta</a>
        </center>

        <p>Cordialmente,</p>
        <p>&nbsp;</p>
        <p>El equipo de Ayax.co,</p>
        <p><img src="${url_ayax}/img/ayax-letra.png" alt="Ayax" width="100" height="90" /></p>
    </body>
</html>