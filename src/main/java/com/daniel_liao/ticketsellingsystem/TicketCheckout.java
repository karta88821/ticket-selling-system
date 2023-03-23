package com.daniel_liao.ticketsellingsystem;

import java.util.List;

import com.daniel_liao.ticketsellingsystem.entity.Seat;

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
