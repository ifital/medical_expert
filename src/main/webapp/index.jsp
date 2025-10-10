<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription - Télé-Expertise</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="container">
    <h2>Créer un compte</h2>

    <form action="register" method="post">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

        <label>Nom complet :</label>
        <input type="text" name="nom" placeholder="Nom complet" required><br>

        <label>Email :</label>
        <input type="email" name="email" placeholder="exemple@domaine.com" required><br>

        <label>Mot de passe :</label>
        <input type="password" name="password" placeholder="********" required><br>

        <label>Confirmer le mot de passe :</label>
        <input type="password" name="confirmPassword" placeholder="********" required><br>

        <label>Rôle :</label>
        <select name="role" required>
            <option value="">-- Sélectionnez un rôle --</option>
            <option value="GENERALISTE">Médecin Généraliste</option>
            <option value="SPECIALISTE">Médecin Spécialiste</option>
            <option value="INFIRMIER">Infirmier</option>
        </select><br>

        <c:if test="${param.role == 'SPECIALISTE'}">
            <label>Spécialité :</label>
            <input type="text" name="specialite" placeholder="Cardiologie, Dermatologie..." required><br>
        </c:if>

        <button type="submit">Créer un compte</button>
    </form>

    <p>Déjà inscrit ? <a href="login.jsp">Se connecter</a></p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <c:if test="${not empty success}">
        <p style="color:green">${success}</p>
    </c:if>
</div>
</body>
</html>
