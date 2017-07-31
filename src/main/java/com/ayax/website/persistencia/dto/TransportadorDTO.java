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
public class TransportadorDTO {

    private String id;
    private String nombre;
    private String buzonElectronico;
    private String numeroContacto;
    private Integer credito;
    private Short serviciosAtendidos;

    public TransportadorDTO() {
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    public Short getServiciosAtendidos() {
        return serviciosAtendidos;
    }

    public void setServiciosAtendidos(Short serviciosAtendidos) {
        this.serviciosAtendidos = serviciosAtendidos;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombres
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombres to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the buzonElectronico
     */
    public String getBuzonElectronico() {
        return buzonElectronico;
    }

    /**
     * @param buzonElectronico the buzonElectronico to set
     */
    public void setBuzonElectronico(String buzonElectronico) {
        this.buzonElectronico = buzonElectronico;
    }

    /**
     * @return the credito
     */
    public Integer getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    /**
     * @return the numeroContacto
     */
    public String getNumeroContacto() {
        return numeroContacto;
    }

    /**
     * @param numeroContacto the numeroContacto to set
     */
    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }
}
