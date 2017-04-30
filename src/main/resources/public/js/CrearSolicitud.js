var datosContacto=true;
var terminos=false;
$( document ).ready(function() {
    $('#fechaInicio').datetimepicker();
    $('#fechaFin').datetimepicker();
});
$("#usuarioRegistrado").on("click", function(){
	
	if ($('#usuarioRegistrado').is(":checked")){
		
		$('#divUsuarioRegistrado').css('display','block');
                $('#divUsuarioNoRegistrado').css('display','none');
                $('#email-address').prop('required',false);
		$('#phone-number').prop('required',false);
		$('#first-name').prop('required',false);
		$('#codigoautorizacion').prop('required',true);
	} else {
		
		$('#divUsuarioRegistrado').css('display','none');
		$('#codigoautorizacion').prop('required',false);
                $('#divUsuarioNoRegistrado').css('display','block');
                $('#email-address').prop('required',true);
		$('#phone-number').prop('required',true);
		$('#first-name').prop('required',true);
	}
});

$( "#crear-solicitud" ).submit(function(event) {
	
  event.preventDefault();
 
if($('#terminos').is(":checked") && datosContacto){
		
    terminos= true;
  
}

if(terminos){
  waitingDialog.show('Estamos procesando tu solicitud... No cierre esta ventana!');
  $.ajax({
    type: "POST",
    url: "/servicio",
    data: $("#checkout-form").serialize(),
    dataType: "json",
    success: function(data) {
		var mensaje="";
		var codigo="";
		var url="";
		if(data.codigo==='000'){
			mensaje="Tu solicitud ha sido creada, por ser tu primer servicio creado, uno de nuestros asesores te contactar\u00E1";
			codigo="000";
			url="/informativo.html?codigo="+codigo+"&mensaje="+mensaje;
			window.location.href = url;
		}
		if(data.codigo==='001'){
			mensaje="Tu solicitud ha sido creada, recuerda estar atento al correo,dado que las ofertas llegar\u00E1n mediante este medio";
			codigo="000";
			url="/informativo.html?codigo="+codigo+"&mensaje="+mensaje;
			window.location.href = url;
		}
		if(data.codigo==='002'){
			mensaje="No se ha podido crear la solicitud, intentalo de nuevo, si ha sucedido antes escr\u00EDbenos a soporte@ayax.co ";
			codigo="001";
			url="/informativo.html?codigo="+codigo+"&mensaje="+mensaje;
			window.location.href = url;
		}
		if(data.codigo==='003'){
			
			waitingDialog.hide();
			$('#errorMensaje').text("Usuario ingresado no existe");
			$('#divMensajeError_usuario').css('visibility','visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
			
		}
                
                if(data.codigo==='004'){
			
			waitingDialog.hide();
			$('#errorMensaje').text("Correo,nit o c\u00E9dula ya registrada, utiliza tu c\u00F3digo de autorizaci\u00F3n, solicita el servicio como usuario Registrado");
			$('#divMensajeError_usuario').css('visibility','visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
			
		}
                if(data.codigo==='005'){
			
			waitingDialog.hide();
			$('#errorMensaje').text("C\u00F3digo promocional no existe, si no posee c\u00F3digo dejar vac\u00EDo");
			$('#divMensajeError_usuario').css('visibility','visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
			
		}
	
    }
});

}else if(!terminos){
	  
	  $('#tipo-servicio-falta').css('visibility','visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
	  $('#errorGenerico').text('Debes aceptar los t\u00E9rminos y condiciones.');
  }else{
	  
	  $('#tipo-servicio-falta').css('visibility','visible').hide().fadeIn().removeClass('hidden').delay(3000).fadeOut();
	  $('#errorGenerico').text('Campo mal diligenciado.');
  }
  

return false;
});

$(function(){
  $('#text_detalle').on('blur',function(){
     datosContacto=true;
     var str = $('#text_detalle').val();
     $('#datos-contacto').css('visibility','hidden').addClass('hidden');
     var email = extractEmails(str);
     var phone = extractPhone(str);
   
     if(email!=null || phone!=null){
		 $('#datos-contacto').css('visibility','visible').hide().fadeIn().removeClass('hidden');
		 datosContacto=false;
		 
	 }
  });
});

function extractPhone (text)
{
  
  return text.match( /\d+/);
}
function extractEmails (text)
{
    return text.match(/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi);

}