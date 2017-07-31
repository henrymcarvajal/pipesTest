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
import java.util.ArrayList;
import java.util.Collection;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getServicioCollection() == null) {
            usuario.setServicioCollection(new ArrayList<Servicio>());
        }
        if (usuario.getMensajeCollection() == null) {
            usuario.setMensajeCollection(new ArrayList<Mensaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Servicio> attachedServicioCollection = new ArrayList<Servicio>();
            for (Servicio servicioCollectionServicioToAttach : usuario.getServicioCollection()) {
                servicioCollectionServicioToAttach = em.getReference(servicioCollectionServicioToAttach.getClass(), servicioCollectionServicioToAttach.getId());
                attachedServicioCollection.add(servicioCollectionServicioToAttach);
            }
            usuario.setServicioCollection(attachedServicioCollection);
            Collection<Mensaje> attachedMensajeCollection = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionMensajeToAttach : usuario.getMensajeCollection()) {
                mensajeCollectionMensajeToAttach = em.getReference(mensajeCollectionMensajeToAttach.getClass(), mensajeCollectionMensajeToAttach.getId());
                attachedMensajeCollection.add(mensajeCollectionMensajeToAttach);
            }
            usuario.setMensajeCollection(attachedMensajeCollection);
            em.persist(usuario);
            for (Servicio servicioCollectionServicio : usuario.getServicioCollection()) {
                Usuario oldUsuarioOfServicioCollectionServicio = servicioCollectionServicio.getUsuario();
                servicioCollectionServicio.setUsuario(usuario);
                servicioCollectionServicio = em.merge(servicioCollectionServicio);
                if (oldUsuarioOfServicioCollectionServicio != null) {
                    oldUsuarioOfServicioCollectionServicio.getServicioCollection().remove(servicioCollectionServicio);
                    oldUsuarioOfServicioCollectionServicio = em.merge(oldUsuarioOfServicioCollectionServicio);
                }
            }
            for (Mensaje mensajeCollectionMensaje : usuario.getMensajeCollection()) {
                Usuario oldUsuarioOfMensajeCollectionMensaje = mensajeCollectionMensaje.getUsuario();
                mensajeCollectionMensaje.setUsuario(usuario);
                mensajeCollectionMensaje = em.merge(mensajeCollectionMensaje);
                if (oldUsuarioOfMensajeCollectionMensaje != null) {
                    oldUsuarioOfMensajeCollectionMensaje.getMensajeCollection().remove(mensajeCollectionMensaje);
                    oldUsuarioOfMensajeCollectionMensaje = em.merge(oldUsuarioOfMensajeCollectionMensaje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getId()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Collection<Servicio> servicioCollectionOld = persistentUsuario.getServicioCollection();
            Collection<Servicio> servicioCollectionNew = usuario.getServicioCollection();
            Collection<Mensaje> mensajeCollectionOld = persistentUsuario.getMensajeCollection();
            Collection<Mensaje> mensajeCollectionNew = usuario.getMensajeCollection();
            List<String> illegalOrphanMessages = null;
            for (Servicio servicioCollectionOldServicio : servicioCollectionOld) {
                if (!servicioCollectionNew.contains(servicioCollectionOldServicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Servicio " + servicioCollectionOldServicio + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Servicio> attachedServicioCollectionNew = new ArrayList<Servicio>();
            for (Servicio servicioCollectionNewServicioToAttach : servicioCollectionNew) {
                servicioCollectionNewServicioToAttach = em.getReference(servicioCollectionNewServicioToAttach.getClass(), servicioCollectionNewServicioToAttach.getId());
                attachedServicioCollectionNew.add(servicioCollectionNewServicioToAttach);
            }
            servicioCollectionNew = attachedServicioCollectionNew;
            usuario.setServicioCollection(servicioCollectionNew);
            Collection<Mensaje> attachedMensajeCollectionNew = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionNewMensajeToAttach : mensajeCollectionNew) {
                mensajeCollectionNewMensajeToAttach = em.getReference(mensajeCollectionNewMensajeToAttach.getClass(), mensajeCollectionNewMensajeToAttach.getId());
                attachedMensajeCollectionNew.add(mensajeCollectionNewMensajeToAttach);
            }
            mensajeCollectionNew = attachedMensajeCollectionNew;
            usuario.setMensajeCollection(mensajeCollectionNew);
            usuario = em.merge(usuario);
            for (Servicio servicioCollectionNewServicio : servicioCollectionNew) {
                if (!servicioCollectionOld.contains(servicioCollectionNewServicio)) {
                    Usuario oldUsuarioOfServicioCollectionNewServicio = servicioCollectionNewServicio.getUsuario();
                    servicioCollectionNewServicio.setUsuario(usuario);
                    servicioCollectionNewServicio = em.merge(servicioCollectionNewServicio);
                    if (oldUsuarioOfServicioCollectionNewServicio != null && !oldUsuarioOfServicioCollectionNewServicio.equals(usuario)) {
                        oldUsuarioOfServicioCollectionNewServicio.getServicioCollection().remove(servicioCollectionNewServicio);
                        oldUsuarioOfServicioCollectionNewServicio = em.merge(oldUsuarioOfServicioCollectionNewServicio);
                    }
                }
            }
            for (Mensaje mensajeCollectionOldMensaje : mensajeCollectionOld) {
                if (!mensajeCollectionNew.contains(mensajeCollectionOldMensaje)) {
                    mensajeCollectionOldMensaje.setUsuario(null);
                    mensajeCollectionOldMensaje = em.merge(mensajeCollectionOldMensaje);
                }
            }
            for (Mensaje mensajeCollectionNewMensaje : mensajeCollectionNew) {
                if (!mensajeCollectionOld.contains(mensajeCollectionNewMensaje)) {
                    Usuario oldUsuarioOfMensajeCollectionNewMensaje = mensajeCollectionNewMensaje.getUsuario();
                    mensajeCollectionNewMensaje.setUsuario(usuario);
                    mensajeCollectionNewMensaje = em.merge(mensajeCollectionNewMensaje);
                    if (oldUsuarioOfMensajeCollectionNewMensaje != null && !oldUsuarioOfMensajeCollectionNewMensaje.equals(usuario)) {
                        oldUsuarioOfMensajeCollectionNewMensaje.getMensajeCollection().remove(mensajeCollectionNewMensaje);
                        oldUsuarioOfMensajeCollectionNewMensaje = em.merge(oldUsuarioOfMensajeCollectionNewMensaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Servicio> servicioCollectionOrphanCheck = usuario.getServicioCollection();
            for (Servicio servicioCollectionOrphanCheckServicio : servicioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Servicio " + servicioCollectionOrphanCheckServicio + " in its servicioCollection field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Mensaje> mensajeCollection = usuario.getMensajeCollection();
            for (Mensaje mensajeCollectionMensaje : mensajeCollection) {
                mensajeCollectionMensaje.setUsuario(null);
                mensajeCollectionMensaje = em.merge(mensajeCollectionMensaje);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
