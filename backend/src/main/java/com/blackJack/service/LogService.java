package com.blackJack.service;


import java.util.Date;

import com.blackJack.dbo.GameEntity;
import com.blackJack.dbo.LogEntity;
import com.blackJack.repository.LogRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
public class LogService extends AbstractService<LogEntity, LogRepository>
{

    private final GameService gameService;


    public LogService(final LogRepository repository, final ModelMapper modelMapper,
            @Lazy final GameService gameService)
    {
        super(repository, modelMapper);
        this.gameService = gameService;
    }


    @SneakyThrows
    public void saveLog(final GameEntity gameEntity, final String message)
    {
        final LogEntity logEntity = new LogEntity();
        logEntity.setGameEntity(gameEntity);
        logEntity.setDate(new Date());
        logEntity.setMessage(message);
        final LogEntity save = repository.save(logEntity);
        gameEntity.getLogEntities().add(save);
        gameService.save(gameEntity);
    }
}
