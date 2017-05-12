/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.fachadas.ServicioJpaController;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.mail.Messenger;
import com.ayax.website.mail.MessageCreator;
import com.ayax.website.persistencia.entidades.Oferta;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminProcesos {

    public AdminProcesos() {
    }

    public Respuesta enviarCorreosCalificacion() {
        Respuesta respuesta = new Respuesta();

        ServicioJpaController uc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = uc.getEntityManager();
        Query q;

        q = em.createNamedQuery("Servicio.findAllFinished");

        LocalDate date = LocalDate.now();
        //date.minusDays(30);
        java.util.Date uDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        q.setParameter("fecha", uDate, TemporalType.DATE);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        String reportDate = df.format(uDate);

        System.out.println("uDate: " + reportDate);

        List<Object[]> servicios = null;
        try {
            servicios = (List<Object[]>) q.getResultList();
        } catch (NoResultException ex) {
        }

        if (servicios != null && !servicios.isEmpty()) {
            servicios.forEach(item -> {
                Object[] xxx = (Object[]) item;
                Servicio servicio = (Servicio) xxx[0];
                Oferta oferta = (Oferta) xxx[1];
                MessageCreator mc = new MessageCreator();
                Messenger mess = new Messenger();

                System.out.println("Servicio: " + servicio.getId());
                System.out.println("Oferta: " + oferta.getId());

                if (oferta.getServicio().getCalificacionUsuario() == 0) {
                    mess.sendMail("Ayax.co - Calificaci&oacute;n de transportador", mc.crearMensajeCalificarTransportador(oferta), new String[]{oferta.getServicio().getUsuario().getBuzonElectronico()});
                }
                if (oferta.getServicio().getCalificacionTransportador() == 0) {
                    mess.sendMail("Ayax.co - Calificaci&oacute;n de usuario", mc.crearMensajeCalificarUsuario(oferta), new String[]{oferta.getTransportador().getBuzonElectronico()});
                }
            });
        }

        respuesta.setRecurso("/procesos/correos/calificacion");
        respuesta.setVerbo("GET");
        respuesta.setCodigo("0");
        respuesta.setResultado("exito");
        return respuesta;
    }

}
