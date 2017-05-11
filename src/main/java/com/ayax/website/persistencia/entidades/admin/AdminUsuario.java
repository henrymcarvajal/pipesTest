/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades.admin;

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
@Table(name = "l4_admin_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdminUsuario.findAll", query = "SELECT u FROM AdminUsuario u"),
    @NamedQuery(name = "AdminUsuario.findByBuzonElectronico", query = "SELECT u FROM AdminUsuario u WHERE u.buzonElectronico = :buzonElectronico"),
    @NamedQuery(name = "AdminUsuario.findByUsuario", query = "SELECT u FROM AdminUsuario u WHERE u.usuario = :usuario")})
public class AdminUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String usuario;
    @Column(name = "buzon_electronico")
    private String buzonElectronico;
    
    public AdminUsuario() {
    }

    public AdminUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getBuzonElectronico() {
        return buzonElectronico;
    }

    public void setBuzonElectronico(String buzonElectronico) {
        this.buzonElectronico = buzonElectronico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminUsuario)) {
            return false;
        }
        AdminUsuario other = (AdminUsuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.admin.AdminUsuario[ usuario=" + usuario + " ]";
    }
}
