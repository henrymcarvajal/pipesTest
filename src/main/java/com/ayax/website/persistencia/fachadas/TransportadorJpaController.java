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
import com.ayax.website.persistencia.entidades.Vehiculo;
import java.util.ArrayList;
import java.util.Collection;
import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Factura;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Transportador;
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
public class TransportadorJpaController implements Serializable {

    public TransportadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transportador transportador) throws PreexistingEntityException, Exception {
        if (transportador.getVehiculos() == null) {
            transportador.setVehiculos(new ArrayList<Vehiculo>());
        }
        if (transportador.getConversaciones() == null) {
            transportador.setConversaciones(new ArrayList<Conversacion>());
        }
        if (transportador.getOfertas() == null) {
            transportador.setOfertas(new ArrayList<Oferta>());
        }
        if (transportador.getFacturas() == null) {
            transportador.setFacturas(new ArrayList<Factura>());
        }
        if (transportador.getMensajes() == null) {
            transportador.setMensajes(new ArrayList<Mensaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vehiculo> attachedVehiculos = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculosVehiculoToAttach : transportador.getVehiculos()) {
                vehiculosVehiculoToAttach = em.getReference(vehiculosVehiculoToAttach.getClass(), vehiculosVehiculoToAttach.getId());
                attachedVehiculos.add(vehiculosVehiculoToAttach);
            }
            transportador.setVehiculos(attachedVehiculos);
            Collection<Conversacion> attachedConversaciones = new ArrayList<Conversacion>();
            for (Conversacion conversacionesConversacionToAttach : transportador.getConversaciones()) {
                conversacionesConversacionToAttach = em.getReference(conversacionesConversacionToAttach.getClass(), conversacionesConversacionToAttach.getId());
                attachedConversaciones.add(conversacionesConversacionToAttach);
            }
            transportador.setConversaciones(attachedConversaciones);
            Collection<Oferta> attachedOfertas = new ArrayList<Oferta>();
            for (Oferta ofertasOfertaToAttach : transportador.getOfertas()) {
                ofertasOfertaToAttach = em.getReference(ofertasOfertaToAttach.getClass(), ofertasOfertaToAttach.getId());
                attachedOfertas.add(ofertasOfertaToAttach);
            }
            transportador.setOfertas(attachedOfertas);
            Collection<Factura> attachedFacturas = new ArrayList<Factura>();
            for (Factura facturasFacturaToAttach : transportador.getFacturas()) {
                facturasFacturaToAttach = em.getReference(facturasFacturaToAttach.getClass(), facturasFacturaToAttach.getId());
                attachedFacturas.add(facturasFacturaToAttach);
            }
            transportador.setFacturas(attachedFacturas);
            Collection<Mensaje> attachedMensajes = new ArrayList<Mensaje>();
            for (Mensaje mensajesMensajeToAttach : transportador.getMensajes()) {
                mensajesMensajeToAttach = em.getReference(mensajesMensajeToAttach.getClass(), mensajesMensajeToAttach.getId());
                attachedMensajes.add(mensajesMensajeToAttach);
            }
            transportador.setMensajes(attachedMensajes);
            em.persist(transportador);
            for (Vehiculo vehiculosVehiculo : transportador.getVehiculos()) {
                Transportador oldTransportadorOfVehiculosVehiculo = vehiculosVehiculo.getTransportador();
                vehiculosVehiculo.setTransportador(transportador);
                vehiculosVehiculo = em.merge(vehiculosVehiculo);
                if (oldTransportadorOfVehiculosVehiculo != null) {
                    oldTransportadorOfVehiculosVehiculo.getVehiculos().remove(vehiculosVehiculo);
                    oldTransportadorOfVehiculosVehiculo = em.merge(oldTransportadorOfVehiculosVehiculo);
                }
            }
            for (Conversacion conversacionesConversacion : transportador.getConversaciones()) {
                Transportador oldTransportadorOfConversacionesConversacion = conversacionesConversacion.getTransportador();
                conversacionesConversacion.setTransportador(transportador);
                conversacionesConversacion = em.merge(conversacionesConversacion);
                if (oldTransportadorOfConversacionesConversacion != null) {
                    oldTransportadorOfConversacionesConversacion.getConversaciones().remove(conversacionesConversacion);
                    oldTransportadorOfConversacionesConversacion = em.merge(oldTransportadorOfConversacionesConversacion);
                }
            }
            for (Oferta ofertasOferta : transportador.getOfertas()) {
                Transportador oldTransportadorOfOfertasOferta = ofertasOferta.getTransportador();
                ofertasOferta.setTransportador(transportador);
                ofertasOferta = em.merge(ofertasOferta);
                if (oldTransportadorOfOfertasOferta != null) {
                    oldTransportadorOfOfertasOferta.getOfertas().remove(ofertasOferta);
                    oldTransportadorOfOfertasOferta = em.merge(oldTransportadorOfOfertasOferta);
                }
            }
            for (Factura facturasFactura : transportador.getFacturas()) {
                Transportador oldTransportadorOfFacturasFactura = facturasFactura.getTransportador();
                facturasFactura.setTransportador(transportador);
                facturasFactura = em.merge(facturasFactura);
                if (oldTransportadorOfFacturasFactura != null) {
                    oldTransportadorOfFacturasFactura.getFacturas().remove(facturasFactura);
                    oldTransportadorOfFacturasFactura = em.merge(oldTransportadorOfFacturasFactura);
                }
            }
            for (Mensaje mensajesMensaje : transportador.getMensajes()) {
                Transportador oldTransportadorOfMensajesMensaje = mensajesMensaje.getTransportador();
                mensajesMensaje.setTransportador(transportador);
                mensajesMensaje = em.merge(mensajesMensaje);
                if (oldTransportadorOfMensajesMensaje != null) {
                    oldTransportadorOfMensajesMensaje.getMensajes().remove(mensajesMensaje);
                    oldTransportadorOfMensajesMensaje = em.merge(oldTransportadorOfMensajesMensaje);
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
            Collection<Vehiculo> vehiculosOld = persistentTransportador.getVehiculos();
            Collection<Vehiculo> vehiculosNew = transportador.getVehiculos();
            Collection<Conversacion> conversacionesOld = persistentTransportador.getConversaciones();
            Collection<Conversacion> conversacionesNew = transportador.getConversaciones();
            Collection<Oferta> ofertasOld = persistentTransportador.getOfertas();
            Collection<Oferta> ofertasNew = transportador.getOfertas();
            Collection<Factura> facturasOld = persistentTransportador.getFacturas();
            Collection<Factura> facturasNew = transportador.getFacturas();
            Collection<Mensaje> mensajesOld = persistentTransportador.getMensajes();
            Collection<Mensaje> mensajesNew = transportador.getMensajes();
            List<String> illegalOrphanMessages = null;
            for (Vehiculo vehiculosOldVehiculo : vehiculosOld) {
                if (!vehiculosNew.contains(vehiculosOldVehiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vehiculo " + vehiculosOldVehiculo + " since its transportador field is not nullable.");
                }
            }
            for (Conversacion conversacionesOldConversacion : conversacionesOld) {
                if (!conversacionesNew.contains(conversacionesOldConversacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversacion " + conversacionesOldConversacion + " since its transportador field is not nullable.");
                }
            }
            for (Oferta ofertasOldOferta : ofertasOld) {
                if (!ofertasNew.contains(ofertasOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertasOldOferta + " since its transportador field is not nullable.");
                }
            }
            for (Factura facturasOldFactura : facturasOld) {
                if (!facturasNew.contains(facturasOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturasOldFactura + " since its transportador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Vehiculo> attachedVehiculosNew = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculosNewVehiculoToAttach : vehiculosNew) {
                vehiculosNewVehiculoToAttach = em.getReference(vehiculosNewVehiculoToAttach.getClass(), vehiculosNewVehiculoToAttach.getId());
                attachedVehiculosNew.add(vehiculosNewVehiculoToAttach);
            }
            vehiculosNew = attachedVehiculosNew;
            transportador.setVehiculos(vehiculosNew);
            Collection<Conversacion> attachedConversacionesNew = new ArrayList<Conversacion>();
            for (Conversacion conversacionesNewConversacionToAttach : conversacionesNew) {
                conversacionesNewConversacionToAttach = em.getReference(conversacionesNewConversacionToAttach.getClass(), conversacionesNewConversacionToAttach.getId());
                attachedConversacionesNew.add(conversacionesNewConversacionToAttach);
            }
            conversacionesNew = attachedConversacionesNew;
            transportador.setConversaciones(conversacionesNew);
            Collection<Oferta> attachedOfertasNew = new ArrayList<Oferta>();
            for (Oferta ofertasNewOfertaToAttach : ofertasNew) {
                ofertasNewOfertaToAttach = em.getReference(ofertasNewOfertaToAttach.getClass(), ofertasNewOfertaToAttach.getId());
                attachedOfertasNew.add(ofertasNewOfertaToAttach);
            }
            ofertasNew = attachedOfertasNew;
            transportador.setOfertas(ofertasNew);
            Collection<Factura> attachedFacturasNew = new ArrayList<Factura>();
            for (Factura facturasNewFacturaToAttach : facturasNew) {
                facturasNewFacturaToAttach = em.getReference(facturasNewFacturaToAttach.getClass(), facturasNewFacturaToAttach.getId());
                attachedFacturasNew.add(facturasNewFacturaToAttach);
            }
            facturasNew = attachedFacturasNew;
            transportador.setFacturas(facturasNew);
            Collection<Mensaje> attachedMensajesNew = new ArrayList<Mensaje>();
            for (Mensaje mensajesNewMensajeToAttach : mensajesNew) {
                mensajesNewMensajeToAttach = em.getReference(mensajesNewMensajeToAttach.getClass(), mensajesNewMensajeToAttach.getId());
                attachedMensajesNew.add(mensajesNewMensajeToAttach);
            }
            mensajesNew = attachedMensajesNew;
            transportador.setMensajes(mensajesNew);
            transportador = em.merge(transportador);
            for (Vehiculo vehiculosNewVehiculo : vehiculosNew) {
                if (!vehiculosOld.contains(vehiculosNewVehiculo)) {
                    Transportador oldTransportadorOfVehiculosNewVehiculo = vehiculosNewVehiculo.getTransportador();
                    vehiculosNewVehiculo.setTransportador(transportador);
                    vehiculosNewVehiculo = em.merge(vehiculosNewVehiculo);
                    if (oldTransportadorOfVehiculosNewVehiculo != null && !oldTransportadorOfVehiculosNewVehiculo.equals(transportador)) {
                        oldTransportadorOfVehiculosNewVehiculo.getVehiculos().remove(vehiculosNewVehiculo);
                        oldTransportadorOfVehiculosNewVehiculo = em.merge(oldTransportadorOfVehiculosNewVehiculo);
                    }
                }
            }
            for (Conversacion conversacionesNewConversacion : conversacionesNew) {
                if (!conversacionesOld.contains(conversacionesNewConversacion)) {
                    Transportador oldTransportadorOfConversacionesNewConversacion = conversacionesNewConversacion.getTransportador();
                    conversacionesNewConversacion.setTransportador(transportador);
                    conversacionesNewConversacion = em.merge(conversacionesNewConversacion);
                    if (oldTransportadorOfConversacionesNewConversacion != null && !oldTransportadorOfConversacionesNewConversacion.equals(transportador)) {
                        oldTransportadorOfConversacionesNewConversacion.getConversaciones().remove(conversacionesNewConversacion);
                        oldTransportadorOfConversacionesNewConversacion = em.merge(oldTransportadorOfConversacionesNewConversacion);
                    }
                }
            }
            for (Oferta ofertasNewOferta : ofertasNew) {
                if (!ofertasOld.contains(ofertasNewOferta)) {
                    Transportador oldTransportadorOfOfertasNewOferta = ofertasNewOferta.getTransportador();
                    ofertasNewOferta.setTransportador(transportador);
                    ofertasNewOferta = em.merge(ofertasNewOferta);
                    if (oldTransportadorOfOfertasNewOferta != null && !oldTransportadorOfOfertasNewOferta.equals(transportador)) {
                        oldTransportadorOfOfertasNewOferta.getOfertas().remove(ofertasNewOferta);
                        oldTransportadorOfOfertasNewOferta = em.merge(oldTransportadorOfOfertasNewOferta);
                    }
                }
            }
            for (Factura facturasNewFactura : facturasNew) {
                if (!facturasOld.contains(facturasNewFactura)) {
                    Transportador oldTransportadorOfFacturasNewFactura = facturasNewFactura.getTransportador();
                    facturasNewFactura.setTransportador(transportador);
                    facturasNewFactura = em.merge(facturasNewFactura);
                    if (oldTransportadorOfFacturasNewFactura != null && !oldTransportadorOfFacturasNewFactura.equals(transportador)) {
                        oldTransportadorOfFacturasNewFactura.getFacturas().remove(facturasNewFactura);
                        oldTransportadorOfFacturasNewFactura = em.merge(oldTransportadorOfFacturasNewFactura);
                    }
                }
            }
            for (Mensaje mensajesOldMensaje : mensajesOld) {
                if (!mensajesNew.contains(mensajesOldMensaje)) {
                    mensajesOldMensaje.setTransportador(null);
                    mensajesOldMensaje = em.merge(mensajesOldMensaje);
                }
            }
            for (Mensaje mensajesNewMensaje : mensajesNew) {
                if (!mensajesOld.contains(mensajesNewMensaje)) {
                    Transportador oldTransportadorOfMensajesNewMensaje = mensajesNewMensaje.getTransportador();
                    mensajesNewMensaje.setTransportador(transportador);
                    mensajesNewMensaje = em.merge(mensajesNewMensaje);
                    if (oldTransportadorOfMensajesNewMensaje != null && !oldTransportadorOfMensajesNewMensaje.equals(transportador)) {
                        oldTransportadorOfMensajesNewMensaje.getMensajes().remove(mensajesNewMensaje);
                        oldTransportadorOfMensajesNewMensaje = em.merge(oldTransportadorOfMensajesNewMensaje);
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
            Collection<Vehiculo> vehiculosOrphanCheck = transportador.getVehiculos();
            for (Vehiculo vehiculosOrphanCheckVehiculo : vehiculosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Vehiculo " + vehiculosOrphanCheckVehiculo + " in its vehiculos field has a non-nullable transportador field.");
            }
            Collection<Conversacion> conversacionesOrphanCheck = transportador.getConversaciones();
            for (Conversacion conversacionesOrphanCheckConversacion : conversacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Conversacion " + conversacionesOrphanCheckConversacion + " in its conversaciones field has a non-nullable transportador field.");
            }
            Collection<Oferta> ofertasOrphanCheck = transportador.getOfertas();
            for (Oferta ofertasOrphanCheckOferta : ofertasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Oferta " + ofertasOrphanCheckOferta + " in its ofertas field has a non-nullable transportador field.");
            }
            Collection<Factura> facturasOrphanCheck = transportador.getFacturas();
            for (Factura facturasOrphanCheckFactura : facturasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transportador (" + transportador + ") cannot be destroyed since the Factura " + facturasOrphanCheckFactura + " in its facturas field has a non-nullable transportador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Mensaje> mensajes = transportador.getMensajes();
            for (Mensaje mensajesMensaje : mensajes) {
                mensajesMensaje.setTransportador(null);
                mensajesMensaje = em.merge(mensajesMensaje);
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
