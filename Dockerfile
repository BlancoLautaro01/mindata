FROM openjdk:21
ADD target/mindata-0.0.1-SNAPSHOT.jar mindata.jar
ENTRYPOINT [ "java", "-jar","mindata.jar" ]