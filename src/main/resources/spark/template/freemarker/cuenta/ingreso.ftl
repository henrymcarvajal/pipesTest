<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ayax.co - Ingresar</title>
        <#include "../../common/header.ftl">
        <ftl template="ingreso.ftl">
        </head>
    <body>
        <#include "../../common/bar.ftl">
        <form action="autenticarUsuario" method="post">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-sm-12">
                        &nbsp;
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3 col-sm-3">
                        &nbsp;
                    </div>
                    <div class="col-lg-6 col-sm-6">
                        <div class="form-group">
                            <label>Buz&oacute;n electr&oacute;nico:</Label>
                            <input name="buzonElectronico" class="form-control" type="text"></input>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-3">
                        &nbsp;
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3 col-sm-3">
                        &nbsp;
                    </div>
                    <div class="col-lg-6 col-sm-6">
                        <div class="form-group">
                            <label>Tel&eacute;fono m&oacute;vil:</Label>
                            <input name="telefonoMovil" class="form-control" type="password"></input>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-3">
                        &nbsp;
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4 col-sm-4">
                        &nbsp;
                    </div>
                    <div class="col-lg-4 col-sm-4">
                        <input type="submit" class="form-control" value="Ingresar"></input>
                    </div>
                    <div class="col-lg-4 col-sm-4">
                        &nbsp;
                    </div>
                </div>
            </div>
        </form>
        <#include "../../common/copyright.ftl">
        <#include "../../common/footer.ftl">
    </body>
</html>
