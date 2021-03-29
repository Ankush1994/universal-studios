# universal-studios

TO RUN the PROJECT
1. Start Postgres Docker
   docker run --rm --name pg_us -d -p 5500:5432 -e POSTGRES_USER=us -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=us_db postgres:9.6

2. gradle bootrun
3. Import Postman link to call APIs - https://www.getpostman.com/collections/d8b4b5f0670e7a6251ad
