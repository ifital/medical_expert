<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Consultation des demandes d'expertise</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-6">

<div class="container mx-auto">
    <h1 class="text-2xl font-bold mb-4">Demandes d'expertise</h1>

    <!-- Formulaire de filtrage -->
    <form method="get" class="mb-4 flex gap-4">
        <select name="statut" class="border rounded px-2 py-1">
            <option value="">-- Statut --</option>
            <option value="EN_ATTENTE">En attente</option>
            <option value="TERMINEE">Terminée</option>
        </select>

        <select name="priorite" class="border rounded px-2 py-1">
            <option value="">-- Priorité --</option>
            <option value="Haute">Haute</option>
            <option value="Moyenne">Moyenne</option>
            <option value="Basse">Basse</option>
        </select>

        <button type="submit" class="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-700">
            Filtrer
        </button>
    </form>

    <!-- Table des demandes -->
    <table class="min-w-full bg-white shadow rounded">
        <thead class="bg-gray-200">
        <tr>
            <th class="py-2 px-4 border">ID</th>
            <th class="py-2 px-4 border">Patient</th>
            <th class="py-2 px-4 border">Question</th>
            <th class="py-2 px-4 border">Statut</th>
            <th class="py-2 px-4 border">Priorité</th>
            <th class="py-2 px-4 border">Réponse</th>
            <th class="py-2 px-4 border">Recommandations</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="demande" items="${demandes}">
            <tr class="hover:bg-gray-100">
                <td class="py-2 px-4 border">${demande.id}</td>
                <td class="py-2 px-4 border">
                        ${demande.consultation.patient.nom} ${demande.consultation.patient.prenom} <br/>
                    <span class="text-sm text-gray-500">
                        Tél: ${demande.consultation.patient.telephone}
                    </span>
                </td>
                <td class="py-2 px-4 border">${demande.question}</td>
                <td class="py-2 px-4 border">${demande.statut}</td>
                <td class="py-2 px-4 border">${demande.priorite}</td>
                <td class="py-2 px-4 border">
                    <c:if test="${not empty demande.reponse}">
                        ${demande.reponse}
                    </c:if>
                </td>
                <td class="py-2 px-4 border">
                    <c:if test="${not empty demande.recommandations}">
                        ${demande.recommandations}
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty demandes}">
            <tr>
                <td colspan="7" class="py-4 text-center text-gray-500">Aucune demande trouvée</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

</body>
</html>
