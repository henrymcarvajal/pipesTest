/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.controladores.FacturaJpaController;
import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Factura;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class FacturaFacade {

    public Factura buscarPorId(String idFactura) {
        FacturaJpaController ojc = new FacturaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = ojc.getEntityManager();
        Query q = em.createNamedQuery("Factura.findById", Factura.class);
        q.setParameter("id", idFactura);

        Factura oferta = null;
        try {
            oferta = (Factura) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return oferta;
    }

    public boolean crear(Factura factura) {
        try {
            FacturaJpaController tc = new FacturaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(factura);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean actualizar(Factura oferta) {
        try {
            FacturaJpaController tc = new FacturaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.edit(oferta);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
