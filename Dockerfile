FROM openjdk:11
VOLUME /tmp
MAINTAINER java@ilyaHlebik.com
COPY backend/target/backend-1.0-SNAPSHOT.jar opt/blackjack.jar
COPY images /images
EXPOSE 8091
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar","opt/blackjack.jar"]