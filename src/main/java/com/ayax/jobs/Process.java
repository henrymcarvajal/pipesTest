/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.jobs;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Mauris
 */
public class Process {

    public static void main(String[] args) {
        //args[0] = "http://ayaxlandingpage-test.herokuapp.com";
        URLConnection myURLConnection;
        try {//new URL( 
            URL myURL = new URL(args[0] + "/procesos/correos/calificacion");
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();

            InputStream in = myURLConnection.getInputStream();
            int b;
            while ((b = in.read()) != -1) {
                System.out.write(b);
            }

        } catch (MalformedURLException e) {
            // new URL() failed
            // ...
        } catch (IOException e) {
            // openConnection() failed
            // ...
        }
    }
}
