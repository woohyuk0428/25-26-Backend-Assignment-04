package com.gdg.convenience.service;

import com.gdg.convenience.domain.User;
import com.gdg.convenience.domain.WorkLog;
import com.gdg.convenience.jwt.TokenProvider;
import com.gdg.convenience.repository.UserRepository;
import com.gdg.convenience.repository.WorkLogRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@NoArgsConstructor
public class WorkLogService {

    private WorkLogRepository workLogRepository;
    private UserRepository userRepository;
    private TokenProvider tokenProvider;

    public String checkIn(String token) {
        Long userId = tokenProvider.getUserId(token);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("사용자가 없습니다.")
        );

        if (workLogRepository.findTopByUserIdOrderByIdDesc(user.getId()).isPresent()) {
            return "이미 출근 처리된 상태입니다.";
        }

        WorkLog workLog = WorkLog.builder()
                .user(user)
                .checkInTime(LocalDateTime.now())
                .build();

        workLogRepository.save(workLog);

        return "출근처리가 완료되었습니다.";
    }

    public String checkOut(String token) {
        Long userId = tokenProvider.getUserId(token);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("사용자가 없습니다.")
        );

        if (workLogRepository.findTopByUserIdOrderByIdDesc(user.getId()).isPresent()) {
            return "이미 퇴근 처리된 상태입니다.";
        }

        WorkLog workLog = WorkLog.builder()
                .user(user)
                .checkOutTime(LocalDateTime.now())
                .build();

        workLogRepository.save(workLog);

        return "퇴근처리가 왼료되었습니다.";
    }
}
