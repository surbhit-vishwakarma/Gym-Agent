package dev.surbhit.gym.agent.security;

import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.repository.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AppUserRepository userRepo;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, AppUserRepository userRepo) {
        this.jwtProvider = jwtProvider;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtProvider.validateToken(token)) {

                UUID userId = jwtProvider.getUserIdFromToken(token);
                String rolesCsv = jwtProvider.getRolesFromToken(token);

                AppUser user = userRepo.findById(userId).orElse(null);

                if (user != null) {

                    // Convert CSV → authorities list
                    List<SimpleGrantedAuthority> authorities =
                            Arrays.stream(rolesCsv.split(","))
                                    .map(String::trim)
                                    .filter(r -> !r.isEmpty())
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, authorities);

                    // <-- Authentication is stored for THIS request thread
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        // ALWAYS continue the chain
        filterChain.doFilter(request, response);
    }
}
