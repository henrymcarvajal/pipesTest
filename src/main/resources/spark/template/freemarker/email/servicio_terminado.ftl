<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax - Calificaci&oacute;n de Servicio</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <p><img style="display: block; margin-left: auto; margin-right: auto;" src="${url_ayax}/img/ayax-logo.jpg" alt="Ayax" width="230" height="220" /></p>
        <>A continuaci&oacute;n encontrar&aacute;s la informaci&oacute; de contacto de tu transportador:</p>
        <p>&nbsp;</p>
        <table style="margin-left: auto; margin-right: auto;" border="1">
            <thead>
                <tr>
                    <th span="3">Transportador</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Nombre</td>
                    <td>:</td>
                    <td>${transportador.nombres} &nbsp; ${transportador.apellidos}</td>
                </tr>
                <tr>
                    <td>Correo</td>
                    <td>:</td>
                    <td>${transportador.buzonElectronico}</td>
                </tr>
                <tr>
                    <td>Contacto</td>
                    <td>:</td>
                    <td>${transportador.numeroContacto?string.computer}</td>
                </tr>
            </tbody>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;Por favor, <a href="${url_ayax}/calificacionTransportador.html?id=${transportador.numeroContacto?string.computer}">califica al transportador</a> una vez haya terminado el servicio.</p>
        <p>&nbsp;</p>
        <!--table style="margin-left: auto; margin-right: auto;" border="1">
            <tbody>
                <tr>
                    <td align="center"><a href="">5</td>
                    <td>Excelente</td>
                </tr>
                <tr>
                    <td align="center"><a href="">4</td>
                    <td>Bueno</td>
                </tr>
                <tr>
                    <td align="center"><a href="">3</td>
                    <td>Regular</td>
                </tr>
                <tr>
                    <td align="center"><a href="">2</td>
                    <td>Malo</td>
                </tr>
                <tr>
                    <td align="center"><a href="">1</td>
                    <td>P&eacute;simo</td>
                </tr>
            </tbody>
        </table-->
        <p>&nbsp;</p>
        <p>Cordialmente,</p>
        <p>&nbsp;</p>
        <p>El equipo de Ayax.co,</p>
        <p><img src="${url_ayax}/img/ayax-letra.png" alt="Ayax" width="100" height="90" /></p>
    </body>
</html>