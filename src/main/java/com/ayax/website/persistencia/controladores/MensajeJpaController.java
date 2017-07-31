/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class MensajeJpaController implements Serializable {

    public MensajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mensaje mensaje) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conversacion conversacion = mensaje.getConversacion();
            if (conversacion != null) {
                conversacion = em.getReference(conversacion.getClass(), conversacion.getId());
                mensaje.setConversacion(conversacion);
            }
            Transportador transportador = mensaje.getTransportador();
            if (transportador != null) {
                transportador = em.getReference(transportador.getClass(), transportador.getId());
                mensaje.setTransportador(transportador);
            }
            Usuario usuario = mensaje.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                mensaje.setUsuario(usuario);
            }
            em.persist(mensaje);
            if (conversacion != null) {
                conversacion.getMensajeCollection().add(mensaje);
                conversacion = em.merge(conversacion);
            }
            if (transportador != null) {
                transportador.getMensajeCollection().add(mensaje);
                transportador = em.merge(transportador);
            }
            if (usuario != null) {
                usuario.getMensajeCollection().add(mensaje);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMensaje(mensaje.getId()) != null) {
                throw new PreexistingEntityException("Mensaje " + mensaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mensaje mensaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mensaje persistentMensaje = em.find(Mensaje.class, mensaje.getId());
            Conversacion conversacionOld = persistentMensaje.getConversacion();
            Conversacion conversacionNew = mensaje.getConversacion();
            Transportador transportadorOld = persistentMensaje.getTransportador();
            Transportador transportadorNew = mensaje.getTransportador();
            Usuario usuarioOld = persistentMensaje.getUsuario();
            Usuario usuarioNew = mensaje.getUsuario();
            if (conversacionNew != null) {
                conversacionNew = em.getReference(conversacionNew.getClass(), conversacionNew.getId());
                mensaje.setConversacion(conversacionNew);
            }
            if (transportadorNew != null) {
                transportadorNew = em.getReference(transportadorNew.getClass(), transportadorNew.getId());
                mensaje.setTransportador(transportadorNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                mensaje.setUsuario(usuarioNew);
            }
            mensaje = em.merge(mensaje);
            if (conversacionOld != null && !conversacionOld.equals(conversacionNew)) {
                conversacionOld.getMensajeCollection().remove(mensaje);
                conversacionOld = em.merge(conversacionOld);
            }
            if (conversacionNew != null && !conversacionNew.equals(conversacionOld)) {
                conversacionNew.getMensajeCollection().add(mensaje);
                conversacionNew = em.merge(conversacionNew);
            }
            if (transportadorOld != null && !transportadorOld.equals(transportadorNew)) {
                transportadorOld.getMensajeCollection().remove(mensaje);
                transportadorOld = em.merge(transportadorOld);
            }
            if (transportadorNew != null && !transportadorNew.equals(transportadorOld)) {
                transportadorNew.getMensajeCollection().add(mensaje);
                transportadorNew = em.merge(transportadorNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getMensajeCollection().remove(mensaje);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getMensajeCollection().add(mensaje);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = mensaje.getId();
                if (findMensaje(id) == null) {
                    throw new NonexistentEntityException("The mensaje with id " + id + " no longer exists.");
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
            Mensaje mensaje;
            try {
                mensaje = em.getReference(Mensaje.class, id);
                mensaje.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mensaje with id " + id + " no longer exists.", enfe);
            }
            Conversacion conversacion = mensaje.getConversacion();
            if (conversacion != null) {
                conversacion.getMensajeCollection().remove(mensaje);
                conversacion = em.merge(conversacion);
            }
            Transportador transportador = mensaje.getTransportador();
            if (transportador != null) {
                transportador.getMensajeCollection().remove(mensaje);
                transportador = em.merge(transportador);
            }
            Usuario usuario = mensaje.getUsuario();
            if (usuario != null) {
                usuario.getMensajeCollection().remove(mensaje);
                usuario = em.merge(usuario);
            }
            em.remove(mensaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mensaje> findMensajeEntities() {
        return findMensajeEntities(true, -1, -1);
    }

    public List<Mensaje> findMensajeEntities(int maxResults, int firstResult) {
        return findMensajeEntities(false, maxResults, firstResult);
    }

    private List<Mensaje> findMensajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mensaje.class));
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

    public Mensaje findMensaje(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mensaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getMensajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mensaje> rt = cq.from(Mensaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
