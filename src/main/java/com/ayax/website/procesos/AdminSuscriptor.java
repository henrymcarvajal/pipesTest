/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.entidades.Suscriptor;
import com.ayax.website.persistencia.fachadas.SuscriptorFacade;
import com.ayax.website.persistencia.fachadas.TransportadorFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Response;
import spark.Request;
/**
 *
 * @author Ayax
 */
public class AdminSuscriptor {
    
    /**
     *
     * @param req
     * @param res
     * @return
     */
    public Respuesta crearSuscriptor(Request req, Response res) {

        Respuesta rta = new Respuesta();
        try {
            
            String buzonElectronico = req.queryParams("correo");
            SuscriptorFacade sf = new SuscriptorFacade();
            Suscriptor s = new Suscriptor();
            s.setBuzonElectronico(buzonElectronico);
            s.setId(java.util.UUID.randomUUID().toString());
            sf.crear(s);
            rta.setCodigo("000");
            rta.setResultado("Exito");
        } catch (Exception e) {
            
            rta.setCodigo("001");
            rta.setResultado("Error");
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return rta;
    }
}
