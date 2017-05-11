/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Vehiculo;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.fachadas.exceptions.PreexistingEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class VehiculoFacade {

    public Vehiculo buscarPorPlaca(String placa) {
        VehiculoJpaController tc = new VehiculoJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = tc.getEntityManager();
        Query q = em.createNamedQuery("Vehiculo.findByPlaca");
        q.setParameter("placa", placa);
        Vehiculo result = null;
        try {
            result = (Vehiculo) q.getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(VehiculoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Vehiculo buscarPorId(String id) {
        VehiculoJpaController tc = new VehiculoJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = tc.getEntityManager();
        Query q = em.createNamedQuery("Vehiculo.findById");
        q.setParameter("id", id);
        Vehiculo result = null;
        try {
            result = (Vehiculo) q.getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(VehiculoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public List buscarPorNumeroPasajeros(String numeroPasajerosMinimo, String numeroPasajerosMaximo) {
        VehiculoJpaController tc = new VehiculoJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = tc.getEntityManager();
        Query q = em.createNativeQuery("SELECT *  FROM l4_vehiculo where "
                + "to_number(numero_pasajeros,'9999') >=?1 and to_number(numero_pasajeros,'9999')<=?2");
        q.setParameter(1, new Integer(numeroPasajerosMinimo));
        q.setParameter(2, new Integer(numeroPasajerosMaximo));
        List result = null;
        try {
            result = q.getResultList();
        } catch (NoResultException ex) {
            Logger.getLogger(VehiculoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean crear(Vehiculo vehiculo) {
        try {
            VehiculoJpaController tc = new VehiculoJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(vehiculo);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(VehiculoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean actualizar(Vehiculo vehiculo) {
        try {
            VehiculoJpaController tc = new VehiculoJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.edit(vehiculo);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(VehiculoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
