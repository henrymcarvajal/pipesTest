<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax - Nueva Oferta de Servicio</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <p><img style="display: block; margin-left: auto; margin-right: auto;" src="${url_ayax}/img/ayax-logo.jpg" alt="Ayax" width="230" height="220" /></p>
        <p>Has recibido una oferta de servicio especial de transporte en <a href="${url_ayax}">Ayax.co</a> por un valor de COP$${oferta.valor}. 
		Por favor ten en cuenta que el transportador solo garantiza disponibilidad para el servicio hasta 24 horas despu&eacute;s del envio de la presente oferta, por 
		lo que si decides contratarle despu&eacute;s de 24 horas existe la posibilidad de que el transportador no este disponible.</p>
        <p>&nbsp;</p>
        <table style="margin-left: auto; margin-right: auto;" border="1">
                <tr>
                    <td colspan="3">
                        <center>
                        <!--a href="${url_ayax}/oferta/${oferta.id}">Ver detalles de la oferta</a-->
                        <a href="${url_ayax}/detalleOferta.html?id=${oferta.id}">Ver detalles de la oferta</a>
                        </center>
                    </td>
                </tr>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>Cordialmente,</p>
        <p>&nbsp;</p>
        <p>El equipo de Ayax.co,</p>
        <p><img src="${url_ayax}/img/ayax-letra.png" alt="Ayax" width="100" height="90" /></p>
    </body>
</html>