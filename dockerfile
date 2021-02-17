# https://hub.docker.com/r/adoptopenjdk/openjdk11
FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1-alpine
# not necessary: this is for spring application to create work dirs for Tomcat
VOLUME /tmp
COPY target/webapp-0.0.1-SNAPSHOT.jar webapp.jar
ENTRYPOINT ["java","-jar","webapp.jar"]
