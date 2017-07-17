
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Ayax</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap -->
        <link href="css/animate.css" rel="stylesheet">

        <!-- Google Font Lato -->
        <link href='https://fonts.googleapis.com/css?family=Lato:400,700,900,400italic,700italic,900italic' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="css/font-awesome.min.css">

        <!-- Plugin Styles -->
        <link href="css/datepicker.css" rel="stylesheet">

        <link href="css/map.css" rel="stylesheet">
        <!-- Main Styles -->
        <link href="css/styles.css" rel="stylesheet">
        <!-- Available main styles: styles-red.css, styles-green.css -->
        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="img/ico/apple-touch-icon-57-precomposed.png">
        <link rel="shortcut icon" href="img/ico/ayax.ico">
        <script src="push/front-end/main.js"></script> 
       
</head>
<body id="top" data-spy="scroll" data-target=".navbar" data-offset="260">

<button disabled class="js-push-btn mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect">
        Enable Push Messaging
      </button>
    <!-- Header start -->
    <!-- 12/12/2016 11:29 pm AYAX-->
    <header data-spy="affix" data-offset-top="39" data-offset-bottom="0" class="large">
     
        <div class="row container box">
            <div class="col-md-5">
                <!-- Logo start -->
                <div class="brand">
                    <h1><a class="scroll-to" href="#top"><img class="img-responsive" src="img/logo.png" alt="Ayax"></a></h1>
                </div>
                <!-- Logo end -->
            </div>

            <div class="col-md-7">
                <div class="pull-right">
                    <div class="header-info pull-right">
                        <div class="contact pull-left">CONTACTO WHATSAPP: 322 880-4995</div>
                        <!-- Language Switch start -->
                        <!-- Language Switch end -->
                    </div>
                </div>

                <div class="clearfix"></div>

                <!-- start navigation -->
                <nav class="navbar navbar-default" role="navigation">
                    <div class="container-fluid">
                        <!-- Toggle get grouped for better mobile display -->
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand scroll-to" href="#top"><img class="img-responsive"  src="img/logo.png" alt="Ayax"></a>
                        </div>
                        <!-- Collect the nav links, for toggling -->
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <!-- Nav-Links start -->
                            <ul class="nav navbar-nav navbar-right">
                                <li class="active"><a href="#top" class="scroll-to">Inicio</a></li>
                                <li><a href="#services" class="scroll-to">Tu mejor elección</a></li>
                                <li><a href="login.html" class="scroll-to">Soy Transportador</a></li>
                                <li><a href="servicios.html" class="scroll-to">Servicios disponibles</a></li>
                                <!--<li><a href="#contact" class="scroll-to">Contactanos</a></li>-->
                            </ul>
                            <!-- Nav-Links end -->
                        </div>
                    </div>
                </nav>
                <!-- end navigation -->
            </div>
        </div>

    </header>
    <!-- Header end -->



    <!-- Teaser start -->
    <section id="teaser">
        <div class="container">
            <div class="row">
                <div class="col-md-7 col-xs-12 pull-right">
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                        <!-- Wrapper for slides start -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <h1 class="title">Contacta el transportador que mas te convenga
                                    <span class="subtitle">Para tu viaje</span></h1>
                                <div class="car-img">
                                    <img src="img/car1.png" class="img-responsive" alt="car1">
                                </div>
                            </div>
                            <div class="item">
                                <h1 class="title">Te conectamos directamente con los transportadores
                                    <span class="subtitle">Ahorra hasta un 30%</span></h1>
                                <div class="car-img">
                                    <img src="img/car2.png" class="img-responsive" alt="car1">
                                </div>
                            </div>
                        </div>
                        <!-- Wrapper for slides end -->

                        <!-- Slider Controls start -->
                        <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                        </a>
                        <!-- Slider Controls end -->
                    </div>
                </div>
                <div class="col-md-5 col-xs-12 pull-left">
                    <div class="reservation-form-shadow">

                        <form action="#" method="post" name="car-select-form" id="car-select-form">

                            <!-- Car select start -->
                            <div class="styled-select-car">
                                <select name="car-select" id="car-select">
                                    <option value="">Selecciona tipo de vehículo</option>
                                    <option value="img/van6.jpg">Menos de 6 pasajeros</option>
                                    <option value="img/van6.jpg">6-9 Pasajeros</option>
                                    <option value="img/van12.jpg">9-12 pasajeros</option>
                                    <option value="img/van12.jpg">12-16 pasajeros</option>
                                    <option value="img/van19.png">17-19 pasajeros</option>
                                    <option value="img/bus23.jpg">20-24 pasajeros</option>
                                    <option value="img/bus40.jpg">25-32 pasajeros</option>
                                    <option value="img/bus40.jpg">33-40 pasajeros</option>
                                </select>
                            </div>
                            <!-- Car select end -->

                            <!-- Pick-up location start -->
                            <div class="location">
                                <input id="origin-input" class="controls" type="text" placeholder="Ubicación origen" required>

                                <input id="destination-input" class="controls" type="text" placeholder="Ubicación destino" required>

                                <div id="mode-selector" class="controls">
                                    <input type="radio" name="type" id="changemode-walking">
                                    <label for="changemode-walking">Walking</label>

                                    <input type="radio" name="type" id="changemode-transit">
                                    <label for="changemode-transit">Transit</label>

                                    <input type="radio" name="type" id="changemode-driving" checked="checked">
                                    <label for="changemode-driving">Driving</label>
                                </div>

                                <div id="map" style="height:150px;"></div>
                                <!--<div class="input-group pick-up">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-map-marker"></span> Dirección Origen</span>
    
                                    <input type="text" name="pick-up-location" id="pick-up-location" class="form-control autocomplete-location" placeholder="Dirección,barrio,ciudad">
                                </div><br/>
                                <div class="input-group drop-off">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-map-marker"></span> Dirección destino</span>
                                    <input type="text" name="drop-off-location" id="drop-off-location" class="form-control autocomplete-location" placeholder="Dirección,barrio,ciudad">
                                </div>-->
                                <!-- Pick-up location end -->

                            </div>
                            <!-- Drop-off location end -->

                            <!-- Pick-up date/time start -->
                            <div class="datetime pick-up">
                                <div class="date pull-left">
                                    <div class="input-group">
                                        <span class="input-group-addon pixelfix"><span class="glyphicon glyphicon-calendar"></span> Fecha Inicio</span>
                                        <input type="text" name="pick-up-date" id="pick-up-date" class="form-control datepicker" placeholder="mm/dd/yyyy">
                                    </div>
                                </div>
                                <div class="time pull-right">
                                    <div class="styled-select-time">
                                        <select name="pick-up-time" id="pick-up-time">
                                            <option value="12:00 AM">12:00 AM</option>
                                            <option value="12:30 AM">12:30 AM</option>
                                            <option value="01:00 AM">01:00 AM</option>
                                            <option value="01:30 AM">01:30 AM</option>
                                            <option value="02:00 AM">02:00 AM</option>
                                            <option value="02:30 AM">02:30 AM</option>
                                            <option value="03:00 AM">03:00 AM</option>
                                            <option value="03:30 AM">03:30 AM</option>
                                            <option value="04:00 AM">04:00 AM</option>
                                            <option value="04:30 AM">04:30 AM</option>
                                            <option value="05:00 AM">05:00 AM</option>
                                            <option value="05:30 AM">05:30 AM</option>
                                            <option value="06:00 AM">06:00 AM</option>
                                            <option value="06:30 AM">06:30 AM</option>
                                            <option value="07:00 AM">07:00 AM</option>
                                            <option value="07:30 AM">07:30 AM</option>
                                            <option value="08:00 AM">08:00 AM</option>
                                            <option value="08:30 AM">08:30 AM</option>
                                            <option value="09:00 AM">09:00 AM</option>
                                            <option value="09:30 AM">09:30 AM</option>
                                            <option value="10:00 AM">10:00 AM</option>
                                            <option value="10:30 AM">10:30 AM</option>
                                            <option value="11:00 AM">11:00 AM</option>
                                            <option value="11:30 AM">11:30 AM</option>
                                            <option value="12:00 PM">12:00 PM</option>
                                            <option value="12:30 PM">12:30 PM</option>
                                            <option value="01:00 PM">01:00 PM</option>
                                            <option value="01:30 PM">01:30 PM</option>
                                            <option value="02:00 PM">02:00 PM</option>
                                            <option value="02:30 PM">02:30 PM</option>
                                            <option value="03:00 PM">03:00 PM</option>
                                            <option value="03:30 PM">03:30 PM</option>
                                            <option value="04:00 PM">04:00 PM</option>
                                            <option value="04:30 PM">04:30 PM</option>
                                            <option value="05:00 PM">05:00 PM</option>
                                            <option value="05:30 PM">05:30 PM</option>
                                            <option value="06:00 PM">06:00 PM</option>
                                            <option value="06:30 PM">06:30 PM</option>
                                            <option value="07:00 PM">07:00 PM</option>
                                            <option value="07:30 PM">07:30 PM</option>
                                            <option value="08:00 PM">08:00 PM</option>
                                            <option value="08:30 PM">08:30 PM</option>
                                            <option value="09:00 PM">09:00 PM</option>
                                            <option value="09:30 PM">09:30 PM</option>
                                            <option value="10:00 PM">10:00 PM</option>
                                            <option value="10:30 PM">10:30 PM</option>
                                            <option value="11:00 PM">11:00 PM</option>
                                            <option value="11:30 PM">11:30 PM</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <!-- Pick-up date/time end -->

                            <!-- Drop-off date/time start -->
                            <div class="datetime drop-off">
                                <div class="date pull-left">
                                    <div class="input-group">
                                        <span class="input-group-addon pixelfix"><span class="glyphicon glyphicon-calendar"></span>Fecha Fin</span>
                                        <input type="text" name="drop-off-date" id="drop-off-date" class="form-control datepicker" placeholder="mm/dd/yyyy">
                                    </div>
                                </div>
                                <div class="time pull-right">
                                    <div class="styled-select-time">
                                        <select name="drop-off-time" id="drop-off-time">
                                            <option value="12:00 AM">12:00 AM</option>
                                            <option value="12:30 AM">12:30 AM</option>
                                            <option value="01:00 AM">01:00 AM</option>
                                            <option value="01:30 AM">01:30 AM</option>
                                            <option value="02:00 AM">02:00 AM</option>
                                            <option value="02:30 AM">02:30 AM</option>
                                            <option value="03:00 AM">03:00 AM</option>
                                            <option value="03:30 AM">03:30 AM</option>
                                            <option value="04:00 AM">04:00 AM</option>
                                            <option value="04:30 AM">04:30 AM</option>
                                            <option value="05:00 AM">05:00 AM</option>
                                            <option value="05:30 AM">05:30 AM</option>
                                            <option value="06:00 AM">06:00 AM</option>
                                            <option value="06:30 AM">06:30 AM</option>
                                            <option value="07:00 AM">07:00 AM</option>
                                            <option value="07:30 AM">07:30 AM</option>
                                            <option value="08:00 AM">08:00 AM</option>
                                            <option value="08:30 AM">08:30 AM</option>
                                            <option value="09:00 AM">09:00 AM</option>
                                            <option value="09:30 AM">09:30 AM</option>
                                            <option value="10:00 AM">10:00 AM</option>
                                            <option value="10:30 AM">10:30 AM</option>
                                            <option value="11:00 AM">11:00 AM</option>
                                            <option value="11:30 AM">11:30 AM</option>
                                            <option value="12:00 PM">12:00 PM</option>
                                            <option value="12:30 PM">12:30 PM</option>
                                            <option value="01:00 PM">01:00 PM</option>
                                            <option value="01:30 PM">01:30 PM</option>
                                            <option value="02:00 PM">02:00 PM</option>
                                            <option value="02:30 PM">02:30 PM</option>
                                            <option value="03:00 PM">03:00 PM</option>
                                            <option value="03:30 PM">03:30 PM</option>
                                            <option value="04:00 PM">04:00 PM</option>
                                            <option value="04:30 PM">04:30 PM</option>
                                            <option value="05:00 PM">05:00 PM</option>
                                            <option value="05:30 PM">05:30 PM</option>
                                            <option value="06:00 PM">06:00 PM</option>
                                            <option value="06:30 PM">06:30 PM</option>
                                            <option value="07:00 PM">07:00 PM</option>
                                            <option value="07:30 PM">07:30 PM</option>
                                            <option value="08:00 PM">08:00 PM</option>
                                            <option value="08:30 PM">08:30 PM</option>
                                            <option value="09:00 PM">09:00 PM</option>
                                            <option value="09:30 PM">09:30 PM</option>
                                            <option value="10:00 PM">10:00 PM</option>
                                            <option value="10:30 PM">10:30 PM</option>
                                            <option value="11:00 PM">11:00 PM</option>
                                            <option value="11:30 PM">11:30 PM</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <input type="checkbox" id="idayvuelta"/> ¿ Requieres ida y vuelta?
                            <input type="checkbox" id="disponibilidad" title="Requieres"/> 
                            <span data-toggle="tooltip" id="labelDisponibilidad" title="¿Requieres que el transportador este disponible durante las fechas de inicio y regreso?">
                                Disponible entre fechas
                            </span>

                            <div class="alert alert-danger hidden" id="car-select-form-msg">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                <strong>Nota:</strong> Todos los campos son obligatorios
                            </div>

                            <!-- Drop-off date/time end -->

                            <input type="submit" class="submit" name="submit" value="Conseguir transportador" id="checkoutModalLabel">
                        </form>

                    </div>
                </div>

            </div>
        </div>
    </section>
    <div class="arrow-down"></div>
    <!-- Teaser end -->



    <!-- Services start -->
    <section id="services" class="container">
        <div class="row">
            <div class="col-md-12 title">
                <h2>¿Porqué elegirnos?</h2>
                <span class="underline">&nbsp;</span>
            </div>

            <!-- Service Box start -->
            <div class="col-md-6">
                <div class="service-box wow fadeInLeft" data-wow-offset="100">
                    <div class="service-icon">+</div>
                    <h3 class="service-title">Obtén la mejor oferta del mercado</h3>
                    <div class="clearfix"></div>
                    <p class="service-content">Una vez enviada tu necesidad, muchos transportadores te enviarán una oferta especificando valor económico y fotos del vehículo.</p>
                </div>
            </div>
            <!-- Service Box end -->

            <!-- Service Box start -->
            <div class="col-md-6">
                <div class="service-box wow fadeInRight" data-wow-offset="100">
                    <div class="service-icon">+</div>
                    <h3 class="service-title">Transportadores confiables</h3>
                    <div class="clearfix"></div>
                    <p class="service-content">Puedes apreciar la calificación dada por otros que ya han utilizado sus servicios a través de nuestra plataforma, y una vez ejecutado el servicio podrás calificar el transportador basado en la experiencia que te proporciono.</p>
                </div>
            </div>
            <!-- Service Box end -->

            <!-- Service Box start -->
            <div class="col-md-6">
                <div class="service-box wow fadeInLeft" data-wow-offset="100">
                    <div class="service-icon">+</div>
                    <h3 class="service-title">Un viaje seguro</h3>
                    <div class="clearfix"></div>
                    <p class="service-content">Te garantizamos, los vehículos de las ofertas se encuentren al día con los papeles que argumenten un estado idoneo de los mismos, los seguros y papeles reglamentados para este tipo de servicio.</p>
                </div>
            </div>
            <!-- Service Box end -->

            <!-- Service Box start -->
            <div class="col-md-6">
                <div class="service-box wow fadeInRight" data-wow-offset="100">
                    <div class="service-icon">+</div>
                    <h3 class="service-title">El vehículo que necesitas</h3>
                    <div class="clearfix"></div>
                    <p class="service-content">Contamos con aliados transportadores con toda clase de automotores,
                        pasando por capacidades pequeñas hasta los 40 pasajeros, techo alto, con o sin aire acondicionado, 
                        selecciona el que mas se ajuste a tu necesidad.</p>
                </div>
            </div>
            <!-- Service Box end -->

        </div>
    </section>
    <!-- Services end -->



    <!-- Newsletter start -->
    <section id="newsletter" class="wow slideInLeft" data-wow-offset="300">
        <div class="container">
            <div class="row">
                <div class="col-md-12"><div class="alert hidden" id="newsletter-form-msg"></div></div>
                <div class="col-md-5 col-xs-12">
                    <h2 class="title">Registra tu correo para enviarte promociones y novedades 
                        <span class="subtitle">Accede de forma exclusiva a las mejores ofertas</span></h2>
                </div><br/><br/><br/><br/><br/><br/>
                <div class="col-md-7" id="correo-script">

                    <!--form action="#" method="post" name="newsletter-form" id="newsletter-form">
                        <input type="hidden" name="action" value="send_newsletter_form"/>
                        <div class="input-group">
                            <input type="email" name="correo" class="form-control" placeholder="Ingresa tu correo electrónico">
                            <span class="input-group-btn">
                                <input class="btn btn-default button" type="submit" value="Enviar">
                            </span>
                        </div>
                    </form-->
                    <script type="text/javascript" src="//static.mailerlite.com/data/webforms/368296/i7r1r5.js?v3"></script>
                    <!--div class="social-icons pull-right">
                        <ul>
                            <li><a class="facebook" href="https://www.facebook.com/ayaxcolombia/" target="_blank"><i class="fa fa-facebook"></i></a></li>
                            <li><a class="twitter" href="https://twitter.com/AyaxColombia" target="_blank"><i class="fa fa-twitter"></i></a></li>
                        </ul>
                    </div-->

                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </section>
    <!-- Newsletter end -->


    <!-- Reviews start -->
    <a href="#" class="scrollup">ScrollUp</a>


    <!-- Footer start -->
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-12 text-center">
                    <ul class="footer-nav">
                        <li><a class="scroll-to" href="#top">Inicio</a></li>
                        <li><a class="scroll-to" href="#services">Tu mejor elección</a></li>
                        <li><a class="scroll-to" href="login.html">Soy transportador</a></li>
                        <li><a href="servicios.html" class="scroll-to">Servicios disponibles</a></li>
                        <!--<li><a class="scroll-to" href="#contact">Contactanos</a></li>-->
                    </ul>
                            <ul class="nav navbar-nav navbar-right">
                                <li class="active"><a href="/admin/access" class="scroll-to">Para administradores</a></li>
                                <!--<li><a href="#contact" class="scroll-to">Contactanos</a></li>-->
                            </ul>
                    <div class="clearfix"></div>
                    <p class="copyright">©2016 Ayax, All Rights Reserved</p>
                </div>
            </div>
        </div>
    </footer>
    <!-- Footer end -->



    <!-- Checkout Modal Start -->
    <div class="modal fade" id="checkoutModal" tabindex="-1" role="dialog" aria-labelledby="checkoutModalLabel" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="#" method="post" id="checkout-form" name="checkout-form">
                    <input type="hidden" name="action" value="send_inquiry_form"/>

                    <!-- Modal header start -->
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">Completa la solicitud</h4>
                    </div>
                    <!-- Modal header end -->

                    <!-- Modal body start -->
                    <div class="modal-body">

                        <!-- Checkout Info start -->
                        <div class="checkout-info-box">
                            <h3><i class="fa fa-info-circle"></i> Al completar esta solicitud de servicio, recibirás</h3>
                            <p>Su resumen de servicio, con el código de identificación del mismo.</p>
                        </div>
                        <!-- Checkout Info end -->

                        <!-- Checkout Rental Info start -->
                        <div class="form-group">
                            <div class="checkbox">
                                <input type="hidden" name="usuarioregistrado" value="false">
                                <input id="usuarioRegistrado" type="checkbox" name="usuarioregistrado" value="yes">
                                <label for="usuarioRegistrado">Soy usuario registrado, (ya he pedido servicios antes)</label>
                            </div>
                        </div>
                        <div class="checkout-vehicle-info">
                            <div class="location-date-info">
                                <h3>Ubicación & Fecha</h3>
                                <div class="info-box">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                    <h4 class="info-box-title">Fecha Inicio</h4>
                                    <p class="info-box-description"><span id="pick-up-date-ph"></span> at <span id="pick-up-time-ph"></span></p>
                                    <input type="hidden" name="pick-up" id="pick-up" value="">
                                </div>
                                <div class="info-box">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                    <h4 class="info-box-title">Fecha Fin</h4>
                                    <p class="info-box-description"><span id="drop-off-date-ph"></span> at <span id="drop-off-time-ph"></span></p>
                                    <input type="hidden" name="drop-off" id="drop-off" value="">
                                </div>
                                <div class="info-box">
                                    <span class="glyphicon glyphicon-map-marker"></span>
                                    <h4 class="info-box-title">Dirección Origen</h4>
                                    <p class="info-box-description" id="pickup-location-ph"></p>
                                    <input type="hidden" name="pickup-location" id="pickup-location" value="">
                                </div>
                                <div class="info-box">
                                    <span class="glyphicon glyphicon-map-marker"></span>
                                    <h4 class="info-box-title">Dirección Final</h4>
                                    <p class="info-box-description" id="dropoff-location-ph"></p>
                                    <input type="hidden" name="dropoff-location" id="dropoff-location" value="">
                                </div>
                                <div class="info-box">
                                    <span class="glyphicon glyphicon-road"></span>
                                    <h4 class="info-box-title">Distancia</h4>
                                    <p class="info-box-description" id="distancia-ph"></p>
                                    <input type="hidden" name="distancia" id="distancia" value="">
                                </div>
                                <div class="info-box" id="div-ida-vuelta">
                                    <span class="glyphicon glyphicon-road"></span>
                                    <h4 class="info-box-title">Ida y Vuelta</h4>
                                    <p class="info-box-description" id="distancia-ph"></p>
                                    <input type="hidden" name="idayvueltas" id="idayvueltas" value="">
                                </div>
                                <div class="info-box" id="div-disponibilidad">
                                    <span class="glyphicon glyphicon-road"></span>
                                    <h4 class="info-box-title">Con disponibilidad</h4>
                                    <p class="info-box-description" id="distancia-ph"></p>
                                    <input type="hidden" name="con-disponibilidad" id="con-disponibilidad" value="">
                                </div>

                            </div>

                            <div class="vehicle-info">
                                <h3>VEHÍCULO: <span id="selected-car-ph"></span></h3> <a href="#vehicles" class="scroll-to">[ CAPACIDAD ]</a>
                                <input type="hidden" name="selected-car" id="selected-car" value="">
                                <div class="clearfix"></div>
                                <div class="vehicle-image">
                                    <img class="img-responsive" id="selected-vehicle-image" src="img/vehicle1.jpg" alt="Vehicle">
                                </div>
                            </div>

                            <div class="clearfix"></div>

                        </div>
                        <!-- Checkout Rental Info end -->

                        <hr>

                        <!-- Checkout Personal Info start -->
                        <div class="alert alert-danger hidden" id="datos-contacto">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                            <strong>Nota:</strong> No son permitidos los números ni los datos de contacto.
                            <span id="errorGenerico" style="color:red;"></span>
                        </div>
                        <div style="width:80%;margin:0 auto;">
                            <textarea rows="4" cols="50" name="detalle" id="text_detalle"class="form-control" placeholder="Si necesitas incluir detalles del servicio que solicitas, digítalo en este espacio"></textarea>
                        </div><br/>
                        <div class="checkout-personal-info">
                            <div class="newsletter">
                                <div class="radio">
                                    <label><input id="check1" type="radio" style="top:-15px;" name="solicitante" value="empresa">
                                        Servicio para transportar empleados de la misma empresa/ Estudiantes de una institución</label>
                                </div>
                                <div class="radio">
                                    <label><input id="check2" type="radio" style="top:-15px;" name="solicitante" value="particular">
                                        Grupo determinable de personas / particulares, familia.</label>
                                </div>
                            </div>

                            <h3>INFORMACIÓN DEL SOLICITANTE</h3>
                            <div class="form-group">
                                <label for="first-name">Nombre Completo:</label>
                                <input type="text" class="form-control" name="first-name" id="first-name" placeholder="Ingresa tu nombre completo" required/>
                            </div>
<!--                            <div class="form-group" id="input_empresa">
                                <label for="last-name" id="label-empresa">Empresa</label>
                                <input type="text" class="form-control" name="company-name" id="last-name" placeholder="Si el servicio es para una empresa">
                            </div>-->
                            <div class="form-group">
                                <label for="phone-number">Nùmero Teléfonico:</label>
                                <input type="number" class="form-control" name="phone-number" id="phone-number" placeholder="Ingresa un número de contacto" required/>
                            </div>
                            <div class="form-group">
                                <label for="email-address">Correo Electrónico:</label>
                                <input type="email" class="form-control" name="email-address" id="email-address" placeholder="Ingresa tu correo" required/>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!-- Checkout Personal Info end -->

                        <!-- Checkout Address Info start -->
                        <div class="checkout-address-info">
                            <div class="form-group zip-code left">
                                <label>Nit o Cédula del solicitante</label>
                                <input type="number" class="form-control" name="nit" id="nit" placeholder="Ingresa el Nit de la empresa" required/>
                            </div>
                            <div class="form-group zip-code left" id="codigopdiv">
                                <label>Código promocional</label>
                                <input type="text" class="form-control" name="codigop" id="codigop" placeholder="Si tienes, ingresa código promocional"/>
                            </div>
                            <div class="form-group zip-code right"  id="divcodigo" style="display:none;">
                                <label>Código de autorización</label>
                                <input type="text" class="form-control" name="codigoautorizacion" id="codigoautorizacion" placeholder="Ingresa tu código de autorización"/>
                            </div>
                            <div class="clearfix"></div>
                        </div>

                        <!-- Checkout Address Info end -->
                        <div style="margin-left:5%" class="checkbox">

                            <input type="checkbox" name="terminos" id="terminos" value="yes">
                            <label for="terminos">Acepto<a href="TerminosCondiciones.html" target="_blank"> términos y condiciones</a></label>

                        </div>
                    </div>
                    <!-- Modal body end -->

                    <!-- Modal footer start -->
                    <div class="alert alert-danger hidden" id="divMensajeError_usuario">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <strong>Error!</strong> <span id="errorMensaje">Mensaje de error</span>
                    </div>
                    <div class="modal-footer">
                        <span class="btn-border btn-gray">
                            <button type="button" class="btn btn-default btn-gray" data-dismiss="modal">Cancelar</button>
                        </span>
                        <span class="btn-border btn-yellow">
                            <button type="submit" class="btn btn-primary btn-yellow">Solicitar Ahora</button>
                        </span>
                    </div>
                    <!-- Modal footer end -->
                    <div class="alert alert-danger hidden" id="tipo-servicio-falta">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <strong>Nota:</strong> Falta un campo obligatorio o campo mal diligenciado.
                        <span id="errorGenerico" style="color:red;"></span>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Checkout Modal end -->


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/map.js"></script>
    <script src="js/jquery-1.11.0.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script type="text/javascript" src='https://maps.google.com/maps/api/js?key=AIzaSyDytJVEvO-Qyq9Idtu8Xb1NVx2YigFUF-k&sensor=false&libraries=places&callback=initMap' async defer></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.autocomplete.min.js"></script>
    <script src="js/jquery.placeholder.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>



    <!--[if !(gte IE 8)]><!-->
    <script src="js/wow.min.js"></script>
    <script>
// Initialize WOW
//-------------------------------------------------------------
new WOW({mobile: false}).init();
    </script>
    <!--<![endif]-->

    <script src="js/ProcesandoSolicitud.js"></script>
    <script src="js/custom.js"></script>



</body>
</html>
