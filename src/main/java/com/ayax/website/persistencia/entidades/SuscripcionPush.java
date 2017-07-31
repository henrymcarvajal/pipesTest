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
@Table(name = "l4_suscripcion_push")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuscripcionPush.findAll", query = "SELECT s FROM SuscripcionPush s"),
    @NamedQuery(name = "SuscripcionPush.findByIp", query = "SELECT s FROM SuscripcionPush s WHERE s.ip = :ip"),
    @NamedQuery(name = "SuscripcionPush.findByEndpoint", query = "SELECT s FROM SuscripcionPush s WHERE s.endpoint = :endpoint"),
    @NamedQuery(name = "SuscripcionPush.findByP256dh", query = "SELECT s FROM SuscripcionPush s WHERE s.p256dh = :p256dh"),
    @NamedQuery(name = "SuscripcionPush.findByAuth", query = "SELECT s FROM SuscripcionPush s WHERE s.auth = :auth")})
public class SuscripcionPush implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    @Column(name = "endpoint")
    private String endpoint;
    @Column(name = "p256dh")
    private String p256dh;
    @Column(name = "auth")
    private String auth;

    public SuscripcionPush() {
    }

    public SuscripcionPush(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ip != null ? ip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuscripcionPush)) {
            return false;
        }
        SuscripcionPush other = (SuscripcionPush) object;
        if ((this.ip == null && other.ip != null) || (this.ip != null && !this.ip.equals(other.ip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.SuscripcionPush[ ip=" + ip + " ]";
    }
    
}
