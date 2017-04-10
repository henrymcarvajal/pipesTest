/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.Suscriptor;
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
 * @author Ayax
 */
public class SuscriptorJpaController implements Serializable {

    public SuscriptorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Suscriptor suscriptor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(suscriptor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuscriptor(suscriptor.getId()) != null) {
                throw new PreexistingEntityException("Suscriptor " + suscriptor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suscriptor suscriptor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            suscriptor = em.merge(suscriptor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = suscriptor.getId();
                if (findSuscriptor(id) == null) {
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
            Suscriptor suscriptor;
            try {
                suscriptor = em.getReference(Suscriptor.class, id);
                suscriptor.getId();
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

    public List<Suscriptor> findSuscriptorEntities() {
        return findSuscriptorEntities(true, -1, -1);
    }

    public List<Suscriptor> findSuscriptorEntities(int maxResults, int firstResult) {
        return findSuscriptorEntities(false, maxResults, firstResult);
    }

    private List<Suscriptor> findSuscriptorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suscriptor.class));
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

    public Suscriptor findSuscriptor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suscriptor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuscriptorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suscriptor> rt = cq.from(Suscriptor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
