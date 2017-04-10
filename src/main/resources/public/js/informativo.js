$(window).load(function () {
    var codigo = getUrlParameter('codigo');
    var mensaje = getUrlParameter('mensaje');

    if (codigo == '000') {

        $(".error404").css("font-size", "220px");
        $("span.textodiv").text("OK");
        $("h2#encabezadoInfo").text("Proceso finalizado correctamente");
        $("h2#encabezadodesc").text("Siempre será un gusto servirte");
    } else {

        $(".error404").css("font-size", "140px");
        $("span.textodiv").text("ERROR");
        $("h2#encabezadoInfo").text("No se ha completado el proceso");
        $("h2#encabezadodesc").text("Oops, algo anda mal... intentalo de nuevo!");
    }
    if (mensaje != '' && mensaje != null) {

        $("h3.textodiv").text(mensaje);
    }

});
var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};