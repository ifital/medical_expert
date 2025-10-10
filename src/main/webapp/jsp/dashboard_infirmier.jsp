<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Infirmier</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h2>Bienvenue, Infirmier ${sessionScope.user.nom}</h2>

<h3>Liste des consultations programmées</h3>
<table border="1">
    <tr>
        <th>Patient</th>
        <th>Médecin</th>
        <th>Date</th>
        <th>Statut</th>
    </tr>
    <c:forEach var="consult" items="${consultations}">
        <tr>
            <td>${consult.patient.nom}</td>
            <td>${consult.medecin.nom}</td>
            <td>${consult.date}</td>
            <td>${consult.status}</td>
        </tr>
    </c:forEach>
</table>

<a href="logout">Déconnexion</a>
</body>
</html>
