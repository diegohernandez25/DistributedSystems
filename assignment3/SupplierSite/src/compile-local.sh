#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../ss/Interfaces
mkdir -p ../../ss/Main
mv Interfaces/*.class ../../ss/Interfaces/.
mv Main/*.class ../../ss/Main/.
