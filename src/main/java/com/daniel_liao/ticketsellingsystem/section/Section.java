package com.daniel_liao.ticketsellingsystem.section;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.daniel_liao.ticketsellingsystem.entity.Seat;
import com.daniel_liao.ticketsellingsystem.entity.Ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    @Column(name = "total_row")
    @NotNull(message = "Total row can't be empty.")
    @Range(min = 1, max = 100, message = "The length of the total row must be between 1 and 100.")
    private Integer totalRow;

    @Column(name = "total_number_per_row")
    @NotNull(message = "Total number per row can't be empty.")
    @Range(min = 1, max = 100, message = "The length of the total number per row must be between 1 and 100.")
    private Integer totalNumberPerRow;

    @Column
    @NotNull(message = "Price can't be empty.")
    private Integer price;

    @OneToMany(mappedBy = "section")
    private List<Seat> seats;

    @OneToMany(mappedBy = "section")
    private List<Ticket> tickets;
}
