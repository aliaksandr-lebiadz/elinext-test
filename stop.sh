#!/bin/bash
mvn -f backend/pom.xml spring-boot:stop -Dspring-boot.stop.fork
npm --prefix frontend stop