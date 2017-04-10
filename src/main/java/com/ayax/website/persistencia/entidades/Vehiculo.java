/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.entidades;

import com.ayax.website.procesos.util.CalculadoraDias;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauris
 */
@Entity
@Table(name = "l4_vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT l FROM Vehiculo l"),
    @NamedQuery(name = "Vehiculo.findById", query = "SELECT l FROM Vehiculo l WHERE l.id = :id"),
    @NamedQuery(name = "Vehiculo.findByPlaca", query = "SELECT l FROM Vehiculo l WHERE UPPER(l.placa) = UPPER(:placa)"),
    @NamedQuery(name = "Vehiculo.findByNumeroPasajeros", query = "SELECT l FROM Vehiculo l WHERE l.numeroPasajeros BETWEEN :numeroPasajerosMinimo AND :numeroPasajerosMaximo"),
    @NamedQuery(name = "Vehiculo.findByCiudad", query = "SELECT l FROM Vehiculo l WHERE l.ciudad = :ciudad")})
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "placa")
    private String placa;
    @Column(name = "numero_pasajeros")
    private String numeroPasajeros;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "marca")
    private String marca;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "acondicionado")
    private Boolean acondicionado;
    @Lob
    @Column(name = "soat")
    private byte[] soat;
    @Lob
    @Column(name = "revision_tecnomecanica")
    private byte[] revisionTecnomecanica;
    @Lob
    @Column(name = "seguro_contractual")
    private byte[] seguroContractual;
    @Lob
    @Column(name = "seguro_extracontractual")
    private byte[] seguroExtracontractual;
    @Lob
    @Column(name = "tarjeta_propiedad")
    private byte[] tarjetaPropiedad;
    @Lob
    @Column(name = "foto_vehiculo")
    private byte[] fotoVehiculo;
    @Lob
    @Column(name = "tarjeta_operacion")
    private byte[] tarjetaOperacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "fecha_ven_soat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoSoat;
    @Column(name = "fecha_ven_tecnome")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoTecnomecanica;
    @Column(name = "fecha_ven_scontractual")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoScontractual;
    @Column(name = "fecha_ven_secontractual")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoSecontractual;
    @Column(name = "fecha_ven_toperacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoToperacion;

    @JoinColumn(name = "id_transportador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transportador transportador;

    public Vehiculo() {
    }

    public Vehiculo(String id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(String numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public byte[] getSoat() {
        return soat;
    }

    public void setSoat(byte[] soat) {
        this.soat = soat;
    }

    public byte[] getRevisionTecnomecanica() {
        return revisionTecnomecanica;
    }

    public void setRevisionTecnomecanica(byte[] revisionTecnomecanica) {
        this.revisionTecnomecanica = revisionTecnomecanica;
    }

    public Transportador getTransportador() {
        return transportador;
    }

    public void setTransportador(Transportador transportador) {
        this.transportador = transportador;
    }

    public byte[] getSeguroContractual() {
        return seguroContractual;
    }

    public void setSeguroContractual(byte[] seguroContractual) {
        this.seguroContractual = seguroContractual;
    }

    public byte[] getSeguroExtracontractual() {
        return seguroExtracontractual;
    }

    public void setSeguroExtracontractual(byte[] seguroExtracontractual) {
        this.seguroExtracontractual = seguroExtracontractual;
    }

    public byte[] getTarjetaPropiedad() {
        return tarjetaPropiedad;
    }

    public void setTarjetaPropiedad(byte[] tarjetaPropiedad) {
        this.tarjetaPropiedad = tarjetaPropiedad;
    }

    public byte[] getTarjetaOperacion() {
        return tarjetaOperacion;
    }

    public void setTarjetaOperacion(byte[] tarjetaOperacion) {
        this.tarjetaOperacion = tarjetaOperacion;
    }

    public byte[] getFotoVehiculo() {
        return fotoVehiculo;
    }

    public void setFotoVehiculo(byte[] fotoVehiculo) {
        this.fotoVehiculo = fotoVehiculo;
    }

    public Boolean getAcondicionado() {
        return acondicionado;
    }

    public void setAcondicionado(Boolean acondicionado) {
        this.acondicionado = acondicionado;
    }

    public Date getFechaVencimientoSoat() {
        return fechaVencimientoSoat;
    }

    public void setFechaVencimientoSoat(Date fechaVencimientoSoat) {
        this.fechaVencimientoSoat = fechaVencimientoSoat;
    }

    public Date getFechaVencimientoTecnomecanica() {
        return fechaVencimientoTecnomecanica;
    }

    public void setFechaVencimientoTecnomecanica(Date fechaVencimientoTecnomecanica) {
        this.fechaVencimientoTecnomecanica = fechaVencimientoTecnomecanica;
    }

    public Date getFechaVencimientoScontractual() {
        return fechaVencimientoScontractual;
    }

    public void setFechaVencimientoScontractual(Date fechaVencimientoScontractual) {
        this.fechaVencimientoScontractual = fechaVencimientoScontractual;
    }

    public Date getFechaVencimientoSecontractual() {
        return fechaVencimientoSecontractual;
    }

    public void setFechaVencimientoSecontractual(Date fechaVencimientoSecontractual) {
        this.fechaVencimientoSecontractual = fechaVencimientoSecontractual;
    }

    public Date getFechaVencimientoToperacion() {
        return fechaVencimientoToperacion;
    }

    public void setFechaVencimientoToperacion(Date fechaVencimientoToperacion) {
        this.fechaVencimientoToperacion = fechaVencimientoToperacion;
    }

    public Boolean esVehiculoRevisado() {

        SimpleDateFormat sdf = new SimpleDateFormat("M/d/y 'at' h:m a");
        CalculadoraDias cd = new CalculadoraDias();
        Date fechaActualUS = cd.obtenerFechaHoracero(Calendar.getInstance().getTime());
        Date fechaActual = new Date(fechaActualUS.getTime() - 1 * 24 * 3600 * 1000l);

        if (fechaVencimientoSoat != null && fechaVencimientoTecnomecanica != null
                && fechaVencimientoScontractual != null && fechaVencimientoSecontractual != null
                && fechaVencimientoToperacion != null) {
            boolean soatVencido = fechaActual.after(fechaVencimientoSoat);
            boolean tecnomecanicaVencida = fechaActual.after(fechaVencimientoTecnomecanica);
            boolean contractualVencido = fechaActual.after(fechaVencimientoScontractual);
            boolean econtractualVencido = fechaActual.after(fechaVencimientoSecontractual);
            boolean toperacionVencida = fechaActual.after(fechaVencimientoToperacion);

            if (soatVencido) {
                Logger.getLogger(Vehiculo.class.getName()).log(Level.INFO, "Esta vencida la soat");
                return Boolean.FALSE;
            }
            if (tecnomecanicaVencida) {
                Logger.getLogger(Vehiculo.class.getName()).log(Level.INFO, "Esta vencida la tecnomecanica");
                return Boolean.FALSE;
            }
            if (contractualVencido) {
                Logger.getLogger(Vehiculo.class.getName()).log(Level.INFO, "Esta vencida el extra contractual");
                return Boolean.FALSE;
            }
            if (econtractualVencido) {
                Logger.getLogger(Vehiculo.class.getName()).log(Level.INFO, "Esta vencida el contractual");
                return Boolean.FALSE;
            }
            if (toperacionVencida) {
                Logger.getLogger(Vehiculo.class.getName()).log(Level.INFO, "Esta vencida la tarjeta de operacion");
                return Boolean.FALSE;
            }
        } else {
            Logger.getLogger(Vehiculo.class.getName()).log(Level.INFO, "Las fechas de vencimiento estan null");
            return Boolean.FALSE;
        }

        Logger.getLogger(Vehiculo.class.getName()).log(Level.INFO, "retorna TRUE");
        return Boolean.TRUE;
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
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.ayax.website.util.json.temp.Vehiculo[ id=" + id + " ]";
    }

}
