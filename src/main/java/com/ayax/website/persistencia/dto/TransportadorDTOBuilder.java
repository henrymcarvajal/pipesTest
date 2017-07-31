/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.dto;

import com.ayax.website.persistencia.entidades.Transportador;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class TransportadorDTOBuilder {

    public static TransportadorDTO toDTO(Transportador transportador) {
        TransportadorDTO dto = new TransportadorDTO();
        dto.setBuzonElectronico(transportador.getBuzonElectronico());
        dto.setId(transportador.getId());
        dto.setCredito(transportador.getCredito());
        dto.setNombre(transportador.getNombres());
        dto.setNumeroContacto((transportador.getNumeroContacto() != null) ? transportador.getNumeroContacto().toString() : "");
        dto.setServiciosAtendidos(transportador.getServiciosAtendidos());
        return dto;
    }
}
