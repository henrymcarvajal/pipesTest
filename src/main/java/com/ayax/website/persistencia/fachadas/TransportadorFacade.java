/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class TransportadorFacade {

    public List<Transportador> buscarPorBuzonElectronicoONumeroContacto(String buzonElectronico, String numeroContacto) {
        TransportadorJpaController tc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = tc.getEntityManager();
        Query q = em.createNamedQuery("Transportador.findByBuzonElectronicoONumeroContacto");
        q.setParameter("buzonElectronico", buzonElectronico);
        q.setParameter("numeroContacto", Long.valueOf(numeroContacto));
        List<Transportador> result = null;
        try {
            result = (List<Transportador>) q.getResultList();
        } catch (NoResultException ex) {

        }
        return result;
    }

    public Transportador buscarPorId(String id) {
        TransportadorJpaController tc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = tc.getEntityManager();
        Query q = em.createNamedQuery("Transportador.findById");
        q.setParameter("id", id);
        Transportador result = null;
        try {
            result = (Transportador) q.getSingleResult();
        } catch (NoResultException ex) {

        }
        return result;
    }

    public boolean crear(Transportador transportador) {
        try {
            TransportadorJpaController tc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(transportador);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
        public boolean actualizar(Transportador transportador) {
        try {
            TransportadorJpaController tc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.edit(transportador);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
