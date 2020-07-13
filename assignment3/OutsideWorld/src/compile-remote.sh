#!/bin/bash
javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mkdir -p ../../Public/ow/Interfaces
mkdir -p ../../Public/ow/Resources
mkdir -p ../../Public/ow/Main
mv Interfaces/*.class ../../Public/ow/Interfaces/.
mv Resources/*.class ../../Public/ow/Resources/.
mv Main/*.class ../../Public/ow/Main/.
