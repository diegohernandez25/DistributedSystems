#!/bin/bash/
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../manager/Interfaces
mkdir -p ../../manager/Main
mv Interfaces/*class ../../manager/Interfaces/.
mv Main/*class ../../manager/Main/.
