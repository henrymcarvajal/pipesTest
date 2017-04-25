/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.mail.MessageCreator;
import com.ayax.website.mail.Messenger;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.TransportadorFacade;
import com.ayax.website.util.Cryptographer;
import java.util.Date;
import java.util.List;
import spark.Request;
import spark.Response;

/**
 *
 * @author Mauris
 */
public class AdminTransportador {

    public AdminTransportador() {
    }

    public Respuesta crearTransportador(Request req, Response res) {
        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("transportador");
        respuesta.setVerbo("POST");

        String nombres = req.queryParams("nombres");
        String apellidos = req.queryParams("apellidos");
        String buzonElectronico = req.queryParams("email");
        String numeroContacto = req.queryParams("numero-contacto");
        String password = req.queryParams("password");
        
        buzonElectronico = buzonElectronico.toLowerCase();

        TransportadorFacade tf = new TransportadorFacade();
        List<Transportador> lista = tf.buscarPorBuzonElectronicoONumeroContacto(buzonElectronico, numeroContacto);

        if (lista.isEmpty()) {

            String md5Password = Cryptographer.md5Hash(password, buzonElectronico);
            try {
                Transportador transportador = new Transportador();
                transportador.setId(java.util.UUID.randomUUID().toString());
                transportador.setBuzonElectronico(buzonElectronico);
                transportador.setNombres(nombres);
                transportador.setApellidos(apellidos);
                transportador.setContrasena(md5Password);
                transportador.setNumeroContacto(Long.parseLong(numeroContacto));
                transportador.setCredito(0);
                transportador.setFechaCreacion(new Date(System.currentTimeMillis()));
                transportador.setEstadoRegistro(Transportador.ESTADO_REGISTRO_INCOMPLETO);

                if(!tf.crear(transportador)){
                    
                    respuesta.setCodigo("001");
                    respuesta.setResultado("no se ha creado el transportador");
                    return respuesta;
                }
                    

                MessageCreator mc = new MessageCreator();
                Messenger mess = new Messenger();
                mess.sendMail("Ayax.co - Registro de Transportador Nuevo", mc.crearMensajeTransportadorNuevo(transportador), 
                        new String[]{transportador.getBuzonElectronico()});

                respuesta.setResultado("exito");
                respuesta.setCodigo("000");

            } catch (Exception exception) {
                respuesta.setCodigo("001");
                respuesta.setResultado(exception.toString());
            }
        } else {
            respuesta.setResultado("buzon electronico o contacto ya registrado");
            respuesta.setCodigo("002");
        }
        return respuesta;
    }

    public Respuesta actualizarReputacionTransportador(Request req, Response res) {
        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("transportador");
        respuesta.setVerbo("POST");
        
        String idOferta = req.params("id");
        TransportadorFacade of = new TransportadorFacade();
        Transportador transportador = of.buscarPorId(idOferta);
        if (transportador != null) {

            respuesta.setCodigo("000");
            respuesta.setResultado("Exito");
        } else {
            respuesta.setCodigo("001");
            respuesta.setResultado("");
        }
        return respuesta;
    }
    
    public static void actualizarUsuario(Request req, Transportador transp ){
        req.session().attribute("usuario",transp);
    }
    
    public static Transportador obtenerUsuarioSesion(Request req){
        
        return (Transportador) req.session().attribute("usuario");
    }
}
