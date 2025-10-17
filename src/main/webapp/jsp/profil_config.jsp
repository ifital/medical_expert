<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Configuration Profil</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');
        * { font-family: 'Inter', sans-serif; }
        .gradient-bg { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); transition: all 0.3s ease; }
        .btn-primary:hover { transform: translateY(-1px); box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4); }
        .scrollable { max-height: 500px; overflow-y: auto; }
    </style>
</head>
<body class="bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50 min-h-screen">

<header class="gradient-bg text-white py-6 shadow-lg">
    <div class="container mx-auto px-6 flex items-center justify-between">
        <h1 class="text-3xl font-bold tracking-tight">Configuration du Profil</h1>
    </div>
</header>

<div class="container mx-auto px-6 py-8 max-w-2xl">

    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                ${error}
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
                ${success}
        </div>
    </c:if>

    <div class="bg-white p-8 rounded-3xl shadow-lg scrollable border border-gray-100">
        <form action="${pageContext.request.contextPath}/profil/config" method="post" class="space-y-6">
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Nom :</label>
                <input type="text" value="${specialiste.nom}" disabled
                       class="w-full border rounded-lg px-4 py-2 bg-gray-100 cursor-not-allowed">
            </div>
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Prénom :</label>
                <input type="text" value="${specialiste.prenom}" disabled
                       class="w-full border rounded-lg px-4 py-2 bg-gray-100 cursor-not-allowed">
            </div>
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Email :</label>
                <input type="email" value="${specialiste.email}" disabled
                       class="w-full border rounded-lg px-4 py-2 bg-gray-100 cursor-not-allowed">
            </div>
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Spécialité :</label>
                <input type="text" name="specialite" value="${specialiste.specialite}"
                       class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300"
                       placeholder="Ex: Cardiologie" required>
            </div>
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Tarif (DH) :</label>
                <input type="number" name="tarif" value="${specialiste.tarif}"
                       class="w-full border rounded-lg px-4 py-2 focus:ring focus:ring-blue-300"
                       placeholder="Ex: 200" step="0.01" required>
            </div>
            <div>
                <label class="block text-gray-700 mb-1 font-medium">Durée moyenne consultation :</label>
                <input type="text" value="${specialiste.dureeConsultation} minutes" disabled
                       class="w-full border rounded-lg px-4 py-2 bg-gray-100 cursor-not-allowed">
            </div>
            <div class="flex justify-end space-x-3 pt-4">
                <button type="submit" class="btn-primary text-white px-5 py-2 rounded-lg">
                    Enregistrer
                </button>
            </div>
        </form>
    </div>
</div>

</body>
</html>
