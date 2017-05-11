/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

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
import com.ayax.website.persistencia.fachadas.exceptions.IllegalOrphanException;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
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
        if (servicio.getConversaciones() == null) {
            servicio.setConversaciones(new ArrayList<Conversacion>());
        }
        if (servicio.getOfertas() == null) {
            servicio.setOfertas(new ArrayList<Oferta>());
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
            Collection<Conversacion> attachedConversaciones = new ArrayList<Conversacion>();
            for (Conversacion conversacionesConversacionToAttach : servicio.getConversaciones()) {
                conversacionesConversacionToAttach = em.getReference(conversacionesConversacionToAttach.getClass(), conversacionesConversacionToAttach.getId());
                attachedConversaciones.add(conversacionesConversacionToAttach);
            }
            servicio.setConversaciones(attachedConversaciones);
            Collection<Oferta> attachedOfertas = new ArrayList<Oferta>();
            for (Oferta ofertasOfertaToAttach : servicio.getOfertas()) {
                ofertasOfertaToAttach = em.getReference(ofertasOfertaToAttach.getClass(), ofertasOfertaToAttach.getId());
                attachedOfertas.add(ofertasOfertaToAttach);
            }
            servicio.setOfertas(attachedOfertas);
            em.persist(servicio);
            if (usuario != null) {
                usuario.getServicioCollection().add(servicio);
                usuario = em.merge(usuario);
            }
            for (Conversacion conversacionesConversacion : servicio.getConversaciones()) {
                Servicio oldServicioOfConversacionesConversacion = conversacionesConversacion.getServicio();
                conversacionesConversacion.setServicio(servicio);
                conversacionesConversacion = em.merge(conversacionesConversacion);
                if (oldServicioOfConversacionesConversacion != null) {
                    oldServicioOfConversacionesConversacion.getConversaciones().remove(conversacionesConversacion);
                    oldServicioOfConversacionesConversacion = em.merge(oldServicioOfConversacionesConversacion);
                }
            }
            for (Oferta ofertasOferta : servicio.getOfertas()) {
                Servicio oldServicioOfOfertasOferta = ofertasOferta.getServicio();
                ofertasOferta.setServicio(servicio);
                ofertasOferta = em.merge(ofertasOferta);
                if (oldServicioOfOfertasOferta != null) {
                    oldServicioOfOfertasOferta.getOfertas().remove(ofertasOferta);
                    oldServicioOfOfertasOferta = em.merge(oldServicioOfOfertasOferta);
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
            Collection<Conversacion> conversacionesOld = persistentServicio.getConversaciones();
            Collection<Conversacion> conversacionesNew = servicio.getConversaciones();
            Collection<Oferta> ofertasOld = persistentServicio.getOfertas();
            Collection<Oferta> ofertasNew = servicio.getOfertas();
            List<String> illegalOrphanMessages = null;
            for (Conversacion conversacionesOldConversacion : conversacionesOld) {
                if (!conversacionesNew.contains(conversacionesOldConversacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversacion " + conversacionesOldConversacion + " since its servicio field is not nullable.");
                }
            }
            for (Oferta ofertasOldOferta : ofertasOld) {
                if (!ofertasNew.contains(ofertasOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertasOldOferta + " since its servicio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                servicio.setUsuario(usuarioNew);
            }
            Collection<Conversacion> attachedConversacionesNew = new ArrayList<Conversacion>();
            for (Conversacion conversacionesNewConversacionToAttach : conversacionesNew) {
                conversacionesNewConversacionToAttach = em.getReference(conversacionesNewConversacionToAttach.getClass(), conversacionesNewConversacionToAttach.getId());
                attachedConversacionesNew.add(conversacionesNewConversacionToAttach);
            }
            conversacionesNew = attachedConversacionesNew;
            servicio.setConversaciones(conversacionesNew);
            Collection<Oferta> attachedOfertasNew = new ArrayList<Oferta>();
            for (Oferta ofertasNewOfertaToAttach : ofertasNew) {
                ofertasNewOfertaToAttach = em.getReference(ofertasNewOfertaToAttach.getClass(), ofertasNewOfertaToAttach.getId());
                attachedOfertasNew.add(ofertasNewOfertaToAttach);
            }
            ofertasNew = attachedOfertasNew;
            servicio.setOfertas(ofertasNew);
            servicio = em.merge(servicio);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getServicioCollection().remove(servicio);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getServicioCollection().add(servicio);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Conversacion conversacionesNewConversacion : conversacionesNew) {
                if (!conversacionesOld.contains(conversacionesNewConversacion)) {
                    Servicio oldServicioOfConversacionesNewConversacion = conversacionesNewConversacion.getServicio();
                    conversacionesNewConversacion.setServicio(servicio);
                    conversacionesNewConversacion = em.merge(conversacionesNewConversacion);
                    if (oldServicioOfConversacionesNewConversacion != null && !oldServicioOfConversacionesNewConversacion.equals(servicio)) {
                        oldServicioOfConversacionesNewConversacion.getConversaciones().remove(conversacionesNewConversacion);
                        oldServicioOfConversacionesNewConversacion = em.merge(oldServicioOfConversacionesNewConversacion);
                    }
                }
            }
            for (Oferta ofertasNewOferta : ofertasNew) {
                if (!ofertasOld.contains(ofertasNewOferta)) {
                    Servicio oldServicioOfOfertasNewOferta = ofertasNewOferta.getServicio();
                    ofertasNewOferta.setServicio(servicio);
                    ofertasNewOferta = em.merge(ofertasNewOferta);
                    if (oldServicioOfOfertasNewOferta != null && !oldServicioOfOfertasNewOferta.equals(servicio)) {
                        oldServicioOfOfertasNewOferta.getOfertas().remove(ofertasNewOferta);
                        oldServicioOfOfertasNewOferta = em.merge(oldServicioOfOfertasNewOferta);
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
            Collection<Conversacion> conversacionesOrphanCheck = servicio.getConversaciones();
            for (Conversacion conversacionesOrphanCheckConversacion : conversacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the Conversacion " + conversacionesOrphanCheckConversacion + " in its conversaciones field has a non-nullable servicio field.");
            }
            Collection<Oferta> ofertasOrphanCheck = servicio.getOfertas();
            for (Oferta ofertasOrphanCheckOferta : ofertasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the Oferta " + ofertasOrphanCheckOferta + " in its ofertas field has a non-nullable servicio field.");
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
