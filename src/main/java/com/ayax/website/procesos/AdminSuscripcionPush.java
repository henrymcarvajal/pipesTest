/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.entidades.SuscripcionPush;
import com.ayax.website.persistencia.fachadas.SuscripcionPushFacade;
import com.ayax.website.persistencia.fachadas.TransportadorFacade;
import com.ayax.website.server.ConfigManager;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Ayax
 */
public class AdminSuscripcionPush {

    public Respuesta crearSuscripcion(String ip, String endpoint, String p256dh, String auth) {

        Respuesta respuesta = new Respuesta();
        try {
            SuscripcionPush s = new SuscripcionPush();
            s.setAuth(auth);
            s.setIp(ip);
            s.setEndpoint(endpoint);
            s.setP256dh(p256dh);
            SuscripcionPushFacade sf = new SuscripcionPushFacade();
            sf.crear(s);
            respuesta.setCodigo("000");
            respuesta.setResultado("Exito");
        } catch (Exception e) {
            respuesta.setCodigo("001");
            respuesta.setResultado("Error");
            Logger.getLogger(TransportadorFacade.class.getName()).log(Level.SEVERE, null, e);
        }

        return respuesta;
    }

    public List<SuscripcionPush> obtenerSuscripcionesPush() {
        SuscripcionPushFacade f = new SuscripcionPushFacade();
        return f.obtenerSuscripcionesPush();
    }

    public Respuesta notificarSuscripciones(String idServicio) {
        
        Security.addProvider(new BouncyCastleProvider());
        
        List<SuscripcionPush> lista = obtenerSuscripcionesPush();

        for (SuscripcionPush suscripcionPush : lista) {
            try {
                // Construct notification
                Notification notification = new Notification(suscripcionPush.getEndpoint(), suscripcionPush.getP256dh(), suscripcionPush.getAuth(), getPayload("Servicio nuevo", "Oferta ya!", idServicio));
                
                PushService pushService = new PushService();
                pushService.setSubject("mailto:informacion@ayax.co");
                pushService.setPublicKey(Utils.loadPublicKey(ConfigManager.INSTANCE.getVapidPublicKey()));
                pushService.setPrivateKey(Utils.loadPrivateKey(ConfigManager.INSTANCE.getVapidPrivateKey()));
                
                // Send notification!
                HttpResponse httpResponse;
                try {
                    httpResponse = pushService.send(notification);
                    System.out.println(httpResponse.getStatusLine().getStatusCode());
                    System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
                } catch (Exception ex) {
                    Logger.getLogger(AdminSuscripcionPush.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(AdminSuscripcionPush.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AdminSuscripcionPush.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(AdminSuscripcionPush.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return new Respuesta();
    }
    
    private byte[] getPayload(String titulo, String cuerpo, String idServicio) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("titulo", titulo);
        jsonObject.addProperty("cuerpo", cuerpo);
        jsonObject.addProperty("url", ConfigManager.INSTANCE.getEnvironment() + "/detalleServicio.html?id="+ idServicio);
        return jsonObject.toString().getBytes();
    }
}
