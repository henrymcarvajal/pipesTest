/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.CotizacionSimple;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jespinosa
 */
public class CotizacionSimpleJpaController {

    private EntityManagerFactory emf = null;

    public CotizacionSimpleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void create(CotizacionSimple cotizacionSimple) throws PreexistingEntityException, Exception {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cotizacionSimple);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCotizacion(cotizacionSimple.getId()) != null) {
                throw new PreexistingEntityException("Cotizacion " + cotizacionSimple + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CotizacionSimple findCotizacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CotizacionSimple.class, id);
        } finally {
            em.close();
        }
    }

}
