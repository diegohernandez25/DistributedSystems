CUR_PATH=$(pwd)
java -Djava.rmi.server.codebase="file://$CUR_PATH"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     Main.ServerRegisterRemoteObject
