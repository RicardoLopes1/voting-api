# First stage: complete build environment
FROM maven:3.8.5-openjdk-11-slim as build

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean package -Dspring.profiles.active=prod -DskipTests

#
# Second stage: minimal runtime environment
FROM adoptopenjdk/openjdk11:alpine-jre as runtime

# copy jar from the first stage
COPY --from=build target/*.jar app.jar

EXPOSE 8080
CMD ["java","-Xmx512m","-jar","app.jar"]

# -Xmx512m is the maximum amount of memory that can be used by the JVM.
# -jar is the command to run the jar file.
# app.jar is the name of the jar file.