<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - Télé-Expertise</title>
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
    </style>
</head>

<body class="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50">

<div class="bg-white shadow-2xl rounded-3xl p-10 w-full max-w-md mx-4 border border-gray-100 card-hover">
    <h1 class="text-3xl font-bold text-center mb-6 text-gray-800">Télé-Expertise</h1>
    <p class="text-center text-gray-500 mb-8">Connexion sécurisée à votre espace médecin</p>

    <!-- Formulaire de connexion -->
    <form action="login" method="post" class="space-y-5">
        <div>
            <label class="block text-gray-700 font-medium mb-1">Email :</label>
            <input type="email" name="email" required
                   class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none"
                   placeholder="exemple@domaine.com">
        </div>

        <div>
            <label class="block text-gray-700 font-medium mb-1">Mot de passe :</label>
            <input type="password" name="password" required
                   class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none"
                   placeholder="••••••••">
        </div>

        <c:if test="${not empty error}">
            <p class="text-red-500 text-sm text-center">${error}</p>
        </c:if>

        <button type="submit" class="w-full btn-primary text-white font-semibold py-2 rounded-lg">
            Se connecter
        </button>
    </form>

    <p class="text-center text-gray-500 text-sm mt-6">
        © 2025 Télé-Expertise Médicale — Tous droits réservés
    </p>
</div>

</body>
</html>
