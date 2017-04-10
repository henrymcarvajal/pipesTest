/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Vehiculo;
import com.ayax.website.persistencia.fachadas.exceptions.IllegalOrphanException;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauris
 */
public class TransportadorJpaController implements Serializable {

    public TransportadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transportador transportador) throws PreexistingEntityException, Exception {
        if (transportador.getOfertaCollection() == null) {
            transportador.setOfertaCollection(new ArrayList<Oferta>());
        }
        if (transportador.getVehiculoCollection() == null) {
            transportador.setVehiculoCollection(new ArrayList<Vehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Oferta> attachedOfertaCollection = new ArrayList<Oferta>();
            for (Oferta ofertaCollectionOfertaToAttach : transportador.getOfertaCollection()) {
                ofertaCollectionOfertaToAttach = em.getReference(ofertaCollectionOfertaToAttach.getClass(), ofertaCollectionOfertaToAttach.getId());
                attachedOfertaCollection.add(ofertaCollectionOfertaToAttach);
            }
            transportador.setOfertaCollection(attachedOfertaCollection);
            Collection<Vehiculo> attachedVehiculoCollection = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculoCollectionVehiculoToAttach : transportador.getVehiculoCollection()) {
                vehiculoCollectionVehiculoToAttach = em.getReference(vehiculoCollectionVehiculoToAttach.getClass(), vehiculoCollectionVehiculoToAttach.getId());
                attachedVehiculoCollection.add(vehiculoCollectionVehiculoToAttach);
            }
            transportador.setVehiculoCollection(attachedVehiculoCollection);
            em.persist(transportador);
            for (Oferta ofertaCollectionOferta : transportador.getOfertaCollection()) {
                Transportador oldTransportadorOfOfertaCollectionOferta = ofertaCollectionOferta.getTransportador();
                ofertaCollectionOferta.setTransportador(transportador);
                ofertaCollectionOferta = em.merge(ofertaCollectionOferta);
                if (oldTransportadorOfOfertaCollectionOferta != null) {
                    oldTransportadorOfOfertaCollectionOferta.getOfertaCollection().remove(ofertaCollectionOferta);
                    oldTransportadorOfOfertaCollectionOferta = em.merge(oldTransportadorOfOfertaCollectionOferta);
                }
            }
            for (Vehiculo vehiculoCollectionVehiculo : transportador.getVehiculoCollection()) {
                Transportador oldTransportadorOfVehiculoCollectionVehiculo = vehiculoCollectionVehiculo.getTransportador();
                vehiculoCollectionVehiculo.setTransportador(transportador);
                vehiculoCollectionVehiculo = em.merge(vehiculoCollectionVehiculo);
                if (oldTransportadorOfVehiculoCollectionVehiculo != null) {
                    oldTransportadorOfVehiculoCollectionVehiculo.getVehiculoCollection().remove(vehiculoCollectionVehiculo);
                    oldTransportadorOfVehiculoCollectionVehiculo = em.merge(oldTransportadorOfVehiculoCollectionVehiculo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransportador(transportador.getId()) != null) {
                throw new PreexistingEntityException("Transportador " + transportador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transportador transportador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transportador persistentTransportador = em.find(Transportador.class, transportador.getId());
            Collection<Oferta> ofertaCollectionOld = persistentTransportador.getOfertaCollection();
            Collection<Oferta> ofertaCollectionNew = transportador.getOfertaCollection();
            Collection<Vehiculo> vehiculoCollectionOld = persistentTransportador.getVehiculoCollection();
            Collection<Vehiculo> vehiculoCollectionNew = transportador.getVehiculoCollection();
            List<String> illegalOrphanMessages = null;
            for (Oferta ofertaCollectionOldOferta : ofertaCollectionOld) {
                if (!ofertaCollectionNew.contains(ofertaCollectionOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertaCollectionOldOferta + " since its transportador field is not nullable.");
                }
            }
            for (Vehiculo vehiculoCollectionOldVehiculo : vehiculoCollectionOld) {
                if (!vehiculoCollectionNew.contains(vehiculoCollectionOldVehiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vehiculo " + vehiculoCollectionOldVehiculo + " since its transportador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Oferta> attachedOfertaCollectionNew = new ArrayList<Oferta>();
            for (Oferta ofertaCollectionNewOfertaToAttach : ofertaCollectionNew) {
                ofertaCollectionNewOfertaToAttach = em.getReference(ofertaCollectionNewOfertaToAttach.getClass(), ofertaCollectionNewOfertaToAttach.getId());
                attachedOfertaCollectionNew.add(ofertaCollectionNewOfertaToAttach);
            }
            ofertaCollectionNew = attachedOfertaCollectionNew;
            transportador.setOfertaCollection(ofertaCollectionNew);
            Collection<Vehiculo> attachedVehiculoCollectionNew = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculoCollectionNewVehiculoToAttach : vehiculoCollectionNew) {
                vehiculoCollectionNewVehiculoToAttach = em.getReference(vehiculoCollectionNewVehiculoToAttach.getClass(), vehiculoCollectionNewVehiculoToAttach.getId());
                attachedVehiculoCollectionNew.add(vehiculoCollectionNewVehiculoToAttach);
            }
            vehiculoCollectionNew = attachedVehiculoCollectionNew;
            transportador.setVehiculoCollection(vehiculoCollectionNew);
            transportador = em.merge(transportador);
            for (Oferta ofertaCollectionNewOferta : ofertaCollectionNew) {
                if (!ofertaCollectionOld.contains(ofertaCollectionNewOferta)) {
                    Transportador oldTransportadorOfOfertaCollectionNewOferta = ofertaCollectionNewOferta.getTransportador();
                    ofertaCollectionNewOferta.setTransportador(transportador);
                    ofertaCollectionNewOferta = em.merge(ofertaCollectionNewOferta);
                    if (oldTransportadorOfOfertaCollectionNewOferta != null && !oldTransportadorOfOfertaCollectionNewOferta.equals(transportador)) {
                        oldTransportadorOfOfertaCollectionNewOferta.getOfertaCollection().remove(ofertaCollectionNewOferta);
                        oldTransportadorOfOfertaCollectionNewOferta = em.merge(oldTransportadorOfOfertaCollectionNewOferta);
                    }
                }
            }
            for (Vehiculo vehiculoCollectionNewVehiculo : vehiculoCollectionNew) {
                if (!vehiculoCollectionOld.contains(vehiculoCollectionNewVehiculo)) {
                    Transportador oldTransportadorOfVehiculoCollectionNewVehiculo = vehiculoCollectionNewVehiculo.getTransportador();
                    vehiculoCollectionNewVehiculo.setTransportador(transportador);
                    vehiculoCollectionNewVehiculo = em.merge(vehiculoCollectionNewVehiculo);
                    if (oldTransportadorOfVehiculoCollectionNewVehiculo != null && !oldTransportadorOfVehiculoCollectionNewVehiculo.equals(transportador)) {
                        oldTransportadorOfVehiculoCollectionNewVehiculo.getVehiculoCollection().remove(vehiculoCollectionNewVehiculo);
                        oldTransportadorOfVehiculoCollectionNewVehiculo = em.merge(oldTransportadorOfVehiculoCollectionNewVehiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = transportador.getId();
                if (findTransportador(id) == null) {
                    throw new NonexistentEntityException("The transportador with id " + id + " no longer exists.");
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
            Transportador transportador;
            try {
                transportador = em.getReference(Transportador.class, id);
                transportador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transportador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Oferta> ofertaCollectionOrphanCheck = transportador.getOfertaCollection();
            for (Oferta ofertaCollectionOrphanCheckOferta : ofertaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Oferta " + ofertaCollectionOrphanCheckOferta + " in its ofertaCollection field has a non-nullable transportador field.");
            }
            Collection<Vehiculo> vehiculoCollectionOrphanCheck = transportador.getVehiculoCollection();
            for (Vehiculo vehiculoCollectionOrphanCheckVehiculo : vehiculoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Vehiculo " + vehiculoCollectionOrphanCheckVehiculo + " in its vehiculoCollection field has a non-nullable transportador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(transportador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transportador> findTransportadorEntities() {
        return findTransportadorEntities(true, -1, -1);
    }

    public List<Transportador> findTransportadorEntities(int maxResults, int firstResult) {
        return findTransportadorEntities(false, maxResults, firstResult);
    }

    private List<Transportador> findTransportadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transportador.class));
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

    public Transportador findTransportador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transportador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransportadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transportador> rt = cq.from(Transportador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
