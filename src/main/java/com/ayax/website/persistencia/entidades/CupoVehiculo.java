/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmcarvajal@ayax.co
 */
@Entity
@Table(name = "l4_cupo_x_vehiculo")
@Cacheable
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CupoVehiculo.findAll", query = "SELECT c FROM CupoVehiculo c"),
    @NamedQuery(name = "CupoVehiculo.findById", query = "SELECT c FROM CupoVehiculo c WHERE c.id = :id"),
    @NamedQuery(name = "CupoVehiculo.findByTamano", query = "SELECT c FROM CupoVehiculo c WHERE c.tamano = :tamano"),
    @NamedQuery(name = "CupoVehiculo.findByMinimo", query = "SELECT c FROM CupoVehiculo c WHERE c.minimo = :minimo"),
    @NamedQuery(name = "CupoVehiculo.findByMaximo", query = "SELECT c FROM CupoVehiculo c WHERE c.maximo = :maximo")})
public class CupoVehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "tamano")
    private String tamano;
    @Column(name = "minimo")
    private Short minimo;
    @Column(name = "maximo")
    private Short maximo;

    public CupoVehiculo() {
    }

    public CupoVehiculo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public Short getMinimo() {
        return minimo;
    }

    public void setMinimo(Short minimo) {
        this.minimo = minimo;
    }

    public Short getMaximo() {
        return maximo;
    }

    public void setMaximo(Short maximo) {
        this.maximo = maximo;
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
        if (!(object instanceof CupoVehiculo)) {
            return false;
        }
        CupoVehiculo other = (CupoVehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.CupoVehiculo[ id=" + id + " ]";
    }
    
}
