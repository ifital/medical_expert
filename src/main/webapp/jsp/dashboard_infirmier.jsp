<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Infirmier</title>
</head>
<body>
<h2>Bienvenue, Infirmier ${sessionScope.user.nom}</h2>

<h3>Liste des Patients</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Téléphone</th>
        <th>Mutuelle</th>
        <th>Tension</th>
        <th>Température</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="p" items="${patients}">
        <tr>
            <td>${p.id}</td>
            <td>${p.nom}</td>
            <td>${p.prenom}</td>
            <td>${p.telephone}</td>
            <td>${p.mutuelle}</td>
            <td>${p.tension}</td>
            <td>${p.temperature}</td>
            <td>
                <a href="${pageContext.request.contextPath}/patients?action=edit&id=${p.id}">Modifier</a> |
                <a href="${pageContext.request.contextPath}/patients?action=delete&id=${p.id}">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h3><c:if test="${not empty patientToEdit}">Modifier</c:if><c:if test="${empty patientToEdit}">Ajouter</c:if> un patient</h3>
<form action="${pageContext.request.contextPath}/patients" method="post">
    <c:if test="${not empty patientToEdit}">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${patientToEdit.id}">
    </c:if>
    <c:if test="${empty patientToEdit}">
        <input type="hidden" name="action" value="add">
    </c:if>

    Nom: <input type="text" name="nom" value="${patientToEdit.nom}" required><br>
    Prénom: <input type="text" name="prenom" value="${patientToEdit.prenom}" required><br>
    Numéro Sécu: <input type="text" name="numeroSecuriteSociale" value="${patientToEdit.numeroSecuriteSociale}"><br>
    Téléphone: <input type="text" name="telephone" value="${patientToEdit.telephone}"><br>
    Adresse: <input type="text" name="adresse" value="${patientToEdit.adresse}"><br>
    Mutuelle: <input type="text" name="mutuelle" value="${patientToEdit.mutuelle}"><br>
    Tension: <input type="number" step="0.1" name="tension" value="${patientToEdit.tension}"><br>
    Température: <input type="number" step="0.1" name="temperature" value="${patientToEdit.temperature}"><br>
    Fréquence cardiaque: <input type="number" name="frequenceCardiaque" value="${patientToEdit.frequenceCardiaque}"><br>
    Fréquence respiratoire: <input type="number" name="frequenceRespiratoire" value="${patientToEdit.frequenceRespiratoire}"><br>
    Poids: <input type="number" step="0.1" name="poids" value="${patientToEdit.poids}"><br>
    Taille: <input type="number" step="0.1" name="taille" value="${patientToEdit.taille}"><br>
    <button type="submit"><c:if test="${not empty patientToEdit}">Mettre à jour</c:if><c:if test="${empty patientToEdit}">Ajouter</c:if></button>
</form>

<a href="logout">Déconnexion</a>
</body>
</html>
