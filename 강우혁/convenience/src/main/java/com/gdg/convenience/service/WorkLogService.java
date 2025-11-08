package com.gdg.convenience.service;

import com.gdg.convenience.domain.User;
import com.gdg.convenience.domain.WorkLog;
import com.gdg.convenience.jwt.TokenProvider;
import com.gdg.convenience.repository.UserRepository;
import com.gdg.convenience.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public String checkIn(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        Optional<WorkLog> lastWorkLog = workLogRepository.findTopByUserIdOrderByIdDesc(user.getId());
        if (lastWorkLog.isPresent() && lastWorkLog.get().getCheckOutTime() == null) {
            return "이미 출근 처리된 상태입니다.";
        }

        WorkLog workLog = WorkLog.builder()
                .user(user)
                .checkInTime(LocalDateTime.now())
                .build();

        workLogRepository.save(workLog);
        return "출근처리가 완료되었습니다.";
    }

    public String checkOut(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        WorkLog lastWorkLog = workLogRepository.findTopByUserIdOrderByIdDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("출근 기록이 없습니다."));

        if (lastWorkLog.getCheckOutTime() != null) {
            return "이미 퇴근 처리된 상태입니다.";
        }

        lastWorkLog.setCheckOutTime(LocalDateTime.now());
        workLogRepository.save(lastWorkLog);
        return "퇴근 처리가 완료되었습니다.";
    }
}
