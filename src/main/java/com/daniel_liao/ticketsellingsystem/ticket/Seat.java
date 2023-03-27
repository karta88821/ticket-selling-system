package com.daniel_liao.ticketsellingsystem.ticket;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.daniel_liao.ticketsellingsystem.event.Event;
import com.daniel_liao.ticketsellingsystem.section.Section;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "seat_row")
    private Integer row;

    @Column(name = "seat_number")
    private Integer number;

    @Column
    private Boolean availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;

    @OneToOne(mappedBy = "seat")
    private Ticket ticket;
}
