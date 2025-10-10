<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Généraliste</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h2>Bienvenue Dr. ${sessionScope.user.nom}</h2>

<a href="consultation_form.jsp">➕ Nouvelle Consultation</a> |
<a href="expertise_form.jsp">🩺 Demande d’Expertise</a> |
<a href="logout">Déconnexion</a>

<h3>Mes consultations</h3>
<table border="1">
    <tr>
        <th>Patient</th>
        <th>Date</th>
        <th>Type</th>
        <th>Spécialiste consulté</th>
    </tr>
    <c:forEach var="consult" items="${consultations}">
        <tr>
            <td>${consult.patient.nom}</td>
            <td>${consult.date}</td>
            <td>${consult.type}</td>
            <td>${consult.specialiste.nom}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
