/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauris
 */
public class Encriptador {

    private static final String P_CUST_ID_CLIENTE = "9770";
    private static final String P_KEY = "a77302019ab09dac1d0413bd9261e7de93668507";
    private static final String P_CURRENCY_CODE = "COP";

    public String encriptar(String p_id_invoice, String p_amount) {
        StringBuilder buf = new StringBuilder();
        buf.append(P_CUST_ID_CLIENTE);
        buf.append("^");
        buf.append(P_KEY);
        buf.append("^");
        buf.append(p_id_invoice);
        buf.append("^");
        buf.append(p_amount);
        buf.append("^");
        buf.append(P_CURRENCY_CODE);

        StringBuilder md5 = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buf.toString().getBytes());

            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                md5.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encriptador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md5.toString();
    }
}
