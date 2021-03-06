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
import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.persistencia.entidades.Conversacion;
import java.util.ArrayList;
import java.util.Collection;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Servicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) throws PreexistingEntityException, Exception {
        if (servicio.getConversacionCollection() == null) {
            servicio.setConversacionCollection(new ArrayList<Conversacion>());
        }
        if (servicio.getOfertaCollection() == null) {
            servicio.setOfertaCollection(new ArrayList<Oferta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = servicio.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                servicio.setUsuario(usuario);
            }
            Collection<Conversacion> attachedConversacionCollection = new ArrayList<Conversacion>();
            for (Conversacion conversacionCollectionConversacionToAttach : servicio.getConversacionCollection()) {
                conversacionCollectionConversacionToAttach = em.getReference(conversacionCollectionConversacionToAttach.getClass(), conversacionCollectionConversacionToAttach.getId());
                attachedConversacionCollection.add(conversacionCollectionConversacionToAttach);
            }
            servicio.setConversacionCollection(attachedConversacionCollection);
            Collection<Oferta> attachedOfertaCollection = new ArrayList<Oferta>();
            for (Oferta ofertaCollectionOfertaToAttach : servicio.getOfertaCollection()) {
                ofertaCollectionOfertaToAttach = em.getReference(ofertaCollectionOfertaToAttach.getClass(), ofertaCollectionOfertaToAttach.getId());
                attachedOfertaCollection.add(ofertaCollectionOfertaToAttach);
            }
            servicio.setOfertaCollection(attachedOfertaCollection);
            em.persist(servicio);
            if (usuario != null) {
                usuario.getServicioCollection().add(servicio);
                usuario = em.merge(usuario);
            }
            for (Conversacion conversacionCollectionConversacion : servicio.getConversacionCollection()) {
                Servicio oldServicioOfConversacionCollectionConversacion = conversacionCollectionConversacion.getServicio();
                conversacionCollectionConversacion.setServicio(servicio);
                conversacionCollectionConversacion = em.merge(conversacionCollectionConversacion);
                if (oldServicioOfConversacionCollectionConversacion != null) {
                    oldServicioOfConversacionCollectionConversacion.getConversacionCollection().remove(conversacionCollectionConversacion);
                    oldServicioOfConversacionCollectionConversacion = em.merge(oldServicioOfConversacionCollectionConversacion);
                }
            }
            for (Oferta ofertaCollectionOferta : servicio.getOfertaCollection()) {
                Servicio oldServicioOfOfertaCollectionOferta = ofertaCollectionOferta.getServicio();
                ofertaCollectionOferta.setServicio(servicio);
                ofertaCollectionOferta = em.merge(ofertaCollectionOferta);
                if (oldServicioOfOfertaCollectionOferta != null) {
                    oldServicioOfOfertaCollectionOferta.getOfertaCollection().remove(ofertaCollectionOferta);
                    oldServicioOfOfertaCollectionOferta = em.merge(oldServicioOfOfertaCollectionOferta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findServicio(servicio.getId()) != null) {
                throw new PreexistingEntityException("Servicio " + servicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getId());
            Usuario usuarioOld = persistentServicio.getUsuario();
            Usuario usuarioNew = servicio.getUsuario();
            Collection<Conversacion> conversacionCollectionOld = persistentServicio.getConversacionCollection();
            Collection<Conversacion> conversacionCollectionNew = servicio.getConversacionCollection();
            Collection<Oferta> ofertaCollectionOld = persistentServicio.getOfertaCollection();
            Collection<Oferta> ofertaCollectionNew = servicio.getOfertaCollection();
            List<String> illegalOrphanMessages = null;
            for (Conversacion conversacionCollectionOldConversacion : conversacionCollectionOld) {
                if (!conversacionCollectionNew.contains(conversacionCollectionOldConversacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversacion " + conversacionCollectionOldConversacion + " since its servicio field is not nullable.");
                }
            }
            for (Oferta ofertaCollectionOldOferta : ofertaCollectionOld) {
                if (!ofertaCollectionNew.contains(ofertaCollectionOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertaCollectionOldOferta + " since its servicio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                servicio.setUsuario(usuarioNew);
            }
            Collection<Conversacion> attachedConversacionCollectionNew = new ArrayList<Conversacion>();
            for (Conversacion conversacionCollectionNewConversacionToAttach : conversacionCollectionNew) {
                conversacionCollectionNewConversacionToAttach = em.getReference(conversacionCollectionNewConversacionToAttach.getClass(), conversacionCollectionNewConversacionToAttach.getId());
                attachedConversacionCollectionNew.add(conversacionCollectionNewConversacionToAttach);
            }
            conversacionCollectionNew = attachedConversacionCollectionNew;
            servicio.setConversacionCollection(conversacionCollectionNew);
            Collection<Oferta> attachedOfertaCollectionNew = new ArrayList<Oferta>();
            for (Oferta ofertaCollectionNewOfertaToAttach : ofertaCollectionNew) {
                ofertaCollectionNewOfertaToAttach = em.getReference(ofertaCollectionNewOfertaToAttach.getClass(), ofertaCollectionNewOfertaToAttach.getId());
                attachedOfertaCollectionNew.add(ofertaCollectionNewOfertaToAttach);
            }
            ofertaCollectionNew = attachedOfertaCollectionNew;
            servicio.setOfertaCollection(ofertaCollectionNew);
            servicio = em.merge(servicio);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getServicioCollection().remove(servicio);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getServicioCollection().add(servicio);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Conversacion conversacionCollectionNewConversacion : conversacionCollectionNew) {
                if (!conversacionCollectionOld.contains(conversacionCollectionNewConversacion)) {
                    Servicio oldServicioOfConversacionCollectionNewConversacion = conversacionCollectionNewConversacion.getServicio();
                    conversacionCollectionNewConversacion.setServicio(servicio);
                    conversacionCollectionNewConversacion = em.merge(conversacionCollectionNewConversacion);
                    if (oldServicioOfConversacionCollectionNewConversacion != null && !oldServicioOfConversacionCollectionNewConversacion.equals(servicio)) {
                        oldServicioOfConversacionCollectionNewConversacion.getConversacionCollection().remove(conversacionCollectionNewConversacion);
                        oldServicioOfConversacionCollectionNewConversacion = em.merge(oldServicioOfConversacionCollectionNewConversacion);
                    }
                }
            }
            for (Oferta ofertaCollectionNewOferta : ofertaCollectionNew) {
                if (!ofertaCollectionOld.contains(ofertaCollectionNewOferta)) {
                    Servicio oldServicioOfOfertaCollectionNewOferta = ofertaCollectionNewOferta.getServicio();
                    ofertaCollectionNewOferta.setServicio(servicio);
                    ofertaCollectionNewOferta = em.merge(ofertaCollectionNewOferta);
                    if (oldServicioOfOfertaCollectionNewOferta != null && !oldServicioOfOfertaCollectionNewOferta.equals(servicio)) {
                        oldServicioOfOfertaCollectionNewOferta.getOfertaCollection().remove(ofertaCollectionNewOferta);
                        oldServicioOfOfertaCollectionNewOferta = em.merge(oldServicioOfOfertaCollectionNewOferta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = servicio.getId();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
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
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Conversacion> conversacionCollectionOrphanCheck = servicio.getConversacionCollection();
            for (Conversacion conversacionCollectionOrphanCheckConversacion : conversacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the Conversacion " + conversacionCollectionOrphanCheckConversacion + " in its conversacionCollection field has a non-nullable servicio field.");
            }
            Collection<Oferta> ofertaCollectionOrphanCheck = servicio.getOfertaCollection();
            for (Oferta ofertaCollectionOrphanCheckOferta : ofertaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the Oferta " + ofertaCollectionOrphanCheckOferta + " in its ofertaCollection field has a non-nullable servicio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = servicio.getUsuario();
            if (usuario != null) {
                usuario.getServicioCollection().remove(servicio);
                usuario = em.merge(usuario);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
