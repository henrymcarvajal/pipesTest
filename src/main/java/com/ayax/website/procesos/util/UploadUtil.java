/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import com.ayax.website.server.RouteServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import spark.Request;

/**
 *
 * @author Mauris
 */
public class UploadUtil {

    private static final String MULTIPART_CONFIG_ATTRIBUTE = "org.eclipse.multipartConfig";

    public HashMap<String, HashMap> getParts(Request req, List<String> fieldNames, List<String> fileNames) throws Exception {

        HashMap<String, HashMap> multiParts = new HashMap();
        if (req.raw().getAttribute(MULTIPART_CONFIG_ATTRIBUTE) == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            req.raw().setAttribute(MULTIPART_CONFIG_ATTRIBUTE, multipartConfigElement);
        }

        HashMap<String, byte[]> files = new HashMap();

        for (String fileName : fileNames) {
            try {
                System.out.println("nombre : "+fileName);
                Part file = req.raw().getPart(fileName);
                int len;
                int size = 1024;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[size];

                InputStream is = file.getInputStream();
                while ((len = is.read(buf, 0, size)) != -1) {
                    bos.write(buf, 0, len);
                }
                files.put(fileName, bos.toByteArray());
            } catch (IOException | ServletException ex) {
                Logger.getLogger(RouteServer.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex);
            }
        }
        multiParts.put("files", files);
        return multiParts;
    }
}
