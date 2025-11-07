package com.gdg.convenience.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "work_log")
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    @Builder
    public WorkLog(User user, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.user = user;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

}
