/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.server;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public enum ConfigManager {

    INSTANCE;

    public String getPort() {
        return System.getenv("PORT");
    }

    public String getDatabaseUrl() {
        return System.getenv("DATABASE_URL");
    }

    public String getEnvironment() {
        return System.getenv("WEBSITE_URL");
    }

    public boolean isTestEnvironment() {
        return !System.getenv("ENV").equalsIgnoreCase("live");
    }
    
    public String getVapidPublicKey() {
        //BB1eMqeW7CCgtWczOwCeIzhZcg07_OglSSc6wCusi2iE9LdpIz5r1Qd-5xCh7JgcGDm8gpw6ixZKoLwMmlQfgb8
        return System.getenv("VAPID_PUBLIC_KEY");
    }

    public String getVapidPrivateKey() {
        //IlDX3dpBGd5jeWw1V5T4p1QqmyajYfIoM8TLiYoQ7zM
        return System.getenv("VAPID_PRIVATE_KEY");
    }
}
