#!bin/bash
javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mkdir -p ../../Public/ra/Interfaces
mkdir -p ../../Public/ra/Resources
mkdir -p ../../Public/ra/Main
mv Interfaces/*.class ../../Public/ra/Interfaces/.
mv Resources/*.class ../../Public/ra/Resources/.
mv Main/*.class ../../Public/ra/Main/.
