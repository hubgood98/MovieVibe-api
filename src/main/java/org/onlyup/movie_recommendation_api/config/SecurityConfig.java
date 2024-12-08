package org.onlyup.movie_recommendation_api.config;

import org.onlyup.movie_recommendation_api.jwt.JWTFilter;
import org.onlyup.movie_recommendation_api.jwt.JWTUtil;
import org.onlyup.movie_recommendation_api.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf
        http.csrf((auth) -> auth.disable());
        //from 로그인방식
        http.formLogin((auth)-> auth.disable());

        //http basic 인증
        http.httpBasic((auth)-> auth.disable());

        //요청 권한 설정
        http.authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/login", "/", "/join", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/movies/**","/api/rating/**").permitAll() //나중에 유져 제한으로 변경
                        .requestMatchers("/admin").hasRole("ADMIN")
                        //그 외 요청
                        .anyRequest().authenticated());
        /*
        * addFilterBefore 해당 필터 전에 등록
        * addFilterAfter 해당 필터 후에 등록
        * addFilterAt 원하는 자리에 등록
         */

        //필터 추가
        http.addFilterBefore(new JWTFilter(jwtUtil), LogoutFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
