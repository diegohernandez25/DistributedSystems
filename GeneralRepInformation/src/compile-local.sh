#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../gri/Interfaces
mkdir -p ../../gri/Main
mv Interfaces/*.class ../../gri/Interfaces/.
mv Main/*.class ../../gri/Main/.
