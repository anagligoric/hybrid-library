FROM openjdk:8
ADD target/executableJar.jar hybrid-library.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "hybrid-library.jar"]