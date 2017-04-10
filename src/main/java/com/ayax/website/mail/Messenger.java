/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.mail;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Mauris
 */
public class Messenger {

    private static final String SMTP_HOST = "smtp.zoho.com";
    private static final int SMTP_PORT = 465;
    private static final String SMTP_USER = "informacion@ayax.co";
    private static final String SMTP_CREDENTIALS = "informacion123";
    private static final String FROM_EMAIL = "informacion@ayax.co";

    private void configEmail(HtmlEmail email) {
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        email.setAuthenticator(new DefaultAuthenticator(SMTP_USER, SMTP_CREDENTIALS));
        email.setSSLOnConnect(true);
    }

    public boolean sendMail(String subject, String body, String[] recipients) {
        try {
            HtmlEmail email = new HtmlEmail();
            configEmail(email);
            email.setFrom(FROM_EMAIL);
            email.setSubject(subject);
            email.setHtmlMsg(body);
            for (String recipient : recipients) {
                email.addTo(recipient);
            }
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(Messenger.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
