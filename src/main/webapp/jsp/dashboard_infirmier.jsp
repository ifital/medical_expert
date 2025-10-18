<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Infirmier - Télé-Expertise</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
        * { font-family: 'Inter', sans-serif; }
        .gradient-bg { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            transition: all 0.3s ease;
        }
        .btn-primary:hover {
            transform: translateY(-1px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }
        .card-hover { transition: all 0.3s ease; }
        .card-hover:hover { transform: translateY(-2px); box-shadow: 0 12px 24px rgba(0,0,0,0.05); }
    </style>
</head>
<body class="bg-gray-50 min-h-screen">

<!-- Header -->
<header class="gradient-bg text-white shadow-lg py-4 px-6 flex justify-between items-center">
    <h1 class="text-2xl font-bold">Bienvenue, Infirmier ${sessionScope.user.nom}</h1>
    <div class="space-x-3">
        <a href="${pageContext.request.contextPath}/logout"
           class="bg-red-500 px-4 py-2 rounded-lg font-medium hover:bg-red-600">
            Déconnexion
        </a>
    </div>
</header>

<!-- Contenu principal -->
<div class="container mx-auto px-6 py-8">

    <!-- Filtrage -->
    <form action="${pageContext.request.contextPath}/dashboard/infirmier" method="get"
          class="mb-6 flex flex-wrap items-center gap-3 bg-white p-4 rounded-2xl shadow-md">
        <input type="hidden" name="action" value="filter">
        <label for="date" class="font-medium text-gray-700">Filtrer par date d’arrivée :</label>
        <input type="date" name="date" id="date" value="${dateRecherche}"
               class="border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-300">
        <button type="submit" class="btn-primary text-white px-4 py-2 rounded-lg">
            Filtrer
        </button>
        <a href="${pageContext.request.contextPath}/dashboard/infirmier?action=list"
           class="text-blue-600 font-medium hover:underline">
            Réinitialiser
        </a>
    </form>

    <!-- Tableau -->
    <div class="bg-white shadow-lg rounded-2xl overflow-hidden card-hover">
        <h3 class="text-xl font-semibold px-6 pt-4 pb-2 text-gray-700">Liste des Patients Sans Consultation</h3>
        <div class="overflow-x-auto">
            <table class="min-w-full border-collapse">
                <thead class="bg-gray-100 text-gray-700">
                <tr>
                    <th class="p-3 text-left">Nom</th>
                    <th class="p-3 text-left">Prénom</th>
                    <th class="p-3 text-left">Téléphone</th>
                    <th class="p-3 text-left">Mutuelle</th>
                    <th class="p-3 text-left">Tension</th>
                    <th class="p-3 text-left">Température</th>
                    <th class="p-3 text-left">Fréq. Cardiaque</th>
                    <th class="p-3 text-left">Fréq. Respiratoire</th>
                    <th class="p-3 text-left">Poids</th>
                    <th class="p-3 text-left">Taille</th>
                    <th class="p-3 text-left">Date d’arrivée</th>
                    <th class="p-3 text-center">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${patients}">
                    <tr class="border-t hover:bg-gray-50 transition-colors">
                        <td class="p-3">${p.nom}</td>
                        <td class="p-3">${p.prenom}</td>
                        <td class="p-3">${p.telephone}</td>
                        <td class="p-3">${p.mutuelle}</td>
                        <td class="p-3">${p.tension}</td>
                        <td class="p-3">${p.temperature}</td>
                        <td class="p-3">${p.frequenceCardiaque}</td>
                        <td class="p-3">${p.frequenceRespiratoire}</td>
                        <td class="p-3">${p.poids}</td>
                        <td class="p-3">${p.taille}</td>
                        <td class="p-3">${p.dateArrivee}</td>
                        <td class="p-3 text-center space-x-2">
                            <button
                                    class="text-blue-600 hover:underline"
                                    onclick="openModal(${p.id}, '${p.nom}', '${p.prenom}', '${p.numeroSecuriteSociale}', '${p.telephone}', '${p.adresse}', '${p.mutuelle}', ${p.tension}, ${p.temperature}, ${p.frequenceCardiaque}, ${p.frequenceRespiratoire}, ${p.poids}, ${p.taille})">
                                Modifier
                            </button>
                            |
                            <a href='${pageContext.request.contextPath}/dashboard/infirmier?action=delete&id=${p.id}'
                               onclick="return confirm('Voulez-vous vraiment supprimer ce patient ?');"
                               class="text-red-600 hover:underline">
                                Supprimer
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty patients}">
                    <tr>
                        <td colspan="13" class="p-4 text-center text-gray-500">Aucun patient trouvé</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Bouton Ajouter -->
    <div class="mt-6 flex justify-end">
        <button onclick="openModal(0)" class="btn-primary text-white px-6 py-2 rounded-lg">
            + Ajouter un patient
        </button>
    </div>

</div>

<!-- Modal scrollable -->
<div id="patientModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50">
    <div class="bg-white rounded-2xl shadow-2xl w-96 max-h-[80vh] overflow-y-auto p-6 relative">
        <button onclick="closeModal()" class="absolute top-3 right-3 text-gray-500 hover:text-gray-700 text-2xl">&times;</button>
        <h3 class="text-lg font-semibold mb-4" id="modalTitle">Ajouter un patient</h3>

        <form action="${pageContext.request.contextPath}/dashboard/infirmier" method="post" class="flex flex-col gap-3">
            <input type="hidden" name="action" id="formAction" value="add">
            <input type="hidden" name="id" id="patientId">

            <input type="text" name="nom" id="nom" placeholder="Nom" class="border rounded-lg px-3 py-2">
            <input type="text" name="prenom" id="prenom" placeholder="Prénom" class="border rounded-lg px-3 py-2">
            <input type="text" name="numeroSecuriteSociale" id="numeroSecuriteSociale" placeholder="Numéro Sécu" class="border rounded-lg px-3 py-2">
            <input type="text" name="telephone" id="telephone" placeholder="Téléphone" class="border rounded-lg px-3 py-2">
            <input type="text" name="adresse" id="adresse" placeholder="Adresse" class="border rounded-lg px-3 py-2">
            <input type="text" name="mutuelle" id="mutuelle" placeholder="Mutuelle" class="border rounded-lg px-3 py-2">
            <input type="number" step="0.1" name="tension" id="tension" placeholder="Tension" class="border rounded-lg px-3 py-2">
            <input type="number" step="0.1" name="temperature" id="temperature" placeholder="Température" class="border rounded-lg px-3 py-2">
            <input type="number" name="frequenceCardiaque" id="frequenceCardiaque" placeholder="Fréquence cardiaque" class="border rounded-lg px-3 py-2">
            <input type="number" name="frequenceRespiratoire" id="frequenceRespiratoire" placeholder="Fréquence respiratoire" class="border rounded-lg px-3 py-2">
            <input type="number" step="0.1" name="poids" id="poids" placeholder="Poids" class="border rounded-lg px-3 py-2">
            <input type="number" step="0.1" name="taille" id="taille" placeholder="Taille" class="border rounded-lg px-3 py-2">

            <button type="submit" class="btn-primary text-white px-4 py-2 rounded-lg mt-2 w-full" id="submitButton">
                Ajouter
            </button>
        </form>
    </div>
</div>

<!-- Script -->
<script>
    const modal = document.getElementById('patientModal');
    const formAction = document.getElementById('formAction');
    const modalTitle = document.getElementById('modalTitle');
    const submitButton = document.getElementById('submitButton');

    function openModal(id, nom='', prenom='', numeroSecuriteSociale='', telephone='', adresse='', mutuelle='', tension='', temperature='', frequenceCardiaque='', frequenceRespiratoire='', poids='', taille='') {
        modal.classList.remove('hidden');
        modal.classList.add('flex');

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

        if (id > 0) {
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
        modal.classList.remove('flex');
        modal.classList.add('hidden');
    }
</script>

</body>
</html>
