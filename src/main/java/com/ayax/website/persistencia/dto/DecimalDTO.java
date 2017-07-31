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
public class DecimalDTO {
    
    private Integer parteEntera;
    private Double parteDecimal;

    public Integer getParteEntera() {
        return parteEntera;
    }

    public void setParteEntera(Integer parteEntera) {
        this.parteEntera = parteEntera;
    }

    public Double getParteDecimal() {
        return parteDecimal;
    }

    public void setParteDecimal(Double parteDecimal) {
        this.parteDecimal = parteDecimal;
    } 
    
}
