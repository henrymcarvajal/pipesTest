/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$("#form-cotizar").submit(function(event) {
    event.preventDefault();
    if ($('#precio-peajes').val() !== '' && $('#distancia').val() !== '') {
        var peajesUnTrayecto = $('#precio-peajes').val();
        var distanciaUnTrayecto = $('#distancia').val();
        if ($('#idayRegreso').find(":selected").text() === 'Si') {
            $('#precio-peajes').val(2 * peajesUnTrayecto);
            $('#distancia').val(2 * parseInt(distanciaUnTrayecto));
        } else {
            $('#precio-peajes').val(4 * peajesUnTrayecto);
            $('#distancia').val(4 * parseInt(distanciaUnTrayecto));
        }
        $.ajax({
            type: "POST",
            url: "/cotizar",
            data: $("#form-cotizar").serialize(),
            dataType: "json",
            success: function(data) {
                var url;
                if (data.codigo === '000') {
                    dataSesion.storeUserDataInSession(data.valor, "cotizacion");
                } else {
                    var mensaje = 'Ha ocurrido un error, contactanos a hmcarvajal@ayax.co';
                    var codigo = '001';
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                }
                url = "/Cotizacion.html";
                window.location.href = url;
            }
        });
    } else {
        if ($('#origen-id').val() === '') {
            $('#errorGenericoubOrigen').text("No reconocemos el lugar que ingresas, intenta ingresando \n\
        barrio-ciudad (ejemplo: morato bogota) o algun centro comercial, universidad o lugar reconocido que sirva de punto de referencia");
            $('#error-ub-origen').css('visibility', 'visible').hide().fadeIn().removeClass('hidden');
        }
        if ($('#destino-id').val() === '') {
            $('#errorGenericoubDestino').text("No reconocemos el lugar que ingresas, intenta ingresando \n\
        barrio-ciudad (ejemplo: morato bogota) o algun centro comercial, universidad o lugar reconocido que sirva de punto de referencia");
            $('#error-ub-destino').css('visibility', 'visible').hide().fadeIn().removeClass('hidden');
        }
    }
});

$('#ubicacion-origen').on('blur', function() {
    if ($('#origen-id').val() === '') {
        $('#errorGenericoubOrigen').text("No reconocemos el lugar que ingresas, intenta ingresando \n\
        barrio-ciudad (ejemplo: morato bogota) o algun centro comercial, universidad o lugar reconocido que sirva de punto de referencia");
        $('#error-ub-origen').css('visibility', 'visible').hide().fadeIn().removeClass('hidden');
    } else {
        $('#error-ub-origen').css('visibility', 'hidden').addClass('hidden');
    }
});

$('#ubicacion-destino').on('blur', function() {
    if ($('#destino-id').val() === '') {
        $('#errorGenericoubDestino').text("No reconocemos el lugar que ingresas, intenta ingresando \n\
        barrio-ciudad (ejemplo: morato bogota) o algun centro comercial, universidad o lugar reconocido que sirva de punto de referencia");
        $('#error-ub-destino').css('visibility', 'visible').hide().fadeIn().removeClass('hidden');
    } else {
        $('#error-ub-destino').css('visibility', 'hidden').addClass('hidden');
    }
});

$('#ubicacion-destino').on('keypress', function() {
    $('#destino-id').val('');
});

$('#ubicacion-origen').on('keypress', function() {
    $('#origen-id').val('');
});

$(function() {
    $("[data-hide]").on("click", function() {
        $(this).closest("." + $(this).attr("data-hide")).hide();
    });
});

