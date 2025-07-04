#!/bin/bash

docker run --name postgres-subscription -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=subscription_db -p 5432:5432 -d postgres:latest

./mvnw clean package

java -jar ./target/subscription-app-0.0.1-SNAPSHOT.jar 
