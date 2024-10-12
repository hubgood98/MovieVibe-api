package org.onlyup.movie_recommendation_api.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.onlyup.movie_recommendation_api.domain.User;
import org.onlyup.movie_recommendation_api.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter{

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("Token is null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메서드 종료
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증로직
        if(jwtUtil.isExpired(token)){
            System.out.println("Token is expired");
            filterChain.doFilter(request, response);

            return;
        }

        //매번 DB에서 조회할 수 없으니 임시로 만들어 사용
        String accountId = jwtUtil.getAccountId(token);
        String role = jwtUtil.getRole(token);

        User user = new User(accountId,"dfasdf","secretName","dfkjas",role);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        //스프링 시큐리티 인증토큰 생성
        Authentication auth = new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request,response);


    }
}
