package com.medical_expert.medical_expert.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Gestion simple des tokens CSRF.
 */
public class CsrfTokenManager {

    private static final String CSRF_TOKEN_SESSION_ATTR = "CSRF_TOKEN";
    private static final SecureRandom random = new SecureRandom();

    private CsrfTokenManager() {}

    /**
     * Génère un nouveau token CSRF et le stocke dans la session.
     */
    public static String generateToken(HttpSession session) {
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        String token = Base64.getUrlEncoder().encodeToString(tokenBytes);
        session.setAttribute(CSRF_TOKEN_SESSION_ATTR, token);
        return token;
    }

    /**
     * Récupère le token CSRF actuel dans la session.
     */
    public static String getToken(HttpSession session) {
        Object token = session.getAttribute(CSRF_TOKEN_SESSION_ATTR);
        return token != null ? token.toString() : null;
    }

    /**
     * Vérifie la validité du token CSRF envoyé dans le formulaire.
     */
    public static boolean isValid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        String sessionToken = getToken(session);
        String requestToken = request.getParameter("csrf_token");

        return sessionToken != null && sessionToken.equals(requestToken);
    }
}

