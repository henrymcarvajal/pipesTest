var idOferta;
$(window).load(function () {
    idOferta = getUrlParameter('id');
    $.ajax({
        type: "GET",
        url: "/oferta/" + idOferta,
        dataType: "json",
        success: function (data) {
            if (data.codigo === '000') {
                var oferta = data.valor;
                var aire = (oferta.aireAcondicionado? "Si" : "No");
                var valorPesos = '$' + oferta.valorOferta.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
                $("#nombre-transportador").text(oferta.nombreTransportador);
                $("#valor-oferta").text(valorPesos);
                $("#modelo-vehiculo").text(oferta.modeloVehiculo);
                $("#marca-vehiculo").text(oferta.marcaVehiculo);
                $("#capacidad-vehiculo").text(oferta.capacidadVehiculo);
                $("#aire-vehiculo").text(aire);
                $("#servicios-ejecutados").text(oferta.serviciosEjecutados);
                document.getElementById("foto-vehiculo").src = oferta.fotoVehiculo;
                $('span.stars').stars(oferta.reputacionTransportador);
            } else {
                var mensaje = 'No es posible mostrar la oferta realizada. Por favor escr\u00EDbenos a soporte@ayax.co';
                var codigo = '001';
                var url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                window.location.href = url;
            }
        }
    });
});

$.fn.stars = function(valor) {
    return $(this).each(function() {
        // Get the value
        var val = parseFloat(valor);
        // Make sure that the value is in 0 - 5 range, multiply to get width
        var size = Math.max(0, (Math.min(5, val))) * 16;
        // Create stars holder
        var $span = $('<span />').width(size);
        // Replace the numerical value with stars
        $(this).html($span);
    });
};

$("#boton-contratar").click(function() {
  $.ajax({
        type: "GET",
        url: "/oferta/" + idOferta+"/transportador",
        dataType: "json",
        success: function (data) {
            if (data.codigo === '000') {
                var mensaje = 'El transportador ser\u00E1 notificado, una vez todo este listo a tu correo llegar\u00E1n los datos del transportador';
                var codigo = '000';
            } else {
                var mensaje = 'No es posible mostrar la oferta realizada. Por favor escr\u00EDbenos a soporte@ayax.co';
                var codigo = '001';
            }
   var url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
            window.location.href = url;
        }
    });
});


