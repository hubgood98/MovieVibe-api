package org.onlyup.movie_recommendation_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());
        //from 로그인방식 disable
        http
                .formLogin((auth)-> auth.disable());

        //http basic 인증방식
        http
                .httpBasic((auth)-> auth.disable());

        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/login","/","join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        //그 외 요청
                        .anyRequest().authenticated());
        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
