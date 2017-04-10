/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.mail.Messenger;
import com.ayax.website.mail.MessageCreator;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.TransportadorJpaController;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.util.Cryptographer;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import spark.Request;
import spark.Response;

/**
 *
 * @author Mauris
 */
public class AdminContrasena {

    public AdminContrasena() {
    }

    public Respuesta recuperarContrasena(Request req, Response res) {
        Respuesta respuesta = new Respuesta();

        String buzonElectronico = req.queryParams("usuario");

        String descripcion, codigo;
        if (buzonElectronico != null && !buzonElectronico.equals("")) {

            buzonElectronico = buzonElectronico.toLowerCase();

            TransportadorJpaController uc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            EntityManager em = uc.getEntityManager();
            Query q = em.createNamedQuery("Transportador.findByBuzonElectronico");
            q.setParameter("buzonElectronico", buzonElectronico);

            Transportador transportador = null;
            try {
                transportador = (Transportador) q.getSingleResult();
            } catch (NoResultException ex) {

            }

            if (transportador != null) {
                codigo = "000";
                descripcion = "exito";
                MessageCreator mc = new MessageCreator();
                String message = mc.crearMensajeRecuperarContrasena(transportador);

                Messenger mess = new Messenger();
                mess.sendMail("Solicitud de Recuperación de Contraseña en Ayax.co", message, new String[]{transportador.getBuzonElectronico()});
            } else {
                codigo = "001";
                descripcion = "Usuario (" + buzonElectronico + ") no existe";
            }
        } else {
            codigo = "001";
            descripcion = "Usuario nulo o vacio";
        }

        respuesta.setRecurso("recuperarContrasena");
        respuesta.setVerbo("POST");
        respuesta.setCodigo(codigo);
        respuesta.setResultado(descripcion);
        return respuesta;
    }

    public Respuesta cambiarContrasena(Request req, Response res) throws NonexistentEntityException, Exception {
        String contrasena = req.queryParams("contrasena");
        String id = req.queryParams("usuario");

        String descripcion, codigo;
        if (id != null && !id.equals("")) {

            TransportadorJpaController uc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            EntityManager em = uc.getEntityManager();
            Query q = em.createNamedQuery("Transportador.findById");
            q.setParameter("id", id);

            Transportador transportador = null;
            try {
                transportador = (Transportador) q.getSingleResult();
            } catch (NoResultException ex) {

            }

            if (transportador != null) {

                String md5Password = Cryptographer.md5Hash(contrasena, transportador.getBuzonElectronico());

                transportador.setContrasena(md5Password);
                uc.edit(transportador);

                codigo = "000";
                descripcion = "exito";

            } else {
                codigo = "002";
                descripcion = "Usuario (" + id + ") no existe";
            }
        } else {
            codigo = "001";
            descripcion = "Usuario nulo o vacio";
        }

        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("recuperarContrasena");
        respuesta.setVerbo("POST");
        respuesta.setCodigo(codigo);
        respuesta.setResultado(descripcion);
        return respuesta;
    }
}
