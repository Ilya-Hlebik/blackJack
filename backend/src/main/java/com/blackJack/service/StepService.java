package com.blackJack.service;


import java.util.List;

import com.blackJack.dbo.GameStep;
import com.blackJack.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;

    public GameStep save(final GameStep step) {
        return stepRepository.save(step);
    }

    public void saveAll(final List<GameStep> steps) {
        stepRepository.saveAll(steps);
    }
}
