#!/bin/bash 

javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mv Interfaces/*.class ../../ow/Interfaces/.
mv Resources/*.class ../../ow/Resources/.
mv Main/*.class ../../ow/Main/.
