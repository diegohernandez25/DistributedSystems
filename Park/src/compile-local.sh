#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../park/Interfaces
mkdir -p ../../park/Main
mv Interfaces/*.class ../../park/Interfaces/.
mv Main/*.class ../../park/Main/.
