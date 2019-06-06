#!/bin/bash 
javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mkdir -p ../../ow/Interfaces
mkdir -p ../../ow/Resources
mkdir -p ../../ow/Main
mv Interfaces/*.class ../../ow/Interfaces/.
mv Resources/*.class ../../ow/Resources/.
mv Main/*.class ../../ow/Main/.
