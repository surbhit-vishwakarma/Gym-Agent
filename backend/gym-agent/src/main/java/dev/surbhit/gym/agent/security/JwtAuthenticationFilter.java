package dev.surbhit.gym.agent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.repository.AppUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JwtAuthenticationFilter:
 * - resolves token from Authorization header "Bearer <token>"
 * - validates & parses token via JwtProvider
 * - sets Authentication in SecurityContext with ROLE_ prefix
 * - returns JSON 401 when token is expired or invalid (if token was provided)
 *
 * Note: If no token is provided, the filter does NOT block the chain and lets Spring handle permitAll/auth rules.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtProvider jwtProvider;
    private final AppUserRepository userRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtProvider jwtProvider, AppUserRepository userRepo) {
        this.jwtProvider = jwtProvider;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        // If there's no token, continue (public endpoints allowed)
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Attempt to parse & extract user info. These methods throw JwtException/ExpiredJwtException on failure.
            UUID userId = jwtProvider.getUserIdFromToken(token);
            String rolesCsv = jwtProvider.getRolesFromToken(token); // may be "" if none

            // Load user (optional) to use as principal - or set a simple principal like userId/email.
            AppUser user = userRepo.findById(userId).orElse(null);

            // Build authorities list and ensure ROLE_ prefix for compatibility with hasRole(...)
            List<SimpleGrantedAuthority> authorities = Arrays.stream(
                            (rolesCsv == null ? "" : rolesCsv).split(","))
                    .map(String::trim)
                    .filter(r -> !r.isEmpty())
                    .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // If no user found, you can either reject or set minimal authentication; here we reject:
            if (user == null) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "invalid_token", "User not found for token");
                return;
            }

            // Create Authentication and store in SecurityContext for the request thread
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // continue request
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            log.debug("Expired JWT: {}", ex.getMessage());
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "token_expired",
                    "Access token expired. Please refresh or login again.");
        } catch (JwtException | IllegalArgumentException ex) {
            // covers signature, malformed, unsupported, or other parse issues
            log.debug("Invalid JWT: {}", ex.getMessage());
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "invalid_token",
                    "Invalid access token.");
        } finally {
            // Note: do NOT clear SecurityContext here because other parts might rely on it during the same request.
            // SecurityContext is request-scoped and will be cleared automatically by the framework after the request.
        }
    }

    /**
     * Resolve Bearer token from Authorization header
     */
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    /**
     * Send JSON error response with a simple payload:
     * { "error": "<code>", "message": "<message>" }
     */
    private void sendError(HttpServletResponse response, int status, String errorCode, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> body = new HashMap<>();
        body.put("error", errorCode);
        body.put("message", message);
        String json = objectMapper.writeValueAsString(body);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
