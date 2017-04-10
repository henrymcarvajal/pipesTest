/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.fachadas;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Suscriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayax
 */
public class SuscriptorFacade {
    
    public boolean crear(Suscriptor suscriptor) {
        try {
            SuscriptorJpaController tc = new SuscriptorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            tc.create(suscriptor);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
