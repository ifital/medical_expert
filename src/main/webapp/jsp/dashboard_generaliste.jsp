<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Généraliste</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 text-gray-800">

<!-- HEADER -->
<header class="bg-[oklch(78.9%_0.154_211.53)] text-white py-4 shadow-md">
    <h1 class="text-center text-2xl font-semibold">Tableau de bord - Médecin Généraliste</h1>
</header>

<div class="container mx-auto px-6 py-8">

    <!-- 🔹 SECTION CRÉATION DE CONSULTATION -->
    <section class="bg-white p-6 rounded-2xl shadow mb-8">
        <h2 class="text-xl font-semibold mb-4 text-[oklch(78.9%_0.154_211.53)]">Créer une consultation</h2>
        <form action="/medical_expert_war_exploded/consultations/create" method="post" class="space-y-4">

            <!-- Sélection du patient -->
            <div>
                <label class="block text-sm font-medium mb-1">Patient :</label>
                <select name="patientId" required
                        class="w-full p-2 border border-gray-300 rounded">
                    <option value="">-- Sélectionner un patient --</option>
                    <c:forEach var="p" items="${patients}">
                        <option value="${p.id}">${p.nom} ${p.prenom}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Motif -->
            <div>
                <label class="block text-sm font-medium mb-1">Motif de consultation :</label>
                <input type="text" name="motif" required
                       class="w-full p-2 border border-gray-300 rounded">
            </div>

            <!-- Observations -->
            <div>
                <label class="block text-sm font-medium mb-1">Observations :</label>
                <textarea name="observations" rows="3" class="w-full p-2 border border-gray-300 rounded"></textarea>
            </div>

            <!-- Coût consultation -->
            <div>
                <p class="font-medium">💰 Coût consultation : <span id="consultationCost">150</span> DH</p>
                <input type="hidden" name="cout" value="150">
            </div>

            <button type="submit"
                    class="bg-[oklch(78.9%_0.154_211.53)] text-white px-4 py-2 rounded hover:opacity-90">
                Enregistrer la consultation
            </button>
        </form>
    </section>

    <!-- 🔹 SECTION DEMANDE D’EXPERTISE -->
    <section class="bg-white p-6 rounded-2xl shadow mb-8">
        <h2 class="text-xl font-semibold mb-4 text-[oklch(78.9%_0.154_211.53)]">Demander une expertise</h2>
        <form id="expertiseForm" action="/medical_expert_war_exploded/expertises/create" method="post" class="space-y-4">

            <!-- Spécialité -->
            <div>
                <label class="block text-sm font-medium mb-1">Spécialité :</label>
                <select id="specialiteSelect" name="specialite" required
                        class="w-full p-2 border border-gray-300 rounded"
                        onchange="filterSpecialistes()">
                    <option value="">-- Sélectionner une spécialité --</option>
                    <c:forEach var="s" items="${specialistes}">
                        <option value="${s.specialite}">${s.specialite}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Spécialiste -->
            <div>
                <label class="block text-sm font-medium mb-1">Spécialiste :</label>
                <select id="specialisteSelect" name="specialisteId" required
                        class="w-full p-2 border border-gray-300 rounded">
                    <option value="">-- Choisir un spécialiste --</option>
                    <c:forEach var="s" items="${specialistes}">
                        <option data-specialite="${s.specialite}" data-tarif="${s.tarif}" value="${s.id}">
                                ${s.nom} ${s.prenom} — ${s.specialite} (${s.tarif} DH)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Créneau -->
            <div>
                <label class="block text-sm font-medium mb-1">Créneau disponible :</label>
                <select name="creneau" required class="w-full p-2 border border-gray-300 rounded">
                    <option value="09:00-10:00">09:00 - 10:00</option>
                    <option value="10:00-11:00">10:00 - 11:00</option>
                    <option value="14:00-15:00">14:00 - 15:00</option>
                    <option value="15:00-16:00">15:00 - 16:00</option>
                </select>
            </div>

            <!-- Question -->
            <div>
                <label class="block text-sm font-medium mb-1">Question au spécialiste :</label>
                <textarea name="question" rows="3" class="w-full p-2 border border-gray-300 rounded"></textarea>
            </div>

            <!-- Données -->
            <div>
                <label class="block text-sm font-medium mb-1">Données / Analyses :</label>
                <input type="file" name="fichierAnalyses" class="w-full p-2 border border-gray-300 rounded">
            </div>

            <!-- Coût total -->
            <div>
                <p class="font-medium">
                    💰 Coût total :
                    <span id="totalCost">150</span> DH
                </p>
            </div>

            <button type="submit"
                    class="bg-[oklch(78.9%_0.154_211.53)] text-white px-4 py-2 rounded hover:opacity-90">
                Envoyer la demande d’expertise
            </button>
        </form>
    </section>

    <!-- 🔹 SECTION LISTE DES CONSULTATIONS -->
    <section class="bg-white p-6 rounded-2xl shadow">
        <h2 class="text-xl font-semibold mb-4 text-[oklch(78.9%_0.154_211.53)]">Historique des consultations</h2>

        <table class="w-full border border-gray-200 rounded overflow-hidden">
            <thead class="bg-gray-100">
            <tr>
                <th class="p-2 text-left">Patient</th>
                <th class="p-2 text-left">Date</th>
                <th class="p-2 text-left">Motif</th>
                <th class="p-2 text-left">Coût</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="c" items="${consultations}">
                <tr class="border-t hover:bg-gray-50">
                    <td class="p-2">${c.patient.nom} ${c.patient.prenom}</td>
                    <td class="p-2">${c.dateConsultation}</td>
                    <td class="p-2">${c.motif}</td>
                    <td class="p-2">${c.cout} DH</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>

<script>
    // 🔸 Filtrage des spécialistes par spécialité
    function filterSpecialistes() {
        const selectedSpecialite = document.getElementById("specialiteSelect").value;
        const specialistSelect = document.getElementById("specialisteSelect");
        const options = specialistSelect.querySelectorAll("option");

        options.forEach(opt => {
            if (!opt.value) return;
            const spec = opt.getAttribute("data-specialite");
            opt.style.display = (selectedSpecialite === "" || spec === selectedSpecialite) ? "block" : "none";
        });
    }

    // 🔸 Calcul du coût total (consultation + spécialiste)
    const specialistSelect = document.getElementById("specialisteSelect");
    const totalCostEl = document.getElementById("totalCost");
    const consultationCost = 150;

    specialistSelect.addEventListener("change", () => {
        const tarif = parseFloat(specialistSelect.selectedOptions[0].getAttribute("data-tarif") || 0);
        const total = consultationCost + tarif;
        totalCostEl.textContent = total;
    });
</script>

</body>
</html>
