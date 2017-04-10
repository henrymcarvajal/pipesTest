/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
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
 * @author Mauris
 */
public class OfertaJpaController implements Serializable {

    public OfertaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Oferta oferta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio servicio = oferta.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getId());
                oferta.setServicio(servicio);
            }
            Transportador transportador = oferta.getTransportador();
            if (transportador != null) {
                transportador = em.getReference(transportador.getClass(), transportador.getId());
                oferta.setTransportador(transportador);
            }
            em.persist(oferta);
            if (servicio != null) {
                servicio.getOfertas().add(oferta);
                servicio = em.merge(servicio);
            }
            if (transportador != null) {
                transportador.getOfertaCollection().add(oferta);
                transportador = em.merge(transportador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOferta(oferta.getId()) != null) {
                throw new PreexistingEntityException("Oferta " + oferta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Oferta oferta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oferta persistentOferta = em.find(Oferta.class, oferta.getId());
            Servicio servicioOld = persistentOferta.getServicio();
            Servicio servicioNew = oferta.getServicio();
            Transportador transportadorOld = persistentOferta.getTransportador();
            Transportador transportadorNew = oferta.getTransportador();
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getId());
                oferta.setServicio(servicioNew);
            }
            if (transportadorNew != null) {
                transportadorNew = em.getReference(transportadorNew.getClass(), transportadorNew.getId());
                oferta.setTransportador(transportadorNew);
            }
            oferta = em.merge(oferta);
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getOfertas().remove(oferta);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getOfertas().add(oferta);
                servicioNew = em.merge(servicioNew);
            }
            if (transportadorOld != null && !transportadorOld.equals(transportadorNew)) {
                transportadorOld.getOfertaCollection().remove(oferta);
                transportadorOld = em.merge(transportadorOld);
            }
            if (transportadorNew != null && !transportadorNew.equals(transportadorOld)) {
                transportadorNew.getOfertaCollection().add(oferta);
                transportadorNew = em.merge(transportadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = oferta.getId();
                if (findOferta(id) == null) {
                    throw new NonexistentEntityException("The oferta with id " + id + " no longer exists.");
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
            Oferta oferta;
            try {
                oferta = em.getReference(Oferta.class, id);
                oferta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oferta with id " + id + " no longer exists.", enfe);
            }
            Servicio servicio = oferta.getServicio();
            if (servicio != null) {
                servicio.getOfertas().remove(oferta);
                servicio = em.merge(servicio);
            }
            Transportador transportador = oferta.getTransportador();
            if (transportador != null) {
                transportador.getOfertaCollection().remove(oferta);
                transportador = em.merge(transportador);
            }
            em.remove(oferta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Oferta> findOfertaEntities() {
        return findOfertaEntities(true, -1, -1);
    }

    public List<Oferta> findOfertaEntities(int maxResults, int firstResult) {
        return findOfertaEntities(false, maxResults, firstResult);
    }

    private List<Oferta> findOfertaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Oferta.class));
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

    public Oferta findOferta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Oferta.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfertaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Oferta> rt = cq.from(Oferta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
