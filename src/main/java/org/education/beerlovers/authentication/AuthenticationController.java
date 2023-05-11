package org.education.beerlovers.authentication;

import lombok.RequiredArgsConstructor;
import org.education.beerlovers.security.JwtUtils;
import org.education.beerlovers.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @CrossOrigin(origins="http://localhost:3000")
    @PostMapping(value = "/authenticate", produces = "application/json")
    public ResponseEntity<Map<String, Object>> authenticate(
        @RequestBody AuthenticationRequest request
    ) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userService.loadUserByUsername(request.getEmail());
        Map<String, Object> map = new HashMap<String, Object>();
        if(user != null) {
            map.put("accessToken", jwtUtils.generateToken(user));
            map.put("user", user);
            return ResponseEntity.status(200).body(map);
        }
        map.put("error", "An error has occurred");
        return ResponseEntity.status(400).body(map);
    }
}
