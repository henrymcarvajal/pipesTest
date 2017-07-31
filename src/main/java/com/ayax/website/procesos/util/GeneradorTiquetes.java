/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Mauris
 */
public class GeneradorTiquetes {

    public String generarTiquete() {
        char[] characterSet = "ABCDEF0123456789".toCharArray();
        int length = 6;
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

}
