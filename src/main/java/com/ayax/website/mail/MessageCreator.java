/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.mail;

import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class MessageCreator {

    private static final String NEW_SERVICE_TEMPLATE = "servicio_nuevo.ftl";
    private static final String NEW_OFFER_TEMPLATE = "oferta_nueva.ftl";
    private static final String RECOVER_PASSWORD_TEMPLATE = "recuperar_contrasena.ftl";
    private static final String NEW_TRANSPORTER_TEMPLATE = "transportador_nuevo.ftl";
    private static final String OFFER_ACCEPTED_TEMPLATE = "oferta_aceptada.ftl";
    private static final String SERVICE_STARTED_TEMPLATE = "servicio_iniciado.ftl";
    private static final String SERVICE_FINISHED_TEMPLATE = "servicio_terminado.ftl";
    private static final String CALIFICAR_TRANSPORTADOR_TEMPLATE = "calificar_transportador.ftl";
    private static final String CALIFICAR_USUARIO_TEMPLATE = "calificar_usuario.ftl";

    public String crearMensajeServicioNuevo(Servicio servicio) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("servicio", servicio);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(NEW_SERVICE_TEMPLATE, rootMap);
    }

    public String crearMensajeOfertaNueva(Oferta oferta) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(NEW_OFFER_TEMPLATE, rootMap);
    }

    public String crearMensajeOfertaAceptada(Oferta oferta) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(OFFER_ACCEPTED_TEMPLATE, rootMap);
    }

    public String crearMensajeRecuperarContrasena(Transportador transportador) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("transportador", transportador);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(RECOVER_PASSWORD_TEMPLATE, rootMap);
    }

    public String crearMensajeTransportadorNuevo(Transportador transportador) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("transportador", transportador);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(NEW_TRANSPORTER_TEMPLATE, rootMap);
    }

    public String crearMensajeServicioIniciado(Servicio servicio) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("servicio", servicio);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(SERVICE_STARTED_TEMPLATE, rootMap);
    }

    public String crearMensajeServicioTerminado(Servicio servicio, Transportador transportador) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("servicio", servicio);
        rootMap.put("transportador", transportador);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(SERVICE_FINISHED_TEMPLATE, rootMap);
    }

    public String crearMensajeCalificarTransportador(Oferta oferta) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(CALIFICAR_TRANSPORTADOR_TEMPLATE, rootMap);
    }

    public String crearMensajeCalificarUsuario(Oferta oferta) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(CALIFICAR_USUARIO_TEMPLATE, rootMap);
    }
}
