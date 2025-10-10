<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription - TéléExpertise</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h2>Créer un compte</h2>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label>Nom :</label>
    <input type="text" name="nom" required><br>

    <label>Prénom :</label>
    <input type="text" name="prenom" required><br>

    <label>Email :</label>
    <input type="email" name="email" required><br>

    <label>Nom d’utilisateur :</label>
    <input type="text" name="username" required><br>

    <label>Mot de passe :</label>
    <input type="password" name="password" required><br>

    <label>Rôle :</label>
    <select name="role" required>
        <option value="INFIRMIER">Infirmier</option>
        <option value="GENERALISTE">Médecin généraliste</option>
        <option value="SPECIALISTE">Médecin spécialiste</option>
    </select><br>

    <button type="submit">S'inscrire</button>
</form>

<p>Déjà un compte ? <a href="login.jsp">Se connecter</a></p>

<p style="color:red;">${error}</p>
<p style="color:green;">${message}</p>
</body>
</html>
