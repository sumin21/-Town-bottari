package com.backend.townbottari.jwt;

import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIX_BEARER = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        var accessToken = resolveToken(request);
        ExceptionCode errorCode = jwtTokenProvider.validateToken(accessToken);

        if (accessToken != null && errorCode==null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(jwtTokenProvider.getJwtTokenPayload(accessToken)));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(Long.parseLong(userDetails.getUsername()), "", userDetails.getAuthorities()));
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                        response.getOutputStream(),
                        ExceptionResponse.of(ExceptionCode.FAIL_AUTHENTICATION)
                );
                return;
            }
        }

        request.setAttribute("exception", errorCode);
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
