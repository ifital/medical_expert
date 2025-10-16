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
        .input-focus:focus { border-color: #667eea; outline: none; }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); transition: all 0.3s ease; }
        .btn-primary:hover { transform: translateY(-1px); box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4); }
        .section-title { position: relative; padding-left: 16px; }
        .section-title::before {
            content: ''; position: absolute; left: 0; top: 50%;
            transform: translateY(-50%); width: 4px; height: 24px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 2px;
        }
        .table-row:hover { background-color: #f9fafb; }
        .cost-badge {
            background: linear-gradient(135deg, #ffd89b 0%, #19547b 100%);
            -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;
        }
    </style>
</head>
<body class="bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50 min-h-screen">

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

<div class="container mx-auto px-6 py-8 max-w-7xl">

    <!-- SECTION DEMANDE D’EXPERTISE -->
    <section class="bg-white p-8 rounded-3xl shadow-lg mb-8 card-hover border border-gray-100">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 section-title">Demande d’Expertise Médicale</h2>

        <c:if test="${not empty specialistes}">
            <form method="post" action="${pageContext.request.contextPath}/expertise/request" class="space-y-4">

                <!-- Sélection de la consultation -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Sélectionner la consultation :</label>
                    <select name="consultationId" required class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300">
                        <option value="">-- Sélectionnez une consultation --</option>
                        <c:forEach var="c" items="${consultations}">
                            <option value="${c.id}">Consultation #${c.id} — ${c.patient.nom}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Sélection du spécialiste -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Choisir un spécialiste :</label>
                    <select name="specialisteId" id="specialisteSelect" required class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300" onchange="updateCreneaux()">
                        <option value="">-- Sélectionnez un spécialiste --</option>
                        <c:forEach var="s" items="${specialistes}">
                            <option value="${s.id}">${s.username} — ${s.specialite} (${s.tarif} DH)</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Sélection du créneau -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Sélectionner un créneau disponible :</label>
                    <select name="creneauId" id="creneauSelect" required class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300">
                        <option value="">-- Sélectionnez un créneau --</option>
                    </select>
                </div>

                <!-- Question -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Question au spécialiste :</label>
                    <textarea name="question" rows="4" required class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300" placeholder="Décrivez votre question ou demande d’avis..."></textarea>
                </div>

                <!-- Priorité -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Priorité :</label>
                    <select name="priorite" required class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300">
                        <option value="BASSE">Basse</option>
                        <option value="MOYENNE">Moyenne</option>
                        <option value="HAUTE">Haute</option>
                    </select>
                </div>

                <div class="text-center">
                    <button type="submit" class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700">Envoyer la demande</button>
                </div>
            </form>
        </c:if>

        <c:if test="${empty specialistes}">
            <p class="text-center text-gray-500 mt-6">Aucun spécialiste trouvé pour cette spécialité.</p>
        </c:if>
    </section>
</div>

<script>
    // JSON des spécialistes et leurs créneaux envoyés depuis le servlet
    const specialistes = ${specialistesJs}; // ex: { "1": {creneaux:[{id:1, dateHeure:"2025-10-16 14:00"}]}, ... }

    function updateCreneaux() {
        const selectSpecialiste = document.getElementById('specialisteSelect');
        const selectCreneau = document.getElementById('creneauSelect');
        const specialisteId = selectSpecialiste.value;

        selectCreneau.innerHTML = '<option value="">-- Sélectionnez un créneau --</option>';

        if(specialisteId && specialistes[specialisteId]) {
            const creneaux = specialistes[specialisteId].creneaux;
            creneaux.forEach(cr => {
                const option = document.createElement('option');
                option.value = cr.id;
                option.textContent = cr.dateHeure; // format: "YYYY-MM-DD HH:mm"
                selectCreneau.appendChild(option);
            });
        }
    }

    // Affichage de la date actuelle
    document.getElementById('currentDate').textContent = new Date().toLocaleDateString('fr-FR', {
        weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
    });
</script>

</body>
</html>
