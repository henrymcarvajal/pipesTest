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
public class AnalisisCostosDTO {

    private String sueldoConductor;
    private String gananciaTotal;
    private String precioOfertado;
    private String ahorroVehiculo;

    public String getSueldoConductor() {
        return sueldoConductor;
    }

    public void setSueldoConductor(String sueldoConductor) {
        this.sueldoConductor = sueldoConductor;
    }

    public String getGananciaTotal() {
        return gananciaTotal;
    }

    public void setGananciaTotal(String gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
    }

    public String getPrecioOfertado() {
        return precioOfertado;
    }

    public void setPrecioOfertado(String precioOfertado) {
        this.precioOfertado = precioOfertado;
    }

    public String getAhorroVehiculo() {
        return ahorroVehiculo;
    }

    public void setAhorroVehiculo(String ahorroVehiculo) {
        this.ahorroVehiculo = ahorroVehiculo;
    }

    @Override
    public String toString() {
        return " ahorro vehiculo : " + this.ahorroVehiculo
                + " precio ofertado : " + this.precioOfertado
                + " ganancia total : " + this.gananciaTotal
                + " sueldo conductor : " + this.getSueldoConductor();
    }
}
