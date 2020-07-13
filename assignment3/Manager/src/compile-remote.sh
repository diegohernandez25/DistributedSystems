#!/bin/bash/
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../Public/manager/Interfaces
mkdir -p ../../Public/manager/Main
mv Interfaces/*class ../../Public/manager/Interfaces/.
mv Main/*class ../../Public/manager/Main/.
