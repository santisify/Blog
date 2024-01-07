package com.blog.config;

import com.blog.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //请求授权验证
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 访问权限
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/profile/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/images/**", "/layui/**", "/editormd/**", "/zplayer/**", "/swiper/**", "/bootstrap/**", "/favicon.ico").permitAll()
                .antMatchers("/register", "/login", "/toLogin", "/verifyCode", "/checkusername", "/sendemailcode").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/discover").permitAll()
                .antMatchers("/tofollow").permitAll()
                .antMatchers("/download").permitAll()
                .antMatchers("/newblog").permitAll()
                .antMatchers("/search/**").permitAll()
                .antMatchers("/article/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        // 登录配置
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/toLogin")
                .loginProcessingUrl("/login") // 登陆表单提交请求
                .defaultSuccessUrl("/index"); // 设置默认登录成功后跳转的页面

        //基于token，不适用session
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 注销配置
        http.headers().contentTypeOptions().disable();
        http.logout().logoutSuccessUrl("/");

        // 记住我配置
        http.rememberMe().rememberMeParameter("remember")
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .userDetailsService(userDetailsService);
    }

    // 用户认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置自定义的service，从数据库中获取用户信息
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}