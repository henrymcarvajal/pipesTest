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
 * @author hmcarvajal@ayax.co
 */
@Entity
@Table(name = "l4_vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v"),
    @NamedQuery(name = "Vehiculo.findById", query = "SELECT v FROM Vehiculo v WHERE v.id = :id"),
    @NamedQuery(name = "Vehiculo.findByPlaca", query = "SELECT v FROM Vehiculo v WHERE UPPER(v.placa) = UPPER(:placa)"),
    @NamedQuery(name = "Vehiculo.findByNumeroPasajeros", query = "SELECT v FROM Vehiculo v WHERE v.numeroPasajeros BETWEEN :numeroPasajerosMinimo AND :numeroPasajerosMaximo"),
    @NamedQuery(name = "Vehiculo.findByCiudad", query = "SELECT v FROM Vehiculo v WHERE v.ciudad = :ciudad"),
    @NamedQuery(name = "Vehiculo.findByFechaCreacion", query = "SELECT v FROM Vehiculo v WHERE v.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Vehiculo.findByMarca", query = "SELECT v FROM Vehiculo v WHERE v.marca = :marca"),
    @NamedQuery(name = "Vehiculo.findByModelo", query = "SELECT v FROM Vehiculo v WHERE v.modelo = :modelo"),
    @NamedQuery(name = "Vehiculo.findByAcondicionado", query = "SELECT v FROM Vehiculo v WHERE v.acondicionado = :acondicionado"),
    @NamedQuery(name = "Vehiculo.findByFechaVencimientoSoat", query = "SELECT v FROM Vehiculo v WHERE v.fechaVencimientoSoat = :fechaVencimientoSoat"),
    @NamedQuery(name = "Vehiculo.findByFechaVencimientoTecnomecanica", query = "SELECT v FROM Vehiculo v WHERE v.fechaVencimientoTecnomecanica = :fechaVencimientoTecnomecanica"),
    @NamedQuery(name = "Vehiculo.findByFechaVencimientoScontractual", query = "SELECT v FROM Vehiculo v WHERE v.fechaVencimientoScontractual = :fechaVencimientoScontractual"),
    @NamedQuery(name = "Vehiculo.findByFechaVencimientoSecontractual", query = "SELECT v FROM Vehiculo v WHERE v.fechaVencimientoSecontractual = :fechaVencimientoSecontractual"),
    @NamedQuery(name = "Vehiculo.findByFechaVencimientoToperacion", query = "SELECT v FROM Vehiculo v WHERE v.fechaVencimientoToperacion = :fechaVencimientoToperacion")})
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
    @Lob
    @Column(name = "soat")
    private byte[] soat;
    @Lob
    @Column(name = "revision_tecnomecanica")
    private byte[] revisionTecnomecanica;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
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
    @Column(name = "marca")
    private String marca;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "acondicionado")
    private Boolean acondicionado;
    @Column(name = "fecha_vencimiento_soat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoSoat;
    @Column(name = "fecha_vencimiento_tecnomecanica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoTecnomecanica;
    @Column(name = "fecha_vencimiento_scontractual")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoScontractual;
    @Column(name = "fecha_vencimiento_secontractual")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoSecontractual;
    @Column(name = "fecha_vencimiento_toperacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoToperacion;
    @Lob
    @Column(name = "tarjeta_operacion")
    private byte[] tarjetaOperacion;
    @JoinColumn(name = "transportador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transportador transportador;

    public Vehiculo() {
    }

    public Vehiculo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(String numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public byte[] getFotoVehiculo() {
        return fotoVehiculo;
    }

    public void setFotoVehiculo(byte[] fotoVehiculo) {
        this.fotoVehiculo = fotoVehiculo;
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

    public byte[] getTarjetaOperacion() {
        return tarjetaOperacion;
    }

    public void setTarjetaOperacion(byte[] tarjetaOperacion) {
        this.tarjetaOperacion = tarjetaOperacion;
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
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ayax.website.persistencia.entidades.Vehiculo[ id=" + id + " ]";
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

}
