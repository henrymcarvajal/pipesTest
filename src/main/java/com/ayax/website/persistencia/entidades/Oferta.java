/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import com.ayax.website.procesos.AdminServicio;
import com.ayax.website.procesos.util.ImageUtils;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmcarvajal@ayax.co
 */
@Entity
@Table(name = "l4_oferta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT o FROM Oferta o"),
    @NamedQuery(name = "Oferta.findById", query = "SELECT o FROM Oferta o WHERE o.id = :id"),
    @NamedQuery(name = "Oferta.findByServicioAndTransportador", query = "SELECT o FROM Oferta o JOIN Transportador t JOIN Servicio s WHERE t.id = :idTransportador AND s.id = :idServicio"),
    @NamedQuery(name = "Oferta.findByValor", query = "SELECT o FROM Oferta o WHERE o.valor = :valor"),
    @NamedQuery(name = "Oferta.findByFecha", query = "SELECT o FROM Oferta o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "Oferta.findByAceptada", query = "SELECT o FROM Oferta o WHERE o.aceptada = :aceptada")})
public class Oferta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "valor")
    private Integer valor;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "aceptada")
    private Boolean aceptada;
    @JoinColumn(name = "id_servicio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Servicio servicio;
    @JoinColumn(name = "id_transportador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transportador transportador;

    public Oferta() {
    }

    public Oferta(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean isAceptada() {
        return aceptada != null && aceptada;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Oferta[ id=" + id + " ]";
    }

    public Double getComision() {
        if (valor < 170000) {
            return valor * (0.2 * valor) / 170000;
        }
        return 0.2 * valor;
    }

    public Oferta.OfertaDTO toDTO() {
        
        Oferta.OfertaDTO dto = new Oferta.OfertaDTO();
        Vehiculo vehiculo = (Vehiculo) transportador.getVehiculos().iterator().next();
        Usuario usuario = servicio.getUsuario();
        boolean isEmpresaSEspecial = AdminServicio.TIPO_USUARIO_ESERVICIOESPECIAL.equalsIgnoreCase(usuario.getTipoUsuario());
        dto.setNombreTransportador(transportador.getNombres());
        dto.setMarcaVehiculo(vehiculo.getMarca());
        dto.setModeloVehiculo(vehiculo.getModelo());
        dto.setCapacidadVehiculo(Integer.parseInt(vehiculo.getNumeroPasajeros()));
        dto.setValorOferta(valor);
        dto.setReputacionTransportador(transportador.getReputacion().doubleValue());
        dto.setServiciosEjecutados(transportador.getServiciosAtendidos());
        dto.setAireAcondicionado(vehiculo.getAcondicionado());
        dto.setFotoVehiculo(ImageUtils.encodeImage(vehiculo.getFotoVehiculo()));
        
        if (isEmpresaSEspecial) {
            dto.setNumeroContacto(transportador.getNumeroContacto().toString());
        }
        
        return dto;
    }

    public class OfertaDTO {

        private String nombreTransportador;
        private String modeloVehiculo;
        private String marcaVehiculo;
        private Integer capacidadVehiculo;
        private Integer valorOferta;
        private Double reputacionTransportador;
        private Boolean aireAcondicionado;
        private Short serviciosEjecutados;
        private String fotoVehiculo;
        private String numeroContacto;

        public OfertaDTO() {
        }

        public String getFotoVehiculo() {
            return fotoVehiculo;
        }

        public void setFotoVehiculo(String fotoVehiculo) {
            this.fotoVehiculo = fotoVehiculo;
        }

        public String getNumeroContacto() {
            return numeroContacto;
        }

        public void setNumeroContacto(String numeroContacto) {
            this.numeroContacto = numeroContacto;
        }
        
        public Boolean getAireAcondicionado() {
            return aireAcondicionado;
        }

        public void setAireAcondicionado(Boolean aireAcondicionado) {
            this.aireAcondicionado = aireAcondicionado;
        }

        public Short getServiciosEjecutados() {
            return serviciosEjecutados;
        }

        public void setServiciosEjecutados(Short serviciosEjecutados) {
            this.serviciosEjecutados = serviciosEjecutados;
        }

        public String getNombreTransportador() {
            return nombreTransportador;
        }

        public void setNombreTransportador(String nombreTransportador) {
            this.nombreTransportador = nombreTransportador;
        }

        public String getModeloVehiculo() {
            return modeloVehiculo;
        }

        public void setModeloVehiculo(String modeloVehiculo) {
            this.modeloVehiculo = modeloVehiculo;
        }

        public String getMarcaVehiculo() {
            return marcaVehiculo;
        }

        public void setMarcaVehiculo(String marcaVehiculo) {
            this.marcaVehiculo = marcaVehiculo;
        }

        public Integer getCapacidadVehiculo() {
            return capacidadVehiculo;
        }

        public void setCapacidadVehiculo(Integer capacidadVehiculo) {
            this.capacidadVehiculo = capacidadVehiculo;
        }

        public Integer getValorOferta() {
            return valorOferta;
        }

        public void setValorOferta(Integer valorOferta) {
            this.valorOferta = valorOferta;
        }

        public Double getReputacionTransportador() {
            return reputacionTransportador;
        }

        public void setReputacionTransportador(Double reputacionTransportador) {
            this.reputacionTransportador = reputacionTransportador;
        }
    }
}
