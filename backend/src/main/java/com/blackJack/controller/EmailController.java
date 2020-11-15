package com.blackJack.controller;

import com.blackJack.service.EmailService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/mail")
@Api(tags = "Forgot password")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/forgot_password")
    public ResponseEntity<Void> sendVerifyCodeByEmail(@RequestParam @Valid @Email(message = "Enter a valid email address.") final String email) {
        return emailService.sendVerifyCodeByEmail(email);
    }

    @PostMapping("/verify_token")
    public boolean verifyCode(@RequestBody @Valid final String token) {
        return emailService.verifyToken(token);
    }
}
