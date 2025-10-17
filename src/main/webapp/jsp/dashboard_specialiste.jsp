<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Consultation des demandes d'expertise</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen">

<!-- Header -->
<header class="bg-gradient-to-r from-blue-600 to-purple-600 text-white shadow-lg py-4 px-6 flex justify-between items-center">
    <h1 class="text-2xl font-bold">Demandes d'expertise</h1>
    <div class="space-x-3">
        <a href="${pageContext.request.contextPath}/profil/config"
           class="bg-white text-blue-600 px-4 py-2 rounded-lg font-medium hover:bg-gray-100">
            Voir Profil
        </a>
        <a href="${pageContext.request.contextPath}/logout"
           class="bg-red-500 px-4 py-2 rounded-lg font-medium hover:bg-red-600">
            Logout
        </a>
    </div>
</header>

<div class="container mx-auto px-6 py-6">

    <!-- Formulaire de filtrage -->
    <form method="get" class="mb-6 flex flex-wrap gap-4 items-center">
        <select name="statut" class="border rounded px-3 py-2 focus:ring focus:ring-blue-300">
            <option value="">-- Statut --</option>
            <option value="EN_ATTENTE">En attente</option>
            <option value="TERMINEE">Terminée</option>
        </select>

        <select name="priorite" class="border rounded px-3 py-2 focus:ring focus:ring-blue-300">
            <option value="">-- Priorité --</option>
            <option value="HAUTE">Haute</option>
            <option value="MOYENNE">Moyenne</option>
            <option value="BASSE">Basse</option>
        </select>

        <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            Filtrer
        </button>
    </form>

    <!-- Table des demandes -->
    <div class="overflow-x-auto">
        <table class="min-w-full bg-white shadow-md rounded-lg overflow-hidden">
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
                <tr class="hover:bg-gray-100 transition-colors">
                    <td class="py-2 px-4 border">${demande.id}</td>
                    <td class="py-2 px-4 border">
                            ${demande.consultation.patient.nom} ${demande.consultation.patient.prenom} <br/>
                        <span class="text-sm text-gray-500">Tél: ${demande.consultation.patient.telephone}</span>
                    </td>
                    <td class="py-2 px-4 border">${demande.question}</td>
                    <td class="py-2 px-4 border">${demande.statut}</td>
                    <td class="py-2 px-4 border">${demande.priorite}</td>
                    <td class="py-2 px-4 border">${demande.reponse}</td>
                    <td class="py-2 px-4 border">${demande.recommandations}</td>
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
</div>

</body>
</html>
