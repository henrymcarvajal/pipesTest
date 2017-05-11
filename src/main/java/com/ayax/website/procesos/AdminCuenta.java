/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Factura;
import com.ayax.website.persistencia.entidades.Transportador;
import com.ayax.website.persistencia.entidades.Vehiculo;
import com.ayax.website.persistencia.fachadas.FacturaJpaController;
import com.ayax.website.procesos.util.Encriptador;
import com.ayax.website.server.ConfigManager;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class AdminCuenta {

    public AdminCuenta() {
    }

    public ModelAndView mostrarPagina(Request req, Response res) {

        String monto = req.queryParams("monto");
        Transportador transportador = (Transportador) req.session().attribute("usuario");
        Collection<Vehiculo> veh = transportador.getVehiculos();
        Object[] lveh = veh.toArray();
        Vehiculo v = (Vehiculo) lveh[0];
        
        if (v.esVehiculoRevisado()) {
            Factura factura = new Factura();
            factura.setId(java.util.UUID.randomUUID().toString());
            factura.setTransportador(transportador);
            factura.setValor(BigInteger.valueOf(Long.parseLong(monto)));
            factura.setGenerada(Boolean.TRUE);

            FacturaJpaController sjc = new FacturaJpaController(EntityManagerFactoryBuilder.INSTANCE.build());

            try {
                sjc.create(factura);
            } catch (Exception ex) {
                Logger.getLogger(AdminCuenta.class.getName()).log(Level.SEVERE, null, ex);
            }

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("transportador", transportador);
            dataMap.put("factura", factura);
            dataMap.put("url_ayax", ConfigManager.INSTANCE.getEnvironment());
            dataMap.put("test", ConfigManager.INSTANCE.isTestEnvironment() ? "TRUE" : "FALSE");
            dataMap.put("signature", new Encriptador().encriptar(factura.getId(), monto));
            return new ModelAndView(dataMap, "cuenta/formularioPago.ftl");
        } else {
            Logger.getLogger(AdminCuenta.class.getName()).log(Level.INFO, "Los documentos del veh\u00edculo no estan al d\u00eda, no puede recargar transportador : {0}", transportador.getId());
            res.redirect("/informativo.html?codigo=001&mensaje=No puedes recargar tu cuenta, hasta que los documentos de tu vehiculo esten al dia");
            return new ModelAndView(null, null);
        }
    }
}
