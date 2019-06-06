#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../registry/Interfaces
mkdir -p ../../registry/Main
mv Interfaces/*.class ../../registry/Interfaces/.
mv Main/*.class ../../registry/Main/.

