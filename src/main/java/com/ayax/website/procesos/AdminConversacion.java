/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.mail.MessageCreator;
import com.ayax.website.mail.Messenger;
import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.controladores.ServicioJpaController;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.controladores.ConversacionJpaController;
import com.ayax.website.persistencia.controladores.MensajeJpaController;
import com.ayax.website.server.ConfigManager;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminConversacion {

    public AdminConversacion() {
    }

    public Respuesta crearMensajeTransportador(String idServicio, Transportador transportador, String texto) {

        Respuesta respuesta = new Respuesta();

        if (transportador == null) {
            respuesta.setCodigo("002");
            respuesta.setResultado("Transportador no logueado");
            return respuesta;
        }

        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        TypedQuery q = em.createNamedQuery("Servicio.findById", Servicio.class);
        q.setParameter("id", idServicio);

        Servicio servicio = null;
        try {
            servicio = (Servicio) q.getSingleResult();
        } catch (NoResultException ex) {
            respuesta.setCodigo("001");
            respuesta.setResultado("Servicio con el id especificado no existe");
            return respuesta;
        }

        Mensaje m = new Mensaje();
        m.setId(java.util.UUID.randomUUID().toString());
        m.setFechaCreacion(new Date());
        m.setTexto(texto);

        Conversacion conversacion = new Conversacion();
        conversacion.setId(java.util.UUID.randomUUID().toString());
        conversacion.setFechaCreacion(new Date());
        conversacion.setServicio(servicio);
        conversacion.setTransportador(transportador);

        ConversacionJpaController cc = new ConversacionJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        try {
            cc.create(conversacion);
        } catch (Exception ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        m.setTransportador(transportador);
        m.setConversacion(conversacion);

        MensajeJpaController mc = new MensajeJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        try {
            mc.create(m);
        } catch (Exception ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessageCreator messageCreator = new MessageCreator();
        Messenger mess = new Messenger();
        mess.sendMail("Comentario sobre servicio en Ayax.co", messageCreator.crearMensajeComentarioNuevo(conversacion, ConfigManager.INSTANCE.isTestEnvironment()), new String[]{servicio.getUsuario().getBuzonElectronico()});

        respuesta.setRecurso("/mensaje/servicio/transportador");
        respuesta.setVerbo("POST");
        respuesta.setCodigo("000");
        respuesta.setResultado("exito");
        respuesta.setValor(conversacion.getId());
        return respuesta;
    }

    public Respuesta crearMensajeUsuario(String idConversacion, String texto) {
        Respuesta respuesta = new Respuesta();

        ConversacionJpaController sc = new ConversacionJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        TypedQuery q = em.createNamedQuery("Conversacion.findById", Servicio.class);
        q.setParameter("id", idConversacion);

        Conversacion conversacion = null;
        try {
            conversacion = (Conversacion) q.getSingleResult();
        } catch (NoResultException ex) {
            respuesta.setCodigo("001");
            respuesta.setResultado("Servicio con el id especificado no existe");
            return respuesta;
        }

        Mensaje m = new Mensaje();
        m.setId(java.util.UUID.randomUUID().toString());
        m.setFechaCreacion(new Date());
        m.setTexto(texto);
        m.setUsuario(conversacion.getServicio().getUsuario());
        m.setConversacion(conversacion);
        conversacion.getMensajeCollection().add(m);
        ConversacionJpaController cc = new ConversacionJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        try {
            cc.edit(conversacion);
        } catch (Exception ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        MensajeJpaController mc = new MensajeJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        try {
            mc.create(m);
        } catch (Exception ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        MessageCreator messageCreator = new MessageCreator();
        Messenger mess = new Messenger();
        mess.sendMail("Respuesta sobre servicio en Ayax.co", messageCreator.crearMensajeRespuestaComentario(conversacion, ConfigManager.INSTANCE.isTestEnvironment()), new String[]{conversacion.getTransportador().getBuzonElectronico()});

        respuesta.setRecurso("/mensaje/servicio/usuario");
        respuesta.setVerbo("POST");
        respuesta.setCodigo("000");
        respuesta.setResultado("exito");
        return respuesta;
    }

    public Respuesta obtenerConversacion(String idConversacion) {
        Respuesta respuesta = new Respuesta();

        ConversacionJpaController sc = new ConversacionJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        TypedQuery q = em.createNamedQuery("Conversacion.findById", Servicio.class);
        q.setParameter("id", idConversacion);

        Conversacion conversacion = null;
        try {
            conversacion = (Conversacion) q.getSingleResult();
        } catch (NoResultException ex) {
            respuesta.setCodigo("001");
            respuesta.setResultado("Conversacion con el id especificado no existe");
            return respuesta;
        }

        JSONObject obj = new JSONObject();
        obj.put("fechaCreacion", conversacion.getFechaCreacion());
        obj.put("conteoMensajes", conversacion.getMensajeCollection().size());
        JSONArray array = new JSONArray();
        for (Mensaje m : conversacion.getMensajeCollection()) {
            JSONObject mObj = new JSONObject();
            mObj.put("fechaCreacion", m.getFechaCreacion());
            if (m.getTransportador() != null) {
                mObj.put("transportador", m.getTransportador().getNombres());
            } else {
                mObj.put("usuario", m.getUsuario().getNombre());
            }
            mObj.put("texto", m.getTexto());
            array.put(mObj);
        }
        obj.put("mensajes", array);
        respuesta.setRecurso("/conversacion");
        respuesta.setVerbo("GET");
        respuesta.setCodigo("000");
        respuesta.setResultado("exito");
        respuesta.setValor(obj);
        return respuesta;
    }
}
