package com.daniel_liao.ticketsellingsystem.form;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventForm {

    private String name;

    private List<Date> dates;
}
