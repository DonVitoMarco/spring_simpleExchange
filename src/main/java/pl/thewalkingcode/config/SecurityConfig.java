package pl.thewalkingcode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@ComponentScan({"pl.thewalkingcode.*"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT users.USERNAME, users.PASSWORD, users.ENABLED FROM users WHERE users.USERNAME = ?")
                .authoritiesByUsernameQuery("SELECT users.USERNAME, roles.ROLES FROM users JOIN roles ON users.USER_ROLE = roles.ID_ROLES WHERE username = ?")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/exchange").and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/").and()
                .exceptionHandling()
                .accessDeniedPage("/denied").and()
                .authorizeRequests()
                .antMatchers("/exchange").access("hasRole('ROLE_USER')")
                .antMatchers("/buy/**").access("hasRole('ROLE_USER')")
                .antMatchers("/sell/**").access("hasRole('ROLE_USER')")
                .antMatchers("/profile/**").access("hasRole('ROLE_USER')")
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .and().csrf();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
