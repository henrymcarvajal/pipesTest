/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public enum DiasFestivos {
    
    INSTANCE;

    private static final ArrayList<LocalDate> festivos = new ArrayList();

    static {
        String[] cadenaFestivos = new String[]{
            "2016-01-01",
            "2016-01-11",
            "2016-03-21",
            "2016-03-24",
            "2016-03-25",
            "2016-05-01",
            "2016-05-09",
            "2016-05-30",
            "2016-06-06",
            "2016-07-04",
            "2016-07-20",
            "2016-08-07",
            "2016-08-15",
            "2016-10-17",
            "2016-11-07",
            "2016-11-14",
            "2016-12-08",
            "2016-12-25"
        };

        for (String cadenaFestivo : cadenaFestivos) {
            festivos.add(LocalDate.parse(cadenaFestivo));
        }
    }
    
    public boolean esFestivo(LocalDate dia) {
        boolean esFestivo = false;
        for(LocalDate festivo : festivos) {
            if (festivo.isEqual(dia)) {
                esFestivo = true;
                break;
            }
        }
        return esFestivo;
    }
}
