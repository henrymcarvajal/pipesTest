/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ayax.website.persistencia.dto;

/**
 *
 * @author jespinosa
 */
public class CostosEscenariosDTO {
    
    private AnalisisCostosDTO costosPeorEscenario;
    private AnalisisCostosDTO costosMejorEscenario;

    public AnalisisCostosDTO getCostosPeorEscenario() {
        return costosPeorEscenario;
    }

    public void setCostosPeorEscenario(AnalisisCostosDTO costosPeorEscenario) {
        this.costosPeorEscenario = costosPeorEscenario;
    }

    public AnalisisCostosDTO getCostosMejorEscenario() {
        return costosMejorEscenario;
    }

    public void setCostosMejorEscenario(AnalisisCostosDTO costosMejorEscenario) {
        this.costosMejorEscenario = costosMejorEscenario;
    }
    
    @Override
    public String toString(){
        return " costosPeorEscenario : "+costosPeorEscenario.toString()+
                " costosMejorEscenario : "+costosMejorEscenario.toString();
    }
}
