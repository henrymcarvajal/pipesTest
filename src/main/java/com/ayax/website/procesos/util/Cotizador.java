/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import com.ayax.exceptions.CotizadorException;
import com.ayax.exceptions.GeneralException;
import com.ayax.exceptions.NumberUtilsExceptions;
import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.controladores.ParametroVehiculoJpaController;
import com.ayax.website.persistencia.dto.AnalisisCostosDTO;
import com.ayax.website.persistencia.dto.CostosEscenariosDTO;
import com.ayax.website.persistencia.dto.CotizacionDTO;
import com.ayax.website.persistencia.dto.DecimalDTO;
import com.ayax.website.persistencia.dto.GastosDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author jespinosa
 */
public class Cotizador {

    private final static Logger log = Logger.getLogger(Cotizador.class.getName());

    public CostosEscenariosDTO obtenerCotizacionSolicitud(Integer distancia, Integer precioPeajes,
            Integer numeroPasajeros) throws GeneralException {

        log.log(Level.INFO, "Ingresando Cotizador.obtenerCotizacionSolicitud : ");

        CostosEscenariosDTO costosEscenario = new CostosEscenariosDTO();
        try {

            GastosDTO gastos = calcularGastosServicio(distancia, numeroPasajeros);
            CotizacionDTO cotizacion = calcularPrecios(numeroPasajeros, distancia, gastos, precioPeajes);

            AnalisisCostosDTO analisisCasoMenorPrecio = obtenerAnalisisCostos(Double.valueOf(gastos.getTotal()),
                    Double.valueOf(gastos.getGastosVehiculo()), Double.valueOf(cotizacion.getPrecioMinimo().getPrecioOfertado()),
                    precioPeajes);
            AnalisisCostosDTO analisisCasoMayorPrecio = obtenerAnalisisCostos(Double.valueOf(gastos.getTotal()),
                    Double.valueOf(gastos.getGastosVehiculo()), Double.valueOf(cotizacion.getPrecioMaximo().getPrecioOfertado()),
                    precioPeajes);
            costosEscenario.setCostosPeorEscenario(analisisCasoMenorPrecio);
            costosEscenario.setCostosMejorEscenario(analisisCasoMayorPrecio);
            costosEscenario = validarPrecioMinimoEscenarios(costosEscenario, numeroPasajeros);
        } catch (NumberUtilsExceptions e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "error cotizando : ", e);
            throw new CotizadorException(e);
        }

        return costosEscenario;
    }

    private AnalisisCostosDTO obtenerAnalisisCostos(Double gastosTotal, Double gastosVehiculo,
            Double precio, Integer precioPeajes) {

        log.log(Level.INFO, "Ingresando obtenerAnalisisCostos : ", gastosTotal.toString() + gastosVehiculo.toString()
                + precio.toString() + precioPeajes.toString());
        AnalisisCostosDTO analissCostos = new AnalisisCostosDTO();
        Double sueldoConductor = (precio * 0.2);
        analissCostos.setPrecioOfertado(precio.toString());
        Double gananciaTotal = precio - gastosTotal - sueldoConductor - precioPeajes;
        analissCostos.setGananciaTotal(gananciaTotal.toString());
        analissCostos.setSueldoConductor(sueldoConductor.toString());
        analissCostos.setAhorroVehiculo(gastosVehiculo.toString());
        log.log(Level.INFO, "Ingresando obtenerAnalisisCostos : " + analissCostos.toString());
        return analissCostos;
    }

    private Integer obtenerValorParametro(String llave) {

        Logger.getLogger(Cotizador.class.getName()).log(Level.INFO,
                "Ingresando cotizador. : obtenerValorParametro ", llave);
        String valor = null;
        ParametroVehiculoJpaController parametroVehiculo
                = new ParametroVehiculoJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = parametroVehiculo.getEntityManager();
        Query q = em.createNamedQuery("ParamVehiculo.getValParam");
        q.setParameter("llave", llave);
        valor = (String) q.getSingleResult();
        Logger.getLogger(Cotizador.class.getName()).log(Level.INFO,
                "Saliendo cotizador. : obtenerValorParametro " + valor);
        return Integer.parseInt(valor);
    }

    private GastosDTO calcularGastosServicio(Integer distancia, Integer numPasajeros) {

        log.log(Level.INFO,
                "Ingresando cotizador. : calcularGastosServicio distancia, pasajeros " + distancia.toString() + numPasajeros.toString());

        GastosDTO gastos = new GastosDTO();
        Integer relacionCombustible = 0;
        Integer devaluacionAnual = 0;
        Integer precioTecnomecanica = 0;

        Double gastoCombustible;
        Double gastoMantFrenos;
        Double gastoCambioAceite;
        Double gastoCambioCorreaEmbrague;
        Double gastoLlantas;
        Double gastoDevaluacionAnual;
        Double gastoSegurosContraExtra;
        Double gastoSoat;
        Double gastoTecnomecanica;
        Double gastoFuec = (double) 15000;

        Integer valorCombustible = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_CONBUSTIBLE);
        Integer valorCambioLlantas = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_CAMBIO_LLANTAS);
        Integer valorCambioAceite = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_CAMBIO_ACEITE_PROMEDIO);
        Integer valorMantenimientoFrenos = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_MANTENIMIENTO_FRENOS);
        Integer valorSeguroContraExtra = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_SEGURO_CONTRA_EXTRA);
        Integer valorMantenimientoCorrea = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_CORREA_EMBRAGUE);
        Integer valorSoat = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_SOAT);
        Integer kmsCambioLlantas = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_KMS_CAMBIO_LLANTAS);
        Integer kmsCambioAceite = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_KMS_CAMBIO_ACEITE);
        Integer kmsCambioCorrea = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_KMS_CAMBIO_CORREA);
        Integer kmsMantFrenos = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_KMS_MANT_FRENOS);
        Integer kmsRecorridoAnual = obtenerValorParametro(
                ConstanteLlavesParametroVehiculo.LLAVE_KMS_RECORRIDO_ANUAL);

        if (numPasajeros <= 9) {
            relacionCombustible = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_RELACION_CONSUMO_9);
            devaluacionAnual = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_DEVALUACION_ANUAL_9);
            precioTecnomecanica = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_TECNOMECANICA_9);
        } else if (numPasajeros > 9 && numPasajeros <= 17) {
            relacionCombustible = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_RELACION_CONSUMO_17);
            devaluacionAnual = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_DEVALUACION_ANUAL_17);
            precioTecnomecanica = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_TECNOMECANICA_17_42);
        } else if (numPasajeros > 17 && numPasajeros <= 24) {
            relacionCombustible = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_RELACION_CONSUMO_24);
            devaluacionAnual = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_DEVALUACION_ANUAL_24);
            precioTecnomecanica = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_TECNOMECANICA_17_42);
        } else if (numPasajeros > 24 && numPasajeros <= 32) {
            relacionCombustible = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_RELACION_CONSUMO_32);
            devaluacionAnual = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_DEVALUACION_ANUAL_32);
            precioTecnomecanica = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_TECNOMECANICA_17_42);
        } else if (numPasajeros > 32) {
            relacionCombustible = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_RELACION_CONSUMO_42);
            devaluacionAnual = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_DEVALUACION_ANUAL_42);
            precioTecnomecanica = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_TECNOMECANICA_17_42);
        }
        gastoCombustible = (double) ((double) distancia / (double) relacionCombustible) * valorCombustible;
        log.log(Level.INFO,
                "combustible : " + gastoCombustible.toString());
        gastoLlantas = (double) ((double) distancia / (double) kmsCambioLlantas) * valorCambioLlantas;
        log.log(Level.INFO,
                "llantas : " + gastoLlantas.toString());
        gastoCambioCorreaEmbrague = (double) ((double) distancia / (double) kmsCambioCorrea) * valorMantenimientoCorrea;
        log.log(Level.INFO,
                "gastoCambioCorreaEmbrague : " + gastoCambioCorreaEmbrague.toString());
        gastoDevaluacionAnual = (double) ((double) distancia / (double) kmsRecorridoAnual) * devaluacionAnual;
        log.log(Level.INFO,
                "gastoDevaluacionAnual : " + gastoDevaluacionAnual.toString());
        gastoCambioAceite = (double) ((double) distancia / (double) kmsCambioAceite) * valorCambioAceite;
        log.log(Level.INFO,
                "gastoCambioAceite : " + gastoCambioAceite.toString());
        gastoMantFrenos = (double) ((double) distancia / (double) kmsMantFrenos) * valorMantenimientoFrenos;
        log.log(Level.INFO,
                "gastoMantFrenos : " + gastoMantFrenos.toString());
        gastoSegurosContraExtra = (double) ((double) distancia / (double) kmsRecorridoAnual) * valorSeguroContraExtra;
        log.log(Level.INFO,
                "gastoSegurosContraExtra : " + gastoSegurosContraExtra.toString());
        gastoSoat = (double) ((double) distancia / (double) kmsRecorridoAnual) * valorSoat;
        log.log(Level.INFO,
                "gastoSoat : " + gastoSoat.toString());
        gastoTecnomecanica = (double) ((double) distancia / (double) kmsRecorridoAnual) * precioTecnomecanica;
        log.log(Level.INFO,
                "gastoTecnomecanica : " + gastoTecnomecanica.toString());

        Double gastosServicio = gastoCombustible + gastoFuec;
        Double gastoVehiculo = gastoLlantas + gastoCambioCorreaEmbrague
                + gastoDevaluacionAnual + gastoCambioAceite + gastoMantFrenos
                + gastoSegurosContraExtra + gastoSoat + gastoTecnomecanica;
        Double total = gastosServicio + gastoVehiculo;

        gastos.setGastosServicio(gastosServicio.toString());
        gastos.setGastosVehiculo(gastoVehiculo.toString());
        gastos.setTotal(total.toString());

        log.log(Level.INFO,
                "Saliendo calcularGastosServicio : gastos" + gastos.toString());
        return gastos;
    }

    private CotizacionDTO calcularPrecios(Integer numPasajeros, Integer distancia, GastosDTO gastos,
            Integer precioPeajes) throws GeneralException {

        Logger.getLogger(Cotizador.class.getName()).log(Level.INFO,
                "Ingresando cotizador. : calcularPrecios " + numPasajeros.toString() + " " + distancia.toString());
        Integer precioPromedioVehiculo = 0;
        Double cantVehiculos = (double) 1;
        CotizacionDTO cotizacion = new CotizacionDTO();
        DecimalDTO decimal;
        if (numPasajeros <= 9) {
            log.log(Level.INFO, "ingreso al 9");
            precioPromedioVehiculo = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_PROMEDIO_VEHICULO_9);
        } else if (numPasajeros > 9 && numPasajeros <= 17) {
            log.log(Level.INFO, "ingreso al 17");
            precioPromedioVehiculo = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_PROMEDIO_VEHICULO_17);
        } else if (numPasajeros > 17 && numPasajeros <= 24) {
            log.log(Level.INFO, "ingreso al 24");
            precioPromedioVehiculo = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_PROMEDIO_VEHICULO_24);
        } else if (numPasajeros > 24 && numPasajeros <= 32) {
            log.log(Level.INFO, "ingreso al 32");
            precioPromedioVehiculo = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_PROMEDIO_VEHICULO_32);
        } else if (numPasajeros > 32 && numPasajeros <= 42) {
            log.log(Level.INFO, "ingreso al 42");
            precioPromedioVehiculo = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_PROMEDIO_VEHICULO_42);
        } else if (numPasajeros > 42) {
            log.log(Level.INFO, "ingreso al mayor a 42");
            precioPromedioVehiculo = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_PRECIO_PROMEDIO_VEHICULO_42);
            cantVehiculos = (double) numPasajeros / 42;
        }
        Integer retornoAnua5 = precioPromedioVehiculo / 4;
        log.log(Level.INFO, "Retorno a 3 anos : " + retornoAnua5.toString());
        Double valorKmA5 = (double) retornoAnua5 / ConstanteLlavesParametroVehiculo.RECORRIDO_ANUAL;
        log.log(Level.INFO, "valor a 3 anos : " + valorKmA5.toString());
        Double valorKmA1 = (double) precioPromedioVehiculo / ConstanteLlavesParametroVehiculo.RECORRIDO_ANUAL;
        Double precioMinimoGanancia = valorKmA5 * distancia;
        log.log(Level.INFO, "Precio minimo : " + precioMinimoGanancia.toString());
        Double sueldoConductorRecomendadoMin = precioMinimoGanancia * 0.3;
        log.log(Level.INFO, "Sueldo conductor : " + sueldoConductorRecomendadoMin.toString());
        Double precioMaximoGanancia = valorKmA1 * distancia;
        log.log(Level.INFO, "Precio maximo : " + precioMaximoGanancia.toString());
        Double sueldoConductorRecomendadoMax = precioMaximoGanancia * 0.3;
        log.log(Level.INFO, "Sueldo conductor : " + sueldoConductorRecomendadoMax.toString());
        Double precioMaximo = Math.ceil(precioMaximoGanancia + sueldoConductorRecomendadoMax
                + Double.valueOf(gastos.getTotal()) + precioPeajes);
        log.log(Level.INFO, "precio total max : " + precioMaximo.toString());
        Double precioMinimo = Math.ceil(precioMinimoGanancia + sueldoConductorRecomendadoMin
                + Double.valueOf(gastos.getTotal()) + precioPeajes);
        log.log(Level.INFO, "precio total min : " + precioMinimo.toString());
        AnalisisCostosDTO peorEscenario = new AnalisisCostosDTO();
        AnalisisCostosDTO mejorEscenario = new AnalisisCostosDTO();
        if (!(cantVehiculos > (double) 1)) {
            log.log(Level.INFO, "ingreso al if");
            peorEscenario.setPrecioOfertado(precioMinimo.toString());
            mejorEscenario.setPrecioOfertado(precioMaximo.toString());
            cotizacion.setPrecioMinimo(peorEscenario);
            cotizacion.setPrecioMaximo(mejorEscenario);
        } else {
            log.log(Level.INFO, "ingreso al else");
            NumberUtils utils = new NumberUtils();

            decimal = utils.fromDecimalToInteger(cantVehiculos);
            Double capacidadVehiculoAdicional = Math.ceil(decimal.getParteDecimal() * 42);
            CotizacionDTO cotizacionVehiculoAdicional = calcularPrecios(capacidadVehiculoAdicional.intValue(),
                    distancia, calcularGastosServicio(distancia, capacidadVehiculoAdicional.intValue()), precioPeajes);
            Double precioMinimoTotal = Math.ceil(precioMinimo * decimal.getParteEntera()
                    + Double.valueOf(cotizacionVehiculoAdicional.getPrecioMinimo().getPrecioOfertado()));
            Double precioMaximoTotal = Math.ceil(precioMaximo * decimal.getParteEntera()
                    + Double.valueOf(cotizacionVehiculoAdicional.getPrecioMaximo().getPrecioOfertado()));
            peorEscenario.setPrecioOfertado(precioMinimoTotal.toString());
            mejorEscenario.setPrecioOfertado(precioMaximoTotal.toString());
            cotizacion.setPrecioMinimo(peorEscenario);
            cotizacion.setPrecioMaximo(mejorEscenario);

        }
        log.log(Level.INFO,
                "Saliendo cotizador. : calcularPrecios, precio minimo " + cotizacion.toString(), cotizacion.toString());

        return cotizacion;
    }

    CostosEscenariosDTO validarPrecioMinimoEscenarios(CostosEscenariosDTO costosEscenario, Integer numPasajeros) throws NumberUtilsExceptions {

        log.log(Level.INFO, "Ingresando de validarPrecioMinimoEscenarios");
        DecimalDTO decimal;
        double cantVehiculos = (double) 1;
        Integer gananciaMinima = obtenerGananciaMinima(numPasajeros);

        if ((double)gananciaMinima > Double.parseDouble(costosEscenario.getCostosPeorEscenario().getGananciaTotal())) {
            Double diferenciaASumar;
            diferenciaASumar = 0.0;
            Double gananciaMinimaCot = Double.parseDouble(costosEscenario.
                    getCostosPeorEscenario().getGananciaTotal());

            diferenciaASumar = Math.ceil((double) gananciaMinima - gananciaMinimaCot);

            Double precioMinimoTotal = Double.parseDouble(costosEscenario.
                    getCostosPeorEscenario().getPrecioOfertado()) + diferenciaASumar;
            Double PrecioMaximoTotal = Double.parseDouble(costosEscenario.getCostosMejorEscenario().getPrecioOfertado())
                    + diferenciaASumar;
            Double gananciaMaximoTotal = Double.parseDouble(costosEscenario.getCostosMejorEscenario().getGananciaTotal())
                    + diferenciaASumar;
            AnalisisCostosDTO peorEscenario = costosEscenario.getCostosPeorEscenario();
            peorEscenario.setPrecioOfertado(precioMinimoTotal.toString());
            peorEscenario.setGananciaTotal(gananciaMinima.toString());
            AnalisisCostosDTO mejorEscenario = costosEscenario.getCostosMejorEscenario();
            mejorEscenario.setPrecioOfertado(PrecioMaximoTotal.toString());
            mejorEscenario.setGananciaTotal(gananciaMaximoTotal.toString());
            costosEscenario.setCostosMejorEscenario(mejorEscenario);
            costosEscenario.setCostosPeorEscenario(peorEscenario);

        }
        log.log(Level.INFO, "Saliendo de validarPrecioMinimoEscenarios", costosEscenario.toString());
        return costosEscenario;
    }

    Integer obtenerGananciaMinima(Integer numPasajeros) throws NumberUtilsExceptions {

        log.log(Level.INFO, "Ingresando de obtenerGananciaMinima");
        Double cantVehiculos = (double) 1;
        Integer gananciaMinima = 0;
        if (numPasajeros <= 9) {
            log.log(Level.INFO, "ingreso al 9");
            gananciaMinima = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_GANANCIA_MINIMA_9);
        } else if (numPasajeros > 9 && numPasajeros <= 17) {
            log.log(Level.INFO, "ingreso al 17");
            gananciaMinima = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_GANANCIA_MINIMA_17);
        } else if (numPasajeros > 17 && numPasajeros <= 24) {
            log.log(Level.INFO, "ingreso al 24");
            gananciaMinima = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_GANANCIA_MINIMA_24);
        } else if (numPasajeros > 24 && numPasajeros <= 32) {
            log.log(Level.INFO, "ingreso al 32");
            gananciaMinima = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_GANANCIA_MINIMA_32);
        } else if (numPasajeros > 32 && numPasajeros <= 42) {
            log.log(Level.INFO, "ingreso al 42");
            gananciaMinima = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_GANANCIA_MINIMA_42);
        } else if (numPasajeros > 42) {
            log.log(Level.INFO, "ingreso al mayor a 42");
            gananciaMinima = obtenerValorParametro(
                    ConstanteLlavesParametroVehiculo.LLAVE_GANANCIA_MINIMA_42);
            cantVehiculos = (double) numPasajeros / 42;
        }

        if ((cantVehiculos > (double) 1)) {
            DecimalDTO decimal;
            NumberUtils utils = new NumberUtils();

            decimal = utils.fromDecimalToInteger(cantVehiculos);
            Double capacidadVehiculoAdicional = Math.ceil(decimal.getParteDecimal() * 42);
            Integer gananciaMinimaCapacidadAdicional = obtenerGananciaMinima(capacidadVehiculoAdicional.intValue());
            gananciaMinima = (decimal.getParteEntera()) * gananciaMinima + gananciaMinimaCapacidadAdicional;

        }
        log.log(Level.INFO, "Saliendo de obtenerGananciaMinima gananciaMinima : ", gananciaMinima);
        return gananciaMinima;

    }

}
