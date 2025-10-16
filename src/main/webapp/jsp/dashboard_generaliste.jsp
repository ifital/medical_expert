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

<div class="container mx-auto px-6 py-8 max-w-7xl">

    <!-- 🔹 SECTION CRÉATION DE CONSULTATION -->
    <section class="bg-white p-8 rounded-3xl shadow-lg mb-8 card-hover border border-gray-100">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 section-title">Créer une consultation</h2>
        <form action="${pageContext.request.contextPath}/consultations/add" method="post" class="space-y-6">

            <div class="grid md:grid-cols-2 gap-6">
                <div>
                    <label class="block text-sm font-semibold text-gray-700 mb-2">Patient</label>
                    <select name="patientId" required class="w-full p-3 border-2 border-gray-200 rounded-xl input-focus">
                        <option value="">-- Sélectionner un patient --</option>
                        <c:forEach var="p" items="${patients}">
                            <option value="${p.id}">${p.nom} ${p.prenom}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label class="block text-sm font-semibold text-gray-700 mb-2">Motif de consultation</label>
                    <input type="text" name="motif" required class="w-full p-3 border-2 border-gray-200 rounded-xl input-focus">
                </div>
            </div>

            <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Observations</label>
                <textarea name="observations" rows="4" class="w-full p-3 border-2 border-gray-200 rounded-xl input-focus"></textarea>
            </div>

            <div class="bg-gradient-to-r from-purple-50 to-blue-50 p-4 rounded-xl border border-purple-100">
                <p class="text-lg font-bold text-gray-800">
                    💰 Coût consultation : <span id="consultationCost" class="cost-badge text-xl">150</span> DH
                </p>
                <input type="hidden" name="cout" value="150">
            </div>

            <button type="submit" class="btn-primary text-white px-8 py-3 rounded-xl font-semibold shadow-md">
                ✓ Enregistrer la consultation
            </button>
        </form>
    </section>

    <!-- 🔹 SECTION DEMANDE D’EXPERTISE -->
    <section class="bg-white p-8 rounded-3xl shadow-lg mb-8 card-hover border border-gray-100">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 section-title">Demande d’Expertise Médicale</h2>

        <!-- ✅ Filtrage par spécialité -->
        <form method="get" action="${pageContext.request.contextPath}/expertise/request" class="mb-6 flex space-x-2">
            <input type="text" name="specialite" placeholder="Rechercher par spécialité"
                   value="${param.specialite}" class="flex-1 border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300"/>
            <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
                Rechercher
            </button>
        </form>

        <!-- ✅ Liste des spécialistes -->
        <c:if test="${not empty specialistes}">
            <form method="post" action="${pageContext.request.contextPath}/expertise/request" class="space-y-4">

                <!-- Sélection de la consultation -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Sélectionner la consultation concernée :</label>
                    <select name="consultationId" required
                            class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300">
                        <option value="">-- Sélectionnez une consultation --</option>
                        <c:forEach var="c" items="${consultations}">
                            <option value="${c.id}">Consultation #${c.id} — ${c.patient.nom}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Sélection du spécialiste -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Choisir un spécialiste :</label>
                    <select name="specialisteId" required
                            class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300">
                        <option value="">-- Sélectionnez un spécialiste --</option>
                        <c:forEach var="s" items="${specialistes}">
                            <option value="${s.id}">
                                    ${s.username} — ${s.specialite} (${s.tarif} DH)
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Question -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Question au spécialiste :</label>
                    <textarea name="question" rows="4" required
                              class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300"
                              placeholder="Décrivez votre question ou demande d’avis..."></textarea>
                </div>

                <!-- Priorité -->
                <div>
                    <label class="block text-gray-700 mb-1 font-medium">Priorité :</label>
                    <select name="priorite" required
                            class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300">
                        <option value="BASSE">Basse</option>
                        <option value="MOYENNE">Moyenne</option>
                        <option value="HAUTE">Haute</option>
                    </select>
                </div>

                <div class="text-center">
                    <button type="submit" class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700">
                        Envoyer la demande
                    </button>
                </div>
            </form>
        </c:if>

        <c:if test="${empty specialistes}">
            <p class="text-center text-gray-500 mt-6">
                Aucun spécialiste trouvé pour cette spécialité.
            </p>
        </c:if>
    </section>

    <!-- 🔹 SECTION HISTORIQUE -->
    <section class="bg-white p-8 rounded-3xl shadow-lg border border-gray-100">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 section-title">Historique des consultations</h2>
        <div class="overflow-x-auto rounded-xl border border-gray-200">
            <table class="w-full">
                <thead>
                <tr class="bg-gradient-to-r from-purple-50 to-blue-50">
                    <th class="p-4 text-left font-semibold text-gray-700 border-b-2 border-purple-200">Patient</th>
                    <th class="p-4 text-left font-semibold text-gray-700 border-b-2 border-purple-200">Date</th>
                    <th class="p-4 text-left font-semibold text-gray-700 border-b-2 border-purple-200">Motif</th>
                    <th class="p-4 text-left font-semibold text-gray-700 border-b-2 border-purple-200">Coût</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="c" items="${consultations}">
                    <tr class="table-row border-b border-gray-100">
                        <td class="p-4 font-medium text-gray-800">${c.patient.nom} ${c.patient.prenom}</td>
                        <td class="p-4 text-gray-600">${c.dateConsultation}</td>
                        <td class="p-4 text-gray-600">${c.motif}</td>
                        <td class="p-4 font-semibold text-purple-700">${c.cout} DH</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
</div>

<script>
    const dateEl = document.getElementById('currentDate');
    dateEl.textContent = new Date().toLocaleDateString('fr-FR', {
        weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
    });
</script>
</body>
</html>
