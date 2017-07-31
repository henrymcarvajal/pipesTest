/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.dto;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class OfertaDTO {

    private String nombreTransportador;
    private String modeloVehiculo;
    private String marcaVehiculo;
    private Integer capacidadVehiculo;
    private Integer valorOferta;
    private Double reputacionTransportador;
    private Boolean aireAcondicionado;
    private Short serviciosEjecutados;
    private String fotoVehiculo;
    private String numeroContacto;

    public OfertaDTO() {
    }

    public String getFotoVehiculo() {
        return fotoVehiculo;
    }

    public void setFotoVehiculo(String fotoVehiculo) {
        this.fotoVehiculo = fotoVehiculo;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public Boolean getAireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(Boolean aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }

    public Short getServiciosEjecutados() {
        return serviciosEjecutados;
    }

    public void setServiciosEjecutados(Short serviciosEjecutados) {
        this.serviciosEjecutados = serviciosEjecutados;
    }

    public String getNombreTransportador() {
        return nombreTransportador;
    }

    public void setNombreTransportador(String nombreTransportador) {
        this.nombreTransportador = nombreTransportador;
    }

    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

    public void setModeloVehiculo(String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public Integer getCapacidadVehiculo() {
        return capacidadVehiculo;
    }

    public void setCapacidadVehiculo(Integer capacidadVehiculo) {
        this.capacidadVehiculo = capacidadVehiculo;
    }

    public Integer getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(Integer valorOferta) {
        this.valorOferta = valorOferta;
    }

    public Double getReputacionTransportador() {
        return reputacionTransportador;
    }

    public void setReputacionTransportador(Double reputacionTransportador) {
        this.reputacionTransportador = reputacionTransportador;
    }
}
