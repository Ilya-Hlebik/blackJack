package com.blackJack.service;


import java.util.List;

import com.blackJack.dbo.GameStep;
import com.blackJack.repository.StepRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class StepService extends AbstractService<GameStep, StepRepository>
{

    public StepService(final StepRepository repository, final ModelMapper modelMapper)
    {
        super(repository, modelMapper);
    }


    public GameStep save(final GameStep step)
    {
        return repository.save(step);
    }


    public void saveAll(final List<GameStep> steps)
    {
        repository.saveAll(steps);
    }
}
