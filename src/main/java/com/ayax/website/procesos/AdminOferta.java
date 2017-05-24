/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.mail.Messenger;
import com.ayax.website.mail.MessageCreator;
import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Vehiculo;
import com.ayax.website.persistencia.fachadas.OfertaFacade;
import com.ayax.website.persistencia.fachadas.ServicioFacade;
import com.ayax.website.persistencia.fachadas.ServicioJpaController;
import com.ayax.website.persistencia.fachadas.TransportadorJpaController;
import com.ayax.website.persistencia.fachadas.UsuarioJpaController;
import com.ayax.website.persistencia.fachadas.exceptions.NonexistentEntityException;
import com.ayax.website.procesos.util.singleton.OfertaPendienteSingleton;
import com.ayax.website.util.Util;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminOferta {

    public AdminOferta() {
    }

    public Respuesta crearOferta(Request req, Response res) {

        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("oferta");
        respuesta.setVerbo("POST");
        
        String idServicio=null;
        String valor=null;
        
        if (!"".equals(req.queryParams("id-servicio")) && req.queryParams("id-servicio")!=null ) {
            
            idServicio = req.queryParams("id-servicio");
            valor = req.queryParams("valor");
            Logger.getLogger(AdminVehiculo.class.getName()).log(Level.INFO, "Oferta normal idServicio : ",idServicio);
        } else if (OfertaPendienteSingleton.getInstance().getIdServicio() != null) {
            
            idServicio=OfertaPendienteSingleton.getInstance().getIdServicio();
            valor = OfertaPendienteSingleton.getInstance().getValorOferta();
            Logger.getLogger(AdminVehiculo.class.getName()).log(Level.INFO, "Oferta pendiente idServicio : ",idServicio);
        }
        
        Transportador transportador = AdminTransportador.obtenerUsuarioSesion(req);
        if (transportador != null && transportador.getNumeroIdentificacion() != null
                && Transportador.ESTADO_REGISTRO_EXITOSO.equalsIgnoreCase(transportador.getEstadoRegistro()) 
                && idServicio!=null) {

            OfertaFacade of = new OfertaFacade();
            Oferta oferta = of.buscarPorServicioTransportador(idServicio, transportador.getId());
            if (oferta == null) {

                ServicioFacade sf = new ServicioFacade();
                Servicio servicio = sf.buscarPorId(idServicio);
                if (servicio != null) {
                    oferta = new Oferta();
                    oferta.setId(java.util.UUID.randomUUID().toString());
                    oferta.setServicio(servicio);
                    oferta.setTransportador(transportador);
                    if (!"".equals(valor) && valor != null) {

                        oferta.setValor(calcularValorOferta(transportador, Integer.parseInt(valor)));
                    } else {
                        oferta.setValor(0);
                    }
                    oferta.setFecha(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

//                        if (oferta.getComision() < transportador.getCredito() || transportador.getServiciosAtendidos() == 0) {
                    if (of.crear(oferta)) {
                        respuesta.setCodigo("000");
                        respuesta.setResultado("exito");

                        MessageCreator mc = new MessageCreator();

                        Messenger mess = new Messenger();
                        mess.sendMail("Oferta de Servicio Especial en Ayax.co", mc.crearMensajeOfertaNueva(oferta), new String[]{oferta.getServicio().getUsuario().getBuzonElectronico()});
                    } else {
                        respuesta.setCodigo("003");
                        respuesta.setResultado("error de BD: ");
                    }
//                        } else {
//                            respuesta.setCodigo("001");
//                            respuesta.setResultado("Transportador (" + transportador.getBuzonElectronico() + ") sin credito");
//                        }
                } else {
                    respuesta.setCodigo("002");
                    respuesta.setResultado("No se encontro servicio con el id especificado [" + idServicio + "]");
                }
            } else {
                respuesta.setCodigo("004");
                respuesta.setResultado("Transportador (" + transportador.getBuzonElectronico() + ") ya ha ofertado para este servicio [" + transportador.getId() + "]");
            }

        } else if (transportador != null && Transportador.ESTADO_REGISTRO_INCOMPLETO.
                equalsIgnoreCase(transportador.getEstadoRegistro())) {

            OfertaPendienteSingleton.getInstance().setIdServicio(idServicio);
            OfertaPendienteSingleton.getInstance().setValorOferta(valor);
            respuesta.setCodigo("001");
            respuesta.setResultado("No se ha subido la informacion del vehiculo");
            respuesta.setValor(transportador.getId());

        } else {
            respuesta.setCodigo("005");
            respuesta.setResultado("Usuario no logueado o no registrado");
        }
        System.out.println("respuesta codigo: "+respuesta.getCodigo());
        return respuesta;
    }

    public Respuesta actualizarOferta(Request req, Response res) {
        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("oferta/id/transportador");
        respuesta.setVerbo("PUT");

        String idOferta = req.params("id");
        OfertaFacade of = new OfertaFacade();
        Oferta oferta = of.buscarPorId(idOferta);
        if (oferta != null) {
            System.out.println(oferta.getServicio().getHoraSalida().toString());
            System.out.println(oferta.getServicio().getHoraLlegada().toString());
            System.out.println("-----------------------------");
            System.out.println(oferta.getServicio().getHoraSalida().after(new Date()));
            System.out.println(oferta.getServicio().getHoraLlegada().after(new Date()));
            if (oferta.getServicio().esActivo()) {
                oferta.setAceptada(Boolean.TRUE);
                of.actualizar(oferta);
                MessageCreator mc = new MessageCreator();

                Messenger mess = new Messenger();
                mess.sendMail("Tu oferta ha sido aceptada en Ayax.co", mc.crearMensajeOfertaAceptada(oferta), new String[]{oferta.getTransportador().getBuzonElectronico()});
                respuesta.setCodigo("000");
                respuesta.setResultado("Exito");
            } else {
                respuesta.setCodigo("002");
                respuesta.setResultado("El servicio se encuentra inactivo");
            }
        } else {
            respuesta.setCodigo("001");
            respuesta.setResultado("la oferta especificada no existe [" + idOferta + "]");
        }
        return respuesta;
    }

    public Respuesta iniciarServicio(Request req, Response res) {

        Respuesta respuesta = new Respuesta();
        respuesta.setRecurso("oferta/id/inicio");
        respuesta.setVerbo("PUT");

        String idOferta = req.params("id");
        OfertaFacade of = new OfertaFacade();
        Oferta oferta = of.buscarPorId(idOferta);
        if (oferta != null) {

            Collection<Vehiculo> veh = oferta.getTransportador().getVehiculos();
            Object[] lveh = veh.toArray();
            Vehiculo v = (Vehiculo) lveh[0];

            if (v.esVehiculoRevisado()) {

                res.redirect("/informativo.html?codigo=000&mensaje=Tu solicitud ha sido procesada");
                oferta.getServicio().setFechaEjecucion(new Date(System.currentTimeMillis()));
                ServicioFacade sf = new ServicioFacade();
                sf.actualizar(oferta.getServicio());

                MessageCreator mc = new MessageCreator();

                Messenger mess = new Messenger();
                mess.sendMail("Datos de cliente para Servicio Especial en Ayax.co", mc.crearMensajeServicioIniciado(oferta.getServicio()), new String[]{oferta.getTransportador().getBuzonElectronico()});

                mess.sendMail("Datos de tu transportador de Servicio Especial en Ayax.co", mc.crearMensajeServicioTerminado(oferta.getServicio(), oferta.getTransportador()), new String[]{oferta.getServicio().getUsuario().getBuzonElectronico()});

                respuesta.setCodigo("000");
                respuesta.setResultado("Exito");
            } else {
                res.redirect("/informativo.html?codigo=001&mensaje=Tu vehiculo "
                        + "aun no ha sido autorizado. Debes enviar el SOAT, la revision, "
                        + "seguros contractual y extracontractual al numero: 322 8804995, "
                        + "una vez tu vehiculo sea autorizador puedes atender el servicio "
                        + "para el que fuiste seleccionado.");
                respuesta.setCodigo("002");
                respuesta.setResultado("Sube los documentos de tu vehiculo");
            }
        } else {
            respuesta.setCodigo("001");
            respuesta.setResultado("oferta no existe");
        }
        return respuesta;
    }

    public Respuesta obtenerOferta(Request req, Response res) {
        OfertaFacade of = new OfertaFacade();
        Respuesta respuesta = new Respuesta();

        String idOferta = req.params("id");
        Oferta oferta = of.buscarPorId(idOferta);
        if (oferta != null) {
            respuesta.setCodigo("000");
            respuesta.setResultado("exito");
            respuesta.setValor(oferta.toDTO());
        } else {
            respuesta.setCodigo("001");
            respuesta.setResultado("La oferta especificada no existe [" + idOferta + "]");
        }

        return respuesta;
    }

    private Integer calcularValorOferta(Transportador transportadorOfertante, int valorTransportador) {

        Integer valorOfertaConComision = 0;
        if (transportadorOfertante.getServiciosAtendidos() != 0) {
            Integer valorPorcentajeComision = 0;

            if (valorTransportador <= 180000) {
                valorPorcentajeComision = (20 * valorTransportador) / 180000;
            } else {
                valorPorcentajeComision = 20;
            }
            valorOfertaConComision = valorTransportador + ((valorTransportador * valorPorcentajeComision) / 100);

        } else {

            return valorTransportador;
        }
        return valorOfertaConComision;
    }

    public Respuesta actualizarReputacionTransportador(Request req, Response res) {
        Respuesta respuesta = new Respuesta();
        String idOferta = req.params("id");
        String calificacion = req.queryParams("calificacion");
        Set<String> params = req.queryParams();
        for (String param : params) {
            System.out.println(param);
        }

        System.out.println(idOferta);
        System.out.println(calificacion);

        Oferta oferta = new OfertaFacade().buscarPorId(idOferta);
        if (oferta != null) {
            oferta.getServicio().setCalificacionTransportador(Short.parseShort(calificacion));

            oferta.getTransportador().setServiciosAtendidos((short) (oferta.getTransportador().getServiciosAtendidos() + 1));
            oferta.getTransportador().setCredito((int) (oferta.getComision() - oferta.getTransportador().getCredito()));

            oferta.getServicio().getUsuario().setServiciosCalificados((short) (oferta.getServicio().getUsuario().getServiciosCalificados() + 1));

            try {
                ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
                sc.edit(oferta.getServicio());

                TransportadorJpaController tc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
                tc.edit(oferta.getTransportador());

                UsuarioJpaController uc = new UsuarioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
                uc.edit(oferta.getServicio().getUsuario());

                EntityManager em = tc.getEntityManager();
                Query q = em.createNativeQuery("select avg(s.calificacion_transportador) from l4_servicio s join l4_oferta o on o.id_servicio = s.id join l4_transportador t on t.id = o.id_transportador where t.id = ?idTransportador and s.calificacion_transportador is not null");
                q.setParameter("idTransportador", oferta.getTransportador().getId());

                BigDecimal rep = (BigDecimal) q.getSingleResult();
                oferta.getTransportador().setReputacion(rep);

                tc.edit(oferta.getTransportador());

                respuesta.setCodigo("000");
                respuesta.setResultado("exito");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
                respuesta.setCodigo("001");
                respuesta.setResultado(Util.getExceptionString(ex));
            } catch (Exception ex) {
                Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
                respuesta.setCodigo("002");
                respuesta.setResultado(Util.getExceptionString(ex));
            }
        } else {
            respuesta.setCodigo("003");
            respuesta.setResultado("No se encontro oferta con el id especificado [" + idOferta + "]");
        }

        return respuesta;
    }

    public Object actualizarReputacionUsuario(Request req, Response res) {
        Respuesta respuesta = new Respuesta();
        String idOferta = req.params("id");
        String calificacion = req.queryParams("calificacion");

        Oferta oferta = new OfertaFacade().buscarPorId(idOferta);

        if (oferta != null) {
            oferta.getServicio().setCalificacionUsuario(Short.parseShort(calificacion));

            oferta.getTransportador().setServiciosCalificados((short) (oferta.getTransportador().getServiciosCalificados() + 1));

            try {
                ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
                sc.edit(oferta.getServicio());

                TransportadorJpaController tc = new TransportadorJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
                tc.edit(oferta.getTransportador());

                respuesta.setCodigo("000");
                respuesta.setResultado("exito");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
                respuesta.setCodigo("002");
                respuesta.setResultado(Util.getExceptionString(ex));
            } catch (Exception ex) {
                Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
                respuesta.setCodigo("003");
                respuesta.setResultado(Util.getExceptionString(ex));
            }
        } else {
            respuesta.setCodigo("001");
            respuesta.setResultado("No se encontro oferta con el id especificado [" + idOferta + "]");
        }

        return respuesta;
    }
}
