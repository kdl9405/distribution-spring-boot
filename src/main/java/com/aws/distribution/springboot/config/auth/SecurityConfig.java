package com.aws.distribution.springboot.config.auth;

import com.aws.distribution.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // spring security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final CustomOAuth2UserService customOAuth2UserService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().headers().frameOptions().disable() // h2-콘솔 화면을 사용하기 위해 해당 옵션 disable
                .and().authorizeRequests() //Url별 권환 관리설정옵션의 시작
                .antMatchers("/", "/blog" ,"/css/**", "/img/**", "/js/**", "/vendor/**" ,"/h2-console/**").permitAll() // 전체 열람 권한
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 권한을 가진 사람만 가능
                .anyRequest().authenticated() // anyRequest - 나머지 url
                .and()
                    .logout().logoutSuccessUrl("/") // 로그아웃에 대한 설정
                .and()
                    .oauth2Login()  // 로그인에 대한 설정
                        .userInfoEndpoint() // 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                            .userService(customOAuth2UserService); // userService의 구현체 등록
    }
    
}
