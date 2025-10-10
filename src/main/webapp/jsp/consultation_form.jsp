<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Nouvelle Consultation</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h2>Créer une consultation</h2>

<form action="add-consultation" method="post">
    <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

    <label>Patient :</label>
    <select name="patientId" required>
        <c:forEach var="patient" items="${patients}">
            <option value="${patient.id}">${patient.nom}</option>
        </c:forEach>
    </select><br>

    <label>Date :</label>
    <input type="date" name="date" required><br>

    <label>Motif :</label>
    <textarea name="motif" required></textarea><br>

    <button type="submit">Enregistrer</button>
</form>

<a href="dashboard_generaliste.jsp">Retour</a>
</body>
</html>
