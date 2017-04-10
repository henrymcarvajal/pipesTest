$(window).load(function () {
    $("#usuario").val(getUrlParameter('id'));
});

$("#recuperarContra").submit(function (event) {

    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "/recuperarContrasena",
        data: $("#recuperarContra").serialize(),
        dataType: "json",
        success: function (data) {
            var mensaje = "";
            var codigo = "";
            var url = "";
            switch (data.codigo) {
                case '000':
                    mensaje = "Se ha enviado un correo a la cuenta registrada para que cambies tu contrase\u00F1a";
                    codigo = "000";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    break;
                case '001':
                    mensaje = "El usuario ingresado no tiene una cuenta asociada.";
                    $('#mensajeError').text(mensaje);
                    $('#mensajeError').css("visibility", "visible");
                    break;
                case  '002':
                    mensaje = "Ha ocurrido un error. Por favor, reportanos el error a soporte@ayax.co";
                    codigo = "001";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    break;
            }
        }
    });

});

$("#cambiar-contrasena").submit(function (event) {
    event.preventDefault();

    $.ajax({
        type: "PUT",
        url: "/acceso",
        data: $("#cambiar-contrasena").serialize(),
        dataType: "json",
        success: function (data) {
            var mensaje = "";
            var codigo = "";
            var url = "";
            if (data.codigo === '000') {
                mensaje = "Tu contrase\u00F1a ha sido cambiada satisfactoriamente";
                codigo = "000";
                url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                window.location.href = url;
            }

            if (data.codigo === '001') {
                mensaje = "Ha ocurrido un error, por favor reportanos el error a soporte@ayax.co";
                codigo = "001";
                url = "/informativo.html?codigo=" + data.codigo + "&mensaje=" + data.resultado;//mensaje;
                window.location.href = url;
            }
        }
    });
});