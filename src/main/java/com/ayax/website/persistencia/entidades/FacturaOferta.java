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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmcarvajal@ayax.co
 */
@Entity
@Table(name = "l4_factura_oferta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaOferta.findAll", query = "SELECT l FROM FacturaOferta l"),
    @NamedQuery(name = "FacturaOferta.findByOferta", query = "SELECT l FROM FacturaOferta l WHERE l.oferta = :oferta"),
    @NamedQuery(name = "FacturaOferta.findByValor", query = "SELECT l FROM FacturaOferta l WHERE l.valor = :valor"),
    @NamedQuery(name = "FacturaOferta.findByFechaHoraTx", query = "SELECT l FROM FacturaOferta l WHERE l.fechaHoraTx = :fechaHoraTx"),
    @NamedQuery(name = "FacturaOferta.findByCodigoAprobacion", query = "SELECT l FROM FacturaOferta l WHERE l.codigoAprobacion = :codigoAprobacion"),
    @NamedQuery(name = "FacturaOferta.findByNumeroReferenciaPayco", query = "SELECT l FROM FacturaOferta l WHERE l.numeroReferenciaPayco = :numeroReferenciaPayco"),
    @NamedQuery(name = "FacturaOferta.findByCodigoRespuesta", query = "SELECT l FROM FacturaOferta l WHERE l.codigoRespuesta = :codigoRespuesta"),
    @NamedQuery(name = "FacturaOferta.findByDescripcionRespuesta", query = "SELECT l FROM FacturaOferta l WHERE l.descripcionRespuesta = :descripcionRespuesta"),
    @NamedQuery(name = "FacturaOferta.findByEstadoTx", query = "SELECT l FROM FacturaOferta l WHERE l.estadoTx = :estadoTx"),
    @NamedQuery(name = "FacturaOferta.findByGenerada", query = "SELECT l FROM FacturaOferta l WHERE l.generada = :generada")})
public class FacturaOferta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "oferta")
    private String idOferta;
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
    @JoinColumn(name = "oferta", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Oferta oferta;

    public FacturaOferta() {
    }

    public FacturaOferta(String oferta) {
        this.idOferta = oferta;
    }

    public String getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(String idOferta) {
        this.idOferta = idOferta;
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
        return generada;
    }

    public void setGenerada(Boolean generada) {
        this.generada = generada;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOferta != null ? idOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaOferta)) {
            return false;
        }
        FacturaOferta other = (FacturaOferta) object;
        if ((this.idOferta == null && other.idOferta != null) || (this.idOferta != null && !this.idOferta.equals(other.idOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.FacturaOferta[ idOferta=" + idOferta + " ]";
    }
    
}
