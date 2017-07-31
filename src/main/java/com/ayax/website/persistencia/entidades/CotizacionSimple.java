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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jespinosa
 */
@Entity
@Table(name = "l4_cotizacion_simple")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "Cotizacion.findById", query = "SELECT s FROM CotizacionSimple s WHERE s.id = :id")
})
public class CotizacionSimple implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "ubicacion_origen")
    private String ubicacionOrigen;
    @Column(name = "ubicacion_destino")
    private String ubicacionDestino;
    @Column(name = "correo")
    private String correo;
    @Column(name = "distancia")
    private String distancia;
    @Column(name = "numero_pasajeros")
    private Integer numeroPasajeros;
    @JoinColumn(name = "id_peor_escenario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AnalisisCostos analisisCostosPeorEscenario;
    @JoinColumn(name = "id_mejor_escenario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AnalisisCostos analisisCostosMejorEscenario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUbicacionOrigen() {
        return ubicacionOrigen;
    }

    public void setUbicacionOrigen(String ubicacionOrigen) {
        this.ubicacionOrigen = ubicacionOrigen;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(Integer numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public AnalisisCostos getAnalisisCostosPeorEscenario() {
        return analisisCostosPeorEscenario;
    }

    public void setAnalisisCostosPeorEscenario(AnalisisCostos analisisCostosPeorEscenario) {
        this.analisisCostosPeorEscenario = analisisCostosPeorEscenario;
    }

    public AnalisisCostos getAnalisisCostosMejorEscenario() {
        return analisisCostosMejorEscenario;
    }

    public void setAnalisisCostosMejorEscenario(AnalisisCostos analisisCostosMejorEscenario) {
        this.analisisCostosMejorEscenario = analisisCostosMejorEscenario;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
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
        if (!(object instanceof CotizacionSimple)) {
            return false;
        }
        CotizacionSimple other = (CotizacionSimple) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.CotizacionSimple id=" + id
                + " distancia : " + distancia
                + " numero pasajeros : " + numeroPasajeros
                + " ubicacion origen : " + ubicacionOrigen
                + " ubicacion destino : " + ubicacionDestino
                + " peor Escenario : " + analisisCostosPeorEscenario.toString()
                + " mejor Escenario : " + analisisCostosMejorEscenario.toString();
    }

}
