/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.server;

/**
 *
 * @author Mauris
 */
public enum ConfigManager {

    INSTANCE;

    private static final String TEST_URL = "https://ayaxlandingpage-test.herokuapp.com";
    private static final String PROD_URL = "https://www.ayax.co";

    public String getPort() {
        return System.getenv("PORT");
    }

    public String getDatabaseUrl() {
        return System.getenv("DATABASE_URL");
    }

    public String getEnvironment() {
        if (isTestEnvironment()) {
            return TEST_URL;
        }
        return PROD_URL;
    }

    public boolean isTestEnvironment() {
        return System.getenv("ENV").equalsIgnoreCase("test");
    }

    public String getTestURL() {
        return TEST_URL;
    }

    public String getProductionURL() {
        return PROD_URL;
    }
}
