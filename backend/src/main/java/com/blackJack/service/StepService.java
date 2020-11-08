package com.blackJack.service;

import com.blackJack.dbo.GameStep;
import com.blackJack.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;

    public void save(GameStep step) {
        stepRepository.save(step);
    }
}
