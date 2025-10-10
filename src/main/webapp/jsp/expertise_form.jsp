<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Demande d’expertise</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h2>Nouvelle demande d’expertise</h2>

<form action="demande-expertise" method="post">
    <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

    <label>Consultation concernée :</label>
    <select name="consultationId" required>
        <c:forEach var="consult" items="${consultations}">
            <option value="${consult.id}">${consult.patient.nom} — ${consult.date}</option>
        </c:forEach>
    </select><br>

    <label>Spécialiste :</label>
    <select name="specialisteId" required>
        <c:forEach var="spec" items="${specialistes}">
            <option value="${spec.id}">Dr. ${spec.nom} (${spec.specialite})</option>
        </c:forEach>
    </select><br>

    <label>Motif de la demande :</label>
    <textarea name="motif" required></textarea><br>

    <button type="submit">Envoyer la demande</button>
</form>

<a href="dashboard_generaliste.jsp">Retour</a>
</body>
</html>
