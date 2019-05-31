#!/bin/bash

javac Interfaces/*.java
javac Resources/*.java
javac Main/*.java

mv Interfaces/*.class ../../lounge/Interfaces/.
mv Resources/*.class ../../lounge/Resources/.
mv Main/*.class ../../lounge/Main/.
