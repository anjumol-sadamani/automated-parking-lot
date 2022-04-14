package com.parking.repository;

import com.parking.model.EntranceExitMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntranceRepository extends CrudRepository<EntranceExitMapping,Long> {
    EntranceExitMapping findByEntrance(String entrance);

}
