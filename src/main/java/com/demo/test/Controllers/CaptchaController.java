package com.demo.test.Controllers;

import com.demo.test.Services.CaptchaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@CrossOrigin("*")
@RestController
public class CaptchaController {

    private final CaptchaService captchaService;
    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();
    private final long CAPTCHA_EXPIRATION_MINUTES = 5;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    public ResponseEntity<Map<String, Object>> getCaptcha() {
        // Generate CAPTCHA
        CaptchaService.CaptchaResult result = captchaService.generateCaptcha();

        // Create unique ID
        String captchaId = UUID.randomUUID().toString();

        // Store in memory with expiration
        captchaStore.put(captchaId, result.getText());

        // Schedule removal after expiration
        Executors.newSingleThreadScheduledExecutor().schedule(
                () -> captchaStore.remove(captchaId),
                CAPTCHA_EXPIRATION_MINUTES,
                TimeUnit.MINUTES
        );

        // Return both ID and image
        Map<String, Object> response = new HashMap<>();
        response.put("captchaId", captchaId);
        response.put("imageData", result.getImageData());
        response.put("text", result.getText()); // For debugging, remove in production

        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-captcha")
    public ResponseEntity<Map<String, Object>> validateCaptcha(
            @RequestBody Map<String, String> validationRequest) {

        String captchaId = validationRequest.get("captchaId");
        String userInput = validationRequest.get("userInput");

        Map<String, Object> response = new HashMap<>();

        if (captchaId == null || userInput == null) {
            response.put("valid", false);
            response.put("message", "Missing captchaId or userInput");
            return ResponseEntity.badRequest().body(response);
        }

        String storedCaptcha = captchaStore.get(captchaId);

        System.out.println("session"+storedCaptcha);
        System.out.println("payload"+userInput);
        System.out.println("payload2"+captchaId);

        if (storedCaptcha == null) {
            response.put("valid", false);
            response.put("message", "CAPTCHA expired or invalid");
            return ResponseEntity.badRequest().body(response);
        }

        boolean isValid = storedCaptcha.equalsIgnoreCase(userInput);

        if (isValid) {
            captchaStore.remove(captchaId); // Remove after successful validation
            response.put("valid", true);
            response.put("message", "CAPTCHA validation successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("valid", false);
            response.put("message", "Invalid CAPTCHA");
            return ResponseEntity.badRequest().body(response);
        }
    }
}