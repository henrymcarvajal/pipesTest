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
}
