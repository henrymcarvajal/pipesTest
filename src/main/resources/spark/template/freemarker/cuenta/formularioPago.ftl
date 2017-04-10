<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax.co - Recarga de cuenta</title>
        <!--#include "../../common/header.ftl"-->            
    </head>
    <body>
        <!--#include "../../common/bar.ftl"-->
        <p><img style="display: block; margin-left: auto; margin-right: auto;" src="img/ayax-logo.jpg" alt="Ayax" width="230" height="220" /></p>
        <center>
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-sm-12">
                        <h1>Resumen de la Recarga</h1>
                        <p>Este es el monto que recargar&aacute;s tu cuenta: </p>
                        <h1>$${factura.valor}</h1>
                    </div>
                </div>
                <p>Haz click en el bot&oacute;n "Pagar con ePayco" para proceder con el pago electr&oacute;nico.</p>
                <form id="frm_botonePayco" name="frm_botonePayco" method="post" action="https://secure.payco.co/checkout.php"> 
                    <input name="p_cust_id_cliente" type="hidden" value="9770">
                    <input name="p_key" type="hidden" value="a77302019ab09dac1d0413bd9261e7de93668507">
                    <input name="p_id_invoice" type="hidden" value="${factura.id}">
                    <input name="p_description" type="hidden" value="Recarga de cuenta en Ayax.co por $${factura.valor}">
                    <input name="p_amount" id="p_amount" type="hidden" value="${factura.valor?string.computer}">
                    <input name="p_amount_base" id="p_amount_base" type="hidden" value="${factura.valor?string.computer}">
                    <input name="p_tax" id="p_tax" type="hidden" value="0.00">
                    <input name="p_currency_code" type="hidden" value="COP">
                    <input name="p_email" type="hidden" value="${transportador.buzonElectronico}">
                    <input name="p_signature" type="hidden" id="signature" value="${signature}" />
                    <input name="p_test_request" type="hidden" value="${test}">
                    <input name="p_customer_ip" type="hidden" value="127.0.0.1">
                    <input name="p_url_response" type="hidden" value="${url_ayax}/factura/${factura.id}"> 
                    <input name="p_url_confirmation" type="hidden" value="${url_ayax}/confirmacionPago"> 
                    <input name="idboton"type="hidden" id="idboton"  value="689" />  
                    <input type="image" id="imagen" src="https://369969691f476073508a-60bf0867add971908d4f26a64519c2aa.ssl.cf5.rackcdn.com/btns/btn1.png" />
                </form>
            </div>
        </center>
        <!--#include "../../common/copyright.ftl"-->            
        <!--#include "../../common/footer.ftl"-->            
    </body>
</html>
