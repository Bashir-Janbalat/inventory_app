package org.inventory.app.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.app.dto.AuthResponseDto;
import org.inventory.app.dto.LoginDTO;
import org.inventory.app.dto.UserDTO;
import org.inventory.app.model.Role;
import org.inventory.app.security.jwt.JwtTokenProvider;
import org.inventory.app.service.AuthService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDTO loginDto) {

        String token = authService.login(loginDto);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserDTO userDTO) {
        authService.signup(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        long expiration = jwtTokenProvider.getExpirationFromToken(token) - System.currentTimeMillis();
        jwtTokenProvider.addTokenToBlacklist(token, expiration);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/update-password")
    @CacheEvict(value = "userDetails", key = "#username")
    // TODO:: implement this method
    public void updateUserPassword(@RequestParam String username, @RequestParam String newPassword) {

    }

    @PostMapping("/update-roles")
    @CacheEvict(value = "userDetails", key = "#username")
    @PreAuthorize("hasRole('ROLE_USER_MANAGEMENT')")
    // TODO:: implement this method
    public void updateUserRoles(@RequestParam String username, @RequestBody Set<Role> newRoles) {

    }
}
