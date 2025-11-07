package com.gdg.convenience.controller;

import com.gdg.convenience.dto.TokenDto;
import com.gdg.convenience.dto.UserInfoResponseDto;
import com.gdg.convenience.dto.UserSignUpDto;
import com.gdg.convenience.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("manager")
    public ResponseEntity<TokenDto> managerSignUp(@RequestBody UserSignUpDto userSignUpDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.managerSignUp(userSignUpDto));
    }

    @PostMapping("staff")
    public ResponseEntity<TokenDto> staffSignUp(@RequestBody UserSignUpDto userSignUpDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.staffSignUp(userSignUpDto));
    }
    @GetMapping
    public ResponseEntity<UserInfoResponseDto> getUserInfo(Principal principal) {
        return ResponseEntity.ok(userService.findUserByPrincipal(principal));
    }
}
