package com.demo.test.Controllers;



import com.demo.test.Entities.UserEntity;
import com.demo.test.Repositories.UsersRepo;
import com.demo.test.Services.CaptchaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UsersRepo repo;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private CaptchaController captchaController;
    @PostMapping("/register")
    public ResponseEntity<?> save_user(@RequestBody UserEntity entity) {
        Map<String,Object> response = new HashMap<>();
        try {
            List<Map<String,Object>> user = repo.UserExistence(entity.getUser_name());
            if(!user.isEmpty()){
                response.put("message", "User Name Already Exist");
                return ResponseEntity.status(500).body(response);
            }
            Map<String, String> captchaValidation = new HashMap<>();
            captchaValidation.put("captchaId", entity.getCaptchaId());
            captchaValidation.put("userInput", entity.getCaptcha());

            ResponseEntity<Map<String, Object>> captchaResponse =
                    captchaController.validateCaptcha(captchaValidation);

            System.out.println("resssss"+captchaResponse);
            if (!(boolean) captchaResponse.getBody().get("valid")) {
                response.put("message", "Invalid CAPTCHA");
                response.put("status", "fail");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            UserEntity ent = new UserEntity();
            ent.setUser_name(entity.getUser_name());
            ent.setPassword(entity.getPassword());
            repo.save(ent);
            response.put("message","User saved");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message","fail to save user");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserEntity loginRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            // First validate CAPTCHA
            Map<String, String> captchaValidation = new HashMap<>();
            captchaValidation.put("captchaId", loginRequest.getCaptchaId());
            captchaValidation.put("userInput", loginRequest.getCaptcha());

            ResponseEntity<Map<String, Object>> captchaResponse =
                    captchaController.validateCaptcha(captchaValidation);

            if (!(boolean) captchaResponse.getBody().get("valid")) {
                response.put("message", "Invalid CAPTCHA");
                response.put("status", "fail");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Validate required fields
            if (loginRequest.getUser_name() == null || loginRequest.getUser_name().isEmpty() ||
                    loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                response.put("message", "Username and password are required");
                response.put("status", "fail");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Find user by credentials
            List<UserEntity> users = repo.findingUser(loginRequest.getUser_name(), loginRequest.getPassword());

            if (users.isEmpty()) {
                response.put("message", "Invalid username or password");
                response.put("status", "fail");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Success - user found
            UserEntity user = users.get(0);
            response.put("message", "Login successful");
            response.put("status", "success");
            response.put("user", user); // Note: Remove password from response in production

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Login failed");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
