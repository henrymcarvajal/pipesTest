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
@Table(name = "l4_transportador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transportador.findAll", query = "SELECT t FROM Transportador t"),
    @NamedQuery(name = "Transportador.findById", query = "SELECT t FROM Transportador t WHERE t.id = :id"),
    @NamedQuery(name = "Transportador.findByNombres", query = "SELECT t FROM Transportador t WHERE t.nombres = :nombres"),
    @NamedQuery(name = "Transportador.findByApellidos", query = "SELECT t FROM Transportador t WHERE t.apellidos = :apellidos"),
    @NamedQuery(name = "Transportador.findByBuzonElectronico", query = "SELECT t FROM Transportador t WHERE t.buzonElectronico = :buzonElectronico"),
    @NamedQuery(name = "Transportador.findByBuzonElectronicoONumeroContacto", query = "SELECT t FROM Transportador t WHERE t.buzonElectronico = :buzonElectronico OR t.numeroContacto = :numeroContacto"),
    @NamedQuery(name = "Transportador.findByNumeroContacto", query = "SELECT t FROM Transportador t WHERE t.numeroContacto = :numeroContacto"),
    @NamedQuery(name = "Transportador.findByContrasena", query = "SELECT t FROM Transportador t WHERE t.contrasena = :contrasena"),
    @NamedQuery(name = "Transportador.findByBuzonElectronicoYContrasena", query = "SELECT t FROM Transportador t WHERE t.buzonElectronico = :buzonElectronico AND t.contrasena = :contrasena"),
    @NamedQuery(name = "Transportador.findByCredito", query = "SELECT t FROM Transportador t WHERE t.credito = :credito")})
public class Transportador implements Serializable {

    public static final String ESTADO_REGISTRO_EXITOSO = "0";
    public static final String ESTADO_REGISTRO_INCOMPLETO = "1";
    public static final String ESTADO_REGISTRO_EN_ESPERA = "2";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String id;
    private String nombres;
    private String apellidos;
    @Column(name = "buzon_electronico")
    private String buzonElectronico;
    private String contrasena;
    private Integer credito;
    @Column(name = "numero_contacto")
    private Long numeroContacto;
    @Column(name = "numero_identificacion")
    private Long numeroIdentificacion;
    @Column(name = "servicios_atendidos")
    private Short serviciosAtendidos;
    @Column(name = "servicios_calificados")
    private Short serviciosCalificados;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @Column(name = "estado_registro")
    private String estadoRegistro;

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }
    private Double reputacion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportador")
    private Collection<Vehiculo> vehiculoCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportador")
    private Collection<Oferta> ofertaCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportador")
    private Collection<Factura> facturaCollection;

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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public Long getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(Long numeroContacto) {
        this.numeroContacto = numeroContacto;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(Long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Double getReputacion() {
        return reputacion;
    }

    public void setReputacion(Double reputacion) {
        this.reputacion = reputacion;
    }

    @XmlTransient
    public Collection<Oferta> getOfertaCollection() {
        return ofertaCollection;
    }

    public void setOfertaCollection(Collection<Oferta> ofertaCollection) {
        this.ofertaCollection = ofertaCollection;
    }

    @XmlTransient
    public Collection<Vehiculo> getVehiculoCollection() {
        return vehiculoCollection;
    }

    public void setVehiculoCollection(Collection<Vehiculo> vehiculoCollection) {
        this.vehiculoCollection = vehiculoCollection;
    }

    @XmlTransient
    public String getNumeroFactura() {
        return tipoIdentificacion + "-" + numeroIdentificacion + "-" + System.currentTimeMillis();
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Transportador[ id=" + id + " ]";
    }

    public TransportadorDTO toDTO() {
        TransportadorDTO dto = new TransportadorDTO();
        dto.setBuzonElectronico(buzonElectronico);
        dto.setId(id);
        dto.setCredito(credito);
        dto.setNombre(nombres);
        dto.setNumeroContacto((numeroContacto != null) ? numeroContacto.toString() : "");
        dto.setServiciosAtendidos(serviciosAtendidos);
        return dto;
    }

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
}
