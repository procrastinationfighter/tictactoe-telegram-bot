FROM maven:3.5-jdk-14
COPY src /tictactoe/server/src
COPY pom.xml /tictactoe/server
WORKDIR /tictactoe/server
RUN mvn clean package
CMD ["java","-jar","target/tictactoe-server-1.0-SNAPSHOT.jar"]