package com.parking.repository;

import com.parking.model.Floor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends CrudRepository<Floor,Long> {
    List<Floor> findAllBy();
}
