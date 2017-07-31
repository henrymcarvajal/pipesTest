var confirma_contrasena=$('#confirm-password')[0];
//document.getElementById("confirm-password");
var contrasena=$('#password');
//document.getElementById("password");
$("#formularioRegistro").submit(function (event) {

    event.preventDefault();
    if ($("#terminos").is(':checked')) {
        $.ajax({
            type: "POST",
            url: "/transportador",
            data: $("#formularioRegistro").serialize(),
            dataType: "json",
            success: function (data) {
                var mensaje = "";
                var codigo = "";
                var url = "";

                switch (data.codigo) {
                    case "000" :
                        mensaje = "Te has registrado correctamente. " +
                                "Por favor, no olvides revisar tu correo para poder terminar el proceso de registro y poder ofertar.";
                        codigo = "000";
                        url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                        window.location.href = url;
                        return false;
						break;
                    case "001" :
                        mensaje = "Tu registro ha fallado. Por favor, escr\u00EDbenos a soporte@ayax.co";
                        codigo = "000";
                        url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                        window.location.href = url;
                        return false;
						break;
                    case "002" :
                        $('#confirmMessage').text("El email/n\u00FAmero ingresado ya se encuentra registrado");
                        $('#divMensajeEmail').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(2000).fadeOut();
                        return false;
						break;
                    case "003" :
                        $('#confirmMessageContacto').text("El n\u00FAmero ingresado ya se encuentra registrado");
                        $('#divMensajeContacto').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(2000).fadeOut();
                        return false;
						break;
                }
            }
        });
    } else {
        $('#errorGenerico').text("Debes aceptar los t\u00E9rminos y condiciones");
        $('#terminosFalta').css('visibility', 'visible').hide().fadeIn().removeClass('hidden').delay(2000).fadeOut();
        return false;
    }

});

$('#confirm-password').keyup(function(){
    validarContrasena();
});

function validarContrasena(){
    if($('#confirm-password').val() !== $('#password').val()){
        confirma_contrasena.setCustomValidity("las contrase\u00f1as no coinciden");
    }else{
        confirma_contrasena.setCustomValidity('');
    }
}