/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.TransportadorJpaController;
import com.ayax.website.util.Cryptographer;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminAcceso {

    private static final String ROUTE = "/";

    public Respuesta ingresar(Request req, Response res) {
        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("acceso");
        respuesta.setVerbo("POST");

        String buzonElectronico = req.queryParams("email-address");
        String contrasena = req.queryParams("password");

        if ((buzonElectronico != null && !buzonElectronico.equals("")) && 
                (contrasena != null && !contrasena.equals(""))) {

            buzonElectronico = buzonElectronico.toLowerCase();

            String md5Contrasena = Cryptographer.md5Hash(contrasena, buzonElectronico);

            TransportadorJpaController pc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            EntityManager em = pc.getEntityManager();
            Query q = em.createNamedQuery("Transportador.findByBuzonElectronicoYContrasena");
            q.setParameter("buzonElectronico", buzonElectronico);
            q.setParameter("contrasena", md5Contrasena);

            try {
                Transportador transportador = (Transportador) q.getSingleResult();
                if (transportador != null) {
                               
                        req.session().attribute("usuario", transportador);
                        respuesta.setCodigo("000");
                        respuesta.setResultado("exito");
                        respuesta.setValor(transportador.toDTO());
                     
                } else {
                    respuesta.setCodigo("002");
                    respuesta.setResultado("Usuario no existe");
                }
            } catch (NoResultException e) {
                respuesta.setCodigo("001");
                respuesta.setResultado("Usuario o contraseña incorrectas");
            }
        } else {
            respuesta.setCodigo("001");
            respuesta.setResultado("Usuario o contraseña nulos");
        }

        return respuesta;
    }

    public Respuesta salir(Request req, Response res) {
        if (req.session() != null) {
            req.session().attribute("usuario", null);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("salida");
        respuesta.setVerbo("GET");
        respuesta.setCodigo("000");
        respuesta.setResultado("exito");
        return respuesta;
    }
}
