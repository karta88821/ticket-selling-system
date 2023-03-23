package com.daniel_liao.ticketsellingsystem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column(name = "total_row")
    private Integer totalRow;

    @Column(name = "total_number_per_row")
    private Integer totalNumberPerRow;

    @Column
    private Integer price;

    @OneToMany(mappedBy = "section")
    private List<Seat> seats;

    @OneToMany(mappedBy = "section")
    private List<Ticket> tickets;
}
