#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mv Interfaces/*.class ../../customer/Interfaces/.
mv Main/*.class ../../customer/Main/.
