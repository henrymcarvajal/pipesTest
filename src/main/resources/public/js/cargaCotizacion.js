/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var cotizacion = dataSesion.getUserDataFromSession("cotizacion");
    var mayorPrecio = cotizacion.analisisCostosMejorEscenario.precio;
    mayorPrecio = mayorPrecio + mayorPrecio * 0.16;
    var menorPrecio = cotizacion.analisisCostosPeorEscenario.precio;
    menorPrecio = menorPrecio + menorPrecio * 0.16;
    menorPrecio ='$' + menorPrecio.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
    mayorPrecio ='$' + mayorPrecio.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
    $("#precio-maximo").text(mayorPrecio);
    $("#precio-minimo").text(menorPrecio);
    $("#ubic-origen").text(cotizacion.ubicacionOrigen);
    $("#ubic-destino").text(cotizacion.ubicacionDestino);
    $("#num-pasajeros").text(cotizacion.numeroPasajeros);
});


