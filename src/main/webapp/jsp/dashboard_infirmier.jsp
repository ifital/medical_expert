<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Infirmier</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans p-6">

<h2 class="text-2xl font-bold text-blue-700 mb-6">Bienvenue, Infirmier ${sessionScope.user.nom}</h2>

<!-- Filtrage par date d’arrivée -->
<div class="mb-6">
    <form action="${pageContext.request.contextPath}/patients" method="get" class="flex items-center gap-2">
        <input type="hidden" name="action" value="filter">
        <label for="date" class="font-semibold">Filtrer par date d’arrivée :</label>
        <input type="date" name="date" id="date" value="${dateRecherche}" class="border border-gray-300 rounded px-2 py-1">
        <button type="submit" class="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-800">Filtrer</button>
        <a href="${pageContext.request.contextPath}/patients?action=list" class="text-blue-600 hover:underline">Réinitialiser</a>
    </form>
</div>

<!-- Tableau des patients -->
<h3 class="text-xl font-semibold mb-2">Liste des Patients (triés du plus ancien au plus récent)</h3>
<table class="min-w-full bg-white shadow rounded overflow-hidden">
    <thead class="bg-blue-600 text-white">
    <tr>
        <th class="p-2">ID</th>
        <th class="p-2">Nom</th>
        <th class="p-2">Prénom</th>
        <th class="p-2">Téléphone</th>
        <th class="p-2">Mutuelle</th>
        <th class="p-2">Tension</th>
        <th class="p-2">Température</th>
        <th class="p-2">Fréquence Cardiaque</th>
        <th class="p-2">Fréquence Respiratoire</th>
        <th class="p-2">Poids</th>
        <th class="p-2">Taille</th>
        <th class="p-2">Date d’arrivée</th>
        <th class="p-2">Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${patients}">
        <tr class="text-center border-b hover:bg-gray-50">
            <td class="p-2">${p.id}</td>
            <td class="p-2">${p.nom}</td>
            <td class="p-2">${p.prenom}</td>
            <td class="p-2">${p.telephone}</td>
            <td class="p-2">${p.mutuelle}</td>
            <td class="p-2">${p.tension}</td>
            <td class="p-2">${p.temperature}</td>
            <td class="p-2">${p.frequenceCardiaque}</td>
            <td class="p-2">${p.frequenceRespiratoire}</td>
            <td class="p-2">${p.poids}</td>
            <td class="p-2">${p.taille}</td>
            <td class="p-2">${p.dateArrivee}</td>
            <td class="p-2 space-x-2">
                <button
                        class="text-blue-600 hover:underline"
                        onclick="openModal(${p.id}, '${p.nom}', '${p.prenom}', '${p.numeroSecuriteSociale}', '${p.telephone}', '${p.adresse}', '${p.mutuelle}', ${p.tension}, ${p.temperature}, ${p.frequenceCardiaque}, ${p.frequenceRespiratoire}, ${p.poids}, ${p.taille})">
                    Modifier
                </button>
                |
                <a href="${pageContext.request.contextPath}/patients?action=delete&id=${p.id}"
                   onclick="return confirm('Voulez-vous vraiment supprimer ce patient ?');"
                   class="text-red-600 hover:underline">
                    Supprimer
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Bouton Ajouter -->
<div class="mt-4">
    <button onclick="openModal(0)" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-800">
        Ajouter un patient
    </button>
</div>

<!-- Modal Formulaire -->
<div id="patientModal" class="fixed inset-0 m-auto bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-white rounded-lg shadow-lg w-96 p-6 relative">
        <button onclick="closeModal()" class="absolute top-2 right-2 text-gray-500 hover:text-gray-700">&times;</button>
        <h3 class="text-lg font-semibold mb-4" id="modalTitle">Ajouter un patient</h3>
        <form action="${pageContext.request.contextPath}/patients" method="post" class="flex flex-col gap-2">
            <input type="hidden" name="action" id="formAction" value="add">
            <input type="hidden" name="id" id="patientId">

            <input type="text" name="nom" id="nom" placeholder="Nom" class="border border-gray-300 rounded px-2 py-1">
            <input type="text" name="prenom" id="prenom" placeholder="Prénom" class="border border-gray-300 rounded px-2 py-1">
            <input type="text" name="numeroSecuriteSociale" id="numeroSecuriteSociale" placeholder="Numéro Sécu" class="border border-gray-300 rounded px-2 py-1">
            <input type="text" name="telephone" id="telephone" placeholder="Téléphone" class="border border-gray-300 rounded px-2 py-1">
            <input type="text" name="adresse" id="adresse" placeholder="Adresse" class="border border-gray-300 rounded px-2 py-1">
            <input type="text" name="mutuelle" id="mutuelle" placeholder="Mutuelle" class="border border-gray-300 rounded px-2 py-1">
            <input type="number" step="0.1" name="tension" id="tension" placeholder="Tension" class="border border-gray-300 rounded px-2 py-1">
            <input type="number" step="0.1" name="temperature" id="temperature" placeholder="Température" class="border border-gray-300 rounded px-2 py-1">
            <input type="number" name="frequenceCardiaque" id="frequenceCardiaque" placeholder="Fréquence cardiaque" class="border border-gray-300 rounded px-2 py-1">
            <input type="number" name="frequenceRespiratoire" id="frequenceRespiratoire" placeholder="Fréquence respiratoire" class="border border-gray-300 rounded px-2 py-1">
            <input type="number" step="0.1" name="poids" id="poids" placeholder="Poids" class="border border-gray-300 rounded px-2 py-1">
            <input type="number" step="0.1" name="taille" id="taille" placeholder="Taille" class="border border-gray-300 rounded px-2 py-1">

            <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-800 mt-2" id="submitButton">Ajouter</button>
        </form>
    </div>
</div>

<p class="mt-6"><a href="logout" class="text-red-600 hover:underline">Déconnexion</a></p>

<script>
    const modal = document.getElementById('patientModal');
    const formAction = document.getElementById('formAction');
    const modalTitle = document.getElementById('modalTitle');
    const submitButton = document.getElementById('submitButton');

    function openModal(id, nom='', prenom='', numeroSecuriteSociale='', telephone='', adresse='', mutuelle='', tension='', temperature='', frequenceCardiaque='', frequenceRespiratoire='', poids='', taille='') {
        modal.classList.remove('hidden');

        // Remplissage du formulaire
        document.getElementById('patientId').value = id;
        document.getElementById('nom').value = nom;
        document.getElementById('prenom').value = prenom;
        document.getElementById('numeroSecuriteSociale').value = numeroSecuriteSociale;
        document.getElementById('telephone').value = telephone;
        document.getElementById('adresse').value = adresse;
        document.getElementById('mutuelle').value = mutuelle;
        document.getElementById('tension').value = tension;
        document.getElementById('temperature').value = temperature;
        document.getElementById('frequenceCardiaque').value = frequenceCardiaque;
        document.getElementById('frequenceRespiratoire').value = frequenceRespiratoire;
        document.getElementById('poids').value = poids;
        document.getElementById('taille').value = taille;

        if(id > 0){
            formAction.value = 'update';
            modalTitle.innerText = 'Modifier un patient';
            submitButton.innerText = 'Mettre à jour';
        } else {
            formAction.value = 'add';
            modalTitle.innerText = 'Ajouter un patient';
            submitButton.innerText = 'Ajouter';
        }
    }

    function closeModal() {
        modal.classList.add('hidden');
    }
</script>

</body>
</html>
