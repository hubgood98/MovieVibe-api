package org.onlyup.movie_recommendation_api.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.onlyup.movie_recommendation_api.dto.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String accountId;
        String password;

        try {
            if (!"application/json".equalsIgnoreCase(request.getContentType())) {
                throw new RuntimeException("Unsupported Content-Type: " + request.getContentType());
            }

            Map<String, String> credentials;

            // Check if the request body is empty
            if (request.getInputStream().available() > 0) {
                credentials = objectMapper.readValue(request.getInputStream(), Map.class);
            } else {
                throw new RuntimeException("Empty request body");
            }

            accountId = credentials.get("accountId");
            password = credentials.get("password");

            if (accountId == null || password == null) {
                throw new RuntimeException("Missing accountId or password in request body");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process request", e);
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountId, password)
        );
    }

    //로그인 성공시 실행하는 메서드
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String accountId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(accountId, role,60*60*10L);

        response.addHeader("Authorization","Bearer "+token);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        return new BufferedReader(new InputStreamReader(request.getInputStream()))
                .lines()
                .collect(Collectors.joining("\n"));
    }

}
