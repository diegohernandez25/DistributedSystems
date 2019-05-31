#!/bin/bash

javac Interfaces/*.java
javac Main/*.java

mv Interfaces/*.class ../../gri/Interfaces/.
mv Main/*.class ../../gri/Main/.
