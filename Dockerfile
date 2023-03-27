FROM openjdk:8
EXPOSE 8080
ARG PROFILE=${PROFILE}
WORKDIR /Documents/mydocker
COPY ./target/ticket-selling-system-docker.jar ./ticket-selling-system-docker.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${PROFILE}", "ticket-selling-system-docker.jar"]