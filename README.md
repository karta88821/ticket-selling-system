# Ticketing System

This side project is written in **Java** and **SpringBoot** framework for creating a ticket selling system. 
**MySQL** database is used for storing data and **Redis** database is used for keeping the data that frequently been used. Moreover, this web server is deployed on the **Docker container** in **AWS EC2**.

## Features
This project has serveral features as follows:
- The user can login in to get tickets. If the user does not have an account, they can register an account on the system.
- The user can get a ticket for an event and view tickets they own.
- The `admin` user can add new event, new section and bind a section to an event. 

## Development Tools
- Java
- SpringBoot
- Thymeleaf, Bootstrape
- MySQL (Amazon RDS)
- Redis
- AWS EC2 (Virtual Machine)
- Docker

## Demo
http://13.231.20.36/home

## Example Accounts
|Account                |Password               |  Role|
|---                    |-----------------------|-----------------------------|
|superadmin             |admin2023              |ADMIN                        |
|account1               |password               |NORMAL                       |



## End points
- /home
- /login
- /register
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

## System Architecture
![image](https://github.com/karta88821/ticket-selling-system/blob/main/system-structure.png)

## Database Schema

![image](https://github.com/karta88821/ticket-selling-system/blob/main/db_schema.png.png)

## Home page

![image](https://github.com/karta88821/ticket-selling-system/blob/main/home_page.png.png)
