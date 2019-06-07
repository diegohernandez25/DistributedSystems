#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../Public/ss/Interfaces
mkdir -p ../../Public/ss/Main
mv Interfaces/*.class ../../Public/ss/Interfaces/.
mv Main/*.class ../../Public/ss/Main/.
