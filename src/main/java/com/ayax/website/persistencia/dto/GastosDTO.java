/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.dto;

/**
 *
 * @author jespinosa
 */
public class GastosDTO {

    private String gastosServicio;
    private String gastosVehiculo;
    private String total;

    public String getGastosServicio() {
        return gastosServicio;
    }

    public void setGastosServicio(String gastosServicio) {
        this.gastosServicio = gastosServicio;
    }

    public String getGastosVehiculo() {
        return gastosVehiculo;
    }

    public void setGastosVehiculo(String gastosVehiculo) {
        this.gastosVehiculo = gastosVehiculo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return " gastos vehiculo : " + gastosVehiculo
                + " gastos servicio : " + gastosServicio
                + " total : " + total;
    }

}
