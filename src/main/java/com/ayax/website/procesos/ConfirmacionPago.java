/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class ConfirmacionPago {

    public Map ejecutar(Request req, Response res) {
        Map<String, Object> model = new HashMap<>();
        model.put("x_cust_id_cliente", req.queryParams("x_cust_id_cliente"));
        model.put("x_description", req.queryParams("x_description"));
        model.put("x_amount_ok", req.queryParams("x_amount_ok"));
        model.put("x_id_invoice", req.queryParams("x_id_invoice"));
        model.put("x_amount_base", req.queryParams("x_amount_base"));
        model.put("x_tax", req.queryParams("x_tax"));
        model.put("x_currency_code", req.queryParams("x_currency_code"));
        model.put("x_franchise", req.queryParams("x_franchise"));
        model.put("x_customer_ip", req.queryParams("x_customer_ip"));
        model.put("x_transaction_date", req.queryParams("x_transaction_date"));
        model.put("x_approval_code", req.queryParams("x_approval_code"));
        model.put("x_transaction_id", req.queryParams("x_transaction_id"));
        model.put("x_ref_payco", req.queryParams("x_ref_payco"));
        model.put("x_cod_response", req.queryParams("x_cod_response"));
        model.put("x_response", req.queryParams("x_response"));
        model.put("x_response_reason_text", req.queryParams("x_response_reason_text"));
        model.put("x_extra1", req.queryParams("x_extra1"));
        model.put("x_extra2", req.queryParams("x_extra2"));
        model.put("x_extra3", req.queryParams("x_extra3"));
        return model;
    }
}
