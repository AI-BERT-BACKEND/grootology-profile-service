package com.aibert.dosw.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserAuthUpdateDTO {
    private Integer failedAttempts;
    private LocalDateTime lockedUntil;
}
