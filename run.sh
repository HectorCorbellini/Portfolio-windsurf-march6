#!/bin/bash
cd "/root/CascadeProjects/ISLA/ecosystem-simulation-"
mvn clean compile assembly:single
java -jar target/ecosystem-simulation-1.0-SNAPSHOT-jar-with-dependencies.jar
