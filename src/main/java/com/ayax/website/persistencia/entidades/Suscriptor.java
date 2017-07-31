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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmcarvajal@ayax.co
 */
@Entity
@Table(name = "l4_suscriptor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suscriptor.findAll", query = "SELECT s FROM Suscriptor s"),
    @NamedQuery(name = "Suscriptor.findByBuzonElectronico", query = "SELECT s FROM Suscriptor s WHERE s.buzonElectronico = :buzonElectronico"),
    @NamedQuery(name = "Suscriptor.findById", query = "SELECT s FROM Suscriptor s WHERE s.id = :id")})
public class Suscriptor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "buzon_electronico")
    private String buzonElectronico;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    public Suscriptor() {
    }

    public Suscriptor(String id) {
        this.id = id;
    }

    public Suscriptor(String id, String buzonElectronico) {
        this.id = id;
        this.buzonElectronico = buzonElectronico;
    }

    public String getBuzonElectronico() {
        return buzonElectronico;
    }

    public void setBuzonElectronico(String buzonElectronico) {
        this.buzonElectronico = buzonElectronico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(object instanceof Suscriptor)) {
            return false;
        }
        Suscriptor other = (Suscriptor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Suscriptor[ id=" + id + " ]";
    }
	
}
