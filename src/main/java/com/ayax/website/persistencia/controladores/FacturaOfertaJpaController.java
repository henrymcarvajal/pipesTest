/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.IllegalOrphanException;
import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.FacturaOferta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ayax.website.persistencia.entidades.Oferta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class FacturaOfertaJpaController implements Serializable {

    public FacturaOfertaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaOferta facturaOferta) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Oferta ofertaOrphanCheck = facturaOferta.getOferta();
        if (ofertaOrphanCheck != null) {
            FacturaOferta oldFacturaOfertaOfOferta = ofertaOrphanCheck.getFacturaOferta();
            if (oldFacturaOfertaOfOferta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Oferta " + ofertaOrphanCheck + " already has an item of type FacturaOferta whose oferta column cannot be null. Please make another selection for the oferta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oferta oferta = facturaOferta.getOferta();
            if (oferta != null) {
                oferta = em.getReference(oferta.getClass(), oferta.getId());
                facturaOferta.setOferta(oferta);
            }
            em.persist(facturaOferta);
            if (oferta != null) {
                oferta.setFacturaOferta(facturaOferta);
                oferta = em.merge(oferta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaOferta(facturaOferta.getIdOferta()) != null) {
                throw new PreexistingEntityException("FacturaOferta " + facturaOferta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaOferta facturaOferta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaOferta persistentFacturaOferta = em.find(FacturaOferta.class, facturaOferta.getIdOferta());
            Oferta ofertaOld = persistentFacturaOferta.getOferta();
            Oferta ofertaNew = facturaOferta.getOferta();
            List<String> illegalOrphanMessages = null;
            if (ofertaNew != null && !ofertaNew.equals(ofertaOld)) {
                FacturaOferta oldFacturaOfertaOfOferta = ofertaNew.getFacturaOferta();
                if (oldFacturaOfertaOfOferta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Oferta " + ofertaNew + " already has an item of type FacturaOferta whose oferta column cannot be null. Please make another selection for the oferta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ofertaNew != null) {
                ofertaNew = em.getReference(ofertaNew.getClass(), ofertaNew.getId());
                facturaOferta.setOferta(ofertaNew);
            }
            facturaOferta = em.merge(facturaOferta);
            if (ofertaOld != null && !ofertaOld.equals(ofertaNew)) {
                ofertaOld.setFacturaOferta(null);
                ofertaOld = em.merge(ofertaOld);
            }
            if (ofertaNew != null && !ofertaNew.equals(ofertaOld)) {
                ofertaNew.setFacturaOferta(facturaOferta);
                ofertaNew = em.merge(ofertaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = facturaOferta.getIdOferta();
                if (findFacturaOferta(id) == null) {
                    throw new NonexistentEntityException("The facturaOferta with id " + id + " no longer exists.");
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
            FacturaOferta facturaOferta;
            try {
                facturaOferta = em.getReference(FacturaOferta.class, id);
                facturaOferta.getIdOferta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaOferta with id " + id + " no longer exists.", enfe);
            }
            Oferta oferta = facturaOferta.getOferta();
            if (oferta != null) {
                oferta.setFacturaOferta(null);
                oferta = em.merge(oferta);
            }
            em.remove(facturaOferta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaOferta> findFacturaOfertaEntities() {
        return findFacturaOfertaEntities(true, -1, -1);
    }

    public List<FacturaOferta> findFacturaOfertaEntities(int maxResults, int firstResult) {
        return findFacturaOfertaEntities(false, maxResults, firstResult);
    }

    private List<FacturaOferta> findFacturaOfertaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaOferta.class));
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

    public FacturaOferta findFacturaOferta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaOferta.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaOfertaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaOferta> rt = cq.from(FacturaOferta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
