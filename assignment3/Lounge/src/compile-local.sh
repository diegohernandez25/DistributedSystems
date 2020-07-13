#!/bin/bash
javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mkdir -p ../../lounge/Interfaces
mkdir -p ../../lounge/Resources
mkdir -p ../../lounge/Main
mv Interfaces/*.class ../../lounge/Interfaces/.
mv Resources/*.class ../../lounge/Resources/.
mv Main/*.class ../../lounge/Main/.
