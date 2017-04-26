<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Ayax admin</title>

    </head>
    <body id="top" data-spy="scroll" data-target=".navbar" data-offset="260">

        <table>
<#list servicios as item>
            <tr><td>${item.id}</td><td>$--{--item.usuario.id}</td>
                </tr>
        </#list>
            </table>
</body>
</html>