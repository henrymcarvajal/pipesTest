/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.AnalisisCostos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jespinosa
 */
public class AnalisisCostosJpaController {

    private EntityManagerFactory emf = null;

    public AnalisisCostosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void create(AnalisisCostos analisisCostos) throws PreexistingEntityException, Exception {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(analisisCostos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnalisis(analisisCostos.getId()) != null) {
                throw new PreexistingEntityException("AnalisisCostos " + analisisCostos + " already exists.", ex);
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

    public AnalisisCostos findAnalisis(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnalisisCostos.class, id);
        } finally {
            em.close();
        }
    }
}
