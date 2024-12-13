package org.onlyup.movie_recommendation_api.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
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

        // Content-Type 확인
        String contentType = request.getContentType();

        try {
            if ("application/json".equals(contentType)) {
                // JSON 요청 본문에서 데이터 읽기
                Map<String, String> jsonMap = objectMapper.readValue(getRequestBody(request), new TypeReference<Map<String, String>>() {});
                accountId = jsonMap.get("accountId");
                password = jsonMap.get("password");
            } else {
                // 폼 데이터에서 데이터 읽기
                accountId = request.getParameter("accountId");
                password = request.getParameter("password");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process request", e);
        }

        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(accountId, password, null);

        // 인증 시도
        return authenticationManager.authenticate(authToken);
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
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:6709");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "*");
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
