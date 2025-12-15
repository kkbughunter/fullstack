package com.application.backend.controller;

import com.application.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/test")
    public ResponseEntity<String> sendTestEmail(@RequestParam String to) {
        try {
            emailService.sendTestEmail(to);
            return ResponseEntity.ok("Test email sent successfully to " + to);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }
}