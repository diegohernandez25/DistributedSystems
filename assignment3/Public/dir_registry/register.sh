#!/bin/bash

CURR_PATH=$(pwd)
java -Djava.rmi.server.codebase="file:///$CURR_PATH/dir_registry"\
	-Djava.rmi.server.useCodebaseOnly=false\
	-Djava.security.policy=java.policy\
	Main.ServerRegisterRemoteObject
