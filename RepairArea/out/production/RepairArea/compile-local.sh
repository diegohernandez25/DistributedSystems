#!bin/bash
javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mv Interfaces/*.class ../../ra/Interfaces/.
mv Resources/*.class ../../ra/Resources/.
mv Main/*.class ../../ra/Main/.
