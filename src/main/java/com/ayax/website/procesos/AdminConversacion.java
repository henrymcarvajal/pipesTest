/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.fachadas.ServicioJpaController;
import com.ayax.website.persistencia.fachadas.UsuarioJpaController;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.mail.Messenger;
import com.ayax.website.mail.MessageCreator;
import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.ConversacionJpaController;
import com.ayax.website.persistencia.fachadas.MensajeJpaController;
import com.ayax.website.persistencia.fachadas.TransportadorJpaController;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminConversacion {

    public AdminConversacion() {
    }

    public Respuesta crearMensaje(String idServicio, String idTransportador, String texto) {
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
            respuesta.setResultado("Servicio con el id especificadi no existe");
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
            conversacion.setFecha(new Date());
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

    public List<Object[]> obtenerServicios() {
        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        //TypedQuery<Servicio> q = em.createNamedQuery("Servicio.findAllActive", Servicio.class);
        Query q = em.createNamedQuery("Servicio.findAllActive");
        LocalDate date = LocalDate.now();
        date.minusDays(30);
        java.util.Date uDate = Date.from(date.minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        q.setParameter("fechaLimite", uDate, TemporalType.DATE);
        List<Object[]> list = (List<Object[]>) q.getResultList();
        return list;
    }

    public List<Servicio> obtenerTodosServicios() {
        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        //TypedQuery<Servicio> q = em.createNamedQuery("Servicio.findAllActive", Servicio.class);
        Query q = em.createNamedQuery("Servicio.findAll");
        //LocalDate date = LocalDate.now();
        //date.minusDays(30);
        //java.util.Date uDate = Date.from(date.minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        //q.setParameter("fechaLimite", uDate, TemporalType.DATE);
        //List<Object[]> list = (List<Object[]>) ;
        return q.getResultList();
    }

    public Servicio obtenerServicio(String id) {
        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        TypedQuery q = em.createNamedQuery("Servicio.findById", Servicio.class);
        q.setParameter("id", id);

        Servicio servicio = null;
        try {
            servicio = (Servicio) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return servicio;
    }

    private Double parseDecimal(String number) {
        if (number == null) {
            return null;
        }

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols sfs = new DecimalFormatSymbols();
        sfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(sfs);
        try {
            return df.parse(number).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        sfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(sfs);
        try {
            return df.parse(number).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(AdminConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
