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
public class CotizacionDTO {

    private AnalisisCostosDTO precioMinimo;
    private AnalisisCostosDTO precioMaximo;
    private String ubicacionOrigen;
    private String ubcacionDestino;
    private Integer numeroPasajeros;
    private Integer distancia;
    private String correo;

    public AnalisisCostosDTO getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(AnalisisCostosDTO precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public AnalisisCostosDTO getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(AnalisisCostosDTO precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public String getUbicacionOrigen() {
        return ubicacionOrigen;
    }

    public void setUbicacionOrigen(String ubicacionOrigen) {
        this.ubicacionOrigen = ubicacionOrigen;
    }

    public String getUbcacionDestino() {
        return ubcacionDestino;
    }

    public void setUbcacionDestino(String ubcacionDestino) {
        this.ubcacionDestino = ubcacionDestino;
    }

    public Integer getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(Integer numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {

        return "correo : " + this.correo
                + " distancia : " + this.distancia
                + " numeroPasajeros : " + this.numeroPasajeros
                + " ubicacion origen : " + this.ubicacionOrigen
                + " ubicacion destino : " + this.ubcacionDestino
                + " costos minimo : " + this.precioMinimo.toString()
                + " costos maximo : " + this.precioMaximo.toString();
    }
}
