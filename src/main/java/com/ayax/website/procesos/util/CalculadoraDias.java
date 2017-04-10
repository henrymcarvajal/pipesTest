/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Mauris
 */
public class CalculadoraDias {

    private static final ArrayList<LocalDate> festivos = new ArrayList();

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int periodo;

    public CalculadoraDias(Short periodo) {
        if (periodo == null) {
            throw new IllegalArgumentException("periodo no puede ser nulo");
        }
        this.periodo = periodo;
        iniciarFestivos();
        ponerFechaInicio();
        ponerFechaFin();
    }

    public CalculadoraDias(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("fechaInicio no puede ser nulo");
        }
        if (fechaFin == null) {
            throw new IllegalArgumentException("fechaFin no puede ser nulo");
        }
        this.periodo = Period.between(fechaInicio, fechaFin).getDays();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        iniciarFestivos();
    }

    public CalculadoraDias() {
        
    }

    /**
     * @return the fechaInicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int getDiasPicoyPlaca(Character picoPlaca) {
        if (picoPlaca == null) {
            throw new IllegalArgumentException("picoPlaca no puede ser nulo");
        }
        int numberOfDays = 0;
        LocalDate temp = fechaInicio;
        while (!temp.isAfter(fechaFin)) {
            DayOfWeek dayOfWeek = temp.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !festivos.contains(temp)) {
                int cociente = temp.getDayOfMonth() % 2;
                if (cociente != 0 && picoPlaca.equals('I')) {
                    numberOfDays++;
                } else {
                    numberOfDays++;
                }
            }
            temp = temp.plusDays(1);
        }
        return numberOfDays;
    }

    public int getDiasTransporteNormal() {
        int numberOfDays = 0;
        LocalDate temp = fechaInicio;
        while (!temp.isAfter(fechaFin)) {
            DayOfWeek dayOfWeek = temp.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !festivos.contains(temp)) {
                numberOfDays++;
            }
            temp = temp.plusDays(1);
        }
        return numberOfDays;
    }

    private void ponerFechaInicio() {
        fechaInicio = LocalDate.now();
        while (fechaInicio.getDayOfWeek() == DayOfWeek.SATURDAY || fechaInicio.getDayOfWeek() == DayOfWeek.SUNDAY || festivos.contains(fechaInicio)) {
            fechaInicio = fechaInicio.plusDays(1);
        }
    }

    public void ponerFechaInicio(LocalDate inicio) {        
        fechaInicio = inicio;
        while (fechaInicio.getDayOfWeek() == DayOfWeek.SATURDAY || fechaInicio.getDayOfWeek() == DayOfWeek.SUNDAY || festivos.contains(fechaInicio)) {
            fechaInicio = fechaInicio.plusDays(1);
        }
    }

    private void ponerFechaFin() {
        switch (periodo) {
            case 30:
                fechaFin = fechaInicio.plusMonths(1);
                break;
            case 15:
                fechaFin = fechaInicio.plusWeeks(2);
                break;
            default:
                fechaFin = fechaInicio.plusWeeks(1);
                break;
        }
        fechaFin = fechaFin.minusDays(1);
        while (fechaFin.getDayOfWeek() == DayOfWeek.SATURDAY || fechaFin.getDayOfWeek() == DayOfWeek.SUNDAY || festivos.contains(fechaFin)) {
            fechaFin = fechaFin.minusDays(1);
        }
    }

    private static void iniciarFestivos() {
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
    
    public Date obtenerFechaHoracero(Date fecha) {
        Date res = fecha;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        res = calendar.getTime();

        return res;
    }
}
