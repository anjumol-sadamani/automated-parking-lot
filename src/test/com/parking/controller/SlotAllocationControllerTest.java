package com.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import com.parking.service.SlotAllocationService;
import com.parking.validation.InputValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SlotAllocationController.class)
class SlotAllocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SlotAllocationService parkingService;

    @MockBean
    private InputValidation inputValidation;

    private String URL = "/v1/automatic/parking/allocate/{entrance}";

    @Test
    void allocateSlot_invalid_entrance() throws Exception {
        String vehicleDto = "{\"vinNumber\":1234,\"height\":1.2,\"weight\":1000,\"type\":\"Car\"}";
        Mockito.when(inputValidation.isValidEntrance("E1")).thenReturn(false);
        mockMvc.perform(post(URL, "E1").contentType(APPLICATION_JSON).content(vehicleDto)).andExpect(status().isBadRequest());
    }

    @Test
    void allocateSlot_invalid_vehicle_info() throws Exception {
        String vehicleDto = "{\"vinNumber\":1234,\"height\":\"\",\"weight\":1000,\"type\":\"Car\"}";
        Mockito.when(inputValidation.isValidEntrance("E1")).thenReturn(true);
        mockMvc.perform(post(URL, "E1").contentType(APPLICATION_JSON).content(vehicleDto)).andExpect(status().isBadRequest());
    }

    @Test
    void allocateSlot() throws Exception {
        String requestBody = "{\"vinNumber\":1234,\"height\":1.2,\"weight\":1000,\"type\":\"Car\"}";
        VehicleDto vehicleDto = new VehicleDto(1234l, 1.2f, 1000f, "Car");
        TicketDto ticketDto = new TicketDto.TicketDtoBuilder(12l, 120d).withGift("Gift: Car Wash").build();

        Mockito.when(inputValidation.isValidEntrance("E1")).thenReturn(true);
        Mockito.when(parkingService.allocateSlot(Mockito.eq("E1"), ArgumentMatchers.refEq(vehicleDto))).thenReturn(ticketDto);
        MvcResult mvcResult = mockMvc.perform(post(URL, "E1").contentType(APPLICATION_JSON).content(requestBody)).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertEquals(new ObjectMapper().writeValueAsString(ticketDto), response);
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}