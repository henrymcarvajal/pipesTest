/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.util.json;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 *
 * @author Mauris
 */
public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer toJson() {
        return JsonUtil::toJson;
    }
}
