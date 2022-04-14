package com.parking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketDto {
    private Long vehicleSlotId;
    private Double amount;
    private String gift;

    public TicketDto(TicketDtoBuilder ticketDtoBuilder) {
        this.vehicleSlotId = ticketDtoBuilder.vehicleSlotId;
        this.amount = ticketDtoBuilder.amount;
        this.gift = ticketDtoBuilder.gift;
    }

    public static class TicketDtoBuilder {
        private Long vehicleSlotId;
        private Double amount;
        private String gift;

        public TicketDtoBuilder(Long vehicleSlotId, Double amount) {
            this.vehicleSlotId = vehicleSlotId;
            this.amount = amount;
        }

        public TicketDtoBuilder withGift(String gift) {
            this.gift = gift;
            return this;
        }

        public TicketDto build() {
            return new TicketDto(this);
        }
    }
}
