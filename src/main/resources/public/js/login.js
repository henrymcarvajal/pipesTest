function capLock(e) {
    kc = e.keyCode ? e.keyCode : e.which;
    sk = e.shiftKey ? e.shiftKey : ((kc == 16) ? true : false);
    if (((kc >= 65 && kc <= 90) && !sk) || ((kc >= 97 && kc <= 122) && sk))
        document.getElementById('divMayus').style.visibility = 'visible';
    else
        document.getElementById('divMayus').style.visibility = 'hidden';
}

$(".form-signin").submit(function(event) {

    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "/acceso",
        data: $(".form-signin").serialize(),
        dataType: "json",
        success: function(data) {
            var url = "";
            var mensaje = "";
            if (data.codigo === '000') {
                url = "/servicios.html?id=" + data.valor.id + "&nombreUsuario=" + data.valor.nombre + "&valorRecargado=" + data.valor.credito + "&ser=" + data.valor.serviciosAtendidos;
                window.location.href = url;
                return false;
            }
            if (data.codigo === '001') {
                mensaje = "Usuario o Contrase\u00F1a incorrecta.";
                $('#errorMensaje').text(mensaje);
                $('#divMensajeError').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
                return false;
            }
            if (data.codigo === '002') {
                mensaje = "El usuario ingresado no existe.";
                $('#errorMensaje').text(mensaje);
                $('#divMensajeError').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
                return false;
            }
        }
    });
});