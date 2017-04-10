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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mauris
 */
@Entity
@Table(name = "l4_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s"),
    @NamedQuery(name = "Servicio.findAllActive", query = "SELECT s, o FROM Servicio s LEFT JOIN s.ofertas o WHERE s.fechaCreacion > :fechaLimite AND s.fechaEjecucion is null"),
    @NamedQuery(name = "Servicio.findAllFinished", query = "SELECT s, o FROM Servicio s JOIN s.ofertas o WHERE s.fechaTerminacion < :fecha AND o.aceptada = true"),
    @NamedQuery(name = "Servicio.findById", query = "SELECT s FROM Servicio s WHERE s.id = :id"),
    @NamedQuery(name = "Servicio.findByOrigen", query = "SELECT s FROM Servicio s WHERE s.origen = :origen"),
    @NamedQuery(name = "Servicio.findByDestino", query = "SELECT s FROM Servicio s WHERE s.destino = :destino"),
    @NamedQuery(name = "Servicio.findByHoraLlegada", query = "SELECT s FROM Servicio s WHERE s.horaLlegada = :horaLlegada"),
    @NamedQuery(name = "Servicio.findByHoraSalida", query = "SELECT s FROM Servicio s WHERE s.horaSalida = :horaSalida"),
    @NamedQuery(name = "Servicio.findByNumeroPasajeros", query = "SELECT s FROM Servicio s WHERE s.numeroPasajeros = :numeroPasajeros"),
    @NamedQuery(name = "Servicio.findByFechaCreacion", query = "SELECT s FROM Servicio s WHERE s.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Servicio.findByFechaPublicacion", query = "SELECT s FROM Servicio s WHERE s.fechaPublicacion = :fechaPublicacion"),
    @NamedQuery(name = "Servicio.findByFechaEjecucion", query = "SELECT s FROM Servicio s WHERE s.fechaEjecucion = :fechaEjecucion"),
    @NamedQuery(name = "Servicio.findByFechaTerminacion", query = "SELECT s FROM Servicio s WHERE s.fechaTerminacion = :fechaTerminacion"),
    @NamedQuery(name = "Servicio.findByDistancia", query = "SELECT s FROM Servicio s WHERE s.distancia = :distancia"),
    @NamedQuery(name = "Servicio.findByDisponibilidad", query = "SELECT s FROM Servicio s WHERE s.disponibilidad = :disponibilidad"),
    @NamedQuery(name = "Servicio.findByRedondo", query = "SELECT s FROM Servicio s WHERE s.redondo = :redondo")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "origen")
    private String origen;
    @Column(name = "destino")
    private String destino;
    @Column(name = "hora_llegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaLlegada;
    @Column(name = "hora_salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaSalida;
    @Column(name = "numero_pasajeros")
    private String numeroPasajeros;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;
    @Column(name = "fecha_ejecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @Column(name = "fecha_terminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminacion;
    @Column(name = "distancia")
    private Double distancia;
    @Column(name = "redondo")
    private Boolean redondo;
    @Column(name = "disponibilidad")
    private Boolean disponibilidad;
    @Column(name = "calificacion_usuario")
    private Short calificacionUsuario;
    @Column(name = "calificacion_transportador")
    private Short calificacionTransportador;
    @Column(name = "detalle")
    private String detalle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicio")
    private Collection<Oferta> ofertas;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Servicio() {
    }

    public Servicio(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Date horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(String numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Boolean getRedondo() {
        return redondo != null && redondo;
    }

    public void setRedondo(Boolean redondo) {
        this.redondo = redondo;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad != null && disponibilidad;
    }

    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Short getCalificacionTransportador() {
        return (calificacionTransportador == null ? 0 : calificacionTransportador);
    }

    public void setCalificacionTransportador(Short calificacionTransportador) {
        if (calificacionTransportador == null) {
            this.calificacionTransportador = 0;
        } else {
            this.calificacionTransportador = calificacionTransportador;
        }
    }

    public Short getCalificacionUsuario() {
        return (calificacionUsuario == null ? 0 : calificacionUsuario);
    }

    public void setCalificacionUsuario(Short calificacionUsuario) {
        if (calificacionUsuario == null) {
            this.calificacionUsuario = 0;
        } else {
            this.calificacionUsuario = calificacionUsuario;
        }
    }

    @XmlTransient
    public Collection<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(Collection<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Servicio[ id=" + id + " ]";
    }

    public boolean esActivo() {
        return horaLlegada.before(new Date());
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
}
