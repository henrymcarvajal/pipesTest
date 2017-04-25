 var idVehiculo;
 var esPlaca=false;
 var estado;
var waitingDialog = waitingDialog || (function ($) {
    'use strict';

	// Creating modal dialog's DOM
	var $dialog = $(
		'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
		'<div class="modal-dialog modal-m">' +
		'<div class="modal-content">' +
			'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
			'<div class="modal-body">' +
				'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
			'</div>' +
		'</div></div></div>');

	return {
		/**
		 * Opens our dialog
		 * @param message Custom message
		 * @param options Custom options:
		 * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
		 * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
		 */
		show: function (message, options) {
			// Assigning defaults
			if (typeof options === 'undefined') {
				options = {};
			}
			if (typeof message === 'undefined') {
				message = 'Loading';
			}
			var settings = $.extend({
				dialogSize: 'm',
				progressType: '',
				onHide: null // This callback runs after the dialog was hidden
			}, options);

			// Configuring dialog
			$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
			$dialog.find('.progress-bar').attr('class', 'progress-bar');
			if (settings.progressType) {
				$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
			}
			$dialog.find('h3').text(message);
			// Adding callbacks
			if (typeof settings.onHide === 'function') {
				$dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
					settings.onHide.call($dialog);
				});
			}
			// Opening dialog
			$dialog.modal();
		},
		/**
		 * Closes dialog
		 */
		hide: function () {
			$dialog.modal('hide');
		}
	};

})(jQuery);


$(window).load(function () {
    idVehiculo = getUrlParameter('id');
    estado=getUrlParameter('estado');
    if (typeof idVehiculo === 'undefined'){
        $('#placa').css('display','block');
        $('#Vplaca').prop('required',true);
        idVehiculo='placa';
        esPlaca=true;
    }
});

$(".form-register-vehiculo").submit(function(e)
{
    var formData = new FormData(this);
    if (esPlaca) {
        idVehiculo = idVehiculo + $('#Vplaca').val();
    }
    waitingDialog.show('Esto puede tardar unos minutos... No cierre esta ventana!');
    $.ajax({
        url: "/vehiculo/"+idVehiculo+"/documentos",
        type: 'POST',
        data: formData,
        dataType: 'json',
        mimeType: "multipart/form-data",
        contentType: false,
        cache: false,
        processData: false,
        success: function (data)
        {
            var mensaje = "";
            var codigo = "";
            var url = "";

            switch (data.codigo) {
                case "000" :
                    if(estado==='1'){
                        mensaje = "Se ha creado tu veh\u00EDculo y env\u00EDado la oferta.";
                    }else if(estado==='2'){
                        mensaje = "Se ha creado tu veh\u00EDculo en el sistema, pero tu oferta no fue enviada.";
                    }else{
                        mensaje="Tu veh\u00EDculo fue creado en el sistema. Ya puedes ofertar."
                    }
                    codigo = "000";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    return false;
					break;
                case "001" :
                    mensaje = "No ha sido crear el veh\u00EDculo. \n\
                    Por favor, escribenos a soporte@ayax.co";
                    codigo = "001";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    return false;
                    break;
                case "002" :
                    mensaje = "No se ha encontrado ning\u00FAn veh\u00EDculo con dicha placa o Id";
                    codigo = "001";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    return false;
                    break;    
            }
        },
        error: function (jqXHR, textStatus, errorThrown)
        {
        }
    });
    e.preventDefault(); //Prevent Default action. 
}); 

$('input[type=file]').change(function () {

    var id = $(this).attr('id');
//    if (id === "fTecnot")
//        $('#tecnomecanica').val($(this).val());
//    if (id === "fSoat")
//        $('#soat').val($(this).val());
//    if (id === "fScontrac")
//        $('#contractual').val($(this).val());
//    if (id === "fSecontrac")
//        $('#econtractual').val($(this).val());
//    if (id === "Tpropiedad")
//        $('#ta-propiedad').val($(this).val());
//     if (id === "fToperacion")
//        $('#t-operacion').val($(this).val());
    if (id === "Fvehiculo")
        $('#F-vehiculo').val($(this).val());
   
});
