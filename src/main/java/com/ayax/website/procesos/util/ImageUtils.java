/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import java.util.Base64;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class ImageUtils {

    public static String encodeImage(byte[] imageBytes) {
        if (imageBytes != null) {
            byte[] imgBytesAsBase64 = Base64.getEncoder().encode(imageBytes);
            String imgDataAsBase64 = new String(imgBytesAsBase64);
            return "data:image/png;base64," + imgDataAsBase64;
        }
        return null;
    }
}
