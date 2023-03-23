package com.daniel_liao.ticketsellingsystem;

public interface ISectionStatus {
    Integer getSectionID();
    String getSectionName();
    Integer getTotalRow();
    Integer getTotalNumberPerRow();
    Integer getPrice();
    Integer getAvSeats();
}
