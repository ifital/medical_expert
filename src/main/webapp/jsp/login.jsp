<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Télé-Expertise</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h2>Connexion</h2>

<form action="login" method="post">
    <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
    <label>Email :</label>
    <input type="email" name="email" required><br>

    <label>Mot de passe :</label>
    <input type="password" name="password" required><br>

    <button type="submit">Se connecter</button>
</form>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>
</body>
</html>
