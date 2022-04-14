package com.parking.repository;

import com.parking.model.Slot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends CrudRepository<Slot,Long> {

    @Query("SELECT s FROM Slot s WHERE s.floor.height >= ?1 and s.floor.remainingCapacity >= ?2 " +
            "and s.occupied = false order by s.floor.height ASC, s.floor.remainingCapacity ASC")
    List<Slot> getSuitableSlot(Float h, Float w);
}
