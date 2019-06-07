#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mkdir -p ../../Public/customer/Interfaces
mkdir -p ../../Public/customer/Main
mv Interfaces/*.class ../../Public/customer/Interfaces/.
mv Main/*.class ../../Public/customer/Main/.
