/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var rol;
var id;
var calificacion;
$(window).load(function () {

    rol = getUrlParameter('rol');
    id = getUrlParameter('id');

});

$('.star').click(function () {

    switch (event.target.id) {
        case 'star1':
            calificacion = 1;
            break;
        case 'star2':
            calificacion = 2;
            break;
        case 'star3':
            calificacion = 3;
            break;
        case 'star4':
            calificacion = 4;
            break;
        case 'star5':
            calificacion = 5;
            break;
    }
    var path;
    if (rol === "0") {
        path = "/oferta/" + id + "/transportador/calificacion";
    }
    else {
        path = "/oferta/" + id + "/usuario/calificacion";
    }

    $.ajax({
        type: "POST",
        url: path,
        dataType: "json",
        data: {"calificacion":calificacion},
        success: function (data) {
            var mensaje;
            var codigo;
            var url;
            if (data.codigo === "000") {
                mensaje = 'Tu calificaci\u00F3n fue registrada de forma exitosa.%0AVuelve pronto!';
                codigo = '000';
            } else if (data.codigo === "003") {
                mensaje = 'El transportador no tiene servicios asociados.Por favor, escr\u00EDbenos a soporte@ayax.co';
                codigo = '001';
            }
            else {
                mensaje = 'Ha ocurrido un error en la calificaci\u00F3n. Por favor, escr\u00EDbenos a soporte@ayax.co';
                codigo = '001';
            }
            url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
            window.location.href = url;
        }
    });
});


