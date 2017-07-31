/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.dto;

import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.persistencia.entidades.Vehiculo;
import com.ayax.website.procesos.AdminServicio;
import com.ayax.website.procesos.util.ImageUtils;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class OfertaDTOBuilder {

    public static OfertaDTO toDTO(Oferta oferta) {
        OfertaDTO dto = new OfertaDTO();
        Vehiculo vehiculo = (Vehiculo) oferta.getTransportador().getVehiculoCollection().iterator().next();
        Usuario usuario = oferta.getServicio().getUsuario();
        boolean isEmpresaSEspecial = AdminServicio.TIPO_USUARIO_SERVICIOESPECIAL.equalsIgnoreCase(usuario.getTipoUsuario());
        dto.setNombreTransportador(oferta.getTransportador().getNombres());
        dto.setMarcaVehiculo(vehiculo.getMarca());
        dto.setModeloVehiculo(vehiculo.getModelo());
        dto.setCapacidadVehiculo(Integer.parseInt(vehiculo.getNumeroPasajeros()));
        dto.setValorOferta(oferta.getValorTotal());
        dto.setReputacionTransportador(oferta.getTransportador().getReputacion().doubleValue());
        dto.setServiciosEjecutados(oferta.getTransportador().getServiciosAtendidos());
        dto.setAireAcondicionado(vehiculo.getAcondicionado());
        dto.setFotoVehiculo(ImageUtils.encodeImage(vehiculo.getFotoVehiculo()));

        if (isEmpresaSEspecial) {
            dto.setNumeroContacto(oferta.getTransportador().getNumeroContacto().toString());
        }

        return dto;
    }
}
