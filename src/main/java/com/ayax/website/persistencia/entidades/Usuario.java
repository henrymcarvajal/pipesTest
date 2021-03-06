/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "l4_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByIdPart", query = "SELECT u FROM Usuario u WHERE u.id LIKE CONCAT('%',:id,'%')"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByBuzonElectronico", query = "SELECT u FROM Usuario u WHERE u.buzonElectronico = :buzonElectronico"),
    @NamedQuery(name = "Usuario.findByEmpresa", query = "SELECT u FROM Usuario u WHERE u.empresa = :empresa"),
    @NamedQuery(name = "Usuario.findByIdentificacion", query = "SELECT u FROM Usuario u WHERE u.identificacion = :identificacion"),
    @NamedQuery(name = "Usuario.findByInformacion", query = "SELECT u FROM Usuario u WHERE u.identificacion = :identificacion or u.buzonElectronico = :buzonElectronico"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByEdad", query = "SELECT u FROM Usuario u WHERE u.edad = :edad"),
    @NamedQuery(name = "Usuario.findByReputacion", query = "SELECT u FROM Usuario u WHERE u.reputacion = :reputacion"),
    @NamedQuery(name = "Usuario.findByServiciosCalificados", query = "SELECT u FROM Usuario u WHERE u.serviciosCalificados = :serviciosCalificados"),
    @NamedQuery(name = "Usuario.findByServiciosCompletados", query = "SELECT u FROM Usuario u WHERE u.serviciosCompletados = :serviciosCompletados"),
    @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Usuario.findByTipoUsuario", query = "SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "buzon_electronico")
    private String buzonElectronico;
    @Column(name = "empresa")
    private String empresa;
    @Column(name = "identificacion")
    private BigInteger identificacion;
    @Column(name = "telefono")
    private BigInteger telefono;
    @Column(name = "edad")
    private Short edad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "reputacion")
    private BigDecimal reputacion;
    @Column(name = "servicios_calificados")
    private Short serviciosCalificados;
    @Column(name = "servicios_completados")
    private Short serviciosCompletados;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Servicio> servicioCollection;
    @OneToMany(mappedBy = "usuario")
    private Collection<Mensaje> mensajeCollection;

    public Usuario() {
    }

    public Usuario(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBuzonElectronico() {
        return buzonElectronico;
    }

    public void setBuzonElectronico(String buzonElectronico) {
        this.buzonElectronico = buzonElectronico;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public BigInteger getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(BigInteger identificacion) {
        this.identificacion = identificacion;
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
    }

    public Short getEdad() {
        return edad;
    }

    public void setEdad(Short edad) {
        this.edad = edad;
    }

    public BigDecimal getReputacion() {
        return (reputacion == null ? new BigDecimal(3.0) : reputacion);
    }

    public void setReputacion(BigDecimal reputacion) {
        if (reputacion == null) {
            this.reputacion = new BigDecimal(3.0);
        } else {
            this.reputacion = reputacion;
        }
    }

    public Short getServiciosCalificados() {
        return (serviciosCalificados == null ? 0 : serviciosCalificados);
    }

    public void setServiciosCalificados(Short serviciosCalificados) {
        if (serviciosCalificados == null) {
            this.serviciosCalificados = 0;
        } else {
            this.serviciosCalificados = serviciosCalificados;
        }
    }

    public Short getServiciosCompletados() {
        return (serviciosCompletados == null ? 0 : serviciosCompletados);
    }

    public void setServiciosCompletados(Short serviciosCompletados) {
        if (serviciosCompletados == null) {
            this.serviciosCompletados = 0;
        } else {
            this.serviciosCompletados = serviciosCompletados;
        }
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @XmlTransient
    public Collection<Servicio> getServicioCollection() {
        return servicioCollection;
    }

    public void setServicioCollection(Collection<Servicio> servicioCollection) {
        this.servicioCollection = servicioCollection;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Usuario[ id=" + id + " ]";
    }

}
