package com.blackJack.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@RedisHash("Email")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class EmailConfirmation {
    @NonNull
    @Id
    private String id;
    @NonNull
    @Indexed
    private String token;
    @NonNull
    private boolean isActive;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration = 86400000L;
}
