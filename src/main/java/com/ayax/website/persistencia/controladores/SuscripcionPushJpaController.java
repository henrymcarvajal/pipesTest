/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.SuscripcionPush;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class SuscripcionPushJpaController implements Serializable {

    public SuscripcionPushJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuscripcionPush suscriptor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(suscriptor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuscripcionPush(suscriptor.getIp()) != null) {
                throw new PreexistingEntityException("Suscriptor " + suscriptor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuscripcionPush suscriptor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            suscriptor = em.merge(suscriptor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = suscriptor.getIp();
                if (findSuscripcionPush(id) == null) {
                    throw new NonexistentEntityException("The suscriptor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuscripcionPush suscriptor;
            try {
                suscriptor = em.getReference(SuscripcionPush.class, id);
                suscriptor.getIp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suscriptor with id " + id + " no longer exists.", enfe);
            }
            em.remove(suscriptor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuscripcionPush> findSuscripcionPushEntities() {
        return findSuscripcionPushEntities(true, -1, -1);
    }

    public List<SuscripcionPush> findSuscripcionPushEntities(int maxResults, int firstResult) {
        return findSuscripcionPushEntities(false, maxResults, firstResult);
    }

    private List<SuscripcionPush> findSuscripcionPushEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuscripcionPush.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SuscripcionPush findSuscripcionPush(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuscripcionPush.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuscripcionPushCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuscripcionPush> rt = cq.from(SuscripcionPush.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
