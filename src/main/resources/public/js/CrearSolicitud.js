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