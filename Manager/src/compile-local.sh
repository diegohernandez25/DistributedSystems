#!/bin/bash/
javac Interfaces/*.java
javac Main/*.java

mv Interfaces/*class ../../manager/Interfaces/.
mv Main/*class ../../manager/Main/.
