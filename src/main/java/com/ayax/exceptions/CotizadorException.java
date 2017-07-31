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
public class CotizadorException extends GeneralException{
    
    public String getCode() {
        return ErrorCode.ERROR_COTIZADOR;
    }

    public CotizadorException() {
        super(ErrorMensajes.ERROR_COTIZADOR);
    }

    public CotizadorException(String message) {
        super(message);
    }

    public CotizadorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CotizadorException(Throwable cause) {
        super(cause);
    }
    
}
