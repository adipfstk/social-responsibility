package com.socialportal.portal.security.filter;

import com.socialportal.portal.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (getTokenContent(request).isPresent()) {
            var token = getTokenContent(request).get();
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtService.extractUsername(token));

            if (jwtService.validateToken(token, userDetails)
                    && SecurityContextHolder.getContext().getAuthentication() == null)
            {

                UsernamePasswordAuthenticationToken finToken = new UsernamePasswordAuthenticationToken
                        (
                                userDetails.getUsername(),
                                null,
                                userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(finToken);
            }
        }

        filterChain.doFilter(request, response);

    }

    Optional<String> getTokenContent(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        return header == null || !header.startsWith("Bearer ")
                ? Optional.empty()
                : Optional.of(header.substring(7));
    }
}
