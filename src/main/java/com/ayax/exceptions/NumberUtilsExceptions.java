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
public class NumberUtilsExceptions extends GeneralException {

    public String getCode() {
        return ErrorCode.ERROR_NUMBER_UTILS;
    }

    public NumberUtilsExceptions() {
        super(ErrorMensajes.ERROR_NUMBER_UTIL);
    }

    public NumberUtilsExceptions(String message) {
        super(message);
    }

    public NumberUtilsExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public NumberUtilsExceptions(Throwable cause) {
        super(cause);
    }

}
