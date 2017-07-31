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
@Table(name = "l4_transportador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transportador.findAll", query = "SELECT t FROM Transportador t"),
    @NamedQuery(name = "Transportador.findById", query = "SELECT t FROM Transportador t WHERE t.id = :id"),
    @NamedQuery(name = "Transportador.findByNombres", query = "SELECT t FROM Transportador t WHERE t.nombres = :nombres"),
    @NamedQuery(name = "Transportador.findByBuzonElectronico", query = "SELECT t FROM Transportador t WHERE t.buzonElectronico = :buzonElectronico"),
    @NamedQuery(name = "Transportador.findByBuzonElectronicoONumeroContacto", query = "SELECT t FROM Transportador t WHERE t.buzonElectronico = :buzonElectronico OR t.numeroContacto = :numeroContacto"),
    @NamedQuery(name = "Transportador.findByBuzonElectronicoYContrasena", query = "SELECT t FROM Transportador t WHERE t.buzonElectronico = :buzonElectronico AND t.contrasena = :contrasena"),
    @NamedQuery(name = "Transportador.findByContrasena", query = "SELECT t FROM Transportador t WHERE t.contrasena = :contrasena"),
    @NamedQuery(name = "Transportador.findByCredito", query = "SELECT t FROM Transportador t WHERE t.credito = :credito"),
    @NamedQuery(name = "Transportador.findByServiciosAtendidos", query = "SELECT t FROM Transportador t WHERE t.serviciosAtendidos = :serviciosAtendidos"),
    @NamedQuery(name = "Transportador.findByNumeroContacto", query = "SELECT t FROM Transportador t WHERE t.numeroContacto = :numeroContacto"),
    @NamedQuery(name = "Transportador.findByFechaCreacion", query = "SELECT t FROM Transportador t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Transportador.findByNumeroIdentificacion", query = "SELECT t FROM Transportador t WHERE t.numeroIdentificacion = :numeroIdentificacion"),
    @NamedQuery(name = "Transportador.findByTipoIdentificacion", query = "SELECT t FROM Transportador t WHERE t.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "Transportador.findByApellidos", query = "SELECT t FROM Transportador t WHERE t.apellidos = :apellidos"),
    @NamedQuery(name = "Transportador.findByReputacion", query = "SELECT t FROM Transportador t WHERE t.reputacion = :reputacion"),
    @NamedQuery(name = "Transportador.findByServiciosCalificados", query = "SELECT t FROM Transportador t WHERE t.serviciosCalificados = :serviciosCalificados"),
    @NamedQuery(name = "Transportador.findByEstadoRegistro", query = "SELECT t FROM Transportador t WHERE t.estadoRegistro = :estadoRegistro")})
public class Transportador implements Serializable {

    public static final String ESTADO_REGISTRO_EXITOSO = "0";
    public static final String ESTADO_REGISTRO_INCOMPLETO = "1";
    public static final String ESTADO_REGISTRO_EN_ESPERA = "2";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "buzon_electronico")
    private String buzonElectronico;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "credito")
    private Integer credito;
    @Column(name = "servicios_atendidos")
    private Short serviciosAtendidos;
    @Column(name = "numero_contacto")
    private BigInteger numeroContacto;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "numero_identificacion")
    private BigInteger numeroIdentificacion;
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @Column(name = "apellidos")
    private String apellidos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "reputacion")
    private BigDecimal reputacion;
    @Column(name = "servicios_calificados")
    private Short serviciosCalificados;
    @Column(name = "estado_registro")
    private String estadoRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportador")
    private Collection<Vehiculo> vehiculoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportador")
    private Collection<Conversacion> conversacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportador")
    private Collection<Oferta> ofertaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportador")
    private Collection<Factura> facturaCollection;
    @OneToMany(mappedBy = "transportador")
    private Collection<Mensaje> mensajeCollection;

    public Transportador() {
    }

    public Transportador(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getBuzonElectronico() {
        return buzonElectronico;
    }

    public void setBuzonElectronico(String buzonElectronico) {
        this.buzonElectronico = buzonElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getCredito() {
        return (credito == null ? 0 : credito);
    }

    public void setCredito(Integer credito) {
        if (credito == null) {
            this.credito = 0;
        } else {
            this.credito = credito;
        }
    }

    public Short getServiciosAtendidos() {
        return (serviciosAtendidos == null ? 0 : serviciosAtendidos);
    }

    public void setServiciosAtendidos(Short serviciosAtendidos) {
        if (serviciosAtendidos == null) {
            this.serviciosAtendidos = 0;
        } else {
            this.serviciosAtendidos = serviciosAtendidos;
        }
    }

    public BigInteger getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(BigInteger numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigInteger getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(BigInteger numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    @XmlTransient
    public Collection<Vehiculo> getVehiculoCollection() {
        return vehiculoCollection;
    }

    public void setVehiculoCollection(Collection<Vehiculo> vehiculoCollection) {
        this.vehiculoCollection = vehiculoCollection;
    }

    @XmlTransient
    public Collection<Conversacion> getConversacionCollection() {
        return conversacionCollection;
    }

    public void setConversacionCollection(Collection<Conversacion> conversacionCollection) {
        this.conversacionCollection = conversacionCollection;
    }

    @XmlTransient
    public Collection<Oferta> getOfertaCollection() {
        return ofertaCollection;
    }

    public void setOfertaCollection(Collection<Oferta> ofertaCollection) {
        this.ofertaCollection = ofertaCollection;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
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
        if (!(object instanceof Transportador)) {
            return false;
        }
        Transportador other = (Transportador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Transportador[ id=" + id + " ]";
    }

    public String getNumeroFactura() {
        return tipoIdentificacion + "-" + numeroIdentificacion + "-" + System.currentTimeMillis();
    }

}
