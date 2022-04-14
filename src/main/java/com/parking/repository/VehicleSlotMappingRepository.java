package com.parking.repository;

import com.parking.model.VehicleSlotMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleSlotMappingRepository extends CrudRepository<VehicleSlotMapping,Long> {
}
