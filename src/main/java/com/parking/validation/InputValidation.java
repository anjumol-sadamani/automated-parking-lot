package com.parking.validation;

import com.parking.model.EntranceExitMapping;
import com.parking.repository.EntranceRepository;
import org.springframework.stereotype.Component;

@Component
public class InputValidation {
    private final EntranceRepository entranceRepository;

    public InputValidation(EntranceRepository entranceRepository) {
        this.entranceRepository = entranceRepository;
    }

    public boolean isValidEntrance(String entrance) {
        EntranceExitMapping entranceExitMapping = entranceRepository.findByEntrance(entrance);
        return entranceExitMapping != null;
    }
}
