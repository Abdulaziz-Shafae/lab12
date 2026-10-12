package com.example.lab12.Controller;

import com.example.lab12.Api.ApiResponse;
import com.example.lab12.Model.User;
import com.example.lab12.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        authService.registerUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user registered"));
    }

    @PostMapping("/admin/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid User admin) {
        authService.registerAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("admin registered"));
    }

    @GetMapping("/get/users")
    public ResponseEntity<?> adminGetAllUsers(@AuthenticationPrincipal User admin) {
        return ResponseEntity.status(200).body(authService.adminGetAllUsers(admin.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> adminUpdateUser(@AuthenticationPrincipal User admin, @RequestBody User user) {
        authService.adminUpdateUser(admin.getId(), user);
        return ResponseEntity.status(200).body(new ApiResponse("user updated"));
    }

    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<?> adminDeleteUser(@AuthenticationPrincipal User admin, @PathVariable Integer userID) {
        authService.adminDeleteUser(admin.getId(), userID);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted"));
    }
}