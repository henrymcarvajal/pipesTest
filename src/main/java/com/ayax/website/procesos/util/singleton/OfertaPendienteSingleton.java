/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ayax.website.procesos.util.singleton;

/**
 *
 * @author jespinosa
 */
public class OfertaPendienteSingleton {
    
    private static OfertaPendienteSingleton instance;
    private OfertaPendienteSingleton() {

    }

    public static OfertaPendienteSingleton getInstance() {
        if (instance == null){
            instance= new OfertaPendienteSingleton();
        }
        return instance;
    }

    private String idServicio=null;
    private String valorOferta=null;

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(String valorOferta) {
        this.valorOferta = valorOferta;
    }
}
