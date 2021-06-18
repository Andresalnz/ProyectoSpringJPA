package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.User;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.RoleRepository;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/logout")
    public ResponseEntity<Object> logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setToken(null);
        userRepository.save(user);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login (@RequestParam ("username")  String username,
                                         @RequestParam ("password")  String password){
        String token = null;
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException());
        if(!new BCryptPasswordEncoder().matches(password, user.getPassword()))
            throw new EntityNotFoundException();
        if(user.getToken() != null) {
            try {
                Jwts.parser().parse(user.getToken()).getBody();
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            } catch (Exception e) {
                user.setToken(null);
            }
        }
        String secretKey = "pestillo";
        token = Jwts
                .builder()
                .setId("AlbertIES")
                .setSubject(username)
                .claim("authorities",
                        user.getRoles().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 6000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        user.setToken(token);
        userRepository.save(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
