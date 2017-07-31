package com.ayax.website.server;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.CupoVehiculo;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.procesos.AdminAcceso;
import com.ayax.website.procesos.AdminContrasena;
import com.ayax.website.procesos.AdminConversacion;
import com.ayax.website.procesos.AdminCotizacion;
import com.ayax.website.procesos.AdminCuenta;
import com.ayax.website.procesos.AdminFactura;
import com.ayax.website.procesos.AdminOferta;
import com.ayax.website.procesos.AdminProcesos;
import com.ayax.website.procesos.AdminReserva;
import com.ayax.website.procesos.AdminServicio;
import com.ayax.website.procesos.AdminSuscripcionPush;
import com.ayax.website.procesos.AdminSuscriptor;
import com.ayax.website.procesos.AdminTransportador;
import com.ayax.website.procesos.AdminVehiculo;
import com.ayax.website.procesos.ProcesoLiquidacion;
import static com.ayax.website.util.json.JsonUtil.toJson;
import static com.ayax.website.util.json.JsonUtil.toJson;
import java.util.List;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.json.JSONObject;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class RouteServer {

    private static void preloadEntities() {

        EntityManagerFactory emf = EntityManagerFactoryBuilder.INSTANCE.build();
        EntityManager em = emf.createEntityManager();

        Cache cache = emf.getCache();
        cache.contains(CupoVehiculo.class, em);

        Query q = em.createNamedQuery("CupoVehiculo.findAll");
        List<?> l = q.getResultList();

        System.out.println("Entities preloaded");
    }

    public static void main(String[] args) {

        port(Integer.valueOf(ConfigManager.INSTANCE.getPort()));
        externalStaticFileLocation("src/main/resources/public/");
        staticFileLocation("/public");

        preloadEntities();

        get("/", (req, res) -> {
            return new ModelAndView(null, "index.ftl");
        }, new FreeMarkerEngine());

        //----- Operaciones sobre Acceso
        post("/acceso", (req, res) -> {
            res.type("application/json");
            AdminAcceso adminAcceso = new AdminAcceso();
            return adminAcceso.ingresar(req, res);
        }, toJson());

        put("/acceso", (req, res) -> {
            res.type("application/json");
            AdminContrasena adminContrasena = new AdminContrasena();
            return adminContrasena.cambiarContrasena(req, res);
        }, toJson());

        get("/salida", (req, res) -> {
            res.type("application/json");
            AdminAcceso adminAcceso = new AdminAcceso();
            return adminAcceso.salir(req, res);
        }, toJson());

        post("/recuperarContrasena", (req, res) -> {
            res.type("application/json");
            AdminContrasena adminContrasena = new AdminContrasena();
            return adminContrasena.recuperarContrasena(req, res);
        }, toJson());

        //----- Operaciones sobre factura
        post("/factura/:id", (req, res) -> {
            AdminFactura adminFactura = new AdminFactura();
            return adminFactura.actualizarFactura(req, res);
        }, new FreeMarkerEngine());

        //----- Operaciones sobre Oferta
        post("/oferta", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.crearOferta(req, res);
        }, toJson());

        get("/oferta/:id", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.obtenerOferta(req, res);
        }, toJson());

        get("/oferta/:id/transportador", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.actualizarOferta(req, res);
        }, toJson());

        get("/oferta/:id/inicio", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.iniciarServicio(req, res);
        }, toJson());

        post("/oferta/:id/transportador/calificacion", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.actualizarReputacionTransportador(req, res);
        }, toJson());

        post("/oferta/:id/usuario/calificacion", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.actualizarReputacionUsuario(req, res);
        }, toJson());

        //----- Operaciones sobre Pago
        post("/cuenta", (req, res) -> {
            AdminCuenta pago = new AdminCuenta();
            return pago.mostrarPagina(req, res);
        }, new FreeMarkerEngine());

        post("/pagarTransporte", (req, res) -> {
            ProcesoLiquidacion liquidacion = new ProcesoLiquidacion();
            return liquidacion.ejecutar(req, res);
        }, new FreeMarkerEngine());

        //----- Operaciones sobre Servicio
        get("/servicio/:id", (req, res) -> {
            res.type("application/json");
            AdminServicio adminServicio = new AdminServicio();
            return adminServicio.obtenerServicioObj(req.params(":id"));
        }, toJson());

        post("/servicio/editar/:id", (req, res) -> {
            res.type("application/json");
            AdminServicio adminServicio = new AdminServicio();
            return adminServicio.modificarServicioCreado(req, res);
        }, toJson());

        get("/servicio", (req, res) -> {
            res.type("application/json");
            Transportador transportador = (Transportador) req.session().attribute("usuario");
            AdminServicio adminServicio = new AdminServicio();
            return adminServicio.obtenerServiciosPorTransportador(transportador);
        }, toJson());

        post("/servicio", (req, res) -> {
            res.type("application/json");
            AdminServicio adminServicio = new AdminServicio();
            return adminServicio.crearServicio(req, res);
        }, toJson());

        //----- Operaciones sobre transportador
        post("/transportador", (req, res) -> {
            res.type("application/json");
            AdminTransportador adminTransportador = new AdminTransportador();
            return adminTransportador.crearTransportador(req, res);
        }, toJson());

        //----- Operaciones sobre vehiculos
        get("/vehiculo/:id", (req, res) -> {
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.buscarVehiculo(req, res);
        }, new FreeMarkerEngine());

        post("/vehiculo", (req, res) -> {
            res.type("application/json");
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.crearVehiculo(req, res);
        }, toJson());

        post("/vehiculo/:id/documentos", (req, res) -> {
            res.type("application/json");
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.actualizarDocumentos(req, res);
        }, toJson());

        post("/vehiculo/:placa/fechas", (req, res) -> {
            res.type("application/json");
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.actualizarFechaDocumentosVehiculo(req, res);
        }, toJson());

        get("/procesos/correos/calificacion", (req, res) -> {
            res.type("application/json");
            AdminProcesos adminProcesos = new AdminProcesos();
            return adminProcesos.enviarCorreosCalificacion();
        }, toJson());

        get("/procesos/correos/reenvio/servicionuevo/:id", (req, res) -> {
            res.type("application/json");
            AdminServicio adminProcesos = new AdminServicio();
            return adminProcesos.reenviarCorreoServicioNuevo(req.params(":id"));
        }, toJson());

        post("/suscriptor", (req, res) -> {
            res.type("application/json");
            AdminSuscriptor As = new AdminSuscriptor();
            return As.crearSuscriptor(req, res);
        }, toJson());

        post("/mensaje/servicio/:idServicio/transportador", (req, res) -> {
            res.type("application/json");
            String idServicio = req.params(":idServicio");
            Transportador transportador = (Transportador) req.session().attribute("usuario");
            String mensaje = req.queryParams("comentario-texto");
            AdminConversacion ac = new AdminConversacion();
            return ac.crearMensajeTransportador(idServicio, transportador, mensaje);
        }, toJson());

        post("/mensaje/conversacion/:id/usuario", (req, res) -> {
            res.type("application/json");
            String idConversacion = req.params(":id");
            String mensaje = req.queryParams("comentario-texto");
            AdminConversacion ac = new AdminConversacion();
            return ac.crearMensajeUsuario(idConversacion, mensaje);
        }, toJson());

        get("/conversacion/:id", (req, res) -> {
            res.type("application/json");
            String idConversacion = req.params(":id");
            AdminConversacion ac = new AdminConversacion();
            return ac.obtenerConversacion(idConversacion);
        }, toJson());

        //----- Operaciones sobre Reservas
        get("/oferta/:id/reserva", (req, res) -> {
            AdminReserva reserva = new AdminReserva();
            return reserva.mostrarFormularioPago(req, res);
        }, new FreeMarkerEngine());

        post("/oferta/:id/reserva/pago", (req, res) -> {
            AdminReserva reserva = new AdminReserva();
            return reserva.mostrarConfirmacionPago(req, res);
        }, new FreeMarkerEngine());

        post("/suscripcionPush", (req, res) -> {
            JSONObject subscription = new JSONObject(req.body());

            String endpoint = subscription.getString("endpoint");

            JSONObject keys = subscription.getJSONObject("keys");
            String p256dh = keys.getString("p256dh");
            String auth = keys.getString("auth");

            AdminSuscripcionPush adminPush = new AdminSuscripcionPush();
            return adminPush.crearSuscripcion(req.ip(), endpoint, p256dh, auth);
        }, toJson());

        get("/suscripcionPush", (req, res) -> {
            AdminSuscripcionPush adminPush = new AdminSuscripcionPush();
            return adminPush.notificarSuscripciones("f4818e4d-55c1-4316-a3fc-46dbf840c16a");
        }, toJson());
        
        post("/cotizar", (req, res) -> {
            res.type("application/json");
            AdminCotizacion adminContizacion = new AdminCotizacion();
            return adminContizacion.obtenerCotizacion(req, res);
        }, toJson());
    }
}
