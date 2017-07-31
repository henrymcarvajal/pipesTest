/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.IllegalOrphanException;
import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.Conversacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Mensaje;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class ConversacionJpaController implements Serializable {

    public ConversacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conversacion conversacion) throws PreexistingEntityException, Exception {
        if (conversacion.getMensajeCollection() == null) {
            conversacion.setMensajeCollection(new ArrayList<Mensaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio servicio = conversacion.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getId());
                conversacion.setServicio(servicio);
            }
            Transportador transportador = conversacion.getTransportador();
            if (transportador != null) {
                transportador = em.getReference(transportador.getClass(), transportador.getId());
                conversacion.setTransportador(transportador);
            }
            Collection<Mensaje> attachedMensajeCollection = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionMensajeToAttach : conversacion.getMensajeCollection()) {
                mensajeCollectionMensajeToAttach = em.getReference(mensajeCollectionMensajeToAttach.getClass(), mensajeCollectionMensajeToAttach.getId());
                attachedMensajeCollection.add(mensajeCollectionMensajeToAttach);
            }
            conversacion.setMensajeCollection(attachedMensajeCollection);
            em.persist(conversacion);
            if (servicio != null) {
                servicio.getConversacionCollection().add(conversacion);
                servicio = em.merge(servicio);
            }
            if (transportador != null) {
                transportador.getConversacionCollection().add(conversacion);
                transportador = em.merge(transportador);
            }
            for (Mensaje mensajeCollectionMensaje : conversacion.getMensajeCollection()) {
                Conversacion oldConversacionOfMensajeCollectionMensaje = mensajeCollectionMensaje.getConversacion();
                mensajeCollectionMensaje.setConversacion(conversacion);
                mensajeCollectionMensaje = em.merge(mensajeCollectionMensaje);
                if (oldConversacionOfMensajeCollectionMensaje != null) {
                    oldConversacionOfMensajeCollectionMensaje.getMensajeCollection().remove(mensajeCollectionMensaje);
                    oldConversacionOfMensajeCollectionMensaje = em.merge(oldConversacionOfMensajeCollectionMensaje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConversacion(conversacion.getId()) != null) {
                throw new PreexistingEntityException("Conversacion " + conversacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conversacion conversacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conversacion persistentConversacion = em.find(Conversacion.class, conversacion.getId());
            Servicio servicioOld = persistentConversacion.getServicio();
            Servicio servicioNew = conversacion.getServicio();
            Transportador transportadorOld = persistentConversacion.getTransportador();
            Transportador transportadorNew = conversacion.getTransportador();
            Collection<Mensaje> mensajeCollectionOld = persistentConversacion.getMensajeCollection();
            Collection<Mensaje> mensajeCollectionNew = conversacion.getMensajeCollection();
            List<String> illegalOrphanMessages = null;
            for (Mensaje mensajeCollectionOldMensaje : mensajeCollectionOld) {
                if (!mensajeCollectionNew.contains(mensajeCollectionOldMensaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mensaje " + mensajeCollectionOldMensaje + " since its conversacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getId());
                conversacion.setServicio(servicioNew);
            }
            if (transportadorNew != null) {
                transportadorNew = em.getReference(transportadorNew.getClass(), transportadorNew.getId());
                conversacion.setTransportador(transportadorNew);
            }
            Collection<Mensaje> attachedMensajeCollectionNew = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionNewMensajeToAttach : mensajeCollectionNew) {
                mensajeCollectionNewMensajeToAttach = em.getReference(mensajeCollectionNewMensajeToAttach.getClass(), mensajeCollectionNewMensajeToAttach.getId());
                attachedMensajeCollectionNew.add(mensajeCollectionNewMensajeToAttach);
            }
            mensajeCollectionNew = attachedMensajeCollectionNew;
            conversacion.setMensajeCollection(mensajeCollectionNew);
            conversacion = em.merge(conversacion);
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getConversacionCollection().remove(conversacion);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getConversacionCollection().add(conversacion);
                servicioNew = em.merge(servicioNew);
            }
            if (transportadorOld != null && !transportadorOld.equals(transportadorNew)) {
                transportadorOld.getConversacionCollection().remove(conversacion);
                transportadorOld = em.merge(transportadorOld);
            }
            if (transportadorNew != null && !transportadorNew.equals(transportadorOld)) {
                transportadorNew.getConversacionCollection().add(conversacion);
                transportadorNew = em.merge(transportadorNew);
            }
            for (Mensaje mensajeCollectionNewMensaje : mensajeCollectionNew) {
                if (!mensajeCollectionOld.contains(mensajeCollectionNewMensaje)) {
                    Conversacion oldConversacionOfMensajeCollectionNewMensaje = mensajeCollectionNewMensaje.getConversacion();
                    mensajeCollectionNewMensaje.setConversacion(conversacion);
                    mensajeCollectionNewMensaje = em.merge(mensajeCollectionNewMensaje);
                    if (oldConversacionOfMensajeCollectionNewMensaje != null && !oldConversacionOfMensajeCollectionNewMensaje.equals(conversacion)) {
                        oldConversacionOfMensajeCollectionNewMensaje.getMensajeCollection().remove(mensajeCollectionNewMensaje);
                        oldConversacionOfMensajeCollectionNewMensaje = em.merge(oldConversacionOfMensajeCollectionNewMensaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = conversacion.getId();
                if (findConversacion(id) == null) {
                    throw new NonexistentEntityException("The conversacion with id " + id + " no longer exists.");
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
            Conversacion conversacion;
            try {
                conversacion = em.getReference(Conversacion.class, id);
                conversacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conversacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Mensaje> mensajeCollectionOrphanCheck = conversacion.getMensajeCollection();
            for (Mensaje mensajeCollectionOrphanCheckMensaje : mensajeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Conversacion (" + conversacion + ") cannot be destroyed since the Mensaje " + mensajeCollectionOrphanCheckMensaje + " in its mensajeCollection field has a non-nullable conversacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Servicio servicio = conversacion.getServicio();
            if (servicio != null) {
                servicio.getConversacionCollection().remove(conversacion);
                servicio = em.merge(servicio);
            }
            Transportador transportador = conversacion.getTransportador();
            if (transportador != null) {
                transportador.getConversacionCollection().remove(conversacion);
                transportador = em.merge(transportador);
            }
            em.remove(conversacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Conversacion> findConversacionEntities() {
        return findConversacionEntities(true, -1, -1);
    }

    public List<Conversacion> findConversacionEntities(int maxResults, int firstResult) {
        return findConversacionEntities(false, maxResults, firstResult);
    }

    private List<Conversacion> findConversacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conversacion.class));
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

    public Conversacion findConversacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conversacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getConversacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conversacion> rt = cq.from(Conversacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
