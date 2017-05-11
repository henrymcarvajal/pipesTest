/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmcarvajal@ayax.co
 */
@Entity
@Table(name = "l4_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findById", query = "SELECT f FROM Factura f WHERE f.id = :id"),
    @NamedQuery(name = "Factura.findByValor", query = "SELECT f FROM Factura f WHERE f.valor = :valor"),
    @NamedQuery(name = "Factura.findByFechaHoraTx", query = "SELECT f FROM Factura f WHERE f.fechaHoraTx = :fechaHoraTx"),
    @NamedQuery(name = "Factura.findByCodigoAprobacion", query = "SELECT f FROM Factura f WHERE f.codigoAprobacion = :codigoAprobacion"),
    @NamedQuery(name = "Factura.findByNumeroReferenciaPayco", query = "SELECT f FROM Factura f WHERE f.numeroReferenciaPayco = :numeroReferenciaPayco"),
    @NamedQuery(name = "Factura.findByCodigoRespuesta", query = "SELECT f FROM Factura f WHERE f.codigoRespuesta = :codigoRespuesta"),
    @NamedQuery(name = "Factura.findByDescripcionRespuesta", query = "SELECT f FROM Factura f WHERE f.descripcionRespuesta = :descripcionRespuesta"),
    @NamedQuery(name = "Factura.findByEstadoTx", query = "SELECT f FROM Factura f WHERE f.estadoTx = :estadoTx"),
    @NamedQuery(name = "Factura.findByGenerada", query = "SELECT f FROM Factura f WHERE f.generada = :generada")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "valor")
    private BigInteger valor;
    @Column(name = "fecha_hora_tx")
    private String fechaHoraTx;
    @Column(name = "codigo_aprobacion")
    private Integer codigoAprobacion;
    @Column(name = "numero_referencia_payco")
    private Integer numeroReferenciaPayco;
    @Column(name = "codigo_respuesta")
    private Short codigoRespuesta;
    @Column(name = "descripcion_respuesta")
    private String descripcionRespuesta;
    @Column(name = "estado_tx")
    private String estadoTx;
    @Column(name = "generada")
    private Boolean generada;
    @JoinColumn(name = "id_transportador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transportador transportador;

    public Factura() {
    }

    public Factura(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public String getFechaHoraTx() {
        return fechaHoraTx;
    }

    public void setFechaHoraTx(String fechaHoraTx) {
        this.fechaHoraTx = fechaHoraTx;
    }

    public Integer getCodigoAprobacion() {
        return codigoAprobacion;
    }

    public void setCodigoAprobacion(Integer codigoAprobacion) {
        this.codigoAprobacion = codigoAprobacion;
    }

    public Integer getNumeroReferenciaPayco() {
        return numeroReferenciaPayco;
    }

    public void setNumeroReferenciaPayco(Integer numeroReferenciaPayco) {
        this.numeroReferenciaPayco = numeroReferenciaPayco;
    }

    public Short getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Short codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public String getEstadoTx() {
        return estadoTx;
    }

    public void setEstadoTx(String estadoTx) {
        this.estadoTx = estadoTx;
    }

    public Boolean getGenerada() {
        return ((generada==null)?false:generada);
    }

    public void setGenerada(Boolean generada) {
        this.generada = generada;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Factura[ id=" + id + " ]";
    }
    
}
