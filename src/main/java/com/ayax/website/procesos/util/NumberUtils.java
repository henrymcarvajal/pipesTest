/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import com.ayax.exceptions.NumberUtilsExceptions;
import com.ayax.website.persistencia.dto.DecimalDTO;
import com.ayax.website.procesos.AdminServicio;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jespinosa
 */
public class NumberUtils {

    private final static Logger log = Logger.getLogger(NumberUtils.class.getName());

    DecimalDTO fromDecimalToInteger(Double numero) throws NumberUtilsExceptions {

        log.log(Level.INFO, "Ingresando NumberUtils.fromDecimalToInteger");
        DecimalDTO decimal = new DecimalDTO();
        try {

            long iPart;
            double fPart;
            iPart = numero.longValue();
            fPart = numero - iPart;
            decimal.setParteEntera((int) iPart);
            decimal.setParteDecimal(fPart);

        } catch (Exception e) {
            log.log(Level.OFF, "Error fromDecimalToInteger" + e.getMessage());
            throw new NumberUtilsExceptions(">Error generado en NumberUtils.fromDecimalToInteger ", e);
        }
        return decimal;
    }

    public Double parseDecimal(String number) {

        if (number == null) {
            return null;
        }

        number = number.replaceAll("[^\\d,]", "");

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols sfs = new DecimalFormatSymbols();
        sfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(sfs);
        try {
            return df.parse(number).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        sfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(sfs);
        try {
            return df.parse(number).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
