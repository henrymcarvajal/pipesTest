package com.ayax.website.server;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.CupoVehiculo;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.procesos.AdminVehiculo;
import com.ayax.website.procesos.AdminOferta;
import com.ayax.website.procesos.ProcesoLiquidacion;
import com.ayax.website.procesos.AdminCuenta;
import com.ayax.website.procesos.AdminContrasena;
import com.ayax.website.procesos.AdminServicio;
import com.ayax.website.procesos.AdminTransportador;
import com.ayax.website.procesos.AdminAcceso;
import com.ayax.website.procesos.AdminConversacion;
import com.ayax.website.procesos.AdminFactura;
import com.ayax.website.procesos.AdminProcesos;
import com.ayax.website.procesos.AdminSuscriptor;
import java.util.List;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import static com.ayax.website.util.json.JsonUtil.toJson;

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

        post("/suscriptor", (req, res) -> {
            res.type("application/json");
            AdminSuscriptor As = new AdminSuscriptor();
            return As.crearSuscriptor(req, res);
        }, toJson());

        post("/mensaje/servicio/:idServicio/transportador/:idTransportador", (req, res) -> {
            res.type("application/json");
            String idServicio = req.params(":idServicio");
            String idTransportador = req.params(":idTransportador");
            String mensaje = req.queryParams("texto");
            AdminConversacion ac = new AdminConversacion();
            return ac.crearMensajeTransportador(idServicio, idTransportador, mensaje);
        }, toJson());

        post("/mensaje/servicio/:idServicio/usuario", (req, res) -> {
            res.type("application/json");
            String idServicio = req.params(":idServicio");
            String mensaje = req.queryParams("texto");
            AdminConversacion ac = new AdminConversacion();
            return ac.crearMensajeUsuario(idServicio, mensaje);
        }, toJson());

        /*
        get("/admin/access", (req, res) -> {
            return new ModelAndView(null, "/admin/access.ftl");
        }, new FreeMarkerEngine());

        post("/admin/index", (req, res) -> {
            return new ModelAndView(null, "/admin/index.ftl");
        }, new FreeMarkerEngine());

        get("/admin/servicios", (req, res) -> {
            AdminServicio as = new AdminServicio();
            List<Servicio> servicios = as.obtenerTodosServicios();
            Map<String, Object> root = new HashMap();
            root.put("servicios", servicios);
            return new ModelAndView(root, "/admin/servicios.ftl");
        }, new FreeMarkerEngine());

        get("/admin/servicio/:id", (req, res) -> {
            AdminServicio as = new AdminServicio();
            Servicio servicio = as.obtenerServicio(req.params("id"));
            Map<String, Object> root = new HashMap();
            root.put("item", servicio);
            return new ModelAndView(root, "/admin/servicio.ftl");
        }, new FreeMarkerEngine());

        get("/admin/usuario/:id", (req, res) -> {
            AdminUsuario as = new AdminUsuario();
            Usuario usuario = as.obtenerUsuario(req.params("id"));
            Map<String, Object> root = new HashMap();
            root.put("item", usuario);
            return new ModelAndView(root, "/admin/usuario.ftl");
        }, new FreeMarkerEngine());
         */
    }
}
