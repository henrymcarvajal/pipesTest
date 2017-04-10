$(window).load(function () {
    var idTransportador = getUrlParameter('id');
    $("#idTransportador").val(idTransportador);
});

$(".form-register").submit(function (event) {
	var codigo;
	var mensaje;
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "/vehiculo",
        data: $(".form-register").serialize(),
        dataType: "json",
        success: function (data) {
            switch (data.codigo) {
                case "000" :
//                    mensaje = "Te has registrado correctamente. " +
//                            "Por favor, no olvides enviar las fotos claras de: SOAT, revisi\u00F3n, seguro extra contractual y contractual, tarjeta de propiedad y operaci\u00F3n y una foto del veh\u00EDculo al \n\
//					WhatsApp 322 880-4995.";
//                    codigo = "000";
                    url = "/subirDocumentosVehiculo.html?id=" + data.valor;
                    window.location.href = url;
                    break;
                case "004" :
                    mensaje = "Actualmente no contamos con cupos para tu tipo de veh\u00EDculo, una vez ampliemos el cupo ser\u00E1s notificado por correo Electr\u00F3nico";
                    codigo = "001";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    break;
                case "002" :
                    mensaje = "Placa ya registrada.";
                    codigo = "001";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    break;
            }
        }
    });

});