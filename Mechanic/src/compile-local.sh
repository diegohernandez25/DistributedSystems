#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../mechanic/Interfaces
mkdir -p ../../mechanic/Main
mv Interfaces/*.class ../../mechanic/Interfaces/.
mv Main/*.class ../../mechanic/Main/.
