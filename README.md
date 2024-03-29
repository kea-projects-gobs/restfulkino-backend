[![Build and deploy App](https://github.com/kea-projects-gobs/restfulkino-backend/actions/workflows/main_kino-kea.yml/badge.svg)](https://github.com/kea-projects-gobs/restfulkino-backend/actions/workflows/main_kino-kea.yml)
# Restful kino backend

Group project made by [Benjamin](https://github.com/Benjamin-Harris1), [Gustav](https://github.com/gustavwiese), [Osman](https://github.com/osman-butt) & [Sham](https://github.com/Kapalee).

## Deployment

- See the live version of the app [here](app-kino.onrender.com)
- Backend deployment [here](https://kino-kea.azurewebsites.net/)
- [Frontend repository](https://github.com/kea-projects-gobs/restfulkino-frontend)

## Technologies used

- **Spring Boot**
- **Spring Security**
- **Maven**
- **MySQL**
- **JWT**

## API Documentation

For detailed information about the API endpoints:
- Run the application locally
- Go to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## JavaDocs
For detailed information about the Java code:
- Generate JavaDocs by running the following command in the terminal
```bash
./mvnw javadoc:javadoc
```

## Installation 

To set up the project locally, follow these steps:

1. Clone the repository:
```bash
git clone https://github.com/kea-projects-gobs/restfulkino-backend.git
```

2. Create a .env file and paste the following / or set the following environment variables directly in intelliJ
```bash
JDBC_DATABASE_URL=jdbc:mysql://localhost:3306/your_mysql_dbname
JDBC_DATABASE_USERNAME=username for your local db
JDBC_DATABASE_PASSWORD=password for your local db
TOKEN_SECRET=
```

3. To generate a token secret, run the following command in your terminal
```bash
openssl rand -base64 32
```

4. Run the application
```bash
./mvnw spring-boot:run
```




