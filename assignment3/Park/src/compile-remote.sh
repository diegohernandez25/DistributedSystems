#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../Public/park/Interfaces
mkdir -p ../../Public/park/Main
mv Interfaces/*.class ../../Public/park/Interfaces/.
mv Main/*.class ../../Public/park/Main/.
