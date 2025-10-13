<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Généraliste</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.2/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100 p-6">

<h1 class="text-2xl font-bold mb-6">Dashboard Généraliste</h1>

<!-- ===== FORMULAIRE CREATION CONSULTATION ===== -->
<h2 class="text-xl font-semibold mb-4">Créer une nouvelle consultation</h2>
<form method="post" action="${pageContext.request.contextPath}/consultations/add" class="bg-white p-4 shadow-md rounded mb-6">
    <div class="grid grid-cols-2 gap-4 mb-4">
        <div>
            <label class="block font-medium mb-1">Patient</label>
            <select name="patientId" class="border px-2 py-1 w-full rounded">
                <c:forEach var="p" items="${patients}">
                    <option value="${p.id}">${p.nom} ${p.prenom}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label class="block font-medium mb-1">Motif</label>
            <input type="text" name="motif" class="border px-2 py-1 w-full rounded" required />
        </div>
    </div>
    <div class="mb-4">
        <label class="block font-medium mb-1">Observations</label>
        <textarea name="observations" class="border px-2 py-1 w-full rounded" rows="3"></textarea>
    </div>
    <div class="flex justify-end">
        <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
            Créer Consultation
        </button>
    </div>
</form>

<!-- ===== LISTE DES CONSULTATIONS ===== -->
<h2 class="text-xl font-semibold mb-4">Mes Consultations</h2>
<table class="min-w-full bg-white shadow-md rounded mb-6">
    <thead>
    <tr class="bg-gray-200">
        <th class="py-2 px-4">Patient</th>
        <th class="py-2 px-4">Motif</th>
        <th class="py-2 px-4">Observations</th>
        <th class="py-2 px-4">Statut</th>
        <th class="py-2 px-4">Coût Total</th>
        <th class="py-2 px-4">Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="c" items="${consultations}">
        <tr class="border-b">
            <td class="py-2 px-4">${c.patient.nom} ${c.patient.prenom}</td>
            <td class="py-2 px-4">${c.motif}</td>
            <td class="py-2 px-4">${c.observations}</td>
            <td class="py-2 px-4">${c.statut}</td>
            <td class="py-2 px-4">
                <c:set var="coutTotal" value="${c.cout}" />
                <c:forEach var="a" items="${c.actesTechniques}">
                    <c:set var="coutTotal" value="${coutTotal + a.prix}" />
                </c:forEach>
                    ${coutTotal} DH
            </td>
            <td class="py-2 px-4">
                <form action="${pageContext.request.contextPath}/expertise/request" method="get">
                    <input type="hidden" name="consultationId" value="${c.id}" />
                    <button type="submit" class="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700">
                        Demander Expertise
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- ===== FILTRE SPECIALISTES ===== -->
<h2 class="text-xl font-semibold mb-2">Filtrer Spécialistes</h2>
<form method="get" action="${pageContext.request.contextPath}/dashboard/generaliste" class="mb-6">
    <input type="text" name="specialite" placeholder="Spécialité" class="border px-2 py-1 rounded mr-2" />
    <button type="submit" class="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700">
        Filtrer
    </button>
</form>

<!-- ===== LISTE DES SPECIALISTES ===== -->
<table class="min-w-full bg-white shadow-md rounded">
    <thead>
    <tr class="bg-gray-200">
        <th class="py-2 px-4">Nom</th>
        <th class="py-2 px-4">Spécialité</th>
        <th class="py-2 px-4">Tarif</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="s" items="${specialistes}">
        <tr class="border-b">
            <td class="py-2 px-4">${s.nom} ${s.prenom}</td>
            <td class="py-2 px-4">${s.specialite}</td>
            <td class="py-2 px-4">${s.tarif} DH</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
