var idServicio;
var esEmpS = false;
$(window).load(function () {
    idServicio = getUrlParameter('id');
    $.ajax({
        type: "GET",
        url: "/servicio/" + idServicio,
        dataType: "json",
        success: function (data) {

            if (data !== null) {
                renderizarDetalleServicios(data);
            } else {
                alert("hay un problema");
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
    }else {
         $('#ida-vuelta').text('NO');
    }
    

    if (detalleServicio.map.servicioGratis === '1') {
        $('#valor').prop('required', false);
        $("#servicio-tabla tbody tr.valor-cobra").hide();
        esEmpS = true;
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


$("#boton-ofertar").click(function () {

    $('#valorServicio').val($('#valor').val());
    $('#idServicio').val(idServicio);

    if (($('#valor').val() !== "" && $('#valor').val() > 1000) || esEmpS) {
        $.ajax({
            type: "POST",
            url: "/oferta",
            data: $('#data-enviar-oferta input').serialize(),
            dataType: "json",
            success: function (data) {
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
                    $('#link-vehiculo').attr("href", "completarInformacionRegistro.html?id="+data.valor);
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

