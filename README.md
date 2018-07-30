## Food Service

Main idea of this repository is to create quick and easy to use platform for ordering food.

Pet project to learn Scala, Slick and Akka HTTP

### To start locally

docker run --name postgres -v "$PWD"/:/opt/postgres/ -e POSTGRES_PASSWORD=mysecretpassword -d postgres

docker exec -it postgres psql -U postgres -f /home/zendist/food-service/src/main/resources/sql_evolutions/init.sql

sbt run