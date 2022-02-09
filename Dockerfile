FROM maven:3.8.4-openjdk-17 as build
COPY pom.xml /home/app/
RUN mvn -f /home/app/pom.xml verify clean --fail-never
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:17
COPY --from=build /home/app/target/CloudPi-0.0.1.jar /usr/local/lib/mjm.jar



EXPOSE 8080
#todo
ENTRYPOINT ["java", "-Dspring.profiles.active=dev,docker", "-jar", "/usr/local/lib/mjm.jar"]