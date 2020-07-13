#!bin/bash
javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mkdir -p ../../ra/Interfaces
mkdir -p ../../ra/Resources
mkdir -p ../../ra/Main
mv Interfaces/*.class ../../ra/Interfaces/.
mv Resources/*.class ../../ra/Resources/.
mv Main/*.class ../../ra/Main/.
