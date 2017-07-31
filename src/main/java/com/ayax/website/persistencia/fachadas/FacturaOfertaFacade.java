/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.controladores.FacturaOfertaJpaController;
import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.FacturaOferta;
import com.ayax.website.persistencia.entidades.Oferta;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class FacturaOfertaFacade {

    public FacturaOferta buscarPorId(String idFacturaOferta) {
        
        
        OfertaFacade of = new OfertaFacade();
        Oferta oferta = of.buscarPorId(idFacturaOferta);
        FacturaOfertaJpaController ojc = new FacturaOfertaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = ojc.getEntityManager();
        Query q = em.createNamedQuery("FacturaOferta.findByOferta", FacturaOferta.class);
        q.setParameter("oferta", oferta);

        FacturaOferta facturaOferta = null;
        try {
            facturaOferta = (FacturaOferta) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return facturaOferta;
    }

    public boolean crear(FacturaOferta facturaOferta) {
        try {
            FacturaOfertaJpaController tc = new FacturaOfertaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(facturaOferta);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(FacturaOfertaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean actualizar(FacturaOferta facturaOferta) {
        try {
            FacturaOfertaJpaController tc = new FacturaOfertaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.edit(facturaOferta);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(FacturaOfertaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
