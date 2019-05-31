#!/usr/bin/env bash
CUR_PATH=$(pwd)
java -Djava.rmi.server.codebase="file://$CUR_PATH"\
     -Djava.rmi.server.useCodebaseOnly=false\
     Main.Main 
