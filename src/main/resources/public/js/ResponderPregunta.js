/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var idConversacion;
var datosContacto=false;
$(window).load(function() {
    idConversacion = getUrlParameter('id');
    $.ajax({
        type: "GET",
        url: "/conversacion/" + idConversacion,
        dataType: "json",
        success: function(data) {

            if (data !== null) {
                $("#pregunta").text(data.valor.map.mensajes.myArrayList[0].map.transportador+" "+data.valor.map.mensajes.myArrayList[0].map.texto);
            } else {
                var mensaje = 'La pregunta no existe';
                var codigo = '001';
                var url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                window.location.href = url;
            }
        }
    });
});

$(".responder-class").submit(function(e) {
    e.preventDefault();
    if (!datosContacto) {
        $.ajax({
            type: "POST",
            url: "/mensaje/conversacion/" + idConversacion + "/usuario",
            data: $('.responder-class').serialize(),
            dataType: "json",
            success: function(data) {
                var mensaje;
                var codigo;
                var url;
                if(data.codigo==='000'){
                    mensaje = 'Respuesta enviada';
                    codigo = '000';
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                } else {
                    mensaje = 'Respuesta no enviada';
                    codigo = '001';
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                }
             }
        });
    }
});


function validarDatosContacto() {
    datosContacto = false;
    var str = $('#comentario-text').val();
    $('#datos-contacto').css('visibility', 'hidden').addClass('hidden');
    var email = extractEmails(str);
    var phone = extractPhone(str);

    if (email !== null || phone !== null) {
        datosContacto = true;
        $('#datos-contacto').css('visibility', 'visible').hide().fadeIn().removeClass('hidden');
        $('#boton-responder').prop('disabled', true);

    }
    if (email === null && phone === null) {
        $('#boton-responder').prop('disabled', false);
    }
}



