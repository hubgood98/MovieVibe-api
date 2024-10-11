package org.onlyup.movie_recommendation_api.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.onlyup.movie_recommendation_api.domain.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter{

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null && !authorizationHeader.startsWith("Bearer ")) {

            System.out.println("Token is null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메서드 종료
            return;
        }

        String token = authorizationHeader.split(" ")[1];

        //토큰 소멸 시간 검증로직
        if(jwtUtil.isExpired(token)){
            System.out.println("Token is expired");
            filterChain.doFilter(request, response);

            return;
        }

        String accountId = jwtUtil.getAccountId(token);
        String role = jwtUtil.getRole(token);

        User user = new User();
        //마저 작성하기

    }
}
