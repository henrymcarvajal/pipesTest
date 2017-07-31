/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.controladores.CotizacionSimpleJpaController;
import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.CotizacionSimple;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jespinosa
 */
public class CotizacionSimpleFacade {

    public boolean crear(CotizacionSimple cotizacionSimple) {
        try {
            CotizacionSimpleJpaController tc = new CotizacionSimpleJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(cotizacionSimple);
            return true;
        } catch (NonexistentEntityException | PreexistingEntityException ex) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
