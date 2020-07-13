#!/bin/bash
javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mkdir -p ../../Public/lounge/Interfaces
mkdir -p ../../Public/lounge/Resources
mkdir -p ../../Public/lounge/Main
mv Interfaces/*.class ../../Public/lounge/Interfaces/.
mv Resources/*.class ../../Public/lounge/Resources/.
mv Main/*.class ../../Public/lounge/Main/.
