#!/bin/bash

javac Interfaces/*.java
javac Main/*.java

mv Interfaces/*.class ../../park/Interfaces/.
mv Main/*.class ../../park/Main/.
