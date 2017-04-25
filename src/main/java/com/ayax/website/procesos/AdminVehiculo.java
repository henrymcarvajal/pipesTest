/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Vehiculo;
import com.ayax.website.persistencia.fachadas.TransportadorFacade;
import com.ayax.website.persistencia.fachadas.VehiculoFacade;
import com.ayax.website.procesos.util.CapacidadVehiculoDto;
import com.ayax.website.procesos.util.ImageUtils;
import com.ayax.website.procesos.util.UploadUtil;
import com.ayax.website.procesos.util.singleton.OfertaPendienteSingleton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author Mauris
 */
public class AdminVehiculo {

    private final Integer MINIMO_VEHICULO_PEQUENO_CAMIONETA = 1;
    private final Integer MAXIMO_VEHICULO_PEQUENO_CAMIONETA = 7;
    private final Integer MINIMO_VEHICULO_PEQUENO = 8;
    private final Integer MAXIMO_VEHICULO_PEQUENO = 12;
    private final Integer MINIMO_VEHICULO_MEDIANO = 13;
    private final Integer MAXIMO_VEHICULO_MEDIANO = 16;
    private final Integer MINIMO_VEHICULO_MEDIANO_MICROBUS = 17;
    private final Integer MAXIMO_VEHICULO_MEDIANO_MICROBUS = 22;
    private final Integer MINIMO_VEHICULO_MEDIO_GRANDE = 24;
    private final Integer MAXIMO_VEHICULO_MEDIO_GRANDE = 34;
    private final Integer MINIMO_VEHICULO_GRANDE = 35;
    private final Integer MAXIMO_VEHICULO_GRANDE = 50;
    private final Integer MAXIMO_CUPO = 9;
    private final String PLACA="placa";

    public AdminVehiculo() {
    }

    public ModelAndView buscarVehiculo(Request req, Response res) {
        VehiculoFacade vf = new VehiculoFacade();
        Vehiculo vehiculo = vf.buscarPorId(req.params("id"));

        Map model = new HashMap();
        model.put("vehiculo", vehiculo);
        model.put("soat", ImageUtils.encodeImage(vehiculo.getSoat()));
        model.put("revisionTecnomecanica", ImageUtils.encodeImage(vehiculo.getRevisionTecnomecanica()));
        model.put("seguroContractual", ImageUtils.encodeImage(vehiculo.getSeguroContractual()));
        model.put("seguroExtracontractual", ImageUtils.encodeImage(vehiculo.getSeguroExtracontractual()));
        model.put("tarjetaPropiedad", ImageUtils.encodeImage(vehiculo.getTarjetaPropiedad()));
        model.put("fotoVehiculo", ImageUtils.encodeImage(vehiculo.getFotoVehiculo()));
        String viewName = "/vehiculo.ftl";
        ModelAndView mv = new ModelAndView(model, viewName);
        return mv;
    }

    public Respuesta crearVehiculo(Request req, Response res) {
        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("vehiculo");
        respuesta.setVerbo("POST");

        String idTransportador = req.queryParams("idTransportador");
        String placa = req.queryParams("placa");
        String numeroPasajeros = req.queryParams("numeroPasajeros");
        String ciudad = req.queryParams("ciudad");
        String marca = req.queryParams("marca");
        String modelo = req.queryParams("modelo");
        String acondicionado = req.queryParams("aireAcondicionado");
        String cedulaTransportador = req.queryParams("cedula");

        TransportadorFacade tf = new TransportadorFacade();
        Transportador transportador = tf.buscarPorId(idTransportador);
        if (transportador != null) {

            VehiculoFacade vf = new VehiculoFacade();
//
//            CapacidadVehiculoDto tipoVehiculo = clasificarPorCapacidad(numeroPasajeros);
//            List vehiculosPorCapacidad = vf.buscarPorNumeroPasajeros(tipoVehiculo.getCapacidadMinima(),
//                    tipoVehiculo.getCapacidadMaxima());
//            Logger.getLogger(AdminVehiculo.class.getName()).log(Level.INFO,
//                    "cantidad de vehiculos de esa clase : " + vehiculosPorCapacidad.size());

//            if (vehiculosPorCapacidad.size() > MAXIMO_CUPO) {
//
//                respuesta.setResultado("Se ha superado numero de vehiculos permitidos");
//                respuesta.setCodigo("004");
//                transportador.setEstadoRegistro(Transportador.ESTADO_REGISTRO_EN_ESPERA);
//                if (!tf.actualizar(transportador)) {
//                    Logger.getLogger(AdminVehiculo.class.getName()).log(Level.SEVERE,
//                            "no ha sido posible actualizar el transportador");
//                }
//                return respuesta;
//            }
            placa = placa.replaceAll("\\s+","").toUpperCase();
            Vehiculo vehiculo = vf.buscarPorPlaca(placa);

            transportador.setNumeroIdentificacion(new Long(cedulaTransportador));
            transportador.setEstadoRegistro(Transportador.ESTADO_REGISTRO_EXITOSO);
            tf.actualizar(transportador);
            AdminTransportador.actualizarUsuario(req, transportador);
            if (vehiculo == null) {
                try {
                    vehiculo = new Vehiculo();
                    vehiculo.setId(java.util.UUID.randomUUID().toString());
                    vehiculo.setPlaca(placa);
                    vehiculo.setNumeroPasajeros(numeroPasajeros);
                    vehiculo.setCiudad(ciudad);
                    vehiculo.setMarca(marca);
                    vehiculo.setModelo(modelo);
                    vehiculo.setAcondicionado(acondicionado != null && acondicionado.equalsIgnoreCase("si"));
                    vehiculo.setTransportador(transportador);
                    vehiculo.setFechaCreacion(new Date(System.currentTimeMillis()));

                    vf.crear(vehiculo);

                    Respuesta respuestaOferta=null;
                    
                    if(OfertaPendienteSingleton.getInstance().getIdServicio()!=null){
                        AdminOferta ofertaPendiente = new AdminOferta();
                        respuestaOferta=ofertaPendiente.crearOferta(req, res);
                    }
                    if(respuestaOferta!=null && ("000").equals(respuestaOferta.getCodigo())){
                         respuesta.setResultado("exito");
                         respuesta.setCodigo("004");
                         respuesta.setResultado("Se ha creado vehiculo y oferta pendiente de forma exitosa");
                         respuesta.setValor(vehiculo.getId());
                         OfertaPendienteSingleton.getInstance().setIdServicio(null);
                         OfertaPendienteSingleton.getInstance().setValorOferta(null);
                    }else if(respuestaOferta!=null && !("000").equals(respuestaOferta.getCodigo())){
                         respuesta.setResultado("exito");
                         respuesta.setCodigo("005");
                         respuesta.setResultado("Se ha creado vehiculo, pero la oferta pendiente no ha sido enviada.");
                         respuesta.setValor(vehiculo.getId());
                         OfertaPendienteSingleton.getInstance().setIdServicio(null);
                         OfertaPendienteSingleton.getInstance().setValorOferta(null);
                    } else if (respuestaOferta == null) {
                        respuesta.setResultado("exito");
                        respuesta.setCodigo("000");
                        respuesta.setResultado("Se ha creado vehiculo");
                        respuesta.setValor(vehiculo.getId());
                    }

                } catch (Exception exception) {
                    respuesta.setCodigo("003");
                    respuesta.setResultado(exception.toString());
                }
            } else {
                respuesta.setResultado("Placa [" + placa + "] ya registrada");
                respuesta.setCodigo("002");
            }
        } else {
            respuesta.setResultado("Transportador [" + idTransportador + "] no existe");
            respuesta.setCodigo("001");
        }
        return respuesta;
    }

    public Respuesta actualizarDocumentos(Request req, Response res) {

        String idVehiculo = req.params(":id");
        boolean esPlaca= idVehiculo.substring(0, 5).equalsIgnoreCase(PLACA);
        System.out.println(" id: "+idVehiculo+"0 a 4 "+idVehiculo.substring(0, 5)
        +" 5 en adelante "+idVehiculo.substring(5)+" ES PLACA: "+esPlaca);
        Respuesta respuesta = new Respuesta();
        List<String> docs = new ArrayList();
//        docs.add("fSoat");
//        docs.add("fTecno");
//        docs.add("fScontrac");
//        docs.add("fSecontrac");
//        docs.add("Tpropiedad");
        docs.add("Fvehiculo");
//        docs.add("fToperacion");

        try {
            UploadUtil util = new UploadUtil();
            Vehiculo v = null;
            HashMap<String, HashMap> multipart = util.getParts(req, null, docs);
            HashMap<String, byte[]> files = multipart.get("files");
            VehiculoFacade vf = new VehiculoFacade();
            if (!esPlaca) {
                v = vf.buscarPorId(idVehiculo);
            } else {
                v = vf.buscarPorPlaca(idVehiculo.substring(5));
            }
            if (v != null) {
//            v.setSoat(files.get("fSoat"));
//            v.setRevisionTecnomecanica(files.get("fTecno"));
//            v.setSeguroContractual(files.get("fScontrac"));
//            v.setSeguroExtracontractual(files.get("fSecontrac"));
//            v.setTarjetaPropiedad(files.get("Tpropiedad"));
                v.setFotoVehiculo(files.get("Fvehiculo"));
//            v.setTarjetaOperacion(files.get("fToperacion"));
                if (vf.actualizar(v)) {
                    respuesta.setCodigo("000");
                    respuesta.setResultado("exito");
                } else {
                    respuesta.setCodigo("001");
                    respuesta.setResultado("error");
                }
            } else {
                respuesta.setCodigo("002");
                return respuesta;
            }
        } catch (Exception e) {
            Logger.getLogger(AdminVehiculo.class.getName()).log(Level.SEVERE, null, e);
            respuesta.setCodigo("001");
            respuesta.setResultado("error");
        }
        respuesta.setRecurso("vehiculo/" + idVehiculo + "/documentos");
        respuesta.setVerbo("POST");

        return respuesta;
    }

    public Respuesta actualizarFechaDocumentosVehiculo(Request req, Response res) {
        String placa = req.params(":placa");
        Respuesta respuesta = new Respuesta();
        try {
            VehiculoFacade vf = new VehiculoFacade();
            Vehiculo v = vf.buscarPorPlaca(placa);

            String fechaVencimientoSoat = req.queryParams("fechaVencimientoSoat");
            System.out.println("fecha vencimiento Soat : " + fechaVencimientoSoat + " placa : " + placa);
            String fechaVencimientoTecnomecanica = req.queryParams("fechaVencimientoTecnomecanica");
            String fechaVencimientoScontractual = req.queryParams("fechaVencimientoScontractual");
            String fechaVencimientoSecontractual = req.queryParams("fechaVencimientoSecontractual");
            String fechaVencimientoToperacion = req.queryParams("fechaVencimientoToperacion");

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaVencimiento_soat = new Date();
            Date fechaVencimiento_tecnomecanica = new Date();
            Date fechaVencimiento_scontractual = new Date();
            Date fechaVencimiento_secontractual = new Date();
            Date fechaVencimiento_toperacion = new Date();
            try {
                fechaVencimiento_soat = formatter.parse(fechaVencimientoSoat);
                fechaVencimiento_tecnomecanica = formatter.parse(fechaVencimientoTecnomecanica);
                fechaVencimiento_scontractual = formatter.parse(fechaVencimientoScontractual);
                fechaVencimiento_secontractual = formatter.parse(fechaVencimientoSecontractual);
                fechaVencimiento_toperacion = formatter.parse(fechaVencimientoToperacion);

            } catch (ParseException ex) {
                Logger.getLogger(AdminVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
            v.setFechaVencimientoSoat(fechaVencimiento_soat);
            v.setFechaVencimientoTecnomecanica(fechaVencimiento_tecnomecanica);
            v.setFechaVencimientoScontractual(fechaVencimiento_scontractual);
            v.setFechaVencimientoSecontractual(fechaVencimiento_secontractual);
            v.setFechaVencimientoToperacion(fechaVencimiento_toperacion);

            if (vf.actualizar(v)) {
                respuesta.setCodigo("000");
                respuesta.setResultado("exito");
            } else {
                respuesta.setCodigo("001");
                respuesta.setResultado("error");
            }

        } catch (Exception e) {
            Logger.getLogger(AdminVehiculo.class.getName()).log(Level.SEVERE, null, e);
            respuesta.setCodigo("001");
            respuesta.setResultado("error");
        }
        return respuesta;
    }

    private CapacidadVehiculoDto clasificarPorCapacidad(String numeroPasajeros) {

        Integer capacidad = Integer.valueOf(numeroPasajeros);
        CapacidadVehiculoDto capacidadV = new CapacidadVehiculoDto();

        if (capacidad >= MINIMO_VEHICULO_PEQUENO_CAMIONETA && capacidad <= MAXIMO_VEHICULO_PEQUENO_CAMIONETA) {
            capacidadV.setCapacidadMinima(MINIMO_VEHICULO_PEQUENO_CAMIONETA.toString());
            capacidadV.setCapacidadMaxima(MAXIMO_VEHICULO_PEQUENO_CAMIONETA.toString());

        } else if (capacidad >= MINIMO_VEHICULO_PEQUENO && capacidad <= MAXIMO_VEHICULO_PEQUENO) {
            capacidadV.setCapacidadMinima(MINIMO_VEHICULO_PEQUENO.toString());
            capacidadV.setCapacidadMaxima(MAXIMO_VEHICULO_PEQUENO.toString());
        
        } else if (capacidad >= MINIMO_VEHICULO_MEDIANO_MICROBUS && capacidad <= MAXIMO_VEHICULO_MEDIANO_MICROBUS) {
            capacidadV.setCapacidadMinima(MINIMO_VEHICULO_MEDIANO_MICROBUS.toString());
            capacidadV.setCapacidadMaxima(MAXIMO_VEHICULO_MEDIANO_MICROBUS.toString());
        
        } else if (capacidad >= MINIMO_VEHICULO_MEDIANO && capacidad <= MAXIMO_VEHICULO_MEDIANO) {
            capacidadV.setCapacidadMinima(MINIMO_VEHICULO_MEDIANO.toString());
            capacidadV.setCapacidadMaxima(MAXIMO_VEHICULO_MEDIANO.toString());
        
        } else if (capacidad >= MINIMO_VEHICULO_MEDIO_GRANDE && capacidad <= MAXIMO_VEHICULO_MEDIO_GRANDE) {
            capacidadV.setCapacidadMinima(MINIMO_VEHICULO_MEDIO_GRANDE.toString());
            capacidadV.setCapacidadMaxima(MAXIMO_VEHICULO_MEDIO_GRANDE.toString());
        
        } else if (capacidad >= MINIMO_VEHICULO_GRANDE && capacidad <= MAXIMO_VEHICULO_GRANDE) {
            capacidadV.setCapacidadMinima(MINIMO_VEHICULO_GRANDE.toString());
            capacidadV.setCapacidadMaxima(MAXIMO_VEHICULO_GRANDE.toString());
        }
        Logger.getLogger(AdminVehiculo.class.getName()).log(Level.INFO, "minimo : "
                + capacidadV.getCapacidadMinima());
        Logger.getLogger(AdminVehiculo.class.getName()).log(Level.INFO, "maximo : "
                + capacidadV.getCapacidadMaxima());
        return capacidadV;
    }
}