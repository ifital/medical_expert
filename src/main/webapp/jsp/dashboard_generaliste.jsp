<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Généraliste</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans p-6">

<h2 class="text-2xl font-bold text-blue-700 mb-6">
    Bienvenue, Dr ${sessionScope.user.nom} ${sessionScope.user.prenom}
</h2>

<!-- Liste des patients -->
<h3 class="text-xl font-semibold mb-2">Patients</h3>
<table class="min-w-full bg-white shadow rounded overflow-hidden mb-4">
    <thead class="bg-blue-600 text-white">
    <tr>
        <th class="p-2">ID</th>
        <th class="p-2">Nom</th>
        <th class="p-2">Prénom</th>
        <th class="p-2">Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${patients}">
        <tr class="text-center border-b hover:bg-gray-50">
            <td class="p-2">${p.id}</td>
            <td class="p-2">${p.nom}</td>
            <td class="p-2">${p.prenom}</td>
            <td class="p-2 space-x-2">
                <!-- Bouton pour créer une consultation -->
                <button onclick="openConsultationModal(${p.id}, '${p.nom}', '${p.prenom}')"
                        class="bg-green-600 text-white px-2 py-1 rounded hover:bg-green-800">
                    Créer consultation
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Modal Consultation -->
<div id="consultationModal" class="fixed inset-0 m-auto bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-white rounded-lg shadow-lg w-96 p-6 relative">
        <button onclick="closeConsultationModal()" class="absolute top-2 right-2 text-gray-500 hover:text-gray-700">&times;</button>
        <h3 class="text-lg font-semibold mb-4">Nouvelle Consultation</h3>
        <form action="${pageContext.request.contextPath}/consultations/add" method="post" class="flex flex-col gap-2">
            <input type="hidden" name="patientId" id="modalPatientId">
            <label>Motif :</label>
            <input type="text" name="motif" class="border border-gray-300 rounded px-2 py-1" required>
            <label>Observations :</label>
            <textarea name="observations" class="border border-gray-300 rounded px-2 py-1" required></textarea>

            <p class="text-gray-700 font-semibold">Coût consultation : 150 DH</p>

            <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-800 mt-2">Valider</button>
        </form>
    </div>
</div>

<!-- Modal Expertise -->
<div id="expertiseModal" class="fixed inset-0 m-auto bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-white rounded-lg shadow-lg w-96 p-6 relative">
        <button onclick="closeExpertiseModal()" class="absolute top-2 right-2 text-gray-500 hover:text-gray-700">&times;</button>
        <h3 class="text-lg font-semibold mb-4">Demande d'Expertise</h3>
        <form action="${pageContext.request.contextPath}/expertise/request" method="post" class="flex flex-col gap-2">
            <input type="hidden" name="consultationId" id="expertiseConsultationId">
            <label>Spécialité :</label>
            <select name="specialisteId" class="border border-gray-300 rounded px-2 py-1" required>
                <c:forEach var="s" items="${specialistes}">
                    <option value="${s.id}">${s.specialite} - ${s.nom} ${s.prenom} (Tarif : ${s.tarif} DH)</option>
                </c:forEach>
            </select>
            <label>Question :</label>
            <textarea name="question" class="border border-gray-300 rounded px-2 py-1" required></textarea>
            <label>Priorité :</label>
            <select name="priorite" class="border border-gray-300 rounded px-2 py-1">
                <option value="NORMALE">Normale</option>
                <option value="URGENTE">Urgente</option>
                <option value="NON_URGENTE">Non urgente</option>
            </select>

            <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-800 mt-2">Envoyer</button>
        </form>
    </div>
</div>

<script>
    function openConsultationModal(id, nom, prenom) {
        document.getElementById('modalPatientId').value = id;
        document.getElementById('consultationModal').classList.remove('hidden');
    }

    function closeConsultationModal() {
        document.getElementById('consultationModal').classList.add('hidden');
    }

    function openExpertiseModal(consultationId) {
        document.getElementById('expertiseConsultationId').value = consultationId;
        document.getElementById('expertiseModal').classList.remove('hidden');
    }

    function closeExpertiseModal() {
        document.getElementById('expertiseModal').classList.add('hidden');
    }
</script>

</body>
</html>
