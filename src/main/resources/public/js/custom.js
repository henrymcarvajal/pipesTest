// Custom JS for the Theme

// Config 
//-------------------------------------------------------------

var companyName = "Ayax"; // Enter your event title
var datosContacto=true;


// Initialize Tooltip  
//-------------------------------------------------------------

$('.my-tooltip').tooltip();



// Initialize jQuery Placeholder  
//-------------------------------------------------------------

$('input, textarea').placeholder();



// Toggle Header / Nav  
//-------------------------------------------------------------

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
$(document).on("scroll",function(){
  if($(document).scrollTop()>39){
    $("header").removeClass("large").addClass("small");
  }
  else{
    $("header").removeClass("small").addClass("large");
  }
});



$(function(){
  $('#text_detalle').on('blur',function(){
     datosContacto=true
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


// Vehicles Tabs / Slider  
//-------------------------------------------------------------

$(".vehicle-data").hide();
var activeVehicleData = $(".vehicle-nav .active a").attr("href");
$(activeVehicleData).show();

$('.vehicle-nav-scroll').click(function(){
    var direction = $(this).data('direction');
    var scrollHeight = $('.vehicle-nav li').height() + 1;
    var navHeight = $('#vehicle-nav-container').height() + 1;
    var actTopPos = $(".vehicle-nav").position().top;
    var navChildHeight = $('#vehicle-nav-container').find('.vehicle-nav').height();
    var x = -(navChildHeight - navHeight);

    var fullHeight = 0;
    $('.vehicle-nav li').each(function() {
        fullHeight += scrollHeight;
    });

    navHeight = fullHeight - navHeight + scrollHeight;

    // Scroll Down
    if ((direction == 'down') && (actTopPos > x) && (-navHeight <= (actTopPos - (scrollHeight * 2)))) {
        topPos = actTopPos - scrollHeight;
        $(".vehicle-nav").css('top', topPos);
    }

    // Scroll Up
    if (direction == 'up' && 0 > actTopPos) {
        topPos = actTopPos + scrollHeight;
        $(".vehicle-nav").css('top', topPos);
    }

    return false;
});




$(".vehicle-nav li").on("click", function(){

  $(".vehicle-nav .active").removeClass("active");
  $(this).addClass('active');

  $(activeVehicleData).fadeOut( "slow", function() {
    activeVehicleData = $(".vehicle-nav .active a").attr("href");
    $(activeVehicleData).fadeIn("slow", function() {});
  });

  return false;
});



// Vehicles Responsive Nav  
//-------------------------------------------------------------

$("<div />").appendTo("#vehicle-nav-container").addClass("styled-select-vehicle-data");
$("<select />").appendTo(".styled-select-vehicle-data").addClass("vehicle-data-select");
$("#vehicle-nav-container a").each(function() {
  var el = $(this);
  $("<option />", {
    "value"   : el.attr("href"),
    "text"    : el.text()
  }).appendTo("#vehicle-nav-container select");
});

$(".vehicle-data-select").change(function(){
  $(activeVehicleData).fadeOut( "slow", function() {
    activeVehicleData = $(".vehicle-data-select").val();
    $(activeVehicleData).fadeIn("slow", function() {});
  });

  return false;
});



// Initialize Datepicker
//-------------------------------------------------------------------------------
var nowTemp = new Date();
var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

var checkin = $('#pick-up-date').datepicker({
    onRender: function (date) {
        return date.valueOf() < now.valueOf() ? 'disabled' : '';
    }
}).on('changeDate', function (ev) {
    if (ev.date.valueOf() >= checkout.date.valueOf()) {
        var newDate = new Date(ev.date)
        newDate.setDate(newDate.getDate());
        checkout.setValue(newDate);
    }
    checkin.hide();
    //$('#drop-off-date')[0].focus();
}).data('datepicker');
var checkout = $('#drop-off-date').datepicker({
    onRender: function (date) {
        return date.valueOf() <= checkin.date.valueOf()-1 ? 'disabled' : '';
    }
}).on('changeDate', function (ev) {
    checkout.hide();
}).data('datepicker');



// Toggle Drop-Off Location
//-------------------------------------------------------------------------------
/*$(".input-group.drop-off").hide();
$(".different-drop-off").on("click", function(){
	$(".input-group.drop-off").toggle();
  $(".autocomplete-suggestions").css("width", $('.pick-up .autocomplete-location').outerWidth());
  return false;
});*/



// Scroll to Top Button
//-------------------------------------------------------------------------------

$(window).scroll(function(){
  if ($(this).scrollTop() > 100) {
    $('.scrollup').removeClass("animated fadeOutRight");
    $('.scrollup').fadeIn().addClass("animated fadeInRight");
  } else {
    $('.scrollup').removeClass("animated fadeInRight");
    $('.scrollup').fadeOut().addClass("animated fadeOutRight");
  }
});

$('.scrollup, .navbar-brand').click(function(){
  $("html, body").animate({ scrollTop: 0 }, 'slow', function(){
    $("nav li a").removeClass('active');
  });
  return false;
});


// Scroll To Animation
//-------------------------------------------------------------------------------

var scrollTo = $(".scroll-to");

scrollTo.click( function(event) {
  $('.modal').modal('hide');
  var position = $(document).scrollTop();
  var scrollOffset = 110;

  if(position < 39)
  {
    scrollOffset = 260;
  }

  var marker = $(this).attr('href');
  $('html, body').animate({ scrollTop: $(marker).offset().top - scrollOffset}, 'slow');

  return false;
});

// Car Select Form
//-------------------------------------------------------------------------------

$( "#car-select-form" ).submit(function(event) {

  event.preventDefault();
  var selectedCar = $("#car-select").find(":selected").text();
  var selectedCarVal = $("#car-select").find(":selected").val();
  var selectedCarImage = $("#car-select").val();
  var pickupLocation = $("#origin-input").val();
  var dropoffLocation = $("#destination-input").val();
  var pickUpDate = $("#pick-up-date").val();
  var pickUpTime = $("#pick-up-time").val();
  var dropOffDate = $("#drop-off-date").val();
  var dropOffTime = $("#drop-off-time").val();
  $('#terminos').prop('checked', false);
  $('#usuarioRegistrado').prop('checked', false);
  $('.checkout-personal-info').css('display','block');
  $('#divcodigo').css('display','none');
  $('#codigoautorizacion').prop('required',false);

  var error = 0;

  if(validateNotEmpty(selectedCarVal)) { error = 1; }
  if(validateNotEmpty(pickupLocation)) { error = 1; }
  if(validateNotEmpty(dropoffLocation)) { error = 1; }
  if(validateNotEmpty(pickUpDate)) { error = 1; }
  if(validateNotEmpty(dropOffDate)) { error = 1; }

  if(0 == error)
  {

    $("#selected-car-ph").html(selectedCar);
    $("#selected-car").val(selectedCar);
    $("#selected-vehicle-image").attr('src', selectedCarImage);

    $("#pickup-location-ph").html(pickupLocation);
    $("#pickup-location").val(pickupLocation);
    
    if("" == dropoffLocation)
    {
      $("#dropoff-location-ph").html(pickupLocation);
      $("#dropoff-location").val(pickupLocation);
    }
    else
    {
      $("#dropoff-location-ph").html(dropoffLocation);
      $("#dropoff-location").val(dropoffLocation);
    }
    
    $("#pick-up-date-ph").html(pickUpDate);
    $("#pick-up-time-ph").html(pickUpTime);
    $("#pick-up").val(pickUpDate+' at '+pickUpTime);

    $("#drop-off-date-ph").html(dropOffDate);
    $("#drop-off-time-ph").html(dropOffTime);
    $("#drop-off").val(dropOffDate+' at '+dropOffTime);

    $('#checkoutModal').modal();
  }
  else
  {
    $('#car-select-form-msg').css('visibility','visible').hide().fadeIn().removeClass('hidden').delay(2000).fadeOut();
  }

  return false;
});



// Check Out Form
//-------------------------------------------------------------------------------

$("#check1").on("click", function(){
        var id = parseInt($(this).val(), 10);
        if($(this).is(":checked")) {
            if($("#check2").is(":checked")){
				
				$("#check2").prop( "checked", false );
			}
			$('#input_empresa').css('display','block');
			$("#last-name").prop('required', true);
			$('#email-address').prop('required',true);
			$('#phone-number').prop('required',true);
			$('#first-name').prop('required',true);
		} 
});
$("#check2").on("click", function(){
        var id = parseInt($(this).val(), 10);
        if($(this).is(":checked")) {
            if($("#check1").is(":checked")){
				
				$("#check1").prop( "checked", false );
			}
			$("#last-name").val('');
			$("#last-name").prop('required', false);
			$('#input_empresa').css('display','none');
			$('#email-address').prop('required',true);
			$('#phone-number').prop('required',true);
			$('#first-name').prop('required',true);
		} 
});
	
$( "#checkout-form" ).submit(function(event) {
	
  event.preventDefault();
  $('#checkout-form-msg').addClass('hidden');
  $('#checkout-form-msg').removeClass('alert-success');
  $('#checkout-form-msg').removeClass('alert-danger');
  $('#checkout-form input[type=submit]').attr('disabled', 'disabled');
  
  var continuar = false;
  var terminos= false;
 
 if($("#check1").is(":checked") || $("#check2").is(":checked") || $('#usuarioRegistrado').is(":checked")){
		
		continuar= true;
  
}
if($('#terminos').is(":checked") && datosContacto){
		
    terminos= true;
  
}

if(continuar && terminos){
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
	  $('#errorGenerico').text('Debe seleccionar si la solicitud es hecha por una empresa.');
  }
  

return false;
});

 
// Not Empty Validator Function
//-------------------------------------------------------------------------------

function validateNotEmpty(data){
  if (data == ''){
    return true;
  }else{
    return false;
  }
}


var distanciaOriginal=0;
var distanciaIdayVuelta=0;

$("#checkoutModalLabel").on("click", function(){
		
		
		if($('#distancia').val()!=="" && distanciaOriginal < parseFloat($('#distancia').val())){
		
			distanciaOriginal= $('#distancia').val();
			distanciaIdayVuelta= 2*parseFloat(distanciaOriginal);
		}
		
		if ($('#idayvuelta').is(":checked")){
			
			$('#div-ida-vuelta').css('display','block');
			$('#distancia').val(distanciaIdayVuelta);
			$('#distancia-ph').text(distanciaIdayVuelta+' kms');
			$('#idayvueltas').val("true");
		} else {
			
			$('#div-ida-vuelta').css('display','none');			
			$('#con-idayvueltas').val("false");
			$('#distancia').val(distanciaOriginal);
			$('#distancia-ph').text(distanciaOriginal);
		}
				
		if ($('#disponibilidad').is(":checked")){
			
			$('#div-disponibilidad').css('display','block');
			$('#con-disponibilidad').val("true");
		} else {
			
			$('#div-disponibilidad').css('display','none');
			$('#con-disponibilidad').val("false");
		}

});


$( "#newsletter-form" ).submit(function(event) {
	
    event.preventDefault();
    $.ajax({
    type: "POST",
    url: "/suscriptor",
    data: $("#newsletter-form").serialize(),
    dataType: "json",
    success: function(data) {
        var mensaje = 'No es posible mostrar la oferta realizada. Por favor escr\u00EDbenos a soporte@ayax.co';
        var codigo = '001';
        if (data.codigo === '000') {
            mensaje = 'Tu correo ha sido almacenado correctamente, mediante este medio te llegar\u00E1n novedades y ofertas del servicio.';
            codigo = '000';
        }else{
            mensaje = 'No ha sido posible almacenar tu correo. Por favor escr\u00EDbenos a soporte@ayax.co';
            codigo = '001';
        }
        var url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
        window.location.href = url;
    }
});
});


