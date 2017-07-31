/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ayax.website.persistencia.controladores;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jespinosa
 */
public class ParametroVehiculoJpaController implements Serializable{
    
    private EntityManagerFactory emf = null;
    
    public ParametroVehiculoJpaController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
}
