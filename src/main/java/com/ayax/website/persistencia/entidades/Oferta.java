/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Oferta.findByEstado", query = "SELECT o FROM Oferta o WHERE o.estado = :estado"),
    @NamedQuery(name = "Oferta.findByFactura", query = "SELECT o FROM Oferta o WHERE o.factura = :factura")})
public class Oferta implements Serializable {
    
    public static final String ACEPTADA = "ACEPTADA";
    public static final String CONFIRMADA = "CONFIRMADA";

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
    @Column(name = "estado")
    private String estado;
    @Column(name = "factura")
    private String factura;
    @JoinColumn(name = "servicio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Servicio servicio;
    @JoinColumn(name = "transportador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transportador transportador;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "oferta")
    private FacturaOferta facturaOferta;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
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

    public FacturaOferta getFacturaOferta() {
        return facturaOferta;
    }

    public void setFacturaOferta(FacturaOferta facturaOferta) {
        this.facturaOferta = facturaOferta;
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

    public Integer getComision() {
        return (Double.valueOf(0.16667 * valor).intValue() / 100 ) * 100;
    }
    
    public Integer getReserva() {
        return getComision() + ((Double.valueOf(getComision() * 0.03).intValue() / 100 ) * 100) + 800;
    }

    public Integer getValorTotal() {
        return valor + getReserva();
    }
    
}
