package com.gdg.convenience.service;

import com.gdg.convenience.domain.Role;
import com.gdg.convenience.domain.User;
import com.gdg.convenience.dto.TokenDto;
import com.gdg.convenience.dto.UserInfoResponseDto;
import com.gdg.convenience.dto.UserSignUpDto;
import com.gdg.convenience.jwt.TokenProvider;
import com.gdg.convenience.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDto managerSignUp(UserSignUpDto userSignUpDto) {
        User user = userRepository.save(User.builder()
                .name(userSignUpDto.getName())
                .email(userSignUpDto.getEmail())
                .phone(userSignUpDto.getPhone())
                .role(Role.MANAGER)
                .build()
        );

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public TokenDto staffSignUp(UserSignUpDto userSignUpDto) {
        User user = userRepository.save(User.builder()
                .name(userSignUpDto.getName())
                .email(userSignUpDto.getEmail())
                .phone(userSignUpDto.getPhone())
                .role(Role.STAFF)
                .build()
        );

        String accessToken = tokenProvider.createAccessToken(user);

        return  TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public TokenDto refreshToken(UserSignUpDto userSignUpDto) {
        User user = userRepository.save(User.builder()
                .name(userSignUpDto.getName())
                .email(userSignUpDto.getEmail())
                .phone(userSignUpDto.getPhone())
                .role(Role.STAFF)
                .build()
        );

        String refreshToken = tokenProvider.createRefreshToken(user);

        return  TokenDto.builder()
                .refreshToken(refreshToken)
                .build();
    }

    public UserInfoResponseDto findUserByPrincipal (Principal principal){
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserInfoResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

}
