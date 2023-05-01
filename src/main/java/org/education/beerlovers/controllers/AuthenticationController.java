package org.education.beerlovers.controllers;

import lombok.RequiredArgsConstructor;
import org.education.beerlovers.config.JwtUtils;
import org.education.beerlovers.dao.UserDao;
import org.education.beerlovers.dto.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @CrossOrigin(origins="http://localhost:3000")
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticate(
        @RequestBody AuthenticationRequest request
    ) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDao.findUserByEmail(request.getEmail());
        Map<String, Object> map = new HashMap<String, Object>();
        if(user != null) {
            map.put("accessToken", jwtUtils.generateToken(user));
            map.put("roles", user.getAuthorities());
            return ResponseEntity.ok(map);
        }
        map.put("error", "An error has occurred");
        return ResponseEntity.status(400).body(map);
    }
}
