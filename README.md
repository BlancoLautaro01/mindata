## Lautaro Blanco - MINDATA
### Spaceship CRUD

<b>Run with Maven:</b> mvn spring-boot:run

#### Run with Docker
docker build -t mindata:latest . \
docker run -p 8081:8080 mindata 

<b>Run tests:</b>
mvn test 

<b>Swagger:</b> http://localhost:8080/swagger-ui/index.html \
Also there is a Postman collection in root project with all endpoints to test the API.