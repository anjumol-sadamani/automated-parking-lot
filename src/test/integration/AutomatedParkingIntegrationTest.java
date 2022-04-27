package integration;


import com.parking.AutomatedParkingApp;
import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AutomatedParkingApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutomatedParkingIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String URL = "/v1/automatic-parking/allocate/E1";

    @Test
    void testSlotAllocation() throws Exception {
        VehicleDto vehicleDto = new VehicleDto(1234l, 1.2f, 1000f, "car");
        ResponseEntity<TicketDto> responseEntity = restTemplate.postForEntity(URL, vehicleDto, TicketDto.class);

        int status = responseEntity.getStatusCodeValue();
        TicketDto ticketDto = responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), status);
        assertNotNull(ticketDto);
    }

}

