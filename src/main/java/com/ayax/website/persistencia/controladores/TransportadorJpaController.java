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
import com.ayax.website.persistencia.entidades.Vehiculo;
import java.util.ArrayList;
import java.util.Collection;
import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Factura;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Transportador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hmcarvajal@ayax.co
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
        if (transportador.getVehiculoCollection() == null) {
            transportador.setVehiculoCollection(new ArrayList<Vehiculo>());
        }
        if (transportador.getConversacionCollection() == null) {
            transportador.setConversacionCollection(new ArrayList<Conversacion>());
        }
        if (transportador.getOfertaCollection() == null) {
            transportador.setOfertaCollection(new ArrayList<Oferta>());
        }
        if (transportador.getFacturaCollection() == null) {
            transportador.setFacturaCollection(new ArrayList<Factura>());
        }
        if (transportador.getMensajeCollection() == null) {
            transportador.setMensajeCollection(new ArrayList<Mensaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vehiculo> attachedVehiculoCollection = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculoCollectionVehiculoToAttach : transportador.getVehiculoCollection()) {
                vehiculoCollectionVehiculoToAttach = em.getReference(vehiculoCollectionVehiculoToAttach.getClass(), vehiculoCollectionVehiculoToAttach.getId());
                attachedVehiculoCollection.add(vehiculoCollectionVehiculoToAttach);
            }
            transportador.setVehiculoCollection(attachedVehiculoCollection);
            Collection<Conversacion> attachedConversacionCollection = new ArrayList<Conversacion>();
            for (Conversacion conversacionCollectionConversacionToAttach : transportador.getConversacionCollection()) {
                conversacionCollectionConversacionToAttach = em.getReference(conversacionCollectionConversacionToAttach.getClass(), conversacionCollectionConversacionToAttach.getId());
                attachedConversacionCollection.add(conversacionCollectionConversacionToAttach);
            }
            transportador.setConversacionCollection(attachedConversacionCollection);
            Collection<Oferta> attachedOfertaCollection = new ArrayList<Oferta>();
            for (Oferta ofertaCollectionOfertaToAttach : transportador.getOfertaCollection()) {
                ofertaCollectionOfertaToAttach = em.getReference(ofertaCollectionOfertaToAttach.getClass(), ofertaCollectionOfertaToAttach.getId());
                attachedOfertaCollection.add(ofertaCollectionOfertaToAttach);
            }
            transportador.setOfertaCollection(attachedOfertaCollection);
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : transportador.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getId());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            transportador.setFacturaCollection(attachedFacturaCollection);
            Collection<Mensaje> attachedMensajeCollection = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionMensajeToAttach : transportador.getMensajeCollection()) {
                mensajeCollectionMensajeToAttach = em.getReference(mensajeCollectionMensajeToAttach.getClass(), mensajeCollectionMensajeToAttach.getId());
                attachedMensajeCollection.add(mensajeCollectionMensajeToAttach);
            }
            transportador.setMensajeCollection(attachedMensajeCollection);
            em.persist(transportador);
            for (Vehiculo vehiculoCollectionVehiculo : transportador.getVehiculoCollection()) {
                Transportador oldTransportadorOfVehiculoCollectionVehiculo = vehiculoCollectionVehiculo.getTransportador();
                vehiculoCollectionVehiculo.setTransportador(transportador);
                vehiculoCollectionVehiculo = em.merge(vehiculoCollectionVehiculo);
                if (oldTransportadorOfVehiculoCollectionVehiculo != null) {
                    oldTransportadorOfVehiculoCollectionVehiculo.getVehiculoCollection().remove(vehiculoCollectionVehiculo);
                    oldTransportadorOfVehiculoCollectionVehiculo = em.merge(oldTransportadorOfVehiculoCollectionVehiculo);
                }
            }
            for (Conversacion conversacionCollectionConversacion : transportador.getConversacionCollection()) {
                Transportador oldTransportadorOfConversacionCollectionConversacion = conversacionCollectionConversacion.getTransportador();
                conversacionCollectionConversacion.setTransportador(transportador);
                conversacionCollectionConversacion = em.merge(conversacionCollectionConversacion);
                if (oldTransportadorOfConversacionCollectionConversacion != null) {
                    oldTransportadorOfConversacionCollectionConversacion.getConversacionCollection().remove(conversacionCollectionConversacion);
                    oldTransportadorOfConversacionCollectionConversacion = em.merge(oldTransportadorOfConversacionCollectionConversacion);
                }
            }
            for (Oferta ofertaCollectionOferta : transportador.getOfertaCollection()) {
                Transportador oldTransportadorOfOfertaCollectionOferta = ofertaCollectionOferta.getTransportador();
                ofertaCollectionOferta.setTransportador(transportador);
                ofertaCollectionOferta = em.merge(ofertaCollectionOferta);
                if (oldTransportadorOfOfertaCollectionOferta != null) {
                    oldTransportadorOfOfertaCollectionOferta.getOfertaCollection().remove(ofertaCollectionOferta);
                    oldTransportadorOfOfertaCollectionOferta = em.merge(oldTransportadorOfOfertaCollectionOferta);
                }
            }
            for (Factura facturaCollectionFactura : transportador.getFacturaCollection()) {
                Transportador oldTransportadorOfFacturaCollectionFactura = facturaCollectionFactura.getTransportador();
                facturaCollectionFactura.setTransportador(transportador);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldTransportadorOfFacturaCollectionFactura != null) {
                    oldTransportadorOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldTransportadorOfFacturaCollectionFactura = em.merge(oldTransportadorOfFacturaCollectionFactura);
                }
            }
            for (Mensaje mensajeCollectionMensaje : transportador.getMensajeCollection()) {
                Transportador oldTransportadorOfMensajeCollectionMensaje = mensajeCollectionMensaje.getTransportador();
                mensajeCollectionMensaje.setTransportador(transportador);
                mensajeCollectionMensaje = em.merge(mensajeCollectionMensaje);
                if (oldTransportadorOfMensajeCollectionMensaje != null) {
                    oldTransportadorOfMensajeCollectionMensaje.getMensajeCollection().remove(mensajeCollectionMensaje);
                    oldTransportadorOfMensajeCollectionMensaje = em.merge(oldTransportadorOfMensajeCollectionMensaje);
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
            Collection<Vehiculo> vehiculoCollectionOld = persistentTransportador.getVehiculoCollection();
            Collection<Vehiculo> vehiculoCollectionNew = transportador.getVehiculoCollection();
            Collection<Conversacion> conversacionCollectionOld = persistentTransportador.getConversacionCollection();
            Collection<Conversacion> conversacionCollectionNew = transportador.getConversacionCollection();
            Collection<Oferta> ofertaCollectionOld = persistentTransportador.getOfertaCollection();
            Collection<Oferta> ofertaCollectionNew = transportador.getOfertaCollection();
            Collection<Factura> facturaCollectionOld = persistentTransportador.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = transportador.getFacturaCollection();
            Collection<Mensaje> mensajeCollectionOld = persistentTransportador.getMensajeCollection();
            Collection<Mensaje> mensajeCollectionNew = transportador.getMensajeCollection();
            List<String> illegalOrphanMessages = null;
            for (Vehiculo vehiculoCollectionOldVehiculo : vehiculoCollectionOld) {
                if (!vehiculoCollectionNew.contains(vehiculoCollectionOldVehiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vehiculo " + vehiculoCollectionOldVehiculo + " since its transportador field is not nullable.");
                }
            }
            for (Conversacion conversacionCollectionOldConversacion : conversacionCollectionOld) {
                if (!conversacionCollectionNew.contains(conversacionCollectionOldConversacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversacion " + conversacionCollectionOldConversacion + " since its transportador field is not nullable.");
                }
            }
            for (Oferta ofertaCollectionOldOferta : ofertaCollectionOld) {
                if (!ofertaCollectionNew.contains(ofertaCollectionOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertaCollectionOldOferta + " since its transportador field is not nullable.");
                }
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its transportador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Vehiculo> attachedVehiculoCollectionNew = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculoCollectionNewVehiculoToAttach : vehiculoCollectionNew) {
                vehiculoCollectionNewVehiculoToAttach = em.getReference(vehiculoCollectionNewVehiculoToAttach.getClass(), vehiculoCollectionNewVehiculoToAttach.getId());
                attachedVehiculoCollectionNew.add(vehiculoCollectionNewVehiculoToAttach);
            }
            vehiculoCollectionNew = attachedVehiculoCollectionNew;
            transportador.setVehiculoCollection(vehiculoCollectionNew);
            Collection<Conversacion> attachedConversacionCollectionNew = new ArrayList<Conversacion>();
            for (Conversacion conversacionCollectionNewConversacionToAttach : conversacionCollectionNew) {
                conversacionCollectionNewConversacionToAttach = em.getReference(conversacionCollectionNewConversacionToAttach.getClass(), conversacionCollectionNewConversacionToAttach.getId());
                attachedConversacionCollectionNew.add(conversacionCollectionNewConversacionToAttach);
            }
            conversacionCollectionNew = attachedConversacionCollectionNew;
            transportador.setConversacionCollection(conversacionCollectionNew);
            Collection<Oferta> attachedOfertaCollectionNew = new ArrayList<Oferta>();
            for (Oferta ofertaCollectionNewOfertaToAttach : ofertaCollectionNew) {
                ofertaCollectionNewOfertaToAttach = em.getReference(ofertaCollectionNewOfertaToAttach.getClass(), ofertaCollectionNewOfertaToAttach.getId());
                attachedOfertaCollectionNew.add(ofertaCollectionNewOfertaToAttach);
            }
            ofertaCollectionNew = attachedOfertaCollectionNew;
            transportador.setOfertaCollection(ofertaCollectionNew);
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getId());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            transportador.setFacturaCollection(facturaCollectionNew);
            Collection<Mensaje> attachedMensajeCollectionNew = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionNewMensajeToAttach : mensajeCollectionNew) {
                mensajeCollectionNewMensajeToAttach = em.getReference(mensajeCollectionNewMensajeToAttach.getClass(), mensajeCollectionNewMensajeToAttach.getId());
                attachedMensajeCollectionNew.add(mensajeCollectionNewMensajeToAttach);
            }
            mensajeCollectionNew = attachedMensajeCollectionNew;
            transportador.setMensajeCollection(mensajeCollectionNew);
            transportador = em.merge(transportador);
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
            for (Conversacion conversacionCollectionNewConversacion : conversacionCollectionNew) {
                if (!conversacionCollectionOld.contains(conversacionCollectionNewConversacion)) {
                    Transportador oldTransportadorOfConversacionCollectionNewConversacion = conversacionCollectionNewConversacion.getTransportador();
                    conversacionCollectionNewConversacion.setTransportador(transportador);
                    conversacionCollectionNewConversacion = em.merge(conversacionCollectionNewConversacion);
                    if (oldTransportadorOfConversacionCollectionNewConversacion != null && !oldTransportadorOfConversacionCollectionNewConversacion.equals(transportador)) {
                        oldTransportadorOfConversacionCollectionNewConversacion.getConversacionCollection().remove(conversacionCollectionNewConversacion);
                        oldTransportadorOfConversacionCollectionNewConversacion = em.merge(oldTransportadorOfConversacionCollectionNewConversacion);
                    }
                }
            }
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
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Transportador oldTransportadorOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getTransportador();
                    facturaCollectionNewFactura.setTransportador(transportador);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldTransportadorOfFacturaCollectionNewFactura != null && !oldTransportadorOfFacturaCollectionNewFactura.equals(transportador)) {
                        oldTransportadorOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldTransportadorOfFacturaCollectionNewFactura = em.merge(oldTransportadorOfFacturaCollectionNewFactura);
                    }
                }
            }
            for (Mensaje mensajeCollectionOldMensaje : mensajeCollectionOld) {
                if (!mensajeCollectionNew.contains(mensajeCollectionOldMensaje)) {
                    mensajeCollectionOldMensaje.setTransportador(null);
                    mensajeCollectionOldMensaje = em.merge(mensajeCollectionOldMensaje);
                }
            }
            for (Mensaje mensajeCollectionNewMensaje : mensajeCollectionNew) {
                if (!mensajeCollectionOld.contains(mensajeCollectionNewMensaje)) {
                    Transportador oldTransportadorOfMensajeCollectionNewMensaje = mensajeCollectionNewMensaje.getTransportador();
                    mensajeCollectionNewMensaje.setTransportador(transportador);
                    mensajeCollectionNewMensaje = em.merge(mensajeCollectionNewMensaje);
                    if (oldTransportadorOfMensajeCollectionNewMensaje != null && !oldTransportadorOfMensajeCollectionNewMensaje.equals(transportador)) {
                        oldTransportadorOfMensajeCollectionNewMensaje.getMensajeCollection().remove(mensajeCollectionNewMensaje);
                        oldTransportadorOfMensajeCollectionNewMensaje = em.merge(oldTransportadorOfMensajeCollectionNewMensaje);
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
            Collection<Vehiculo> vehiculoCollectionOrphanCheck = transportador.getVehiculoCollection();
            for (Vehiculo vehiculoCollectionOrphanCheckVehiculo : vehiculoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Vehiculo " + vehiculoCollectionOrphanCheckVehiculo + " in its vehiculoCollection field has a non-nullable transportador field.");
            }
            Collection<Conversacion> conversacionCollectionOrphanCheck = transportador.getConversacionCollection();
            for (Conversacion conversacionCollectionOrphanCheckConversacion : conversacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Conversacion " + conversacionCollectionOrphanCheckConversacion + " in its conversacionCollection field has a non-nullable transportador field.");
            }
            Collection<Oferta> ofertaCollectionOrphanCheck = transportador.getOfertaCollection();
            for (Oferta ofertaCollectionOrphanCheckOferta : ofertaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Oferta " + ofertaCollectionOrphanCheckOferta + " in its ofertaCollection field has a non-nullable transportador field.");
            }
            Collection<Factura> facturaCollectionOrphanCheck = transportador.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable transportador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Mensaje> mensajeCollection = transportador.getMensajeCollection();
            for (Mensaje mensajeCollectionMensaje : mensajeCollection) {
                mensajeCollectionMensaje.setTransportador(null);
                mensajeCollectionMensaje = em.merge(mensajeCollectionMensaje);
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
