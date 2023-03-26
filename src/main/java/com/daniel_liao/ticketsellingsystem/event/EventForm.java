package com.daniel_liao.ticketsellingsystem.event;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventForm {

    @NotEmpty(message = "Name can't be empty.")
    private String name;

    @NotNull(message = "Dates can't be empty.")
    @Size(min = 1, message = "Dates can't be empty.")
    @Valid
    private List<String> dates;
}
