package org.education.beerlovers.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingsController {

    @GetMapping
    public ResponseEntity<String> greetUser() {
        return ResponseEntity.ok("Hello from user!");
    }

    @GetMapping("/farewell-user")
    public ResponseEntity<String> farewellUser() {
        return ResponseEntity.ok("Goodbye and see you again.");
    }
}
