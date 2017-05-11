/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.fachadas.ServicioJpaController;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.persistencia.fachadas.ConversacionJpaController;
import com.ayax.website.persistencia.fachadas.MensajeJpaController;
import com.ayax.website.persistencia.fachadas.TransportadorJpaController;
import com.ayax.website.persistencia.fachadas.UsuarioJpaController;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminConversacion {

    public AdminConversacion() {
    }

    public Respuesta crearMensajeTransportador(String idServicio, String idTransportador, String texto) {
        Respuesta respuesta = new Respuesta();

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

        Conversacion conversacion = null;
        for (Conversacion c : servicio.getConversaciones()) {
            if (c.getTransportador().getId().equalsIgnoreCase(idTransportador)) {
                conversacion = c;
                break;
            }
        }

        Mensaje m = new Mensaje();
        m.setId(java.util.UUID.randomUUID().toString());
        m.setFechaCreacion(new Date());
        m.setTexto(texto);
        if (conversacion != null) {
            m.setTransportador(conversacion.getTransportador());
            m.setConversacion(conversacion);
            conversacion.getMensajes().add(m);
        } else {
            TransportadorJpaController tc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            em = tc.getEntityManager();
            q = em.createNamedQuery("Transportador.findById", Transportador.class);
            q.setParameter("id", idTransportador);

            Transportador transportador = null;
            try {
                transportador = (Transportador) q.getSingleResult();
            } catch (NoResultException ex) {
                respuesta.setCodigo("002");
                respuesta.setResultado("Transportador con el id especificado no existe");
                return respuesta;
            }
            conversacion = new Conversacion();
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
        }
        MensajeJpaController mc = new MensajeJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        try {
            mc.create(m);
        } catch (Exception ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        respuesta.setRecurso("/mensaje/servicio/transportador");
        respuesta.setVerbo("POST");
        respuesta.setCodigo("000");
        respuesta.setResultado("exito");
        return respuesta;
    }

    public Respuesta crearMensajeUsuario(String idServicio, String idUsuario, String texto) {
        Respuesta respuesta = new Respuesta();

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

        Conversacion conversacion = null;
        for (Conversacion c : servicio.getConversaciones()) {
            if (c.getServicio().getUsuario().getId().equalsIgnoreCase(idUsuario)) {
                conversacion = c;
                break;
            }
        }

        Mensaje m = new Mensaje();
        m.setId(java.util.UUID.randomUUID().toString());
        m.setFechaCreacion(new Date());
        m.setTexto(texto);
        if (conversacion != null) {
            m.setUsuario(conversacion.getServicio().getUsuario());
            m.setConversacion(conversacion);
            conversacion.getMensajes().add(m);
        } else {
            UsuarioJpaController tc = new UsuarioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            em = tc.getEntityManager();
            q = em.createNamedQuery("Usuario.findById", Usuario.class);
            q.setParameter("id", idUsuario);

            Usuario usuario = null;
            try {
                usuario = (Usuario) q.getSingleResult();
            } catch (NoResultException ex) {
                respuesta.setCodigo("002");
                respuesta.setResultado("Usuario con el id especificado no existe");
                return respuesta;
            }
            conversacion = new Conversacion();
            conversacion.setId(java.util.UUID.randomUUID().toString());
            conversacion.setFechaCreacion(new Date());
            conversacion.setServicio(servicio);

            ConversacionJpaController cc = new ConversacionJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            try {
                cc.create(conversacion);
            } catch (Exception ex) {
                Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            m.setUsuario(usuario);
            m.setConversacion(conversacion);
        }
        MensajeJpaController mc = new MensajeJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        try {
            mc.create(m);
        } catch (Exception ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        respuesta.setRecurso("/mensaje/servicio/transportador");
        respuesta.setVerbo("POST");
        respuesta.setCodigo("000");
        respuesta.setResultado("exito");
        return respuesta;
    }
}
