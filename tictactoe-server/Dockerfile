FROM maven:3.8.2-openjdk-16
COPY .. /tictactoe
WORKDIR /tictactoe
RUN mvn clean package
CMD ["java","-jar","tictactoe-server/target/tictactoe-server-1.0-SNAPSHOT.jar"]