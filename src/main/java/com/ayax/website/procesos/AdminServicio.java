/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.controladores.ServicioJpaController;
import com.ayax.website.persistencia.controladores.UsuarioJpaController;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.mail.Messenger;
import com.ayax.website.mail.MessageCreator;
import com.ayax.website.persistencia.entidades.Conversacion;
import com.ayax.website.persistencia.entidades.Mensaje;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.fachadas.ServicioFacade;
import com.ayax.website.server.ConfigManager;
import com.ayax.website.server.RouteServer;
import com.ayax.website.util.Util;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminServicio {

    public AdminServicio() {
    }

    private static final String CODIGO_SERVICIO_ESPECIAL = "CESE1A";
    public static final String TIPO_USUARIO_SERVICIOESPECIAL = "1";

    public Respuesta crearServicio(Request req, Response res) {
        Respuesta respuesta = new Respuesta();

        String pick_up = req.queryParams("pick-up");
        String codigo_promocional = req.queryParams("codigop");
        String pickup_location = req.queryParams("pickup-location");

        String drop_off = req.queryParams("drop-off");
        String dropoff_location = req.queryParams("dropoff-location");

        String selected_car = req.queryParams("selected-car");
        String nit = req.queryParams("nit");
        String first_name = req.queryParams("first-name");
        String email_address = req.queryParams("email-address");
        String phone_number = req.queryParams("phone-number");
        String distancia = req.queryParams("distancia");
        String redondo = req.queryParams("idayvueltas");
        String disponibilidad = req.queryParams("con-disponibilidad");
        String detalle = req.queryParams("detalle");
        String codigoAutorizacion = req.queryParams("codigoautorizacion");
        boolean esRegistrado = false;

        DateFormat formatter = new SimpleDateFormat("M/d/y 'at' h:m a");
        Date pick_up_value = new Date();
        try {
            pick_up_value = formatter.parse(pick_up);
        } catch (ParseException ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date drop_off_value = new Date();
        try {
            drop_off_value = formatter.parse(drop_off);
        } catch (ParseException ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        UsuarioJpaController uc = new UsuarioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = uc.getEntityManager();
        Query q;
        if (codigoAutorizacion != null && !"".equals(codigoAutorizacion)) {
            q = em.createNamedQuery("Usuario.findByIdPart");
            q.setParameter("id", codigoAutorizacion);
            esRegistrado = true;
        } else {
            q = em.createNamedQuery("Usuario.findByInformacion");
            q.setParameter("identificacion", BigInteger.valueOf(Long.parseLong(nit)));
            q.setParameter("buzonElectronico", email_address);
        }

        boolean esUsuarioNuevo = false;

        Usuario usuario = null;
        try {
            usuario = (Usuario) q.getSingleResult();
        } catch (NoResultException ex) {
        }

        if (usuario != null && !esRegistrado) {
            respuesta.setRecurso("servicio");
            respuesta.setVerbo("POST");
            respuesta.setCodigo("004");
            respuesta.setResultado("Correo ya registrado.");
            return respuesta;
        }

        if (usuario == null && !esRegistrado) {
            esUsuarioNuevo = true;
            usuario = new Usuario();
            usuario.setId(java.util.UUID.randomUUID().toString());
            usuario.setBuzonElectronico(email_address);
            usuario.setNombre(first_name);
            //usuario.setEmpresa(last_name);
            usuario.setIdentificacion(BigInteger.valueOf(Long.parseLong(nit)));
            usuario.setTelefono(BigInteger.valueOf(Long.parseLong(phone_number)));
            usuario.setFechaCreacion(new Date(System.currentTimeMillis()));
            System.out.println("Codigo promocional : " + codigo_promocional);

            if (CODIGO_SERVICIO_ESPECIAL.equalsIgnoreCase(codigo_promocional)) {
                usuario.setTipoUsuario(TIPO_USUARIO_SERVICIOESPECIAL);
            } else if (!"".equalsIgnoreCase(codigo_promocional) && codigo_promocional != null
                    && !CODIGO_SERVICIO_ESPECIAL.equalsIgnoreCase(codigo_promocional)) {

                Logger.getLogger(AdminServicio.class.getName()).log(Level.INFO, "Codigo promocional incorrecto");
                respuesta.setRecurso("servicio");
                respuesta.setVerbo("POST");
                respuesta.setCodigo("005");
                respuesta.setResultado("El codigo promocional no es correcto.");
                return respuesta;
            }
        } else if (usuario == null && esRegistrado) {

            Logger.getLogger(AdminServicio.class.getName()).log(Level.INFO, "Usuario registrado no existe");
            respuesta.setRecurso("servicio");
            respuesta.setVerbo("POST");
            respuesta.setCodigo("003");
            respuesta.setResultado("El usuario registrado insertado no corresponde.");
            return respuesta;
        } else if (usuario != null && esRegistrado) {
            if (!usuario.getIdentificacion().equals(BigInteger.valueOf(Long.parseLong(nit)))) {
                Logger.getLogger(AdminServicio.class.getName()).log(Level.INFO, "Usuario registrado no existe");
                respuesta.setRecurso("servicio");
                respuesta.setVerbo("POST");
                respuesta.setCodigo("003");
                respuesta.setResultado("El usuario registrado insertado no corresponde.");
                return respuesta;
            }
        }

        Servicio servicio = new Servicio();
        servicio.setId(java.util.UUID.randomUUID().toString());
        servicio.setDestino(dropoff_location);
        servicio.setOrigen(pickup_location);
        servicio.setFechaCreacion(new Date(System.currentTimeMillis()));
        servicio.setDistancia(new BigDecimal(this.parseDecimal(distancia)));
        servicio.setDisponibilidad(disponibilidad != null && disponibilidad.equalsIgnoreCase("true"));
        servicio.setRedondo(redondo != null && redondo.equalsIgnoreCase("true"));
        servicio.setNumeroPasajeros(selected_car);
        servicio.setDetalle(detalle);

        try {
            servicio.setHoraSalida(formatter.parse(pick_up));
        } catch (ParseException ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            servicio.setHoraLlegada(formatter.parse(drop_off));
        } catch (ParseException ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        servicio.setUsuario(usuario);

        String descripcion = "exito", codigo = "000";

        try {
            if (esUsuarioNuevo) {
                uc.create(usuario);
            } else {
                codigo = "001";
                descripcion = "Usuario existente";
            }

            ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            sc.create(servicio);

            MessageCreator mc = new MessageCreator();

            Messenger mess = new Messenger();
            mess.sendMail("Solicitud de Servicio en Ayax.co", mc.crearMensajeServicioNuevo(servicio, ConfigManager.INSTANCE.isTestEnvironment()), new String[]{usuario.getBuzonElectronico(), "lespinosa@ayax.co"});

            AdminSuscripcionPush adminPush = new AdminSuscripcionPush();
            adminPush.notificarSuscripciones(servicio.getId());
        } catch (Exception exception) {
            descripcion = exception.toString();
            descripcion += pick_up_value.toString();
            descripcion += drop_off_value.toString();
        }

        respuesta.setRecurso("servicio");
        respuesta.setVerbo("POST");
        respuesta.setCodigo(codigo);
        respuesta.setResultado(descripcion);
        return respuesta;
    }

    public List<Object[]> obtenerServicios() {
        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        //TypedQuery<Servicio> q = em.createNamedQuery("Servicio.findAllActive", Servicio.class);
        Query q = em.createNamedQuery("Servicio.findAllActive");
        LocalDate date = LocalDate.now();
        java.util.Date uDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        q.setParameter("fechaLimite", uDate, TemporalType.DATE);
        List<Object[]> list = (List<Object[]>) q.getResultList();
        return list;
    }

    public List<Servicio> obtenerTodosServicios() {
        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        Query q = em.createNamedQuery("Servicio.findAll");
        return q.getResultList();
    }

    public Servicio obtenerServicio(String id) {
        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        TypedQuery q = em.createNamedQuery("Servicio.findById", Servicio.class);
        q.setParameter("id", id);

        Servicio servicio = null;
        try {
            servicio = (Servicio) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return servicio;
    }

    public JSONObject obtenerServicioObj(String id) {
        Servicio item = obtenerServicio(id);
        JSONObject jsonServicio = new JSONObject();
        try {
            jsonServicio.put("id", item.getId());
            jsonServicio.put("origen", item.getOrigen());
            jsonServicio.put("destino", item.getDestino());
            jsonServicio.put("horaLlegada", item.getHoraLlegada());
            jsonServicio.put("horaSalida", item.getHoraSalida());
            jsonServicio.put("numeroPasajeros", item.getNumeroPasajeros());
            jsonServicio.put("fechaCreacion", item.getFechaCreacion());
            jsonServicio.put("distancia", item.getDistancia());
            jsonServicio.put("redondo", item.getRedondo());
            jsonServicio.put("descripcion", item.getDetalle());
            jsonServicio.put("disponibilidad", item.getDisponibilidad());
            String servicioGratis = TIPO_USUARIO_SERVICIOESPECIAL.
                    equalsIgnoreCase(item.getUsuario().getTipoUsuario()) ? "1" : null;
            jsonServicio.put("servicioGratis", servicioGratis);

            if (!item.getConversacionCollection().isEmpty()) {
                JSONArray conversaciones = new JSONArray();
                for (Conversacion c : item.getConversacionCollection()) {
                    JSONArray conversacion = new JSONArray();
                    //conversacion.put("conteoMensajes", c.getMensajes().size());
                    for (Mensaje m : c.getMensajeCollection()) {
                        JSONObject mensaje = new JSONObject();
                        mensaje.put("fechaCreacion", m.getFechaCreacion());
                        if (m.getTransportador() != null) {
                            mensaje.put("transportador", m.getTransportador().getNombres());
                        } else {
                            mensaje.put("usuario", m.getUsuario().getNombre());
                        }
                        mensaje.put("texto", m.getTexto());
                        conversacion.put(mensaje);
                    }
                    conversaciones.put(conversacion);
                }
                jsonServicio.put("comentarios", conversaciones);
            }
        } catch (JSONException ex) {
            Logger.getLogger(RouteServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonServicio;
    }

    public Respuesta obtenerServiciosPorTransportador(Transportador transportador) {
        Respuesta respuesta = new Respuesta();
        AdminServicio adminServicio = new AdminServicio();
        List<Object[]> l = adminServicio.obtenerServicios();
        JSONArray array = new JSONArray();

        try {
            if (transportador != null) {
                Map ids = new HashMap();
                l.stream().map((item) -> (Object[]) item).forEach((result) -> {
                    Servicio servicio = (Servicio) result[0];
                    Oferta oferta = (Oferta) result[1];

                    if (!ids.containsKey(servicio)) {
                        if (oferta != null && oferta.getTransportador().getId().equalsIgnoreCase(transportador.getId())) {
                            ids.put(servicio, oferta);
                        } else {
                            ids.put(servicio, null);
                        }
                    } else if (oferta != null && oferta.getTransportador().getId().equalsIgnoreCase(transportador.getId())) {
                        ids.put(servicio, oferta);
                    }
                });

                ids.forEach((Object k, Object v) -> {
                    Servicio servicio = (Servicio) k;
                    Oferta oferta = (Oferta) v;
                    JSONObject obj = new JSONObject();
                    obj.put("id", servicio.getId());
                    obj.put("origen", servicio.getOrigen());
                    obj.put("destino", servicio.getDestino());
                    obj.put("horaLlegada", servicio.getHoraLlegada());
                    obj.put("horaSalida", servicio.getHoraSalida());
                    obj.put("numeroPasajeros", servicio.getNumeroPasajeros());
                    obj.put("fechaCreacion", servicio.getFechaCreacion());
                    obj.put("distancia", servicio.getDistancia());
                    obj.put("redondo", servicio.getRedondo());
                    if (oferta != null) {
                        obj.put("idTransportador", oferta.getTransportador().getId());
                        obj.put("valorOferta", oferta.getValor());
                    }
                    array.put(obj);
                });
            } else {
                Map ids = new HashMap();
                l.stream().map((item) -> (Object[]) item).forEach((result) -> {
                    Servicio servicio = (Servicio) result[0];
                    if (!ids.containsKey(servicio.getId())) {
                        ids.put(servicio.getId(), servicio);
                    }
                });

                ids.forEach((Object k, Object v) -> {
                    Servicio servicio = (Servicio) v;
                    JSONObject obj = new JSONObject();
                    obj.put("id", k);
                    obj.put("origen", servicio.getOrigen());
                    obj.put("destino", servicio.getDestino());
                    obj.put("horaLlegada", servicio.getHoraLlegada());
                    obj.put("horaSalida", servicio.getHoraSalida());
                    obj.put("numeroPasajeros", servicio.getNumeroPasajeros());
                    obj.put("fechaCreacion", servicio.getFechaCreacion());
                    obj.put("distancia", servicio.getDistancia());
                    obj.put("redondo", servicio.getRedondo());
                    array.put(obj);
                });
            }
        } catch (Exception ex) {
            respuesta.setCodigo("001");
            respuesta.setResultado(Util.getExceptionString(ex));
        }
        respuesta.setCodigo("000");
        respuesta.setResultado("exito");
        respuesta.setValor(array);
        return respuesta;
    }

    private Double parseDecimal(String number) {

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

    public Respuesta modificarServicioCreado(Request req, Response res) {
        Respuesta respuesta = new Respuesta();

        try {
            String pick_up = req.queryParams("pick-up");
            String id = req.params(":id");
            String pickup_location = req.queryParams("pickup-location");

            String drop_off = req.queryParams("drop-off");
            String dropoff_location = req.queryParams("dropoff-location");

            String selected_car = req.queryParams("selected-car");
            String redondo = req.queryParams("idayvueltas");
            String disponibilidad = req.queryParams("con-disponibilidad");
            String detalle = req.queryParams("descripcion-servicio");

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm");
            Servicio servicio = obtenerServicio(id);
            servicio.setDestino(dropoff_location);
            servicio.setOrigen(pickup_location);
            servicio.setFechaCreacion(new Date(System.currentTimeMillis()));
            servicio.setDisponibilidad(disponibilidad != null && disponibilidad.equalsIgnoreCase("true"));
            servicio.setRedondo(redondo != null && redondo.equalsIgnoreCase("true"));
            servicio.setNumeroPasajeros(selected_car);
            servicio.setDetalle(detalle);

            servicio.setHoraSalida(formatter.parse(pick_up));

            servicio.setHoraLlegada(formatter.parse(drop_off));

            ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
            EntityManager em = sc.getEntityManager();
            em.getTransaction().begin();
            em.merge(servicio);
            em.getTransaction().commit();

        } catch (Exception e) {

            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, e);
            respuesta.setCodigo("001");
            respuesta.setResultado("ha ocurrido un error");
        }
        String descripcion = "exito", codigo = "000";
        respuesta.setRecurso("servicio");
        respuesta.setVerbo("POST");
        respuesta.setCodigo(codigo);
        respuesta.setResultado(descripcion);
        return respuesta;
    }

    public Respuesta reenviarCorreoServicioNuevo(String idServicio) {
        Respuesta respuesta = new Respuesta();

        ServicioFacade sf = new ServicioFacade();
        Servicio servicio = sf.buscarPorId(idServicio);

        MessageCreator mc = new MessageCreator();

        Messenger mess = new Messenger();
        mess.sendMail("Solicitud de Servicio en Ayax.co", mc.crearMensajeServicioNuevo(servicio, ConfigManager.INSTANCE.isTestEnvironment()), new String[]{servicio.getUsuario().getBuzonElectronico(), "lespinosa@ayax.co", "hmcarvajal@ayax.co"});

        respuesta.setRecurso("servicio");
        respuesta.setVerbo("GET");
        respuesta.setCodigo("001");
        respuesta.setResultado("OK");
        return respuesta;
    }
}
