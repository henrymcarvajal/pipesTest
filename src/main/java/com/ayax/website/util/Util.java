/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class Util {

    public static String getExceptionString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
