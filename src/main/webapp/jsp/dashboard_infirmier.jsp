<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Infirmier</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f5f8fa;
        }
        h2 {
            color: #0077b6;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            background: white;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background: #0077b6;
            color: white;
        }
        tr:nth-child(even) {
            background: #f1f1f1;
        }
        form {
            margin: 20px 0;
            background: #ffffff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        label {
            font-weight: bold;
        }
        input {
            padding: 6px;
            margin: 5px 0;
            width: 250px;
        }
        button {
            padding: 8px 14px;
            background-color: #0077b6;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #005f87;
        }
        a {
            color: #0077b6;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .actions a {
            margin: 0 5px;
        }
        .filter-section {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<h2>Bienvenue, Infirmier ${sessionScope.user.nom}</h2>

<!-- 🔍 Filtrage par date d’arrivée -->
<div class="filter-section">
    <form action="${pageContext.request.contextPath}/patients" method="get">
        <input type="hidden" name="action" value="filter">
        <label for="date">Filtrer par date d’arrivée :</label>
        <input type="date" name="date" id="date" value="${dateRecherche}">
        <button type="submit">Filtrer</button>
        <a href="${pageContext.request.contextPath}/patients?action=list">Réinitialiser</a>
    </form>
</div>

<!-- 📋 Tableau des patients -->
<h3>Liste des Patients (triés du plus ancien au plus récent)</h3>
<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Téléphone</th>
        <th>Mutuelle</th>
        <th>Tension</th>
        <th>Température</th>
        <th>Fréquence Cardiaque</th>
        <th>Fréquence Respiratoire</th>
        <th>Poids</th>
        <th>Taille</th>
        <th>Date d’arrivée</th>
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
            <td>${p.frequenceCardiaque}</td>
            <td>${p.frequenceRespiratoire}</td>
            <td>${p.poids}</td>
            <td>${p.taille}</td>
            <td>
                <fmt:formatDate value="${p.dateArrivee}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/patients?action=edit&id=${p.id}">Modifier</a> |
                <a href="${pageContext.request.contextPath}/patients?action=delete&id=${p.id}"
                   onclick="return confirm('Voulez-vous vraiment supprimer ce patient ?');">
                    Supprimer
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<!-- 🧾 Formulaire d’ajout / modification -->
<h3>
    <c:choose>
        <c:when test="${not empty patientToEdit}">Modifier un patient</c:when>
        <c:otherwise>Ajouter un patient</c:otherwise>
    </c:choose>
</h3>

<form action="${pageContext.request.contextPath}/patients" method="post">
    <c:if test="${not empty patientToEdit}">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${patientToEdit.id}">
    </c:if>
    <c:if test="${empty patientToEdit}">
        <input type="hidden" name="action" value="add">
    </c:if>

    <label>Nom :</label><br>
    <input type="text" name="nom" value="${patientToEdit.nom}" required><br>

    <label>Prénom :</label><br>
    <input type="text" name="prenom" value="${patientToEdit.prenom}" required><br>

    <label>Numéro Sécu :</label><br>
    <input type="text" name="numeroSecuriteSociale" value="${patientToEdit.numeroSecuriteSociale}"><br>

    <label>Téléphone :</label><br>
    <input type="text" name="telephone" value="${patientToEdit.telephone}"><br>

    <label>Adresse :</label><br>
    <input type="text" name="adresse" value="${patientToEdit.adresse}"><br>

    <label>Mutuelle :</label><br>
    <input type="text" name="mutuelle" value="${patientToEdit.mutuelle}"><br>

    <label>Tension :</label><br>
    <input type="number" step="0.1" name="tension" value="${patientToEdit.tension}"><br>

    <label>Température :</label><br>
    <input type="number" step="0.1" name="temperature" value="${patientToEdit.temperature}"><br>

    <label>Fréquence cardiaque :</label><br>
    <input type="number" name="frequenceCardiaque" value="${patientToEdit.frequenceCardiaque}"><br>

    <label>Fréquence respiratoire :</label><br>
    <input type="number" name="frequenceRespiratoire" value="${patientToEdit.frequenceRespiratoire}"><br>

    <label>Poids :</label><br>
    <input type="number" step="0.1" name="poids" value="${patientToEdit.poids}"><br>

    <label>Taille :</label><br>
    <input type="number" step="0.1" name="taille" value="${patientToEdit.taille}"><br>

    <button type="submit">
        <c:choose>
            <c:when test="${not empty patientToEdit}">Mettre à jour</c:when>
            <c:otherwise>Ajouter</c:otherwise>
        </c:choose>
    </button>
</form>

<!-- 🔚 Déconnexion -->
<p><a href="logout">Déconnexion</a></p>

</body>
</html>
