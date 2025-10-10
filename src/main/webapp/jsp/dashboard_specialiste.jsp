<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Spécialiste</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h2>Bienvenue Dr. ${sessionScope.user.nom}</h2>

<h3>Demandes d’expertise reçues</h3>
<table border="1">
    <tr>
        <th>Patient</th>
        <th>Motif</th>
        <th>Médecin demandeur</th>
        <th>Action</th>
    </tr>
    <c:forEach var="demande" items="${demandes}">
        <tr>
            <td>${demande.patient.nom}</td>
            <td>${demande.motif}</td>
            <td>${demande.generaliste.nom}</td>
            <td>
                <form action="reponse-expertise" method="post">
                    <input type="hidden" name="id" value="${demande.id}">
                    <input type="text" name="avis" placeholder="Votre avis médical" required>
                    <button type="submit">Envoyer</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="logout">Déconnexion</a>
</body>
</html>
