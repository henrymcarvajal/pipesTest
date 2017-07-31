$('#mostrarContrasena').change(function(){
	if($(this).is(':checked')){
		
			$('#contrasena').removeAttr("type", "password");
	} else {
		
		$('#contrasena').attr("type", "password");
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

function extractPhone (text)
{
  var phoneRegex= /(?:(?:\+?1\s*(?:[.-]\s*)?)?(?:\(\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\s*\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\s*(?:[.-]\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\s*(?:[.-]\s*)?([0-9]{4})(?:\s*(?:#|x\.?|ext\.?|extension)\s*(\d+))?/img;
  //alert(text.replace(phoneRegex,'*********'));
  var phone=text.match(phoneRegex);
  if(phone===null){
    text=text.split(' ').join('');
    phone=text.match(phoneRegex);
    }
   if(phone===null){
    text=text.split('-').join('');
    phone=text.match(phoneRegex);
    }
    if(phone===null){
    text=text.split('.').join('');
    phone=text.match(phoneRegex);
    }
  return phone;
}
function extractEmails (text)
{
	var phoneRegex=/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi;
    var email= text.match(phoneRegex);
	if(email===null){
    text=text.split(' ').join('');
    email=text.match(phoneRegex);
    }
   if(email===null){
    text=text.split('-').join('');
    email=text.match(phoneRegex);
    }
    if(email===null){
    text=text.split('.').join('');
    email=text.match(phoneRegex);
    }
	return email;
}

var dataSesion = {
    storeUserDataInSession: function(data, nameVariable) {
        var userObjectString = JSON.stringify(data);
        window.sessionStorage.setItem(nameVariable, userObjectString);
    },
    getUserDataFromSession: function(nameVariable) {
        var data = window.sessionStorage.getItem(nameVariable);
        return JSON.parse(data);
    }
}