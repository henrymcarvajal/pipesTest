/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class OfertaFacade {

    public Oferta buscarPorServicioTransportador(String idServicio, String idTransportador) {
        OfertaJpaController ojc = new OfertaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = ojc.getEntityManager();
        Query q = em.createNativeQuery("select * from l4_oferta where id_Servicio = ?idServicio and id_Transportador = ?idTransportador", Oferta.class);
        q.setParameter("idServicio", idServicio);
        q.setParameter("idTransportador", idTransportador);

        Oferta oferta = null;
        try {
            oferta = (Oferta) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return oferta;
    }

    public Oferta buscarPorId(String idOferta) {
        OfertaJpaController ojc = new OfertaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = ojc.getEntityManager();
        Query q = em.createNamedQuery("Oferta.findById", Oferta.class);
        q.setParameter("id", idOferta);

        Oferta oferta = null;
        try {
            oferta = (Oferta) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return oferta;
    }

    public boolean crear(Oferta oferta) {
        try {
            OfertaJpaController tc = new OfertaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(oferta);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean actualizar(Oferta oferta) {
        try {
            OfertaJpaController tc = new OfertaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.edit(oferta);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
