<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>USD/EUR Currency mailing</title>
</head>
<body>
<form action="/EmptyEmail" method="POST">
    <div class="container">
        <b>Enter your e-mail</b>
        <br>
        <input type="text" placeholder="e-mail" name="email" required>
        <br>
        <h4>Choose currency you want to get</h4>
        <input type="checkbox" id="usd" name="USD" value="true">
        <label for="USD">USD</label>
        <br>
        <input type="checkbox" id="eur" name="EUR" value="true">
        <label for="EUR">EUR</label>
        <br>
        <input type="checkbox" id="rub" name="RUB" value="true">
        <label for="RUB">RUB</label>

        <form action="/EmptyEmail" method="POST">
            <button type="submit">Send me currency </button>
        </form>
    </div>
</form>



</body>
</html>