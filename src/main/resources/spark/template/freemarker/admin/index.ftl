<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Ayax admin</title>

    </head>
    <body id="top" data-spy="scroll" data-target=".navbar" data-offset="260">
        <form name="accessFrm" action="/accessAdmin" method="POST">
            <table border="1">
                <tbody>
                    <tr>
                        <td>Usuario:</td>
                        <td><input type="text" name="user" value="" size="30" /></td>
                    </tr>
                    <tr>
                        <td>Contraseña:</td>
                        <td><input type="password" name="password" value="" size="30" /></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>