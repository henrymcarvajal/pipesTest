var idServicio;
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
    $('#ida-vuelta').text(detalleServicio.map.redondo);
}



function mostrarValor(){
	var totalOferta=parseFloat($('#valor').val());
	if(totalOferta!=null && totalOferta>0){
	$('#valor_oferta').text(" $ " + totalOferta.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
	} else {
		
		$('#valor_oferta').text(" $ 0");
	}
}


 $("#boton-ofertar").click(function () {
	 
        $('#valorServicio').val($('#valor').val());
		$('#idServicio').val(idServicio);
		
        if($('#valor').val()!="" && $('#valor').val()>1000){
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
                    mensaje = "Tu oferta fue enviada exitósamente";
                }
                if (data.codigo === '001') {
                    titulo = "Recarga tu cuenta";
                    mensaje = "No posees cr\u00E9dito en tu cuenta para ofertar, por favor recarga tu cuenta e intenta de nuevo.";
                    $('#boton-recargar').html('<a href class="btn submit-message recargar-cuenta" style ="background-color: #ffbf00;"/>RECARGA TU CUENTA</a>');
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
                    mensaje = "Tu oferta no ha sido enviada, debes completar tu registro, por favor ve a completar registro";
                }
                if (data.codigo === '006') {
                    titulo = "Error";
                    mensaje = "Para poder ofertar es necesario que tengas todos los documentos en orden en la plataforma";
                }
                $('#titulo-informativo').text(titulo);
                $('#mensaje-informativo-texto').text(mensaje);
                $('#mensaje-informativo').modal('show');
            }
        });
		}else{
			$('#titulo-informativo').text("Valor de oferta vacia");
                $('#mensaje-informativo-texto').text("Por favor digita cuánto cobras por el servicio");
                $('#mensaje-informativo').modal('show');
		}
    });

