/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.mail.MessageCreator;
import com.ayax.website.mail.Messenger;
import com.ayax.website.persistencia.entidades.FacturaOferta;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Vehiculo;
import com.ayax.website.persistencia.fachadas.FacturaOfertaFacade;
import com.ayax.website.persistencia.fachadas.OfertaFacade;
import com.ayax.website.persistencia.fachadas.ServicioFacade;
import com.ayax.website.procesos.util.Encriptador;
import com.ayax.website.server.ConfigManager;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminReserva {

    public AdminReserva() {
    }

    public ModelAndView mostrarFormularioPago(Request req, Response res) {
        String idOferta = req.params(":id");

        OfertaFacade of = new OfertaFacade();
        Oferta oferta = of.buscarPorId(idOferta);

        if (oferta != null) {
            FacturaOferta facturaOferta;
            if (oferta.getFacturaOferta() == null) {
                facturaOferta = new FacturaOferta();
                facturaOferta.setIdOferta(oferta.getId());
                facturaOferta.setOferta(oferta);
                facturaOferta.setValor(BigInteger.valueOf(oferta.getReserva()));
                facturaOferta.setGenerada(Boolean.TRUE);

                try {
                    FacturaOfertaFacade fof = new FacturaOfertaFacade();
                    fof.crear(facturaOferta);
                    oferta.setFacturaOferta(facturaOferta);
                    oferta.setEstado(Oferta.ACEPTADA);
                    of.actualizar(oferta);

                    MessageCreator mc = new MessageCreator();
                    Messenger mess = new Messenger();
                    mess.sendMail("Tu oferta ha sido aceptada en Ayax.co", mc.crearMensajeOfertaAceptada(facturaOferta.getOferta(), ConfigManager.INSTANCE.isTestEnvironment()), new String[]{facturaOferta.getOferta().getTransportador().getBuzonElectronico(), "lespinosa@ayax.co"});
                } catch (Exception ex) {
                    Logger.getLogger(AdminReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                facturaOferta = oferta.getFacturaOferta();
            }

            Map<String, Object> model = new HashMap<>();
            model.put("oferta", oferta);
            model.put("usuario", oferta.getServicio().getUsuario());
            model.put("factura", facturaOferta);
            model.put("url_ayax", ConfigManager.INSTANCE.getEnvironment());
            model.put("test", ConfigManager.INSTANCE.isTestEnvironment() ? "TRUE" : "FALSE");

            model.put("ip_cliente", req.ip());
            model.put("signature", new Encriptador().encriptar(facturaOferta.getIdOferta(), oferta.getReserva().toString()));
            return new ModelAndView(model, "reserva/formularioPagoReserva.ftl");
        } else {
            res.redirect("/informativo.html?codigo=001&mensaje=No puedes recargar tu cuenta, hasta que los documentos de tu vehiculo esten al dia");
            return new ModelAndView(null, null);
        }
    }

    public ModelAndView mostrarConfirmacionPago(Request req, Response res) {
        String view = "reserva/confirmacionPagoReserva.ftl";

        String x_transaction_date = req.queryParams("x_transaction_date");
        String x_approval_code = req.queryParams("x_approval_code");
        String x_ref_payco = req.queryParams("x_ref_payco");
        String x_cod_response = req.queryParams("x_cod_response");
        String x_response_reason_text = req.queryParams("x_response_reason_text");
        String x_response = req.queryParams("x_response");

        String idFactura = req.params("id");
        FacturaOfertaFacade fof = new FacturaOfertaFacade();
        FacturaOferta facturaOferta = fof.buscarPorId(idFactura);
        if (facturaOferta != null) {
            facturaOferta.setCodigoAprobacion(Integer.valueOf(x_approval_code));
            facturaOferta.setNumeroReferenciaPayco(Integer.valueOf(x_ref_payco));
            facturaOferta.setFechaHoraTx(x_transaction_date);
            facturaOferta.setCodigoRespuesta(Short.valueOf(x_cod_response));

            facturaOferta.setDescripcionRespuesta(x_response_reason_text);
            facturaOferta.setEstadoTx(x_response);
            try {
                fof.actualizar(facturaOferta);

                if (x_cod_response.equalsIgnoreCase("1")) {
                    facturaOferta.getOferta().setEstado(Oferta.CONFIRMADA);
                    OfertaFacade of = new OfertaFacade();
                    of.actualizar(facturaOferta.getOferta());

                    facturaOferta.getOferta().getServicio().setFechaEjecucion(new Date(System.currentTimeMillis()));
                    ServicioFacade sf = new ServicioFacade();
                    sf.actualizar(facturaOferta.getOferta().getServicio());

                    MessageCreator mc = new MessageCreator();
                    Messenger mess = new Messenger();
                    mess.sendMail("Datos de tu cliente para Servicio Especial en Ayax.co", mc.crearMensajeServicioIniciado(facturaOferta.getOferta().getServicio(), facturaOferta.getOferta().getTransportador(), ConfigManager.INSTANCE.isTestEnvironment()), new String[]{facturaOferta.getOferta().getTransportador().getBuzonElectronico(), "lespinosa@ayax.co"});
                    mess.sendMail("Datos de tu transportador de Servicio Especial en Ayax.co", mc.crearMensajeServicioTerminado(facturaOferta.getOferta().getServicio(), facturaOferta.getOferta().getTransportador(), ConfigManager.INSTANCE.isTestEnvironment()), new String[]{facturaOferta.getOferta().getServicio().getUsuario().getBuzonElectronico(), "lespinosa@ayax.co"});

                }
            } catch (Exception ex) {

            }
        } else {
            //respuesta.setCodigo("001");
            //respuesta.setResultado("");
        }
        ConfirmacionTransaccionEpayco conf = new ConfirmacionTransaccionEpayco();
        Map model = conf.extrarDatos(req, res);
        model.put("factura", facturaOferta);
        model.put("url_ayax", ConfigManager.INSTANCE.getEnvironment());
        return new ModelAndView(model, view);
    }

}
