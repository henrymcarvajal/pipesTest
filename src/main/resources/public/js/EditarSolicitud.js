/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var idServicio;
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

    $('#descripcion-servicio').val(detalleServicio.map.descripcion);
    $('#origen').val(detalleServicio.map.origen);
    $('#destino').val(detalleServicio.map.destino);
    $('#distancia').val(detalleServicio.map.distancia);
    var fechaHoraSalida = new Date(detalleServicio.map.horaSalida);
    var horaFormatoSalida = formatHour(fechaHoraSalida);
    $('#hora-inicio').val(horaFormatoSalida);
    var fechaFormatoSalida = formatDate(fechaHoraSalida);
    $('#fecha-inicio').val(fechaFormatoSalida);
    var fechaHoraLlegada = new Date(detalleServicio.map.horaLlegada);
    var horaFormatoRecogida = formatHour(fechaHoraLlegada);
    $('#hora-recogida').val(horaFormatoRecogida);
    var fechaFormatoLlegada = formatDate(fechaHoraLlegada);
    $('#fecha-fin').val(fechaFormatoLlegada);
    $('#numero-pasajeros').val(detalleServicio.map.numeroPasajeros);
    if (detalleServicio.map.redondo === true) {
        $('#ida-vuelta').val("true");
    } else {
        $('#ida-vuelta').val("false");
    }
    if (detalleServicio.map.disponibilidad === true) {
        $('#disponibilidad').val("true");
    } else {
        $('#disponibilidad').val("false");
    }
}

$("#actualizar_servicio").submit(function(e) {
    e.preventDefault();
    var dateSalida = $("#fecha-inicio").val() + ' at ' + $('#hora-inicio').val();
    var dateRecogida = $('#fecha-fin').val() + ' at ' + $('#hora-recogida').val();
    $("#drop-off").val(dateRecogida);
    $("#pick-up").val(dateSalida);
    if (!datosContacto) {
        $.ajax({
            type: "POST",
            url: "/servicio/editar/" + idServicio,
            data: $("#actualizar_servicio").serialize(),
            dataType: "json",
            success: function(data) {
                var mensaje = "";
                var codigo = "";
                var url = "";
                if (data.codigo === '000') {
                    mensaje = "Tu solicitud ha sido modificada";
                    codigo = "000";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                }

            }
        });
    }

});

function validarDatosContacto() {
    datosContacto = false;
    var str = $('#descripcion-servicio').val();
    $('#datos-contacto').css('visibility', 'hidden').addClass('hidden');
    var email = extractEmails(str);
    var phone = extractPhone(str);

    if (email !== null || phone !== null) {
        datosContacto = true;
        $('#datos-contacto').css('visibility', 'visible').hide().fadeIn().removeClass('hidden');
    }
}

function formatDate(fecha) {

    var day = ("0" + fecha.getDate()).slice(-2);
    var month = ("0" + (fecha.getMonth() + 1)).slice(-2);
    var fechaFormato = fecha.getFullYear() + "-" + (month) + "-" + (day);

    return fechaFormato;

}

function formatHour(fecha) {
    var hora = ("0" + fecha.getHours()).slice(-2);
    var minuto = ("0" + fecha.getMinutes()).slice(-2);
    var segundos = ("0" + fecha.getMilliseconds()).slice(-2);
    var horafinal = hora + ":" + minuto + ":" + segundos;
    return horafinal;


}
