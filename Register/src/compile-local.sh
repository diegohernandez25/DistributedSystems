#!/bin/bash
javac Interfaces/*.java
javac Main/*.java
mv Interfaces/*.class ../../registry/Interfaces/.
mv Main/*.class ../../registry/Main/.

