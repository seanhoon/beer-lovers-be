package org.education.beerlovers.controllers;

import org.education.beerlovers.authentication.AuthenticationController;
import org.education.beerlovers.security.JwtUtils;
import org.education.beerlovers.authentication.AuthenticationRequest;
import org.education.beerlovers.user.UserRepository;
import org.education.beerlovers.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
@ContextConfiguration(classes= {UserService.class})
class AuthenticationControllerTest {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    AuthenticationControllerTest(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void authenticate() {
        AuthenticationRequest requestBody = new AuthenticationRequest();
        requestBody.setEmail("user1@epam.com");
        requestBody.setPassword("password");

        AuthenticationManager authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
        UserService userService = new UserService(userRepository, bCryptPasswordEncoder);
        JwtUtils jwtUtils = new JwtUtils();

        AuthenticationController controller = new AuthenticationController(
          authenticationManager,
          userService,
          jwtUtils
        );

        ResponseEntity<Map<String, Object>> response = controller.authenticate(requestBody);
        String accessToken = response.getBody().get("accessToken").toString();
        String roles = response.getBody().get("roles").toString();
        int statusCode = response.getStatusCodeValue();
        assertTrue(!accessToken.isEmpty());
        assertEquals(200, statusCode);
        assertEquals("[ROLE_ADMIN]", roles);
    }
}
