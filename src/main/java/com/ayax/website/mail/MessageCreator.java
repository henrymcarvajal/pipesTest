/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.mail;

import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Vehiculo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class MessageCreator {

    private static final String PLANTILLA_SERVICIO_NUEVO = "servicio_nuevo.ftl";
    private static final String PLANTILLA_OFERTA_NUEVA = "oferta_nueva.ftl";
    private static final String PLANTILLA_RECUPERAR_CONTRASENA = "recuperar_contrasena.ftl";
    private static final String PLANTILLA_TRANSPORTADOR_NUEVO = "transportador_nuevo.ftl";
    private static final String PLANTILLA_OFERTA_ACEPTADA = "oferta_aceptada.ftl";
    private static final String PLANTILLA_SERVICIO_INICIADO = "servicio_iniciado.ftl";
    private static final String PLANTILLA_SERVICIO_TERMINADO = "servicio_terminado.ftl";
    private static final String PLANTILLA_CALIFICAR_TRANSPORTADOR = "calificar_transportador.ftl";
    private static final String PLANTILLA_CALIFICAR_USUARIO = "calificar_usuario.ftl";
    private static final String PLANTILLA_COMENTARIO_NUEVO = "comentario_nuevo.ftl";
    private static final String PLANTILLA_RESPUESTA_COMENTARIO = "respuesta_comentario.ftl";

    public String crearMensajeServicioNuevo(Servicio servicio, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("servicio", servicio);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_SERVICIO_NUEVO, rootMap);
    }

    public String crearMensajeOfertaNueva(Oferta oferta, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_OFERTA_NUEVA, rootMap);
    }

    public String crearMensajeOfertaAceptada(Oferta oferta, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_OFERTA_ACEPTADA, rootMap);
    }

    public String crearMensajeRecuperarContrasena(Transportador transportador, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("transportador", transportador);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_RECUPERAR_CONTRASENA, rootMap);
    }

    public String crearMensajeTransportadorNuevo(Transportador transportador, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("transportador", transportador);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_TRANSPORTADOR_NUEVO, rootMap);
    }

    public String crearMensajeServicioIniciado(Servicio servicio, Transportador transportador, boolean test) {
        Collection<Vehiculo> veh = transportador.getVehiculoCollection();
        Object[] lveh = veh.toArray();
        Vehiculo v = (Vehiculo) lveh[0];

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("servicio", servicio);
        rootMap.put("transportador", transportador);
        rootMap.put("test", test);
        rootMap.put("docsAlDia", v.esVehiculoRevisado());
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_SERVICIO_INICIADO, rootMap);
    }

    public String crearMensajeServicioTerminado(Servicio servicio, Transportador transportador, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("servicio", servicio);
        rootMap.put("transportador", transportador);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_SERVICIO_TERMINADO, rootMap);
    }

    public String crearMensajeCalificarTransportador(Oferta oferta, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_CALIFICAR_TRANSPORTADOR, rootMap);
    }

    public String crearMensajeCalificarUsuario(Oferta oferta, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("oferta", oferta);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_CALIFICAR_USUARIO, rootMap);
    }

    public String crearMensajeComentarioNuevo(Conversacion conversacion, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("conversacion", conversacion);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_COMENTARIO_NUEVO, rootMap);
    }

    public String crearMensajeRespuestaComentario(Conversacion conversacion, boolean test) {
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("conversacion", conversacion);
        rootMap.put("test", test);
        ContentGenerator cg = new ContentGenerator();
        return cg.generateFrom(PLANTILLA_RESPUESTA_COMENTARIO, rootMap);
    }
}
