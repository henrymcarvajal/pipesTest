/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.fachadas.ServicioJpaController;
import com.ayax.website.persistencia.fachadas.UsuarioJpaController;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.mail.Messenger;
import com.ayax.website.mail.MessageCreator;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import spark.Request;
import spark.Response;

/**
 *
 * @author Mauris
 */
public class AdminServicio {

    public AdminServicio() {
    }

    private static final String CODIGO_ESERVICIO_ESPECIAL = "CESE1A";
    public static final String TIPO_USUARIO_ESERVICIOESPECIAL = "1";

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
        String usuarioRegistrado = req.queryParams("usuarioregistrado");
        String codigoAutorizacion = req.queryParams("codigoautorizacion");

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

        if (usuario != null && !usuarioRegistrado.equalsIgnoreCase("yes")) {
            respuesta.setRecurso("servicio");
            respuesta.setVerbo("POST");
            respuesta.setCodigo("004");
            respuesta.setResultado("Correo ya registrado.");
            return respuesta;
        }
        if (usuario == null && !usuarioRegistrado.equalsIgnoreCase("yes")) {
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

            if (CODIGO_ESERVICIO_ESPECIAL.equalsIgnoreCase(codigo_promocional)) {

                usuario.setTipo_usuario(TIPO_USUARIO_ESERVICIOESPECIAL);
            } else if (!"".equalsIgnoreCase(codigo_promocional) && codigo_promocional != null
                    && !CODIGO_ESERVICIO_ESPECIAL.equalsIgnoreCase(codigo_promocional)) {

                Logger.getLogger(AdminServicio.class.getName()).log(Level.INFO, "Codigo promocional incorrecto");
                respuesta.setRecurso("servicio");
                respuesta.setVerbo("POST");
                respuesta.setCodigo("005");
                respuesta.setResultado("El codigo promocional no es correcto.");
                return respuesta;
            }
        } else if (usuario == null && usuarioRegistrado.equalsIgnoreCase("yes")) {

            Logger.getLogger(AdminServicio.class.getName()).log(Level.INFO, "Usuario registrado no existe");
            respuesta.setRecurso("servicio");
            respuesta.setVerbo("POST");
            respuesta.setCodigo("003");
            respuesta.setResultado("El usuario registrado insertado no corresponde.");
            return respuesta;
        } else if (usuario != null && usuarioRegistrado.equalsIgnoreCase("yes")) {
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
        servicio.setDistancia(this.parseDecimal(distancia));
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
            mess.sendMail("Solicitud de Servicio en Ayax.co", mc.crearMensajeServicioNuevo(servicio), new String[]{usuario.getBuzonElectronico()});

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
        date.minusDays(30);
        java.util.Date uDate = Date.from(date.minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        q.setParameter("fechaLimite", uDate, TemporalType.DATE);
        List<Object[]> list = (List<Object[]>) q.getResultList();
        return list;
    }

    public List<Servicio> obtenerTodosServicios() {
        ServicioJpaController sc = new ServicioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        //TypedQuery<Servicio> q = em.createNamedQuery("Servicio.findAllActive", Servicio.class);
        Query q = em.createNamedQuery("Servicio.findAll");
        //LocalDate date = LocalDate.now();
        //date.minusDays(30);
        //java.util.Date uDate = Date.from(date.minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        //q.setParameter("fechaLimite", uDate, TemporalType.DATE);
        //List<Object[]> list = (List<Object[]>) ;
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

    private Double parseDecimal(String number) {
        if (number == null) {
            return null;
        }

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
