/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.exceptions.GeneralException;
import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.controladores.AnalisisCostosJpaController;
import com.ayax.website.persistencia.controladores.CotizacionSimpleJpaController;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.dto.CostosEscenariosDTO;
import com.ayax.website.persistencia.dto.CotizacionDTO;
import com.ayax.website.persistencia.entidades.AnalisisCostos;
import com.ayax.website.persistencia.entidades.CotizacionSimple;
import com.ayax.website.procesos.util.Cotizador;
import com.ayax.website.procesos.util.NumberUtils;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import spark.Request;
import spark.Response;

/**
 *
 * @author jespinosa
 */
public class AdminCotizacion {

    private final static Logger log = Logger.getLogger(AdminCotizacion.class.getName());

    public Respuesta obtenerCotizacion(Request req, Response res) {

        Respuesta respuesta = new Respuesta();
        try {
            NumberUtils utils = new NumberUtils();
            log.log(Level.INFO, "Ingresando AdminCotizacion.obtenerCotizacion");
            String ubicacionOrigen = req.queryParams("ubicacion-origen");
            String ubicacionDestino = req.queryParams("ubicacion-destino");
            String correo = req.queryParams("correo");
            Integer distancia = utils.parseDecimal(req.queryParams("distancia")).intValue();
            Integer numeroPasajeros = Integer.valueOf(req.queryParams("numero-pasajeros"));
            Integer precioPeajes = Integer.valueOf(req.queryParams("precio-peajes"));
            log.log(Level.INFO, "Request: Ubicacion-origen: " + ubicacionOrigen
                    + " ubicacion-destino: " + ubicacionDestino + " correo : " + correo
                    + " distancia: " + distancia.toString() + " precio peajes : " + precioPeajes
                    + " numero pasajeros: " + numeroPasajeros);
            CotizacionDTO infoCotizacion = new CotizacionDTO();
            infoCotizacion.setCorreo(correo);
            infoCotizacion.setDistancia(distancia);
            infoCotizacion.setNumeroPasajeros(numeroPasajeros);
            infoCotizacion.setUbicacionOrigen(ubicacionOrigen);
            infoCotizacion.setUbcacionDestino(ubicacionDestino);

            CotizacionSimple contizacionFinal = calcularIntervaloPrecios(infoCotizacion, precioPeajes);
            respuesta.setCodigo("000");
            respuesta.setResultado("cotizacion realizada correctamente");
            respuesta.setValor(contizacionFinal);
        } catch (PreexistingEntityException e) {
            log.log(Level.SEVERE, "Error  en BD", e);
            respuesta.setCodigo("001");
            respuesta.setResultado("ha ocurrido un error" + e.getMessage());
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            respuesta.setCodigo("001");
            respuesta.setResultado("ha ocurrido un error" + e.getMessage());

        }
        log.log(Level.INFO, "Saliendo AdminCotizacion.obtenerCotizacion", respuesta.toString());
        return respuesta;
    }

    public CotizacionSimple calcularIntervaloPrecios(CotizacionDTO cotizacion, Integer precioPeajes)
            throws GeneralException, Exception {

        log.log(Level.INFO, "Ingresando AdminCotizacion.calcularIntervaloPrecios");
        Cotizador cotizador = new Cotizador();
        CostosEscenariosDTO costoEscenarios = cotizador.obtenerCotizacionSolicitud(cotizacion.getDistancia(), precioPeajes,
                cotizacion.getNumeroPasajeros());
        CotizacionSimple cotizacionFinal = new CotizacionSimple();
        AnalisisCostos analisisCostosPeorEscenario = new AnalisisCostos();
        AnalisisCostos analisisCostosMejorEscenario = new AnalisisCostos();
        Double ahorroVehiculoP = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosPeorEscenario().getAhorroVehiculo()));
        Double gananciaP = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosPeorEscenario().getGananciaTotal()));
        Double precioP = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosPeorEscenario().getPrecioOfertado()));
        Double salarioConductorP = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosPeorEscenario().getSueldoConductor()));
        Double ahorroVehiculoM = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosMejorEscenario().getAhorroVehiculo()));
        Double gananciaM = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosMejorEscenario().getGananciaTotal()));
        Double precioM = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosMejorEscenario().getPrecioOfertado()));
        Double salarioConductorM = Math.ceil(Double.
                valueOf(costoEscenarios.getCostosMejorEscenario().getSueldoConductor()));
        analisisCostosPeorEscenario.setAhorroVehiculo(ahorroVehiculoP.intValue());
        analisisCostosPeorEscenario.setGanancia(gananciaP.intValue());
        analisisCostosPeorEscenario.setPrecio(precioP.intValue());
        analisisCostosPeorEscenario.setSalarioConductor(salarioConductorP.intValue());
        analisisCostosPeorEscenario.setId(java.util.UUID.randomUUID().toString());
        analisisCostosMejorEscenario.setAhorroVehiculo(ahorroVehiculoM.intValue());
        analisisCostosMejorEscenario.setGanancia(gananciaM.intValue());
        analisisCostosMejorEscenario.setPrecio(precioM.intValue());
        analisisCostosMejorEscenario.setSalarioConductor(salarioConductorM.intValue());
        analisisCostosMejorEscenario.setId(java.util.UUID.randomUUID().toString());

        cotizacionFinal.setUbicacionOrigen(cotizacion.getUbicacionOrigen());
        cotizacionFinal.setUbicacionDestino(cotizacion.getUbcacionDestino());
        cotizacionFinal.setAnalisisCostosPeorEscenario(analisisCostosPeorEscenario);
        cotizacionFinal.setAnalisisCostosMejorEscenario(analisisCostosMejorEscenario);
        cotizacionFinal.setDistancia(cotizacion.getDistancia().toString());
        cotizacionFinal.setId(java.util.UUID.randomUUID().toString());
        cotizacionFinal.setCorreo(cotizacion.getCorreo());
        cotizacionFinal.setNumeroPasajeros(cotizacion.getNumeroPasajeros());
        almacenarCotizacion(cotizacionFinal, analisisCostosPeorEscenario, analisisCostosMejorEscenario);
        log.log(Level.INFO, "Saliendo  AdminCotizacion.calcularIntervaloPrecios cotizacion : "
                + cotizacionFinal.toString(), cotizacionFinal.toString());
        //todo eliminar columnas precio minimo y maximo de cotizacionFinal simple, arreglar query para paso de ambiente, 
        //todo crear plantilla y logica de envio de correo, conectar pagina de cotizacionFinal con backend, proceder con el paso posterior a la cotizacionFinal
        //todo para crear solicitud que notifique a transportadores, evaluar formas de enrutar usuario, y posibles casos de tipo de servicio
        return cotizacionFinal;
    }

    private void almacenarCotizacion(CotizacionSimple cotizacion, AnalisisCostos peorEscenario, AnalisisCostos mejorEscenario) throws Exception {

        CotizacionSimpleJpaController cotizacionController = new CotizacionSimpleJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        AnalisisCostosJpaController analisisController = new AnalisisCostosJpaController(EntityManagerFactoryBuilder.INSTANCE.build());

        analisisController.create(peorEscenario);
        analisisController.create(mejorEscenario);
        cotizacionController.create(cotizacion);
    }

    public CotizacionSimple obtenerCotizacionSimple(String id) {
        CotizacionSimpleJpaController sc = new CotizacionSimpleJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        TypedQuery q = em.createNamedQuery("Cotizacion.findById", CotizacionSimple.class);
        q.setParameter("id", id);

        CotizacionSimple cotizacion = null;
        try {
            cotizacion = (CotizacionSimple) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return cotizacion;
    }
}
