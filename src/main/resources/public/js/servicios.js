
var satendidos;

$(window).load(function () {
    var idTransportador = getUrlParameter('id');
    var nombreUsuario = getUrlParameter('nombreUsuario');
    var valorRecargado = getUrlParameter('valorRecargado');
    satendidos= getUrlParameter('ser');

    if (idTransportador !== '' && typeof idTransportador !== 'undefined' && idTransportador !== null) {
        $('#nombreUsuario').text(nombreUsuario);
        $('#valorRecargado').text("$ " + valorRecargado);
    } else {
        $('#usuario-sesion').css('display', 'none');
        $('#cerrar-sesion').css('display', 'none');        
    }

    $.ajax({
        type: "GET",
        url: "/servicio",
        dataType: "json",
        success: function (data) {
            
            if(data !== null && data.codigo==='000'){
                renderizarServicios(idTransportador, data);
            }else{
                var mensaje = 'no es posible mostrar los servicios actualmente publicados';
                var codigo = '001';
                var url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                window.location.href = url;
            }
        }
});
});

    $("#cerrar-sesion").click(function () {
        $.ajax({
            type: "GET",
            url: "/salida",
            dataType: "json",
            success: function (data) {
                var url = "";
                var mensaje = "";
                var codigo = "";
                if (data.codigo === '000') {
                    url = "/login.html";
                } else {

                    mensaje = 'No es posible cerra sesi\u00F3n, por favor reportanos el error a soporte@ayax.co';
                    codigo = '001';
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                }
                window.location.href = url;
            }
        });
        return false;
    });
    
var gastoCombustible;
var gastoLlantas;
var gastoAceite;
var gastoFrenos;
var costoSeguroObligatorio;
var costoTecnomecanica;
var costoSegurosAdicionales;
var devaluacionVehiculo;

function renderizarServicios(id, response) {
    var indice = 1;

    $(function () {
        $.each(response.valor.myArrayList, function (i, item) {
            item = item.map;
            var idayvuelta = (item.redondo === 'true')? "si" : "no";
            //var link = (item.idTransportador === id)? "$ " + item.valorOferta : '<a href="#modal-ofertar" data-distancia="' + item.distancia + '" data-id="' + item.id + '" data-toggle="modal" class="btn btn-success ofertar-servicio"> Ofertar </a>';
            var link='<a target="_blank" href="/detalleServicio.html?id='+item.id+'"> ver Detalle</a>';
			var $tr = $('<tr>').append(
                    $('<td>').text(indice++),
                    $('<td>').text(item.origen),
                    $('<td>').text(item.horaSalida),
                    $('<td>').text(item.numeroPasajeros),
                    $('<td>').append(link)
                    ).appendTo('#records_table');
        });
    });
}
//
//$(document).on("click", ".ofertar-servicio", function () {
//
//    var rendimientoGasolina = 35;
//    var precioGalon = 8000;
//    var distanciaCambioLlantas = 60000;
//    var precioLlantas = 1600000;
//    var distanciaCambioAceite = 5000;
//    var precioAceite = 180000;
//    var distanciaCambioFrenos = 10000;
//    var precioFrenos = 150000;
//    var distanciaAnual = 50000;
//    var precioSeguroObligatorio = 1100000;
//    var precioTecnomecanica = 150000;
//    var preciosSegurosAdicionales = 600000;
//    var devaluacionPromedioAnual = 4000000;
//
//    var distancia = $(this).data('distancia');
//
//    gastoCombustible = Math.round((parseInt(distancia) / rendimientoGasolina) * precioGalon);
//    gastoLlantas = Math.round((parseInt(distancia) / distanciaCambioLlantas) * precioLlantas);
//    gastoAceite = Math.round((parseInt(distancia) / distanciaCambioAceite) * precioAceite);
//    gastoFrenos = Math.round((parseInt(distancia) / distanciaCambioFrenos) * precioFrenos);
//    costoSeguroObligatorio = Math.round((parseInt(distancia) / distanciaAnual) * precioSeguroObligatorio);
//    costoTecnomecanica = Math.round((parseInt(distancia) / distanciaAnual) * precioTecnomecanica);
//    costoSegurosAdicionales = Math.round((parseInt(distancia) / distanciaAnual) * preciosSegurosAdicionales);
//    devaluacionVehiculo = Math.round((parseInt(distancia) / distanciaAnual) * devaluacionPromedioAnual);
//    var id = $(this).data('id');
//
//    $('.modal-body #distancia').val(distancia);
//    $('#idServicio').val(id);
//    $('.modal-body #verDistancia').text(distancia);
//    $('.modal-body #resultadoKms').text("$ " + gastoCombustible);
//    $('.modal-body #desgasteLlantas').text("$ " + gastoLlantas);
//    $('.modal-body #desgasteAceite').text("$ " + gastoAceite);
//    $('.modal-body #desgasteFrenos').text("$ " + gastoFrenos);
//    $('.modal-body #resultadoSeguroObligatorio').text("$ " + costoSeguroObligatorio);
//    $('.modal-body #resultadoTecnomecanica').text("$ " + costoTecnomecanica);
//    $('.modal-body #resultadosSegurosAdicional').text("$ " + costoSegurosAdicionales);
//    $('.modal-body #devaluacion').text("$ " + devaluacionVehiculo);
//    $('.modal-body #ofertaEnviar').text(" $0 ");
//    $('.modal-body #resultadoRetencion').text("$ 0");
//    $('.modal-body #salarioConductor').text("$ 0");
//    $('.modal-body #gananciaTotal').text("$ 0");
//
//});


//
//function calcularValores() {
//    var precioOfertar = parseInt($('#precioOfertar').val());
//    var totalOferta=0;
//    var viaticos=parseInt($('#viaticos').val());
//    var peajes =parseInt($('#peajes').val());
//    var retenciones = Math.round(precioOfertar * (4 / 100));
//    var pagoConductor = Math.round(precioOfertar * (25 / 100));
//    var gananciaTotal = precioOfertar - gastoCombustible - gastoLlantas - gastoFrenos - gastoAceite
//            - costoSeguroObligatorio - costoTecnomecanica - costoSegurosAdicionales - retenciones - pagoConductor - devaluacionVehiculo-viaticos-peajes;
//    var comision = 0;
//    var informacion;
//    if (satendidos !== null && satendidos !== "" && satendidos > 0) {
//        informacion="Ten en cuenta que al valor que estas ofertando se le suma un monto que corresponde al pago por el servicio prestado por Ayax, para ser enviado al solicitante,\n\
//        en este caso tu propuesta ser\u00E1 enviada por : ";
//        if (precioOfertar <= 180000) {
//            comision = (20 * precioOfertar) / 180000;
//        } else {
//            comision = 20;
//        }
//        comision = ((precioOfertar * comision) / 100);
//    } else {
//        informacion="Por ser tu primer servicio el valor que ofertas ser\u00E1 enviado sin adicionar el pago a Ayax. El valor a enviar al solicitante es: ";
//    }
//    totalOferta=parseFloat(comision)+parseFloat(precioOfertar);
//    $('.modal-body #ofertaEnviar').text(informacion+" $ " + totalOferta.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
//    $('.modal-body #resultadoRetencion').text("$ " + retenciones);
//    $('.modal-body #salarioConductor').text("$ " + pagoConductor);
//    $('.modal-body #gananciaTotal').text("$ " + gananciaTotal);
//}