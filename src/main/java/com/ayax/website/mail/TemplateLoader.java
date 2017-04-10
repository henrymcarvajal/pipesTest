/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Mauris
 */
public class TemplateLoader {
    
    private static final String TEMPLATE_LOCATION = "src/main/resources/spark/template/freemarker/email";

    public static Template getTemplate(String templateName) {
        try {
            Configuration cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_LOCATION));
            return cfg.getTemplate(templateName);
        } catch (IOException ex) {

        }
        return null;
    }
}
