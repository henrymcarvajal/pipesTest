/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.mail;

import com.ayax.website.server.ConfigManager;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 *
 * @author Mauris
 */
public class ContentGenerator {

    public String generateFrom(String templateName, Map rootMap) {
        rootMap.put("url_ayax", ConfigManager.INSTANCE.getEnvironment());
        Writer out = new StringWriter();
        String output = null;
        try {
            Template template = TemplateLoader.getTemplate(templateName);
            template.process(rootMap, out);
            output = out.toString();
        } catch (TemplateException | IOException ex) {
            output = ex.toString();
        }
        return output;
    }
}
