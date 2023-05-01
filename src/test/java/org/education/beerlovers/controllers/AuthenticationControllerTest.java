package org.education.beerlovers.controllers;

import org.education.beerlovers.config.JwtUtils;
import org.education.beerlovers.dao.UserDao;
import org.education.beerlovers.dto.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
@ContextConfiguration(classes= {UserDao.class})
class AuthenticationControllerTest {

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
        UserDao userDao = new UserDao();
        JwtUtils jwtUtils = new JwtUtils();

        AuthenticationController controller = new AuthenticationController(
          authenticationManager,
          userDao,
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
