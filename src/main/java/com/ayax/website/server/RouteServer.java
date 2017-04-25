package com.ayax.website.server;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.CupoVehiculo;
import com.ayax.website.persistencia.entidades.Oferta;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.procesos.AdminVehiculo;
import com.ayax.website.procesos.AdminOferta;
import com.ayax.website.procesos.ProcesoLiquidacion;
import com.ayax.website.procesos.AdminCuenta;
import com.ayax.website.procesos.AdminContrasena;
import com.ayax.website.procesos.AdminServicio;
import com.ayax.website.procesos.AdminTransportador;
import com.ayax.website.procesos.AdminAcceso;
import com.ayax.website.procesos.AdminFactura;
import com.ayax.website.procesos.AdminProcesos;
import com.ayax.website.procesos.AdminSuscriptor;
import com.ayax.website.procesos.Respuesta;
import com.ayax.website.util.Util;
import static com.ayax.website.util.json.JsonUtil.json;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;

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
        }, json());

        put("/acceso", (req, res) -> {
            res.type("application/json");
            AdminContrasena adminContrasena = new AdminContrasena();
            return adminContrasena.cambiarContrasena(req, res);
        }, json());

        get("/salida", (req, res) -> {
            res.type("application/json");
            AdminAcceso adminAcceso = new AdminAcceso();
            return adminAcceso.salir(req, res);
        }, json());

        post("/recuperarContrasena", (req, res) -> {
            res.type("application/json");
            AdminContrasena adminContrasena = new AdminContrasena();
            return adminContrasena.recuperarContrasena(req, res);
        }, json());

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
        }, json());

        get("/oferta/:id", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.obtenerOferta(req, res);
        }, json());

        get("/oferta/:id/transportador", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.actualizarOferta(req, res);
        }, json());

        get("/oferta/:id/inicio", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.iniciarServicio(req, res);
        }, json());

        post("/oferta/:id/transportador/calificacion", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.actualizarReputacionTransportador(req, res);
        }, json());

        post("/oferta/:id/usuario/calificacion", (req, res) -> {
            res.type("application/json");
            AdminOferta adminOferta = new AdminOferta();
            return adminOferta.actualizarReputacionUsuario(req, res);
        }, json());

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
            Servicio item = adminServicio.obtenerServicio(req.params(":id"));
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", item.getId());
                obj.put("origen", item.getOrigen());
                obj.put("destino", item.getDestino());
                obj.put("horaLlegada", item.getHoraLlegada());
                obj.put("horaSalida", item.getHoraSalida());
                obj.put("numeroPasajeros", item.getNumeroPasajeros());
                obj.put("fechaCreacion", item.getFechaCreacion());
                obj.put("distancia", item.getDistancia());
                obj.put("redondo", item.getRedondo());
                obj.put("descripcion", item.getDetalle());
                String servicioGratis=AdminServicio.TIPO_USUARIO_ESERVICIOESPECIAL.
                        equalsIgnoreCase(item.getUsuario().getTipo_usuario())?"1":null;
                obj.put("servicioGratis", servicioGratis);
            } catch (JSONException ex) {
                Logger.getLogger(RouteServer.class.getName()).log(Level.SEVERE, null, ex);

            }
            return obj;
        }, json());

        get("/servicio", (req, res) -> {
            res.type("application/json");
            Respuesta respuesta = new Respuesta();

            try {
                AdminServicio adminServicio = new AdminServicio();
                List<Object[]> l = adminServicio.obtenerServicios();
                JSONArray array = new JSONArray();
                Transportador transportador = (Transportador) req.session().attribute("usuario");
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
                respuesta.setCodigo("000");
                respuesta.setResultado("exito");
                respuesta.setValor(array);
            } catch (Exception ex) {
                respuesta.setCodigo("001");
                respuesta.setResultado(Util.getExceptionString(ex));
            }
            return respuesta;
        }, json());

        post("/servicio", (req, res) -> {
            res.type("application/json");
            AdminServicio adminServicio = new AdminServicio();
            return adminServicio.crearServicio(req, res);
        }, json());

        //----- Operaciones sobre transportador
        post("/transportador", (req, res) -> {
            res.type("application/json");
            AdminTransportador adminTransportador = new AdminTransportador();
            return adminTransportador.crearTransportador(req, res);
        }, json());

        //----- Operaciones sobre vehiculos
        get("/vehiculo/:id", (req, res) -> {
            //res.type("application/json");
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.buscarVehiculo(req, res);
            //return req.queryParams("idTransportador");
        }, new FreeMarkerEngine());

        post("/vehiculo", (req, res) -> {
            res.type("application/json");
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.crearVehiculo(req, res);
            //return req.queryParams("idTransportador");
        }, json());

        post("/vehiculo/:id/documentos", (req, res) -> {
            res.type("application/json");
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.actualizarDocumentos(req, res);
        }, json());

        post("/vehiculo/:placa/fechas", (req, res) -> {
            res.type("application/json");
            AdminVehiculo adminVehiculo = new AdminVehiculo();
            return adminVehiculo.actualizarFechaDocumentosVehiculo(req, res);
        }, json());

        get("/procesos/correos/calificacion", (req, res) -> {
            res.type("application/json");
            AdminProcesos adminProcesos = new AdminProcesos();
            return adminProcesos.enviarCorreosCalificacion(req, res);
        }, json());

        post("/suscriptor", (req, res) -> {
            res.type("application/json");
            AdminSuscriptor As = new AdminSuscriptor();
            return As.crearSuscriptor(req, res);
        }, json());

        get("/admin", (req, res) -> {
            return new ModelAndView(null, "/admin/index.ftl");
        }, new FreeMarkerEngine());

        post("/admin/access", (req, res) -> {
            return new ModelAndView(null, "/admin/index.ftl");
        }, new FreeMarkerEngine());
    }
}
