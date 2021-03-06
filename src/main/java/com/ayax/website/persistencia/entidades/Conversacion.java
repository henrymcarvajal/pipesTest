/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmcarvajal@ayax.co
 */
@Entity
@Table(name = "l4_conversacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conversacion.findAll", query = "SELECT c FROM Conversacion c"),
    @NamedQuery(name = "Conversacion.findById", query = "SELECT c FROM Conversacion c WHERE c.id = :id"),
    @NamedQuery(name = "Conversacion.findByFechaCreacion", query = "SELECT c FROM Conversacion c WHERE c.fechaCreacion = :fechaCreacion")})
public class Conversacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "servicio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Servicio servicio;
    @JoinColumn(name = "transportador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transportador transportador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversacion")
    @OrderBy("fechaCreacion ASC")
    private Collection<Mensaje> mensajeCollection;

    public Conversacion() {
    }

    public Conversacion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Transportador getTransportador() {
        return transportador;
    }

    public void setTransportador(Transportador transportador) {
        this.transportador = transportador;
    }

    @XmlTransient
    public Collection<Mensaje> getMensajeCollection() {
        return mensajeCollection;
    }

    public void setMensajeCollection(Collection<Mensaje> mensajeCollection) {
        this.mensajeCollection = mensajeCollection;
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
        if (!(object instanceof Conversacion)) {
            return false;
        }
        Conversacion other = (Conversacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Conversacion[ id=" + id + " ]";
    }
    
}
