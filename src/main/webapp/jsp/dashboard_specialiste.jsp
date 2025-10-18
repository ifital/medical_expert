<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demandes d'expertise - Télé-Expertise</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
        * { font-family: 'Inter', sans-serif; }
        .gradient-bg { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); transition: all 0.3s ease; }
        .btn-primary:hover { transform: translateY(-1px); box-shadow: 0 6px 20px rgba(102,126,234,0.4); }
        .card-hover { transition: all 0.3s ease; }
        .card-hover:hover { transform: translateY(-2px); box-shadow: 0 12px 24px rgba(0,0,0,0.05); }
    </style>
</head>
<body class="bg-gray-50 min-h-screen">

<header class="gradient-bg text-white shadow-lg py-4 px-6 flex justify-between items-center">
    <h1 class="text-2xl font-bold">Demandes d'expertise</h1>
    <div class="space-x-3">
        <a href="${pageContext.request.contextPath}/profil/config" class="bg-white text-blue-600 px-4 py-2 rounded-lg font-medium hover:bg-gray-100">Voir Profil</a>
        <a href="${pageContext.request.contextPath}/mes-creneaux" class="bg-white text-blue-600 px-4 py-2 rounded-lg font-medium hover:bg-gray-100">Mes Créneaux</a>
        <a href="${pageContext.request.contextPath}/logout" class="bg-red-500 px-4 py-2 rounded-lg font-medium hover:bg-red-600">Logout</a>
    </div>
</header>

<div class="container mx-auto px-6 py-8">

    <!-- Formulaire de filtrage -->
    <form method="get" class="mb-6 flex flex-wrap gap-4 items-center">
        <select name="statut" class="border rounded-lg px-4 py-2 focus:ring-2 focus:ring-blue-300">
            <option value="">-- Statut --</option>
            <option value="EN_ATTENTE" <c:if test="${selectedStatut == 'EN_ATTENTE'}">selected</c:if>>En attente</option>
            <option value="TERMINEE" <c:if test="${selectedStatut == 'TERMINEE'}">selected</c:if>>Terminée</option>
        </select>

        <select name="priorite" class="border rounded-lg px-4 py-2 focus:ring-2 focus:ring-blue-300">
            <option value="">-- Priorité --</option>
            <option value="HAUTE" <c:if test="${selectedPriorite == 'HAUTE'}">selected</c:if>>Haute</option>
            <option value="MOYENNE" <c:if test="${selectedPriorite == 'MOYENNE'}">selected</c:if>>Moyenne</option>
            <option value="BASSE" <c:if test="${selectedPriorite == 'BASSE'}">selected</c:if>>Basse</option>
        </select>

        <button type="submit" class="btn-primary text-white px-4 py-2 rounded-lg">Filtrer</button>
    </form>

    <!-- Table des demandes -->
    <div class="overflow-x-auto card-hover bg-white rounded-3xl shadow-md p-4">
        <table class="min-w-full border-collapse">
            <thead class="bg-gray-100 rounded-lg">
            <tr>
                <th class="py-2 px-4 text-left border-b">ID</th>
                <th class="py-2 px-4 text-left border-b">Patient</th>
                <th class="py-2 px-4 text-left border-b">Question</th>
                <th class="py-2 px-4 text-left border-b">Statut</th>
                <th class="py-2 px-4 text-left border-b">Priorité</th>
                <th class="py-2 px-4 text-left border-b">Réponse</th>
                <th class="py-2 px-4 text-left border-b">Recommandations</th>
                <th class="py-2 px-4 text-left border-b">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="demande" items="${demandes}">
                <tr class="hover:bg-gray-50 transition-colors">
                    <td class="py-2 px-4 border">${demande.id}</td>
                    <td class="py-2 px-4 border">
                            ${demande.consultation.patient.nom} ${demande.consultation.patient.prenom} <br/>
                        <span class="text-sm text-gray-500">Tél: ${demande.consultation.patient.telephone}</span>
                    </td>
                    <td class="py-2 px-4 border">${demande.question}</td>
                    <td class="py-2 px-4 border">
                        <span class="px-2 py-1 rounded-full
                            ${demande.statut == 'EN_ATTENTE' ? 'bg-yellow-100 text-yellow-700' :
                              (demande.statut == 'TERMINEE' ? 'bg-green-100 text-green-700' :
                              'bg-gray-100 text-gray-700')}">
                                ${demande.statut}
                        </span>
                    </td>
                    <td class="py-2 px-4 border">
                        <span class="px-2 py-1 rounded-full
                            ${demande.priorite == 'HAUTE' ? 'bg-red-100 text-red-700' :
                              (demande.priorite == 'MOYENNE' ? 'bg-yellow-100 text-yellow-700' :
                              'bg-green-100 text-green-700')}">
                                ${demande.priorite}
                        </span>
                    </td>
                    <td class="py-2 px-4 border">${demande.reponse}</td>
                    <td class="py-2 px-4 border">${demande.recommandations}</td>
                    <td class="py-2 px-4 border">
                        <button type="button"
                                class="btn-primary text-white px-3 py-1 rounded-lg"
                                data-id="${demande.id}"
                                data-reponse="${demande.reponse}"
                                data-recommandations="${demande.recommandations}"
                                data-statut="${demande.statut}"
                                onclick="openTraitementModal(this)">
                            Traiter
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty demandes}">
                <tr>
                    <td colspan="8" class="py-6 text-center text-gray-500">Aucune demande trouvée</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

</div>

<!-- MODAL TRAITEMENT DEMANDE -->
<div id="traitementModal" class="hidden fixed inset-0 bg-black/50 flex items-center justify-center z-50">
    <div class="bg-white rounded-3xl shadow-lg w-full max-w-lg relative max-h-[90vh] overflow-y-auto p-8">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">Traiter la demande d'expertise</h2>
        <form id="traitementForm" method="post" action="">
            <input type="hidden" name="demandeId" id="demandeId">

            <div>
                <label class="block text-gray-700 mb-1 font-medium">Avis médical :</label>
                <textarea name="reponse" id="reponse" rows="4" required class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300" placeholder="Saisir l'avis médical..."></textarea>
            </div>

            <div>
                <label class="block text-gray-700 mb-1 font-medium">Recommandations :</label>
                <textarea name="recommandations" id="recommandations" rows="3" class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300" placeholder="Saisir les recommandations..."></textarea>
            </div>

            <div class="mt-4 flex items-center space-x-3">
                <label class="inline-flex items-center">
                    <input type="checkbox" name="terminer" id="terminer" class="form-checkbox h-5 w-5 text-blue-600">
                    <span class="ml-2 text-gray-700">Marquer comme terminée</span>
                </label>
            </div>

            <div class="flex justify-end space-x-3 pt-6">
                <button type="button" onclick="closeTraitementModal()" class="px-5 py-2 rounded-lg border border-gray-300 hover:bg-gray-100">Annuler</button>
                <button type="submit" class="btn-primary text-white px-5 py-2 rounded-lg">Enregistrer</button>
            </div>
        </form>
        <button onclick="closeTraitementModal()" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 text-xl">&times;</button>
    </div>
</div>

<script>
    function openTraitementModal(button) {
        const id = button.dataset.id;
        document.getElementById('demandeId').value = id;
        document.getElementById('reponse').value = button.dataset.reponse || '';
        document.getElementById('recommandations').value = button.dataset.recommandations || '';
        document.getElementById('terminer').checked = (button.dataset.statut === 'TERMINEE');

        // ✅ Mettre l'ID dans l'action POST pour la servlet
        document.getElementById('traitementForm').action = '${pageContext.request.contextPath}/expertise/update/' + id;

        document.getElementById('traitementModal').classList.remove('hidden');
    }

    function closeTraitementModal() {
        document.getElementById('traitementModal').classList.add('hidden');
    }

    // Fermer la modal en cliquant à l'extérieur
    document.addEventListener('click', function(event) {
        const modal = document.getElementById('traitementModal');
        if (event.target === modal) closeTraitementModal();
    });
</script>

</body>
</html>
