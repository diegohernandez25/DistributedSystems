#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../Public/mechanic/Interfaces
mkdir -p ../../Public/mechanic/Main
mv Interfaces/*.class ../../Public/mechanic/Interfaces/.
mv Main/*.class ../../Public/mechanic/Main/.
