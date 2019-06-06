#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../customer/Interfaces
mkdir -p ../../customer/Main
mv Interfaces/*.class ../../customer/Interfaces/.
mv Main/*.class ../../customer/Main/.
