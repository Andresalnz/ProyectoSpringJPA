package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.security;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.User;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private UserRepository userRepository;

    public JWTAuthorizationFilter(ApplicationContext applicationContext) {
        this.userRepository = applicationContext.getBean(UserRepository.class);
    }


    protected void simpleDemoFilter(HttpServletRequest request) {
        String encabezado = request.getHeader("Authorization");
        if(encabezado != null && encabezado.equals("OK"))
            simpleSpringAuthentication();
        else
            SecurityContextHolder.clearContext();
    }


    private void simpleSpringAuthentication() {
        List<String> authoritiesText = new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_BOSS"));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("lalalala", null, sinLambdas(authoritiesText));


    }

    private List<SimpleGrantedAuthority> sinLambdas(List<String> textList) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for( String text: textList ) {
            authorities.add(new SimpleGrantedAuthority(text));
        }

        return authorities;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getHeader("Authorization")!=null) {
            if (!httpServletRequest.getHeader("Authorization").startsWith("Bearer ")) {
                SecurityContextHolder.clearContext();
            } else {
                String jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
                try {
                    Claims claims = Jwts.parser().setSigningKey("pestillo".getBytes()).parseClaimsJws(jwtToken).getBody();
                    String username = claims.getSubject();
                    User user = userRepository.findUserByUsername(username)
                            .orElseThrow(EntityNotFoundException::new);
                    if(!user.getToken().equals(jwtToken))
                        throw new Exception();
                    setUpSpringAuthentication(user);
                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                }
            }
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setUpSpringAuthentication(User user) {

        Hibernate.initialize(user.getRoles());
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null,
                        user.getRoles());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}

