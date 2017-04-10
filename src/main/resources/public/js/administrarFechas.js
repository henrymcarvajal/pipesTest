$(".formularioRegistroFechas").submit(function(e)
{
    
    
    var placa = $("#placa").val();
    var action = "/vehiculo/"+placa+"/fechas";
    e.preventDefault(); //Prevent Default action. 
    $.ajax({
        url: action,
        type: 'POST',
        data: $(".formularioRegistroFechas").serialize(),
        dataType: 'json',
        success: function (data)
        {
            var mensaje = "";
            var codigo = "";
            var url = "";

            switch (data.codigo) {
                case "000" :
                    mensaje = "Se han subido las fechas satisfactoriamente. ";
                    codigo = "000";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    return false;
                case "001" :
                    mensaje = "No ha sido posible modificar las fechas del veh\u00EDculo.";
                    codigo = "001";
                    url = "/informativo.html?codigo=" + codigo + "&mensaje=" + mensaje;
                    window.location.href = url;
                    return false;
            }
        }
    });
}); 

