## Lautaro Blanco - MINDATA
### Spaceship CRUD

#### Run Application:
##### With Maven:
mvn spring-boot:run

##### With Docker:
docker build -t mindata:latest .
docker run -p 8081:8080 mindata

#### Run tests:
mvn test

#### Documentation
Swagger: http://localhost:8080/swagger-ui/index.html \
Also there is a Postman collection in root project with all endpoints to test the API.