/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.controladores.SuscripcionPushJpaController;
import com.ayax.website.persistencia.entidades.SuscripcionPush;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Ayax
 */
public class SuscripcionPushFacade {
    
    public boolean crear(SuscripcionPush suscripcionPush) {
        try {
            SuscripcionPushJpaController tc = new SuscripcionPushJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(suscripcionPush);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<SuscripcionPush> obtenerSuscripcionesPush() {
        SuscripcionPushJpaController ojc = new SuscripcionPushJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = ojc.getEntityManager();
        Query q = em.createNamedQuery("SuscripcionPush.findAll", SuscripcionPush.class);

        List<SuscripcionPush> suscripciones = null;
        try {
            suscripciones = q.getResultList();
        } catch (NoResultException ex) {
        }
        return suscripciones;
    }
}
