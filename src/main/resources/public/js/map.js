function route(origin_place_id, destination_place_id, travel_mode,
        directionsService) {
    if (!origin_place_id || !destination_place_id) {
        return;
    }
    directionsService.route({
        origin: {'placeId': origin_place_id},
        destination: {'placeId': destination_place_id},
        travelMode: travel_mode
    }, function(response, status) {
        if (status === google.maps.DirectionsStatus.OK) {

            $('#distancia').val(response.routes[0].legs[0].distance.text);
            var path = (response.routes[0].overview_path);
            var texto = obtenerTextoCoordenadas(response) + '&capas=1Y2';
            llamadoPeajes("https://crossorigin.me/https://app.invias.gov.co:8080/viajero/webresources/listaEvento?punto=" + texto, obtenerSumaPeaje);


        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });
}

function obtenerTextoCoordenadas(response) {
    var nPoints = response.routes[0].overview_path.length;
    var points_text = "";
    for (var i = 0; i < nPoints; i++) {
        points_text += response.routes[0].overview_path[i].lng().toFixed(5) + ',' + response.routes[0].overview_path[i].lat().toFixed(5) + 'Y';
    }
    return points_text;
}

function obtenerListadoPeajes(listadoPeajes) {
    var numeroPeajes = listadoPeajes.length;
    var listaPeajes = "";
    for (var i = 0; i < numeroPeajes; i++) {
        listaPeajes += listadoPeajes[i].id_localizacion + "Y";
        ;
    }
    return listaPeajes;
}

function obtenerSumaPeaje(listadoP) {
    var listaPeaje = obtenerListadoPeajes(listadoP);
    var url = "https://crossorigin.me/https://app.invias.gov.co:8080/viajero/webresources/sumarPeaje?lstPeaje=" + listaPeaje + "&categoria=1";
    llamadoPeajes(url, llamadoCotizar);
}

function llamadoPeajes(path, callback) {
    $.ajax({
        type: 'GET',
        url: path,
        dataType: 'json',
        async: false,
        cache: false,
        success: function(data) {

            callback(data);

        },
        error: function(xhr, type) {
            // to do something
            console.log(xhr);
        }
    });
}

function llamadoCotizar(precioPeajes) {
    $('#precio-peajes').val(precioPeajes.valor);
}