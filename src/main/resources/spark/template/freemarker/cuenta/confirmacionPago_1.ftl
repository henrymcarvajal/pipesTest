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
        <div>Esta es la confirmacion del pago</div>
        <p>Id del cliente :${x_cust_id_cliente}</p><br/>
        <p>Descripci&oacute;n del producto :${x_description}</p><br/>
        <p>Valor total del producto :${x_amount_ok}</p><br/>
        <p>Id de la factura :${x_id_invoice}</p><br/>
        <p>Valor sin IVA :${x_amount_base}</p><br/>
        <p>IVA :${x_tax}</p><br/>
        <p>C&oacute;digo de la moneda (COP ,USD) :${x_currency_code}</p><br/>
        <p>Franquicia (VISA, AMEX, AMERICAN EXPRESS...) :${x_franchise}</p><br/>
        <p>Direcci&oacute;n IP donde se origin&oacute; la transacci&oacute;n :${x_customer_ip}</p><br/>
        <p>Fecha y hora de la transacci&oacute;n :${x_transaction_date}</p><br/>
        <p>C&oacute;digo de aprobaci&oacute;n :${x_approval_code}</p><br/>
        <p>Numero de recibo de la transacci&oacute;n :${x_transaction_id}</p><br/>
        <p>Numero de referencia de payco :${x_ref_payco}</p><br/>
        <p>C&oacute;digo de la respuesta :${x_cod_response}</p><br/>
        <p>Respuesta o estado de la transacci&oacute;n (Aceptada, Rechazada, Pendiente) :${x_response}</p><br/>
        <p>Descripci&oacute;n de la respuesta :${x_response_reason_text}</p><br/>
        <p>Dato extra 1 :${x_extra1}</p><br/>
        <p>Dato extra 2 :${x_extra2}</p><br/>
        <p>Dato extra 3 :${x_extra3}</p><br/>
        </body>
    </html>
