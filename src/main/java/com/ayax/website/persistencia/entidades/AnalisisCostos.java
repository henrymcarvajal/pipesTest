/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jespinosa
 */
@Entity
@Table(name = "l4_analisis_costos")
@XmlRootElement
public class AnalisisCostos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "precio")
    private Integer precio;
    @Column(name = "sueldo_conductor")
    private Integer salarioConductor;
    @Column(name = "ganancia")
    private Integer ganancia;
    @Column(name = "ahorro_vehiculo")
    private Integer ahorroVehiculo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getSalarioConductor() {
        return salarioConductor;
    }

    public void setSalarioConductor(Integer salarioConductor) {
        this.salarioConductor = salarioConductor;
    }

    public Integer getGanancia() {
        return ganancia;
    }

    public void setGanancia(Integer ganancia) {
        this.ganancia = ganancia;
    }

    public Integer getAhorroVehiculo() {
        return ahorroVehiculo;
    }

    public void setAhorroVehiculo(Integer ahorroVehiculo) {
        this.ahorroVehiculo = ahorroVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalisisCostos)) {
            return false;
        }
        AnalisisCostos other = (AnalisisCostos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.AnalisisCostos id=" + id
                + " ahorro vehiculo : " + ahorroVehiculo
                + " ganancia total : " + ganancia
                + " precio : " + precio
                + " salario conductor : " + salarioConductor;
    }

}
