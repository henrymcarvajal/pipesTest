<#ftl strip_whitespace = true>


<#import "/libs/mylib.ftl" as my>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Confirmacion Pago</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </head>
    <body>
        <p><img style="display: block; margin-left: auto; margin-right: auto;" src="../img/ayax-logo.jpg" alt="Ayax" width="230" height="220" /></p>
        <div>Esta es la confirmacion de tu pago</div>
        <table style="margin-left: auto; margin-right: auto;" border="1">
            <thead>
                <tr>
                    <td colspan="2">Detalle de la Transacci&oacute;n</td>
                </tr>
                </thead>
            <tbody>
            <tr><td>Descripci&oacute;n del producto </td><td>${x_description}</td></tr>
            <tr><td>Valor total del producto </td><td>${x_amount_ok}</td></tr>
            <tr><td>Id de la factura </td><td>${x_id_invoice}</td></tr>
            <tr><td>Valor sin IVA </td><td>${x_amount_base}</td></tr>
            <tr><td>C&oacute;digo de la moneda (COP ,USD) </td><td>${x_currency_code}</td></tr>
            <tr><td>Franquicia (VISA, AMEX, AMERICAN EXPRESS...) </td><td>${x_franchise}</td></tr>
            <tr><td>Direcci&oacute;n IP donde se origin&oacute; la transacci&oacute;n </td><td>${x_customer_ip}</td></tr>
            <tr><td>Fecha y hora de la transacci&oacute;n </td><td>${x_transaction_date}</td></tr>
            <tr><td>C&oacute;digo de aprobaci&oacute;n </td><td>${x_approval_code}</td></tr>
            <tr><td>Numero de recibo de la transacci&oacute;n </td><td>${x_transaction_id}</td></tr>
            <tr><td>Numero de referencia de payco </td><td>${x_ref_payco}</td></tr>
            <tr><td>C&oacute;digo de la respuesta </td><td>${x_cod_response}</td></tr>
            <tr><td>Respuesta o estado de la transacci&oacute;n (Aceptada, Rechazada, Pendiente) </td><td>${x_response}</td></tr>
            <tr><td>Descripci&oacute;n de la respuesta </td><td>${x_response_reason_text}</td></tr>
            <tr><td>Dato extra 1 </td><td>${x_extra1}</td></tr>
            <tr><td>Dato extra 2 </td><td>${x_extra2}</td></tr>
            <tr><td>Dato extra 3 </td><td>${x_extra3}</td></tr>
            </tbody>
            </table>
        <#if x_cod_response == "1">
        <p>Haz nuevamente click <a href="../servicios.html?nombreUsuario=${factura.transportador.nombres} ${factura.transportador.apellidos}&valorRecargado=${factura.transportador.credito}">aqu&iacute;</a> para continuar ofertando.</p>
        <#else>
        <p>Haz nuevamente click <a href="../servicios.html?nombreUsuario=${factura.transportador.nombres} ${factura.transportador.apellidos}&valorRecargado=${factura.transportador.credito}">aqu&iacute;</a> para continuar ofertando.</p>
        </#if>
        </body>
    </html>
