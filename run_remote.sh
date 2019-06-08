#!/bin/bash

########### CONSTANTS DEFINITION ##########
# PASSWORD
PASSWORD='dievalint'

# ENTITIES HOSTS
REGISTRYHOST=''
GRIHOST=''
LOUNGEHOST=''
PARKHOST=''
OWHOST=''
RAHOST=''
SSHOST=''
MANAGERHOST=''
MECHANICHOST=''
CUSTOMERHOST=''

#ENTITIES PORTS
REGISTRYPORT='22460'
SERVERREGISTRYPORT='22467'
GRIPORT='22466'
LOUNGEPORT='22461'
PARKPORT='22463'
OWPORT='22464'
RAPORT='22462'
SSPORT='22465'

#VARIABLES VALUES
NUM_CUSTOMERS=''
NUM_MECHANICS=''
NUM_REPLACEMENT=''
NUM_CAR_TYPES=''
CAR_PARTS=''
MAX_CAR_PARTS=''
LOG_NAME='log.txt'

#TEMPORARY VALUES
N=1
############# END OF CONSTANTS ############

########### FUNCTIONS DEFINITION ##########
function getMachines {
  while IFS='' read -r line || [[ -n "$line" ]]; do
      # Get Registry machine
      if [[ $line == *"SERVER_REGISTRY_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            SERVERREGISTRYPORT=$i
          fi
        done
      fi
      if [[ $line == *" REGISTRY_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            REGISTRYPORT=$i
          fi
        done
      fi
      if [[ $line == *"REGISTRY_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            REGISTRYHOST=$i
          fi
        done
      fi

      # Get General Repository Information machine
      if [[ $line == *"GENERALREP_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            GRIPORT=$i
          fi
        done
      fi
      if [[ $line == *"GENERALREP_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            GRIHOST=$i
          fi
        done
      fi

      # Get Lounge machine
      if [[ $line == *"LOUNGE_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            LOUNGEPORT=$i
          fi
        done
      fi
      if [[ $line == *"LOUNGE_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            LOUNGEHOST=$i
          fi
        done
      fi

      # Get Park machine
      if [[ $line == *"PARK_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            PARKPORT=$i
          fi
        done
      fi
      if [[ $line == *"PARK_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            PARKHOST=$i
          fi
        done
      fi

      # Get Outside World machine
      if [[ $line == *"OW_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            OWPORT=$i
          fi
        done
      fi
      if [[ $line == *"OW_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            OWHOST=$i
          fi
        done
      fi

      # Get Repair Area machine
      if [[ $line == *"REPAIRAREA_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            RAPORT=$i
          fi
        done
      fi
      if [[ $line == *"REPAIRAREA_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            RAHOST=$i
          fi
        done
      fi

      # Get Supplier Site machine
      if [[ $line == *"SS_PORT"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            SSPORT=$i
          fi
        done
      fi
      if [[ $line == *"SS_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            SSHOST=$i
          fi
        done
      fi

      # Get Manager machine
      if [[ $line == *"MANAGER_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            MANAGERHOST=$i
          fi
        done
      fi

      # Get Mechanic machine
      if [[ $line == *"MECHANIC_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            MECHANICHOST=$i
          fi
        done
      fi

      # Get Customer machine
      if [[ $line == *"CUSTOMER_HOST"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            CUSTOMERHOST=$i
          fi
        done
      fi

      # Get number customers
      if [[ $line == *"NUM_CUSTOMERS"* ]]; then
        IFS='= *;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
            NUM_CUSTOMERS=$i
        done
      fi

      # Get number mechanics
      if [[ $line == *"NUM_MECHANICS"* ]]; then
        IFS='= *;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
            NUM_MECHANICS=$i
        done
      fi

      # Get number replacement
      if [[ $line == *"NUM_REPLACEMENT"* ]]; then
        IFS='= *;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
            NUM_REPLACEMENT=$i
        done
      fi

      # Get number car types
      if [[ $line == *"NUM_CAR_TYPES"* ]]; then
        IFS='= *;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
            NUM_CAR_TYPES=$i
        done
      fi

      # Get number car parts
      if [[ $line == *" CAR_PARTS"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
            CAR_PARTS=$i
        done
      fi

      # Get number max car parts
      if [[ $line == *"MAX_CAR_PARTS"* ]]; then
        IFS='=*;' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
            MAX_CAR_PARTS=$i
        done
      fi

      # Get log name
      if [[ $line == *"LOG_NAME"* ]]; then
        IFS='= "*";' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *".txt"* ]]; then
            LOG_NAME=$i
          fi
        done
      fi
  done < Parameters.java
}

function writeFile {
  > Parameters.java
  echo "package Main;" >> Parameters.java
  echo "" >> Parameters.java
  echo "public class Parameters {" >> Parameters.java
  echo "  public final static int REGISTRY_PORT           = ${REGISTRYPORT};" >> Parameters.java
  echo "  public final static int LOUNGE_PORT             = ${LOUNGEPORT};" >> Parameters.java
  echo "  public final static int REPAIRAREA_PORT         = ${RAPORT};" >> Parameters.java
  echo "  public final static int PARK_PORT               = ${PARKPORT};" >> Parameters.java
  echo "  public final static int OW_PORT                 = ${OWPORT};" >> Parameters.java
  echo "  public final static int SS_PORT                 = ${SSPORT};" >> Parameters.java
  echo "  public final static int GENERALREP_PORT         = ${GRIPORT};" >> Parameters.java
  echo "  public final static int SERVER_REGISTRY_PORT    = ${SERVERREGISTRYPORT};" >> Parameters.java
  echo "" >> Parameters.java
  echo "  public final static String REGISTRY_NAME            = \"Registry\";" >> Parameters.java
  echo "  public final static String REGISTRY_NAME_ENTRY      = \"RegisterHandler\";" >> Parameters.java
  echo "  public final static String REPAIRAREA_NAME          = \"RepairArea\";" >> Parameters.java
  echo "  public final static String LOUNGE_NAME              = \"Lounge\";" >> Parameters.java
  echo "  public final static String PARK_NAME                = \"Park\";" >> Parameters.java
  echo "  public final static String OW_NAME                  = \"OutsideWorld\";" >> Parameters.java
  echo "  public final static String SS_NAME                  = \"SupplierSite\";" >> Parameters.java
  echo "  public final static String GENERALREP_NAME          = \"GeneralRepository\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  public final static String LOCALHOST            = \"localhost\";" >> Parameters.java
  echo "  public final static String GENERALREP_HOST      = \"${GRIHOST}\";" >> Parameters.java
  echo "  public final static String LOUNGE_HOST          = \"${LOUNGEHOST}\";" >> Parameters.java
  echo "  public final static String PARK_HOST            = \"${PARKHOST}\";" >> Parameters.java
  echo "  public final static String OW_HOST              = \"${OWHOST}\";" >> Parameters.java
  echo "  public final static String REPAIRAREA_HOST      = \"${RAHOST}\";" >> Parameters.java
  echo "  public final static String SS_HOST              = \"${SSHOST}\";" >> Parameters.java
  echo "  public final static String MECHANIC_HOST        = \"${MECHANICHOST}\";" >> Parameters.java
  echo "  public final static String MANAGER_HOST         = \"${MANAGERHOST}\";" >> Parameters.java
  echo "  public final static String CUSTOMER_HOST        = \"${CUSTOMERHOST}\";" >> Parameters.java
  echo "  public final static String REGISTRY_HOST        = \"${REGISTRYHOST}\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  // Definition of variables" >> Parameters.java
  echo "  public final static int NUM_CUSTOMERS   = ${NUM_CUSTOMERS};" >> Parameters.java
  echo "  public final static int NUM_MECHANICS   = ${NUM_MECHANICS};" >> Parameters.java
  echo "  public final static int NUM_REPLACEMENT = ${NUM_REPLACEMENT};" >> Parameters.java
  echo "  public final static int NUM_CAR_TYPES   = ${NUM_CAR_TYPES};" >> Parameters.java
  echo "  public final static int[] CAR_PARTS     = ${CAR_PARTS};" >> Parameters.java
  echo "  public final static int[] MAX_CAR_PARTS = ${MAX_CAR_PARTS};" >> Parameters.java
  echo "  public final static String LOG_NAME     = \"${LOG_NAME}\";" >> Parameters.java
  echo "}" >> Parameters.java
}

function defaultValues {
        REGISTRYHOST="l040101-ws01.ua.pt"
        REGISTRYPORT="22460"
        SERVERREGISTRYPORT="22467"
        GRIHOST="l040101-ws02.ua.pt"
        GRIPORT="22466"
        LOUNGEHOST="l040101-ws03.ua.pt"
        LOUNGEPORT="22461"
        PARKHOST="l040101-ws04.ua.pt"
        PARKPORT="22463"
        OWHOST="l040101-ws05.ua.pt"
        OWPORT="22464"
        RAHOST="l040101-ws06.ua.pt"
        RAPORT="22462"
        SSHOST="l040101-ws07.ua.pt"
        SSPORT="22465"
        MANAGERHOST="l040101-ws08.ua.pt"
        MECHANICHOST="l040101-ws09.ua.pt"
        CUSTOMERHOST="l040101-ws10.ua.pt"

        NUM_CUSTOMERS="30"
        NUM_MECHANICS="2"
        NUM_REPLACEMENT="3"
        NUM_CAR_TYPES="3"
        CAR_PARTS="{0, 0, 0}"
        MAX_CAR_PARTS="{1, 1, 1}"
        LOG_NAME="log.txt"
}

function getVars {
  echo -e "\nEnter the values for each parameter:"

  read -p "  Registry host name: " REGISTRYHOST
  read -p "  Registry port number: " REGISTRYPORT
  read -p "  Server Registry port number: " SERVERREGISTRYPORT
  read -p "  General Repository Information host name: " GRIHOST
  read -p "  General Repository Information port number: " GRIPORT
  read -p "  Lounge host name: " LOUNGEHOST
  read -p "  Lounge port number: " LOUNGEPORT
  read -p "  Park host name: " PARKHOST
  read -p "  Park port number: " PARKPORT
  read -p "  Outside World host name: " OWHOST
  read -p "  Outside World port number: " OWPORT
  read -p "  Repair Area host name: " RAHOST
  read -p "  Repair Area port number: " RAPORT
  read -p "  Supplier Site host name: " SSHOST
  read -p "  Supplier Site port number: " SSPORT
  read -p "  Manager host name: " MANAGERHOST
  read -p "  Mechanic host name: " MECHANICHOST
  read -p "  Customer host name: " CUSTOMERHOST
  echo ""
  read -p "  Number of Customers: " NUM_CUSTOMERS
  read -p "  Number of Mechanics: " NUM_MECHANICS
  read -p "  Number of Replacement Cars: " NUM_REPLACEMENT
  read -p "  Number of Car Parts types: " NUM_CAR_TYPES

  CAR_PARTS="{"
  for (( i=1; i<=${NUM_CAR_TYPES}; i++ ))
  do
      read -p "  Car Part $i starting stock: " N
      if [[ $i == 1 ]]; then
        CAR_PARTS="$CAR_PARTS$N"
      else
        CAR_PARTS="$CAR_PARTS, $N"
      fi
  done
  CAR_PARTS="$CAR_PARTS}"

  MAX_CAR_PARTS="{"
  for (( i=1; i<=${NUM_CAR_TYPES}; i++ ))
  do
      read -p "  Maximum number of stock Car Part $i can have: " N
      if [[ $i == 1 ]]; then
        MAX_CAR_PARTS="$MAX_CAR_PARTS$N"
      else
        MAX_CAR_PARTS="$MAX_CAR_PARTS, $N"
      fi
  done
  MAX_CAR_PARTS="$MAX_CAR_PARTS}"

  read -p "  Log file name (ended in .txt): " LOG_NAME
}

function getVarsLess {
  echo -e "\nEnter the values for each parameter:"
  read -p "  Number of Customers: " NUM_CUSTOMERS
  read -p "  Number of Mechanics: " NUM_MECHANICS
  read -p "  Number of Replacement Cars: " NUM_REPLACEMENT
  read -p "  Number of Car Parts types: " NUM_CAR_TYPES

  CAR_PARTS="{"
  for (( i=1; i<=${NUM_CAR_TYPES}; i++ ))
  do
      read -p "  Car Part $i starting stock: " N
      if [[ $i == 1 ]]; then
        CAR_PARTS="$CAR_PARTS$N"
      else
        CAR_PARTS="$CAR_PARTS, $N"
      fi
  done
  CAR_PARTS="$CAR_PARTS}"

  MAX_CAR_PARTS="{"
  for (( i=1; i<=${NUM_CAR_TYPES}; i++ ))
  do
      read -p "  Maximum number of stock Car Part $i can have: " N
      if [[ $i == 1 ]]; then
        MAX_CAR_PARTS="$MAX_CAR_PARTS$N"
      else
        MAX_CAR_PARTS="$MAX_CAR_PARTS, $N"
      fi
  done
  MAX_CAR_PARTS="$MAX_CAR_PARTS}"

  read -p "  Log file name (ended in .txt): " LOG_NAME
}

function copyParameters {
    echo "Copying Parameters.java file..."
    cp Parameters.java Register/src/Main/
    cp Parameters.java Manager/src/Main/
    cp Parameters.java Mechanic/src/Main/
    cp Parameters.java Customer/src/Main/
    cp Parameters.java Lounge/src/Main/
    cp Parameters.java Park/src/Main/
    cp Parameters.java OutsideWorld/src/Main/
    cp Parameters.java RepairArea/src/Main/
    cp Parameters.java SupplierSite/src/Main/
    cp Parameters.java GeneralRepInformation/src/Main/
}

function writeReg {
    cd registry
    > registry_com.sh
    echo "java -Djava.rmi.server.codebase=\"http://${REGISTRYHOST}/sd0406/registry\"\\" >> registry_com.sh
    echo "     -Djava.rmi.server.useCodebaseOnly=true\\" >> registry_com.sh
    echo "     -Djava.security.policy=java.policy\\" >> registry_com.sh
    echo "     Main.ServerRegisterRemoteObject" >> registry_com.sh

    > set_rmiregistry.sh
    echo "rmiregistry -J-Djava.rmi.server.codebase=\"http:/${REGISTRYHOST}/sd0406/registry\"\\" >> set_rmiregistry.sh
    echo "            -J-Djava.rmi.server.useCodebaseOnly=true \$1" >> set_rmiregistry.sh
    cd ..
}

function writeServer {
    cd $2
    > serverSide_com.sh
    echo "java -Djava.rmi.server.codebase=\"http://$1/sd0406/$2\"\\" >> serverSide_com.sh
    echo "     -Djava.rmi.server.useCodebaseOnly=true\\" >> serverSide_com.sh
    echo "     -Djava.security.policy=java.policy\\" >> serverSide_com.sh
    echo "     Main.Main" >> serverSide_com.sh
    cd ..
}

function toHostReg {
    echo "Compiling $2"
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@$1 << EOF
        put -r $2
        bye
EOF
    sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd $2/src/
        bash compile-remote.sh
        exit
EOF
    cd $3
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd Public/$3
        put registry_com.sh
        put set_rmiregistry.sh
        put java.policy
        bye
EOF
    cd ..
}

function toHostServer {
    echo "Compiling $2"
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@$1 << EOF
        put -r $2
        bye
EOF
    sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd $2/src/
        bash compile-remote.sh
        exit
EOF
    cd $3
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd Public/$3
        put serverSide_com.sh
        put java.policy
        bye
EOF
    cd ..
}

function toHostClient {
    echo "Compiling $2"
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@$1 << EOF
        put -r $2
        bye
EOF
    sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd $2/src/
        bash compile-remote.sh
        exit
EOF
    cd $3
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd Public/$3
        put clientSide_com.sh
        bye
EOF
    cd ..
}

function initReg {
    gnome-terminal -- bash -c "sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${REGISTRYHOST} << EOF
        cd Public/registry/
        sh set_rmiregistry.sh ${REGISTRYPORT}
EOF"

    sleep 2

    gnome-terminal -- bash -c "sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${REGISTRYHOST} << EOF
        cd Public/registry/
        sh registry_com.sh
EOF"
}

function initServer {
    gnome-terminal -- bash -c "sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd Public/$2/
        sh serverSide_com.sh
EOF"
}

function initClient {
    gnome-terminal -- bash -c "sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
        cd Public/$2/
        sh clientSide_com.sh
EOF"
}

function getLog {
    echo "Getting log file..."
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${GRIHOST} << EOF
        cd Public/gri/
        get -r ${LOG_NAME}
        bye
EOF
}

function cleanMachines {
    sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
        rm -rf $2
        rm -rf Public/$3
        exit
EOF
}

function getParameters {
  while IFS='' read -r line || [[ -n "$line" ]]; do
    # Get number customers
    if [[ $line == *"NUM_CUSTOMERS"* ]]; then
      IFS='= *;' read -ra MACHINE <<< "$line"
      for i in "${MACHINE[@]}"; do
          NUM_CUSTOMERS=$i
      done
    fi

    # Get number mechanics
    if [[ $line == *"NUM_MECHANICS"* ]]; then
      IFS='= *;' read -ra MACHINE <<< "$line"
      for i in "${MACHINE[@]}"; do
          NUM_MECHANICS=$i
      done
    fi

    # Get number replacement
    if [[ $line == *"NUM_REPLACEMENT"* ]]; then
      IFS='= *;' read -ra MACHINE <<< "$line"
      for i in "${MACHINE[@]}"; do
          NUM_REPLACEMENT=$i
      done
    fi

    # Get number car types
    if [[ $line == *"NUM_CAR_TYPES"* ]]; then
      IFS='= *;' read -ra MACHINE <<< "$line"
      for i in "${MACHINE[@]}"; do
          NUM_CAR_TYPES=$i
      done
    fi

    # Get number car parts
    if [[ $line == *" CAR_PARTS"* ]]; then
      IFS='=*;' read -ra MACHINE <<< "$line"
      for i in "${MACHINE[@]}"; do
          CAR_PARTS=$i
      done
    fi

    # Get number max car parts
    if [[ $line == *"MAX_CAR_PARTS"* ]]; then
      IFS='=*;' read -ra MACHINE <<< "$line"
      for i in "${MACHINE[@]}"; do
          MAX_CAR_PARTS=$i
      done
    fi

    # Get log name
    if [[ $line == *"LOG_NAME"* ]]; then
      IFS='= "*";' read -ra MACHINE <<< "$line"
      for i in "${MACHINE[@]}"; do
        if [[ $i == *".txt"* ]]; then
          LOG_NAME=$i
        fi
      done
    fi
  done < Parameters.java
}

function compile {
    echo "Compiling the code..."
    writeReg
    writeServer ${GRIHOST} gri
    writeServer ${OWHOST} ow
    writeServer ${PARKHOST} park
    writeServer ${RAHOST} ra
    writeServer ${SSHOST} ss
    writeServer ${LOUNGEHOST} lounge

    toHostReg ${REGISTRYHOST} Register registry
    toHostServer ${GRIHOST} GeneralRepInformation gri
    toHostServer ${OWHOST} OutsideWorld ow
    toHostServer ${PARKHOST} Park park
    toHostServer ${RAHOST} RepairArea ra
    toHostServer ${SSHOST} SupplierSite ss
    toHostServer ${LOUNGEHOST} Lounge lounge
    toHostClient ${MANAGERHOST} Manager manager
    toHostClient ${MECHANICHOST} Mechanic mechanic
    toHostClient ${CUSTOMERHOST} Customer customer
}

function canConnect {
    echo "Checking which machines are available..."
    connectedMachines=()
    availableMachines=()
    count=0

    for i in {1..10}
    do
        if [ ! $i = 10 ]
        then
            foo="l040101-ws0$i.ua.pt"
        else
            foo="l040101-ws$i.ua.pt"
        fi
        sshpass -p ${PASSWORD} ssh -q -o StrictHostKeyChecking=no sd0406@$foo exit

        if [ $? = 0 ]   # can connect
        then
            connectedMachines[${#connectedMachines[@]}]=$i
        else
            echo "Can't connect to machine $foo"
        fi
    done
    availableMachines=("${connectedMachines[@]}")
}

function assignMachine {
    if [ ${#availableMachines[@]} = 0 ] # all machines are taken
    then
        availableMachines=("${connectedMachines[@]}")
    fi

    if [ ! ${availableMachines[0]} = 10 ]
    then
        foo="l040101-ws0${availableMachines[0]}.ua.pt"
    else
        foo="l040101-ws${availableMachines[0]}.ua.pt"
    fi

    availableMachines=("${availableMachines[@]:1}")
}

function assignMachines {
    echo "Assigning machines..."
    assignMachine
    REGISTRYHOST=$foo
    echo "Registry set to machine $REGISTRYHOST"
    assignMachine
    GRIHOST=$foo
    echo "General Repository set to machine $GRIHOST"
    assignMachine
    LOUNGEHOST=$foo
    echo "Lounge set to machine $LOUNGEHOST"
    assignMachine
    PARKHOST=$foo
    echo "Park set to machine $PARKHOST"
    assignMachine
    OWHOST=$foo
    echo "Outside World set to machine $OWHOST"
    assignMachine
    RAHOST=$foo
    echo "Repair Area set to machine $RAHOST"
    assignMachine
    SSHOST=$foo
    echo "SupplierSite set to machine $SSHOST"
    assignMachine
    MANAGERHOST=$foo
    echo "Manager set to machine $MANAGERHOST"
    assignMachine
    MECHANICHOST=$foo
    echo "Mechanic set to machine $MECHANICHOST"
    assignMachine
    CUSTOMERHOST=$foo
    echo "Customer set to machine $CUSTOMERHOST"
}

function checkM {
    if sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no sd0406@$1 "[ -d Public/$2 ]"
    then
        echo "Found $2 in machine $1."
        return 0
    fi
    return 1
}

function checkMachine {
    found=false

    if checkM $1 registry
    then
        found=true
        REGISTRYHOST=$1
        par=`sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no sd0406@$1 "cat Register/src/Main/Parameters.java"`
        while read -r line; do
            if [[ $line == *" REGISTRY_PORT"* ]]; then
              IFS='= *;' read -ra MACHINE <<< "$line"
              for i in "${MACHINE[@]}"; do
                    if [[ $i == *"224"* ]]; then
                        REGISTRYPORT=$i
                    fi
              done
            fi
        done <<< "$par"
    fi
    if checkM $1 gri
    then
        found=true
        GRIHOST=$1
    fi
    if checkM $1 lounge
    then
        found=true
        LOUNGEHOST=$1
    fi
    if checkM $1 park
    then
        found=true
        PARKHOST=$1
    fi
    if checkM $1 ow
    then
        found=true
        OWHOST=$1
    fi
    if checkM $1 ra
    then
        found=true
        RAHOST=$1
    fi
    if checkM $1 ss
    then
        found=true
        SSHOST=$1
    fi
    if checkM $1 manager
    then
        found=true
        MANAGERHOST=$1
    fi
    if checkM $1 mechanic
    then
        found=true
        MECHANICHOST=$1
    fi
    if checkM $1 customer
    then
        found=true
        CUSTOMERHOST=$1
    fi
}

function checkMachines {
    for i in ${connectedMachines[@]}
    do
        if [ ! $i = 10 ]
        then
            machine="l040101-ws0$i.ua.pt"
        else
            machine="l040101-ws$i.ua.pt"
        fi

        checkMachine $machine
        if  ! $found
        then
            echo "ERROR: No compiled code found in at least one machine."
            exit 1
        fi
    done
}

function checkDir {
    result=`sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no sd0406@$1 "ls ."`

    while read -r line; do
          IFS=' ' read -ra MACHINE <<< "$line"
          for i in "${MACHINE[@]}"; do
                if [ ! $i = "Public" ]; then
                    sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
                        rm -rf $i
                        exit
EOF
                fi
          done
    done <<< "$result"

    result=`sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no sd0406@$1 "ls Public"`

    while read -r line; do
          IFS=' ' read -ra MACHINE <<< "$line"
          for i in "${MACHINE[@]}"; do
                if [ ! $i = "applet" ]; then
                    sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@$1 << EOF
                        rm -rf Public/$i
                        exit
EOF
                fi
          done
    done <<< "$result"
}

function cleanTrash {
    echo "Cleaning previous code from the machines..."
    for i in ${connectedMachines[@]}
    do
        if [ ! $i = 10 ]
        then
            foo="l040101-ws0$i.ua.pt"
        else
            foo="l040101-ws$i.ua.pt"
        fi

        checkDir $foo
    done
}
############# END OF FUNCTIONS ############

echo "Please make sure you have sshpass installed."
echo "Starting deployment..."

compiled=false

echo "Compile the code?"
PS3="Choice: "
options=("Yes" "No")

select op in "${options[@]}"
do
  case $op in
      "Yes")
            compiled=true
            echo -e "\nUse dynamic machines (picked automatically) or write manually?"
            PS3="Choice: "
            options=("Dynamic" "Manual" "Exit")

            select op in "${options[@]}"
            do
                case $op in
                    "Dynamic" )
                        echo -e "\nChoose how you want to define the parameters"
                        PS3="Choice: "
                        options=("Parameters file" "Interactive" "Default values" "Exit")

                        select op in "${options[@]}"
                        do
                          case $op in
                              "Parameters file")
                                  echo -e "\nPlease note the machines in the Parameters.java file will be ignored.\n"
                                  read -p "Please write your parameters in Parameters.java. Press enter to continue."

                                  if [[ ! -f Parameters.java ]]; then
                                      echo "ERROR: File Parameters.java not found!"
                                      break
                                  else
                                    canConnect
                                    cleanTrash
                                    getParameters
                                    assignMachines
                                    writeFile
                                    copyParameters
                                    compile
                                  fi

                                  break
                                  ;;

                              "Interactive")
                                      canConnect
                                      cleanTrash
                                      getVarsLess
                                      assignMachines
                                      writeFile
                                      copyParameters
                                      compile
                                  break
                                  ;;

                              "Default values")
                                      echo -e "\nThe default values are:"
                                      echo "  Number of Customers: 30"
                                      echo "  Number of Mechanics: 2"
                                      echo "  Number of Replacement Cars: 3"
                                      echo "  Number of Car Parts Types: 3"
                                      echo "  Car Parts in stock: 0 0 0"
                                      echo "  Maximum number of Car Parts: 1 1 1"
                                      echo "  Log file name: log.txt"

                                      echo -e "\nDo you still want to continue?"
                                      PS3="Choice: "
                                      options=("Yes" "No")

                                      select op in "${options[@]}"
                                      do
                                          case $op in
                                              "Yes")
                                                  canConnect
                                                  cleanTrash
                                                  defaultValues
                                                  assignMachines
                                                  writeFile
                                                  copyParameters
                                                  compile

                                                  break
                                                  ;;

                                              "No")
                                                  break
                                                  ;;

                                              *)
                                                  echo "Invalid option $REPLY"
                                                  break
                                                  ;;
                                          esac
                                      done
                                  break
                                  ;;

                              "Exit")
                                  exit 0
                                  break
                                  ;;

                              *)
                                  echo "Invalid option $REPLY"
                                  ;;
                            esac
                        done
                        break
                        ;;

                    "Manual" )
                        echo -e "\nChoose how you want to define the parameters"
                        PS3="Choice: "
                        options=("Parameters file" "Interactive" "Default values" "Exit")

                        select op in "${options[@]}"
                        do
                          case $op in
                              "Parameters file")
                                  echo ""
                                  read -p "Please write your parameters in the Parameters.java. Press enter to continue."

                                  if [[ ! -f Parameters.java ]]; then
                                      echo "ERROR: File Parameters.java not found!"
                                      break
                                  else
                                      getMachines
                                      copyParameters
                                      compile
                                  fi

                                  break
                                  ;;
                              "Interactive")
                                      getVars
                                      writeFile
                                      copyParameters
                                      compile
                                  break
                                  ;;
                              "Default values")
                                      echo -e "\nThe default values are:"
                                      echo "  Registry:"
                                      echo "   -host: l040101-ws01.ua.pt"
                                      echo "   -port: 22460"
                                      echo "   -server registry port: 22467"
                                      echo "  General Repository Information:"
                                      echo "   -host: l040101-ws02.ua.pt"
                                      echo "   -port: 22466"
                                      echo "  Lounge:"
                                      echo "   -host: l040101-ws03.ua.pt"
                                      echo "   -port: 22461"
                                      echo "  Park:"
                                      echo "   -host: l040101-ws04.ua.pt"
                                      echo "   -port: 22463"
                                      echo "  Outside World:"
                                      echo "   -host: l040101-ws05.ua.pt"
                                      echo "   -port: 22464"
                                      echo "  Repair Area:"
                                      echo "   -host: l040101-ws06.ua.pt"
                                      echo "   -port: 22462"
                                      echo "  Supplier Site:"
                                      echo "   -host: l040101-ws07.ua.pt"
                                      echo "   -port: 22465"
                                      echo "  Manager:"
                                      echo "   -host: l040101-ws08.ua.pt"
                                      echo "  Mechanic:"
                                      echo "   -host: l040101-ws09.ua.pt"
                                      echo "  Customer:"
                                      echo "   -host: l040101-ws10.ua.pt"
                                      echo "  Number of Customers: 30"
                                      echo "  Number of Mechanics: 2"
                                      echo "  Number of Replacement Cars: 3"
                                      echo "  Number of Car Parts Types: 3"
                                      echo "  Car Parts in stock: 0 0 0"
                                      echo "  Maximum number of Car Parts: 1 1 1"
                                      echo "  Log file name: log.txt"

                                      echo -e "\nDo you still want to continue?"
                                      PS3="Choice: "
                                      options=("Yes" "No")

                                      select op in "${options[@]}"
                                      do
                                          case $op in
                                              "Yes")
                                                  defaultValues
                                                  writeFile
                                                  copyParameters
                                                  compile

                                                  break
                                                  ;;

                                              "No")
                                                  break
                                                  ;;

                                              *)
                                                  echo "Invalid option $REPLY"
                                                  break
                                                  ;;
                                          esac
                                      done
                                  break
                                  ;;
                              "Exit")
                                  exit 0
                                  break
                                  ;;
                              *)
                                  echo "Invalid option $REPLY"
                                  ;;
                          esac
                        done
                        break
                        ;;

                    "Exit")
                        exit 0
                        break
                        ;;

                    *)
                        echo "Invalid option $REPLY"
                        ;;
                esac
            done

            break
            ;;
      "No")
            break
            ;;
      *)
            echo "Invalid option $REPLY"
            ;;
  esac
done

if ! $compiled
then
    canConnect
fi

echo "Checking if executable code exists in machines..."
checkMachines

echo "Initiating Registry"
initReg

sleep 2

echo "Initiating General Repository Information"
initServer ${GRIHOST} gri

sleep 2

echo "Initiating Outside World"
initServer ${OWHOST} ow

sleep 2

echo "Initiating Park"
initServer ${PARKHOST} park

sleep 2

echo "Initiating Repair Area"
initServer ${RAHOST} ra

sleep 2

echo "Initiating Supplier Site"
initServer ${SSHOST} ss

sleep 2

echo "Initiating Lounge"
initServer ${LOUNGEHOST} lounge

sleep 2

echo "Initiating Manager"
initClient ${MANAGERHOST} manager

sleep 2

echo "Initiating Mechanic"
initClient ${MECHANICHOST} mechanic

sleep 2

echo "Initiating Customer"
initClient ${CUSTOMERHOST} customer

echo "Get the log file?"
PS3="Choice: "
options=("Yes" "No")

select op in "${options[@]}"
do
  case $op in
      "Yes")
            getLog
            break
            ;;
      "No")
            break
            ;;
      *)
            echo "Invalid option $REPLY"
            ;;
  esac
done

echo "Clean compiled code from the machines?"
PS3="Choice: "
options=("Yes" "No")

select op in "${options[@]}"
do
  case $op in
      "Yes")
            echo "Cleaning the machines..."
            cleanMachines ${REGISTRYHOST} Register registry
            cleanMachines ${GRIHOST} GeneralRepInformation gri
            cleanMachines ${OWHOST} OutsideWorld ow
            cleanMachines ${PARKHOST} Park park
            cleanMachines ${RAHOST} RepairArea ra
            cleanMachines ${SSHOST} SupplierSite ss
            cleanMachines ${LOUNGEHOST} Lounge lounge
            cleanMachines ${MANAGERHOST} Manager manager
            cleanMachines ${MECHANICHOST} Mechanic mechanic
            cleanMachines ${CUSTOMERHOST} Customer customer
            break
            ;;
      "No")
            break
            ;;
      *)
            echo "Invalid option $REPLY"
            ;;
  esac
done

echo "Remote deployment complete!"
