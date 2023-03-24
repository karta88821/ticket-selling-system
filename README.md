# Ticket Selling System

This side project create a system for selling tickets for concerts.
This project has serveral features as follows:
- The user can login in to get tickets. If the user does not have an account, they can register an account on the system.
- The user can get a ticket for an event and view tickets they own.
- The `admin` user can add new event, new section and bind a section to an event. 

## Development Tools
- Java
- SpringBoot, Spring Security, Spring Jpa 
- Thymeleaf, Bootstrape
- MySQL

## End points
- /login
- /register
- /home
- /users/all
- /sections/all
- /events/all
- /events/event_id/sections/all
- /events/event_id/sections/do-bind-section
- /tickets/all
- /tickets/events
- /tickets/events/event_id
- /tickets/checkout
- /tickets/finish

## Database Schema

![image](https://github.com/karta88821/ticket-selling-system/blob/main/db_schema.png.png)

## Home page

![image](https://github.com/karta88821/ticket-selling-system/blob/main/home_page.png.png)
