<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes Créneaux</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen p-8">

<div class="container mx-auto max-w-2xl bg-white p-6 rounded-xl shadow">
    <h1 class="text-2xl font-bold mb-4">Mes Créneaux</h1>

    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">${error}</div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">${success}</div>
    </c:if>

    <table class="table-auto w-full border border-gray-200">
        <thead class="bg-gray-200">
        <tr>
            <th class="px-4 py-2">Début</th>
            <th class="px-4 py-2">Fin</th>
            <th class="px-4 py-2">Disponible</th>
            <th class="px-4 py-2">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${creneaux}">
            <tr class="border-t">
                <td class="px-4 py-2">${c.debut}</td>
                <td class="px-4 py-2">${c.fin}</td>
                <td class="px-4 py-2">
                    <c:choose>
                        <c:when test="${c.disponible}">✓ Disponible</c:when>
                        <c:otherwise>x Indisponible</c:otherwise>
                    </c:choose>
                </td>
                <td class="px-4 py-2">
                    <c:choose>
                        <c:when test="${c.disponible}">
                            <form action="${pageContext.request.contextPath}/reserver-creneau" method="post">
                                <input type="hidden" name="id" value="${c.id}">
                                <button type="submit" class="bg-green-500 text-white px-3 py-1 rounded">Réserver</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/annuler-creneau" method="post">
                                <input type="hidden" name="id" value="${c.id}">
                                <button type="submit" class="bg-red-500 text-white px-3 py-1 rounded">Annuler</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
