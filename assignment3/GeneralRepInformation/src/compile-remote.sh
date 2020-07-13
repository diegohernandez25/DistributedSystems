#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../Public/gri/Interfaces
mkdir -p ../../Public/gri/Main
mv Interfaces/*.class ../../Public/gri/Interfaces/.
mv Main/*.class ../../Public/gri/Main/.
