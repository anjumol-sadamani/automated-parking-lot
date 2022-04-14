package com.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    @NotNull(message = "VIN Number is mandatory")
    private Long vinNumber;

    @NotNull(message = "Vehicle height is mandatory")
    private Float height;

    @NotNull(message = "Vehicle weight is mandatory")
    private Float weight;
    @NotBlank(message = "Vehicle type is mandatory")
    private String type;
}
