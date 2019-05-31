#!/bin/bash
javac Interfaces/*.java
javac Main/*.java

mv Interfaces/*.class ../../mechanic/Interfaces/.
mv Main/*.class ../../mechanic/Main/.
