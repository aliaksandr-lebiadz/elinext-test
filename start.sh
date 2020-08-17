#!/bin/bash
mvn -f backend/pom.xml spring-boot:start &
npm --prefix frontend start