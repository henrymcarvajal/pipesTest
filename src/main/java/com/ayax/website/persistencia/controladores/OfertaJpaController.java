/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.IllegalOrphanException;
import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.FacturaOferta;
import com.ayax.website.persistencia.entidades.Oferta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hmcarvajal@ayax.co
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
            FacturaOferta facturaOferta = oferta.getFacturaOferta();
            if (facturaOferta != null) {
                facturaOferta = em.getReference(facturaOferta.getClass(), facturaOferta.getIdOferta());
                oferta.setFacturaOferta(facturaOferta);
            }
            em.persist(oferta);
            if (servicio != null) {
                servicio.getOfertaCollection().add(oferta);
                servicio = em.merge(servicio);
            }
            if (transportador != null) {
                transportador.getOfertaCollection().add(oferta);
                transportador = em.merge(transportador);
            }
            if (facturaOferta != null) {
                Oferta oldOfertaOfFacturaOferta = facturaOferta.getOferta();
                if (oldOfertaOfFacturaOferta != null) {
                    oldOfertaOfFacturaOferta.setFacturaOferta(null);
                    oldOfertaOfFacturaOferta = em.merge(oldOfertaOfFacturaOferta);
                }
                facturaOferta.setOferta(oferta);
                facturaOferta = em.merge(facturaOferta);
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

    public void edit(Oferta oferta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oferta persistentOferta = em.find(Oferta.class, oferta.getId());
            Servicio servicioOld = persistentOferta.getServicio();
            Servicio servicioNew = oferta.getServicio();
            Transportador transportadorOld = persistentOferta.getTransportador();
            Transportador transportadorNew = oferta.getTransportador();
            FacturaOferta facturaOfertaOld = persistentOferta.getFacturaOferta();
            FacturaOferta facturaOfertaNew = oferta.getFacturaOferta();
            List<String> illegalOrphanMessages = null;
            if (facturaOfertaOld != null && !facturaOfertaOld.equals(facturaOfertaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain FacturaOferta " + facturaOfertaOld + " since its oferta field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getId());
                oferta.setServicio(servicioNew);
            }
            if (transportadorNew != null) {
                transportadorNew = em.getReference(transportadorNew.getClass(), transportadorNew.getId());
                oferta.setTransportador(transportadorNew);
            }
            if (facturaOfertaNew != null) {
                facturaOfertaNew = em.getReference(facturaOfertaNew.getClass(), facturaOfertaNew.getIdOferta());
                oferta.setFacturaOferta(facturaOfertaNew);
            }
            oferta = em.merge(oferta);
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getOfertaCollection().remove(oferta);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getOfertaCollection().add(oferta);
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
            if (facturaOfertaNew != null && !facturaOfertaNew.equals(facturaOfertaOld)) {
                Oferta oldOfertaOfFacturaOferta = facturaOfertaNew.getOferta();
                if (oldOfertaOfFacturaOferta != null) {
                    oldOfertaOfFacturaOferta.setFacturaOferta(null);
                    oldOfertaOfFacturaOferta = em.merge(oldOfertaOfFacturaOferta);
                }
                facturaOfertaNew.setOferta(oferta);
                facturaOfertaNew = em.merge(facturaOfertaNew);
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            FacturaOferta facturaOfertaOrphanCheck = oferta.getFacturaOferta();
            if (facturaOfertaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Oferta (" + oferta + ") cannot be destroyed since the FacturaOferta " + facturaOfertaOrphanCheck + " in its facturaOferta field has a non-nullable oferta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Servicio servicio = oferta.getServicio();
            if (servicio != null) {
                servicio.getOfertaCollection().remove(oferta);
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
