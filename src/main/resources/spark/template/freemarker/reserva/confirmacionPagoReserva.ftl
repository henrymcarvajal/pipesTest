<#ftl strip_whitespace = true>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax.co - Confirmacion de Transacci&oacute;n</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="${url_ayax}/img/ico/ayax.ico">
    </head>
    <body>
        <p><img style="display: block; margin-left: auto; margin-right: auto;" src="${url_ayax}/img/ayax-logo.jpg" alt="Ayax" width="230" height="220" /></p>
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-sm-12">
                    <center>
                    <h1>Resumen de la Transacci&oacute;n</h1>
                    </center>
                    <table style="margin-left: auto; margin-right: auto;" border="1">
                        <!--thead>
                            <tr>
                                <th colspan="2">Detalle de la Transacci&oacute;n</th>
                            </tr>
                        </thead-->
                        <tbody>
                            <tr><td>Descripci&oacute;n del producto</td><td>${x_description}</td></tr>
                            <tr><td>Valor total</td><td>${x_amount_ok}</td></tr>
                            <!--tr><td>Id de la factura </td><td>${x_id_invoice}</td></tr-->
                            <tr><td>Valor sin IVA </td><td>${x_amount_base}</td></tr>
                            <tr><td>Moneda (COP ,USD) </td><td>${x_currency_code}</td></tr>
                            <tr><td>Franquicia (VISA, AMEX, AMERICAN EXPRESS...) </td><td>${x_franchise}</td></tr>
                            <!--tr><td>Direcci&oacute;n IP donde se origin&oacute; la transacci&oacute;n </td><td>${x_customer_ip}</td></tr-->
                            <tr><td>Fecha y hora de la transacci&oacute;n </td><td>${x_transaction_date}</td></tr>
                            <tr><td>C&oacute;digo de aprobaci&oacute;n </td><td>${x_approval_code}</td></tr>
                            <!--tr><td>Numero de recibo de la transacci&oacute;n </td><td>${x_transaction_id}</td></tr-->
                            <tr><td>Numero de referencia de payco </td><td>${x_ref_payco}</td></tr>
                            <!--tr><td>C&oacute;digo de la respuesta </td><td>${x_cod_response}</td></tr-->
                            <tr><td>Estado de la transacci&oacute;n (Aceptada, Rechazada, Pendiente) </td><td>${x_response}</td></tr>
                            <!--tr><td>Descripci&oacute;n de la respuesta </td><td>${x_response_reason_text}</td></tr-->
                            <!--tr><td>Dato extra 1 </td><td>${x_extra1}</td></tr-->
                            <!--tr><td>Dato extra 2 </td><td>${x_extra2}</td></tr-->
                            <!--tr><td>Dato extra 3 </td><td>${x_extra3}</td></tr-->
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-sm-12">
                    <center>
                    <#if x_cod_response == "1">
                    <p>Te hemos enviado a t&iacute; y al transportador un correo con la informaci&oacute;n de contacto.</p>
                    <p>Gracias por contar con <a href="${url_ayax}">Ayax.co</a>, para tus necesidades de transporte.</p>
                    <p>Te invitamos a calificar al transportador cuando haya finalizado el servicio, porque tu calificaci&oacute;n nos permitir&aacute; servirte mejor.</p>
                    <#else>
                    <p>Tan pronto podamos confirmar tu pago, nos comunicaremos con el transportador, para definir los detalles adicionales del servicio.</p>
                    </#if>
                    </center>
                </div>
            </div>
        </div>
    </body>
</html>
