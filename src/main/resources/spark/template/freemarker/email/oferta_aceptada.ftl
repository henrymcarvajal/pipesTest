<#assign url_inicioservicio = "${url_ayax}/oferta/${oferta.id}/inicio"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax - Oferta de Servicio Aceptada</title>
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
        <p>Tu oferta de servicio especial de transporte en <a href="${url_ayax}">Ayax.co</a> ha sido aceptada.</p>
        <p>&nbsp;</p>
		<p>El servicio sobre el que fue aceptada tu oferta es</p>
		<table style="margin-left: auto; margin-right: auto;" border="1">
            <tbody>
                <tr>
                    <td>Origen</td>
                    <td>:</td>
                    <td>${oferta.servicio.origen}</td>
                </tr>
                <tr>
                    <td>Destino</td>
                    <td>:</td>
                    <td>${oferta.servicio.destino}</td>
                </tr>
                <tr>
                    <td>Hora salida</td>
                    <td>:</td>
                    <td>${oferta.servicio.horaSalida?datetime}</td>
                </tr>
                <tr>
                    <td>Hora llegada</td>
                    <td>:</td>
                    <td>${oferta.servicio.horaLlegada?datetime}</td>
                </tr>
                <tr>
                    <td>N&uacute;mero pasajeros</td>
                    <td>:</td>
                    <td>${oferta.servicio.numeroPasajeros}</td>
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