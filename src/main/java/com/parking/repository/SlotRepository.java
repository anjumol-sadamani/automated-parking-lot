package com.parking.repository;

import com.parking.model.Slot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface SlotRepository extends CrudRepository<Slot,Long> {

    /**
     * This method was added for future scope, when running a scaled system to lock
     * the slot selected for parking
     * @param h height of the vehicle
     * @param w weight of the vehicle
     * @param pageable page object to get single result
     * @return Slot A suitable slot
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Slot s WHERE s.floor.height >= ?1 and s.floor.remainingCapacity >= ?2 " +
            "and s.occupied = false order by s.floor.height ASC, s.floor.remainingCapacity ASC")
    List<Slot> getSuitableSlot(Float h, Float w, Pageable pageable);

}
