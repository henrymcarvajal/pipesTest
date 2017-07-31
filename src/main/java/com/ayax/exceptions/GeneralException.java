/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.exceptions;

import com.ayax.exceptions.code.ErrorCode;
import com.ayax.exceptions.code.ErrorMensajes;

/**
 *
 * @author jespinosa
 */
public class GeneralException extends Exception {

    public String getCode() {
        return ErrorCode.ERROR_GENERAL;
    }

    public GeneralException() {
        super(ErrorMensajes.UNKNOWN);
    }

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralException(Throwable cause) {
        super(cause);
    }

}
