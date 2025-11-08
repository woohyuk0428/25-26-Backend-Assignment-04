package com.gdg.convenience.controller;

import com.gdg.convenience.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkLogController {

    private final WorkLogService workLogService;

    @PostMapping("/check-in")
    public String checkIn(@RequestHeader("Authorization") String token) {
        return workLogService.checkIn(token.substring(7));
    }

    @PostMapping("/check-out")
    public String checkOut(@RequestHeader("Authorization") String token) {
        return workLogService.checkOut(token.substring(7));
    }
}
