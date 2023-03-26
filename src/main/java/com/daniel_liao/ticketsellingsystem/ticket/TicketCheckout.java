package com.daniel_liao.ticketsellingsystem.ticket;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TicketCheckout {
    private Integer eventID;
    private Integer sectionID;
    private List<Seat> selectedSeats;
}
