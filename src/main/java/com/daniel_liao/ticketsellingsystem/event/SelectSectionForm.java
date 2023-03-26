package com.daniel_liao.ticketsellingsystem.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SelectSectionForm {
    private Integer sectionID;
    private Integer numberOfseats;
}
