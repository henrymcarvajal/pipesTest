var idServicio;
var esEmpS = false;
var datosContacto = false;
$(window).load(function() {
    idServicio = getUrlParameter('id');
    $.ajax({
        type: "GET",
        url: "/servicio/" + idServicio,
        dataType: "json",
        success: function(data) {

            if (data !== null) {
                renderizarDetalleServicios(data);
            } else {
                var mensaje = 'no es posible mostrar los servicios actualmente publicados';
                var codigo = '001';
                var url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                window.location.href = url;
            }
        }
    });
});

function renderizarDetalleServicios(detalleServicio) {

    $('#descripcion-servicio').text(detalleServicio.map.descripcion);
    $('#origen').text(detalleServicio.map.origen);
    $('#destino').text(detalleServicio.map.destino);
    $('#distancia').text(detalleServicio.map.distancia);
    $('#fecha-inicio').text(detalleServicio.map.horaSalida);
    $('#fecha-fin').text(detalleServicio.map.horaLlegada);
    $('#numero-pasajeros').text(detalleServicio.map.numeroPasajeros);
    if (detalleServicio.map.redondo === true) {
        $('#ida-vuelta').text('SI');
    } else {
        $('#ida-vuelta').text('NO');
    }
    if (detalleServicio.map.disponibilidad === true) {
        $('#disponibilidad').text('SI');
    } else {
        $('#disponibilidad').text('NO');
    }


    if (detalleServicio.map.servicioGratis === '1') {
        $('#valor').prop('required', false);
        $("#servicio-tabla tbody tr.valor-cobra").hide();
        esEmpS = true;
    }
    var comentarios = detalleServicio.map.comentarios;
    if (typeof comentarios !== 'undefined') {
        renderizarMensajes(comentarios.myArrayList);
    }
}



function mostrarValor() {
    var totalOferta = parseFloat($('#valor').val());
    if (totalOferta !== null && totalOferta > 0) {
        $('#valor_oferta').text(" $ " + totalOferta.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
    } else {

        $('#valor_oferta').text(" $ 0");
    }
}


$("#boton-ofertar").click(function() {

    $('#valorServicio').val($('#valor').val());
    $('#idServicio').val(idServicio);

    if (($('#valor').val() !== "" && $('#valor').val() > 1000) || esEmpS) {
        $.ajax({
            type: "POST",
            url: "/oferta",
            data: $('#data-enviar-oferta input').serialize(),
            dataType: "json",
            success: function(data) {
                var mensaje = "";
                var titulo = "";
                var $a;
                if (data.codigo === '000') {
                    titulo = "Oferta enviada";
                    mensaje = "Tu oferta fue enviada";
                }
                if (data.codigo === '001') {
                    titulo = "Crea tu veh\u00EDculo en el sistema";
                    mensaje = "Es indispensable que subas la informaci\u00F3n de tu veh\u00EDculo para que el solicitante pueda verlo, una vez lo hagas tu oferta será enviada";
                    $('#boton-inicio').html('<center><a href id="link-vehiculo" class="btn submit-message recargar-cuenta" style ="background-color: #9de20d;color:white;">CREAR VEH\u00CDCULO</a></center>');
                    $('#link-vehiculo').attr("href", "completarInformacionRegistro.html?id=" + data.valor);
                }
                if (data.codigo === '002') {
                    titulo = "Servicio no vigente";
                    mensaje = "El servicio que ofertas ya no esta vigente.";
                }
                if (data.codigo === '003') {
                    titulo = "Error";
                    mensaje = "No se ha podido enviar la oferta, por favor escribenos a soporte@ayax.co";
                }
                if (data.codigo === '004') {
                    titulo = "Error";
                    mensaje = "Ya tienes una oferta para este servicio, solo puedes enviar una oferta por solicitud";
                }
                if (data.codigo === '005') {
                    titulo = "Error";
                    mensaje = "Para poder ofertar debes estar registrado, si estas registrado inicia sesion, si no estas registrado reg\u00EDstrate..";
                    $('#boton-inicio').append('<a href="login.html" class="btn submit-message recargar-cuenta" style ="background-color: #9de20d;color:white;margin-right:10%;">INICIAR SESI\u00D3N</a>');
                    $('#boton-inicio').append('<a href="registroTransportadores.html" class="btn submit-message recargar-cuenta" style ="background-color: #ffbf00;color:white;">REG\u00CDSTRATE</a>');
                }
                $('#titulo-informativo').text(titulo);
                $('#mensaje-informativo-texto').text(mensaje);
                $('#mensaje-informativo').modal('show');
            }
        });
    } else {
        $('#titulo-informativo').text("Valor de oferta vacia");
        $('#mensaje-informativo-texto').text("Por favor digita cuanto cobras por el servicio");
        $('#mensaje-informativo').modal('show');
    }
});

$(".preguntar-class").submit(function(event) {

    event.preventDefault();
    if (!datosContacto) {
        $.ajax({
            type: "POST",
            url: "/mensaje/servicio/" + idServicio + "/transportador",
            data: $('.preguntar-class').serialize(),
            dataType: "json",
            success: function(data) {
                var mensaje = "";
                var titulo = "";
                if (data.codigo === '000') {
                    titulo = "Pregunta enviada";
                    mensaje = "Tu pregunta ha sido creada";
                    renderizarComentario($('textarea#comentario-text').val(), undefined);
                }
                if (data.codigo === '002') {
                    titulo = "No has iniciado sesi\u00F3n";
                    mensaje = "Ingresa tu usuario y contrasena:";
                    $('#Entradas_adicionales').append("<form class='form-register' id='inicia_sesion'><div class='form-row'><div class='alert alert-danger hidden' id='divMensajeError'>\n\
                        <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>\n\
                        <strong>Error!</strong> <span id='errorMensaje'>Mensaje de error</span></div></div><div class='form-row'><label><span>Correo</span>\n\
                        <input type='email' id='usuario' placeholder='Correo' name='email-address'></label></div>\n\
                        <div class='form-row'><label><span>Contrase\u00F1a</span><input name='password' type='password' id='password' placeholder='Contrase\u00F1a' required></label></div>\n\
                        <div class='form-row'><button type='button' id='btn_inicio_sesion' onclick='iniciarSesion()' class='btn btn-success'>Iniciar Sesi\u00F3n</button></div></form>");
                }
                $('#titulo-informativo').text(titulo);
                $('#mensaje-informativo-texto').text(mensaje);
                $('#mensaje-informativo').modal('show');

            }
        });

    }
});

function iniciarSesion() {

    if ($("#usuario").val() !== '' && $("#password").val() !== '') {
        $.ajax({
            type: "POST",
            url: "/acceso",
            data: $("#inicia_sesion").serialize(),
            dataType: "json",
            success: function(data) {
                var url = "";
                var mensaje = "";
                if (data.codigo === '000') {
                    $('#mensaje-informativo').modal('hide');
                    location.reload();
                }
                if (data.codigo === '001') {
                    mensaje = "Usuario o Contrase\u00F1a incorrecta.";
                    $('#errorMensaje').text(mensaje);
                    $('#divMensajeError').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
                }
                if (data.codigo === '002') {
                    mensaje = "El usuario ingresado no existe.";
                    $('#errorMensaje').text(mensaje);
                    $('#divMensajeError').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
                }
            }
        });
    } else {
        mensaje = "Usuario y Contrase\u00F1a son obligatorios.";
        $('#errorMensaje').text(mensaje);
        $('#divMensajeError').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
    }
}

function renderizarMensajes(mensajes) {

    if (mensajes.length > 0) {
        for (var i = 0; i < mensajes.length; i++) {
            if (mensajes[i].myArrayList !== null) {
                var mensajeRespuesta=undefined;
                var mensajePregunta = mensajes[i].myArrayList[0].map.transportador;
                mensajePregunta = mensajePregunta + ': ' + mensajes[i].myArrayList[0].map.texto;
                if (mensajes[i].myArrayList.length > 1) {
                    mensajeRespuesta = mensajes[i].myArrayList[1].map.usuario;
                    mensajeRespuesta = mensajeRespuesta + ': ' + mensajes[i].myArrayList[1].map.texto;
                }
                renderizarComentario(mensajePregunta, mensajeRespuesta);
            }
        }
    }

}

function renderizarComentario(pregunta, respuesta) {
    var $seccion = $('<li>').append($('<article>').append(
            $('<p>').append('<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> &nbsp;' + pregunta)
            ),
            (respuesta !== undefined ? $('<article class="respuesta-article">').append(
                    $('<p>').append('<span class="glyphicon glyphicon-comment" aria-hidden="true"></span> &nbsp;' + respuesta)
                    ) : '')).appendTo('.listado_de_preguntas');
}


$(function() {
    $('#comentario-text').on('blur', function() {
        validarDatosContacto();
    });
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
        $('#boton-preguntar').prop('disabled', true);

    }
    if (email === null && phone === null) {
        $('#boton-preguntar').prop('disabled', false);
    }
}