package com.parking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Float height;
    private Float capacity;
    private Double rate;
    private Float remainingCapacity;
    private String name;

    @OneToMany(mappedBy = "floor")
    private List<Slot> slots;
}
