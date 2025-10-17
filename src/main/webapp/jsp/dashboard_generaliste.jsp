<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Généraliste</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');

        * { font-family: 'Inter', sans-serif; }
        .gradient-bg { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .card-hover { transition: all 0.3s ease; }
        .card-hover:hover { transform: translateY(-2px); box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1); }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); transition: all 0.3s ease; }
        .btn-primary:hover { transform: translateY(-1px); box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4); }
        .section-title { position: relative; padding-left: 16px; }
        .section-title::before {
            content: ''; position: absolute; left: 0; top: 50%; transform: translateY(-50%);
            width: 4px; height: 24px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 2px;
        }
        .modal {
            position: fixed; inset: 0; background: rgba(0,0,0,0.5);
            display: none; align-items: center; justify-content: center;
        }
        .modal.active { display: flex; }
    </style>
</head>
<body class="bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50 min-h-screen">

<!-- HEADER -->
<header class="gradient-bg text-white py-6 shadow-lg">
    <div class="container mx-auto px-6 flex items-center justify-between">
        <div>
            <h1 class="text-3xl font-bold tracking-tight">Tableau de bord</h1>
            <p class="text-blue-100 text-sm mt-1">Médecin Généraliste</p>
        </div>
        <div class="bg-white/20 backdrop-blur-sm px-4 py-2 rounded-lg">
            <p class="text-sm font-medium">Aujourd'hui</p>
            <p class="text-xs text-blue-100" id="currentDate"></p>
        </div>
    </div>
</header>

<!-- CONTENU -->
<div class="container mx-auto px-6 py-8 max-w-7xl">

    <!-- Bouton pour ouvrir le pop-up -->
    <div class="flex justify-end mb-6">
        <button onclick="openModal()" class="btn-primary text-white px-6 py-2 rounded-lg">
            ➕ Nouvelle consultation
        </button>
    </div>

    <!-- Liste des consultations -->
    <section class="bg-white p-8 rounded-3xl shadow-lg card-hover border border-gray-100">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 section-title">Liste des consultations</h2>

        <c:if test="${empty consultations}">
            <p class="text-gray-500 text-center">Aucune consultation enregistrée.</p>
        </c:if>

        <c:if test="${not empty consultations}">
            <table class="w-full border-collapse">
                <thead>
                <tr class="bg-gray-100 text-gray-700">
                    <th class="py-3 px-4 text-left">#</th>
                    <th class="py-3 px-4 text-left">Patient</th>
                    <th class="py-3 px-4 text-left">Motif</th>
                    <th class="py-3 px-4 text-left">Statut</th>
                    <th class="py-3 px-4 text-left">Date</th>
                    <th class="py-3 px-4 text-left">Coût</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="c" items="${consultations}">
                    <tr class="border-b hover:bg-gray-50">
                        <td class="py-3 px-4">${c.id}</td>
                        <td class="py-3 px-4">${c.patient.nom} ${c.patient.prenom}</td>
                        <td class="py-3 px-4">${c.motif}</td>
                        <td class="py-3 px-4">
                                <span class="px-3 py-1 text-xs rounded-full
                                    ${c.statut == 'EN_COURS' ? 'bg-yellow-100 text-yellow-700' :
                                      (c.statut == 'TERMINEE' ? 'bg-green-100 text-green-700' :
                                      'bg-gray-100 text-gray-700')}">
                                        ${c.statut}
                                </span>
                        </td>
                        <td class="py-3 px-4">${c.dateConsultation}</td>
                        <td class="py-3 px-4 font-semibold text-blue-600">${c.cout} DH</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </section>
</div>

<!-- MODAL AJOUT CONSULTATION -->
<div id="consultationModal" class="modal">
    <div class="bg-white rounded-3xl shadow-lg p-8 w-full max-w-lg relative">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">Ajouter une Consultation</h2>
        <form action="${pageContext.request.contextPath}/consultations/add" method="post" class="space-y-4">

            <!-- Patient -->
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Patient :</label>
                <select name="patientId" required
                        class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300">
                    <option value="">-- Sélectionnez un patient --</option>
                    <c:forEach var="p" items="${patients}">
                        <option value="${p.id}">${p.nom} ${p.prenom}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Motif -->
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Motif :</label>
                <input type="text" name="motif" required
                       class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300"
                       placeholder="Ex: Douleur abdominale">
            </div>

            <!-- Observations -->
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Observations :</label>
                <textarea name="observations" rows="4"
                          class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300"
                          placeholder="Notes cliniques..."></textarea>
            </div>

            <!-- Boutons -->
            <div class="flex justify-end space-x-3 pt-4">
                <button type="button" onclick="closeModal()"
                        class="px-5 py-2 rounded-lg border border-gray-300 hover:bg-gray-100">Annuler</button>
                <button type="submit"
                        class="btn-primary text-white px-5 py-2 rounded-lg">Enregistrer</button>
            </div>
        </form>

        <!-- Bouton X -->
        <button onclick="closeModal()" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 text-xl">&times;</button>
    </div>
</div>

<!-- SCRIPT -->
<script>
    const modal = document.getElementById('consultationModal');
    function openModal() { modal.classList.add('active'); }
    function closeModal() { modal.classList.remove('active'); }

    document.getElementById('currentDate').textContent = new Date().toLocaleDateString('fr-FR', {
        weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
    });
</script>

</body>
</html>
