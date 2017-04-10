/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.mail.Messenger;
import com.ayax.website.mail.MessageCreator;
import com.ayax.website.persistencia.entidades.Factura;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.FacturaFacade;
import com.ayax.website.persistencia.fachadas.OfertaFacade;
import com.ayax.website.persistencia.fachadas.ServicioFacade;
import com.ayax.website.persistencia.fachadas.TransportadorFacade;
import com.ayax.website.server.ConfigManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author Mauris
 */
public class AdminFactura {

    public AdminFactura() {
    }

    public ModelAndView actualizarFactura(Request req, Response res) {
        String view = "";
        
        String x_transaction_date = req.queryParams("x_transaction_date");
        String x_approval_code = req.queryParams("x_approval_code");
        String x_transaction_id = req.queryParams("x_transaction_id");
        String x_ref_payco = req.queryParams("x_ref_payco");
        String x_cod_response = req.queryParams("x_cod_response");
        String x_response_reason_text = req.queryParams("x_response_reason_text");
        String x_response = req.queryParams("x_response");

        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("oferta/id/transportador");
        respuesta.setVerbo("PUT");

        String idFactura = req.params("id");
        FacturaFacade of = new FacturaFacade();
        Factura factura = of.buscarPorId(idFactura);
        if (factura != null) {
            factura.setCodigoAprobacion(Integer.valueOf(x_approval_code));
            factura.setNumeroReferenciaPayco(Integer.valueOf(x_ref_payco));
            factura.setFechaHoraTx(x_transaction_date);
            factura.setCodigoRespuesta(Short.valueOf(x_cod_response));

            factura.setDescripcionRespuesta(x_response_reason_text);
            factura.setEstadoTx(x_response);

            if (x_cod_response.equalsIgnoreCase("1")) {
                try {
                    of.actualizar(factura);
                    factura.getTransportador().setCredito(factura.getValor().intValue() + factura.getTransportador().getCredito());
                    TransportadorFacade tf = new TransportadorFacade();
                    tf.actualizar(factura.getTransportador());
                } catch (Exception ex) {

                }
                view = "cuenta/confirmacionPago.ftl";

                respuesta.setCodigo("000");
                respuesta.setResultado("Exito");
            }
        } else {
            respuesta.setCodigo("001");
            respuesta.setResultado("");
        }
        ConfirmacionPago conf = new ConfirmacionPago();
        Map model = conf.ejecutar(req, res);
        model.put("factura", factura);
        return new ModelAndView(model, view);
    }
}
