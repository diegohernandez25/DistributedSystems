#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../Public/registry/Interfaces
mkdir -p ../../Public/registry/Main
mv Interfaces/*.class ../../Public/registry/Interfaces/.
mv Main/*.class ../../Public/registry/Main/.
