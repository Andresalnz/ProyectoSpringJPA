package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class JparestformulaunodemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JparestformulaunodemoApplication.class, args);
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/login**").permitAll()
                    .antMatchers("/rol/**").permitAll()
                    .antMatchers("/logout/**").authenticated()
                    .antMatchers("/pilot/**").authenticated()
                    .antMatchers("/team/**").hasAnyRole("ADMIN","BOSS")
                    .antMatchers("/").authenticated();
                    //.and().formLogin();

        }



        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }



    }
}
