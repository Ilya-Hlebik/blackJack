package com.blackJack.service;


import java.util.Date;

import com.blackJack.dbo.GameEntity;
import com.blackJack.dbo.LogEntity;
import com.blackJack.repository.GameRepository;
import com.blackJack.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LogService
{
    private final LogRepository logRepository;

    private final GameRepository gameRepository;



    @SneakyThrows
    public void saveLog(final GameEntity gameEntity, final String message)
    {
        final LogEntity logEntity = new LogEntity();
        logEntity.setGameEntity(gameEntity);
        logEntity.setDate(new Date());
        logEntity.setMessage(message);
        final LogEntity save = logRepository.save(logEntity);
        gameEntity.getLogEntities().add(save);
        gameRepository.save(gameEntity);
    }
}
