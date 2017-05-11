/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.procesos.util.Encriptador;
import com.ayax.website.util.Util;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.NoResultException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class ProcesoLiquidacion {

    public ModelAndView ejecutar(Request req, Response res) {

        Encriptador encriptador = new Encriptador();

        String route = "pago/formularioPago.ftl";
        Map<String, Object> model = new HashMap<>();

        Usuario persona = (Usuario) req.session().attribute("usuario");

        try {
            
            String numeroFactura = persona.getIdentificacion()+ "" + System.currentTimeMillis();
            try {
                model.put("persona_buzonElectronico", persona.getBuzonElectronico());
                model.put("persona_numeroFactura", numeroFactura);
                model.put("p_signature", encriptador.encriptar(numeroFactura, "9"));
            } catch (NoResultException e) {
                model.put("mensaje", Util.getExceptionString(e));
                route = "errorGeneral.ftl";
            }
        } catch (NumberFormatException e) {
            model.put("mensaje", Util.getExceptionString(e));
            route = "errorGeneral.ftl";
        } catch (Exception e) {
            model.put("mensaje", Util.getExceptionString(e));
            route = "errorGeneral.ftl";
        }

        return new ModelAndView(model, route);
    }

}
