#!/bin/bash

########### CONSTANTS DEFINITION ##########
# PASSWORD
PASSWORD='dievalint'

# COLORS
NORMAL='\033[0m'
SYSTEM='\033[0;34m'
WARNING='\033[1;33m'
ERROR='\033[0;31m'
SUCCESS='\033[0;32m'

# ENTITIES HOSTS
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
GRIPORT=''
LOUNGEPORT=''
PARKPORT=''
OWPORT=''
RAPORT=''
SSPORT=''

#VARIABLES VALUES
NUM_CUSTOMERS=''
NUM_MECHANICS=''
NUM_REPLACEMENT=''
NUM_CAR_TYPES=''
CAR_PARTS=''
MAX_CAR_PARTS=''
############# END OF CONSTANTS ############

########### FUNCTIONS DEFINITION ##########
function getMachines {
  while IFS='' read -r line || [[ -n "$line" ]]; do
      # Get General Repository Information machine
      if [[ $line == *"griHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            GRIHOST=$i
          fi
        done
      fi

      # Get Lounge machine
      if [[ $line == *"loungeHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            LOUNGEHOST=$i
          fi
        done
      fi

      # Get Park machine
      if [[ $line == *"parkHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            PARKHOST=$i
          fi
        done
      fi

      # Get Outside World machine
      if [[ $line == *"owHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            OWHOST=$i
          fi
        done
      fi

      # Get Repair Area machine
      if [[ $line == *"raHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            RAHOST=$i
          fi
        done
      fi

      # Get Supplier Site machine
      if [[ $line == *"ssHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            SSHOST=$i
          fi
        done
      fi

      # Get Manager machine
      if [[ $line == *"managerHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            MANAGERHOST=$i
          fi
        done
      fi

      # Get Mechanic machine
      if [[ $line == *"mechanicHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            MECHANICHOST=$i
          fi
        done
      fi

      # Get Customer machine
      if [[ $line == *"customerHost"* ]]; then
        IFS='"' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"l040101"* ]]; then
            CUSTOMERHOST=$i
          fi
        done
      fi
  done < Parameters.java
}

function writeFile {
  echo -e "\n${SYSTEM}Generating default Parameters.java file...${NORMAL}"

  > Parameters.java
  echo "package Main;" >> Parameters.java
  echo "" >> Parameters.java
  echo "/**" >> Parameters.java
  echo "* Parameters for the deployment of Assignment 2 on local or remote machines." >> Parameters.java
  echo "*/" >> Parameters.java
  echo "public class Parameters {" >> Parameters.java
  echo "  /**" >> Parameters.java
  echo "  * General Repository Information host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String griHost = \"${GRIHOST}\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  /**" >> Parameters.java
  echo "  * General Repository Information port number" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int griPort = ${GRIPORT};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Lounge host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String loungeHost = \"${LOUNGEHOST}\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  /**" >> Parameters.java
  echo "  * Lounge port number" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int loungePort = ${LOUNGEPORT};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Park host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String parkHost = \"${PARKHOST}\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  /**" >> Parameters.java
  echo "  * Park port number" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int parkPort = ${PARKPORT};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Outside World host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String owHost = \"${OWHOST}\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  /**" >> Parameters.java
  echo "  * Outside World port number" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int owPort = ${OWPORT};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Repair Area host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String raHost = \"${RAHOST}\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  /**" >> Parameters.java
  echo "  * Repair Area port number" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int raPort = ${RAPORT};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Supplier Site host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String ssHost = \"${SSHOST}\";" >> Parameters.java
  echo "" >> Parameters.java
  echo "  /**" >> Parameters.java
  echo "  * Supplier Site port number" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int ssPort = ${SSPORT};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Manager host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String managerHost = \"${MANAGERHOST}\";" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Mechanic host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String mechanicHost = \"${MECHANICHOST}\";" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Customer host name" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static String customerHost = \"${CUSTOMERHOST}\";" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Total number of Customers" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int numCustomers = ${NUM_CUSTOMERS};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Total number of Mechanics" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int numMechanics = ${NUM_MECHANICS};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Total number of Replacement Cars" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int numReplacementCars = ${NUM_REPLACEMENT};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Total number of Car Parts types" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int numCarTypes = ${NUM_CAR_TYPES};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Car parts" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int[] carParts = ${CAR_PARTS};" >> Parameters.java
  echo "" >> Parameters.java

  echo "  /**" >> Parameters.java
  echo "  * Maximum number of storage for each Car Part" >> Parameters.java
  echo "  */" >> Parameters.java
  echo "  public final static int[] maxCarParts = ${MAX_CAR_PARTS};" >> Parameters.java
  echo "}" >> Parameters.java

  echo -e "\nDefault Parameters.java file ${SUCCESS}generated${NORMAL}"
}

function copyFile {
  echo -e "\n${SYSTEM}Copying Parameters.java file...${NORMAL}"
  cp Parameters.java Manager/src/Main/Parameters.java
  cp Parameters.java Mechanic/src/Main/Parameters.java
  cp Parameters.java Customer/src/Main/Parameters.java
  cp Parameters.java Lounge/src/Main/Parameters.java
  cp Parameters.java Park/src/Main/Parameters.java
  cp Parameters.java OutsideWorld/src/Main/Parameters.java
  cp Parameters.java RepairArea/src/Main/Parameters.java
  cp Parameters.java SupplierSite/src/Main/Parameters.java
  cp Parameters.java GeneralRepInformation/src/Main/Parameters.java
  echo -e "\n${SUCCESS}Parameters.java copied${NORMAL}"
}

function compileCodeLocal {
  echo -e "\n${SYSTEM}Removing previous compilations...${NORMAL}\n"

  cd GeneralRepInformation/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "General Repository Information ${SUCCESS}cleaned${NORMAL}"
  cd Lounge/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "Lounge ${SUCCESS}cleaned${NORMAL}"
  cd OutsideWorld/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "Park ${SUCCESS}cleaned${NORMAL}"
  cd RepairArea/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "Outside World ${SUCCESS}cleaned${NORMAL}"
  cd Park/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "RepairArea ${SUCCESS}cleaned${NORMAL}"
  cd SupplierSite/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "Supplier Site ${SUCCESS}cleaned${NORMAL}"
  cd Manager/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "Manager ${SUCCESS}cleaned${NORMAL}"
  cd Mechanic/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "Mechanic ${SUCCESS}cleaned${NORMAL}"
  cd Customer/
  find src -type f -name "*.class" -delete
  cd ..
  echo -e "Customer ${SUCCESS}cleaned${NORMAL}"

  echo -e "\n${SYSTEM}Compiling the code...${NORMAL}\n"

  cd GeneralRepInformation/
  javac $(find . -name '*.java')
  cd ..
  echo -e "General Repository Information ${SUCCESS}compiled${NORMAL}"
  cd Lounge/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Lounge ${SUCCESS}compiled${NORMAL}"
  cd OutsideWorld/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Park ${SUCCESS}compiled${NORMAL}"
  cd RepairArea/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Outside World ${SUCCESS}compiled${NORMAL}"
  cd Park/
  javac $(find . -name '*.java')
  cd ..
  echo -e "RepairArea ${SUCCESS}compiled${NORMAL}"
  cd SupplierSite/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Supplier Site ${SUCCESS}compiled${NORMAL}"
  cd Manager/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Manager ${SUCCESS}compiled${NORMAL}"
  cd Mechanic/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Mechanic ${SUCCESS}compiled${NORMAL}"
  cd Customer/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Customer ${SUCCESS}compiled${NORMAL}"
}

function compileCodeRemote {
  echo -e "\n${SYSTEM}Compiling the code...${NORMAL}\n"

  # General Repository Information
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${GRIHOST} << EOF
    cd GeneralRepInformation/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "General Repository Information ${SUCCESS}compiled${NORMAL}"
  # Lounge
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${LOUNGEHOST} << EOF
    cd Lounge/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Lounge ${SUCCESS}compiled${NORMAL}"
  # Park
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${PARKHOST} << EOF
    cd Park/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Park ${SUCCESS}compiled${NORMAL}"
  # Outside World
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${OWHOST} << EOF
    cd OutsideWorld/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Outside World ${SUCCESS}compiled${NORMAL}"
  # Repair Area
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${RAHOST} << EOF
    cd RepairArea/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Repair Area ${SUCCESS}compiled${NORMAL}"
  # Supplier Site
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${SSHOST} << EOF
    cd SupplierSite/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Supplier Site ${SUCCESS}compiled${NORMAL}"
  # Manager
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${MANAGERHOST} << EOF
    cd Manager/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Manager ${SUCCESS}compiled${NORMAL}"
  # Mechanic
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${MECHANICHOST} << EOF
    cd Mechanic/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Mechanic ${SUCCESS}compiled${NORMAL}"
  # Customer
  sshpass -p ${PASSWORD} ssh -T -o StrictHostKeyChecking=no sd0406@${CUSTOMERHOST} << EOF
    cd Customer/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Customer ${SUCCESS}compiled${NORMAL}"
}

function executeCodeLocal {
  echo -e "\n${SYSTEM}Executing the code...${NORMAL}\n"
  cd GeneralRepInformation/src/
  java Main &
  cd ../..
  echo -e "General Repository Information ${SUCCESS}running${NORMAL}"

  sleep 1

  cd Lounge/src/
  java Main &
  cd ../..
  echo -e "Lounge ${SUCCESS}running${NORMAL}"
  cd OutsideWorld/src/
  java Main &
  cd ../..
  echo -e "Park ${SUCCESS}running${NORMAL}"
  cd RepairArea/src/
  java Main &
  cd ../..
  echo -e "Outside World ${SUCCESS}running${NORMAL}"
  cd Park/src/
  java Main &
  cd ../..
  echo -e "RepairArea ${SUCCESS}running${NORMAL}"
  cd SupplierSite/src/
  java Main &
  cd ../..
  echo -e "Supplier Site ${SUCCESS}running${NORMAL}"

  sleep 1

  cd Manager/src/
  java Main &
  cd ../..
  echo -e "Manager ${SUCCESS}running${NORMAL}"

  sleep 1

  cd Mechanic/src/
  java Main &
  cd ../..
  echo -e "Mechanic ${SUCCESS}running${NORMAL}"

  sleep 1

  cd Customer/src/
  java Main &
  cd ../..
  echo -e "Customer ${SUCCESS}running${NORMAL}"
}

function executeCodeRemote {
  echo -e "\n${SYSTEM}Executing the code...${NORMAL}\n"

  # General Repository Information
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${GRIHOST} << EOF
    cd GeneralRepInformation/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "General Repository Information ${SUCCESS}running${NORMAL}"

  sleep 1

  # Lounge
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${LOUNGEHOST} << EOF
    cd Lounge/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "Lounge ${SUCCESS}running${NORMAL}"
  # Park
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${PARKHOST} << EOF
    cd Park/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "Park ${SUCCESS}running${NORMAL}"
  # Outside World
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${OWHOST} << EOF
    cd OutsideWorld/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "Outside World ${SUCCESS}running${NORMAL}"
  # Repair Area
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${RAHOST} << EOF
    cd RepairArea/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "Repair Area ${SUCCESS}running${NORMAL}"
  # Supplier Site
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${SSHOST} << EOF
    cd SupplierSite/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "Supplier Site ${SUCCESS}running${NORMAL}"

  sleep 1

  # Manager
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${MANAGERHOST} << EOF
    cd Manager/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "Manager ${SUCCESS}running${NORMAL}"

  sleep 1

  # Mechanic
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${MECHANICHOST} << EOF
    cd Mechanic/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar &
    exit
EOF
  echo -e "Mechanic ${SUCCESS}running${NORMAL}"

  sleep 1

  # Customer
  sshpass -p ${PASSWORD} ssh -tt -o StrictHostKeyChecking=no sd0406@${CUSTOMERHOST} << EOF
    cd Customer/src/
    rm Main.jar
    find . -name "*.class" > temp.txt
    jar -cvfe Main.jar Main @temp.txt > /dev/null
    java -jar Main.jar
    exit
EOF
  echo -e "Customer ${SUCCESS}running${NORMAL}"
}

function copyMachine {
  echo -e "\n${SYSTEM}Copying the code to the machines...${NORMAL}\n"

  # General Repository Information
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${GRIHOST} << EOF
    put -r GeneralRepInformation
    bye
EOF
  echo -e "General Repository Information ${SUCCESS}copied${NORMAL}"
  # Lounge
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${LOUNGEHOST} << EOF
    put -r Lounge
    bye
EOF
  echo -e "Lounge ${SUCCESS}copied${NORMAL}"
  # Park
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${PARKHOST} << EOF
    put -r Park
    bye
EOF
  echo -e "Park ${SUCCESS}copied${NORMAL}"
  # Outside World
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${OWHOST} << EOF
    put -r OutsideWorld
    bye
EOF
  echo -e "Outside World ${SUCCESS}copied${NORMAL}"
  # Repair Area
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${RAHOST} << EOF
    put -r RepairArea
    bye
EOF
  echo -e "Repair Area ${SUCCESS}copied${NORMAL}"
  # Supplier Site
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${SSHOST} << EOF
    put -r SupplierSite
    bye
EOF
  echo -e "Supplier Site ${SUCCESS}copied${NORMAL}"
  # Manager
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${MANAGERHOST} << EOF
    put -r Manager
    bye
EOF
  echo -e "Manager ${SUCCESS}copied${NORMAL}"
  # Mechanic
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${MECHANICHOST} << EOF
    put -r Mechanic
    bye
EOF
  echo -e "Mechanic ${SUCCESS}copied${NORMAL}"
  # Customer
  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${CUSTOMERHOST} << EOF
    put -r Customer
    bye
EOF
  echo -e "Customer ${SUCCESS}copied${NORMAL}"
}

function getLog {
  echo -e "\n${SYSTEM}Downloading log.txt...${NORMAL}\n"

  sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@${GRIHOST} << EOF
    cd GeneralRepInformation/
    get -r log.txt
    bye
EOF

  echo -e "Log ${SUCCESS}obtained${NORMAL}"
}

function deleteMachine {
  echo -e "\n${SYSTEM}Cleaning the code from the machines...${NORMAL}\n"

  # General Repository Information
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${GRIHOST} "rm -rf GeneralRepInformation"
  echo -e "General Repository Information ${SUCCESS}removed${NORMAL}"
  # Lounge
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${LOUNGEHOST} "rm -rf Lounge"
  echo -e "Lounge ${SUCCESS}removed${NORMAL}"
  # Park
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${PARKHOST} "rm -rf Park"
  echo -e "Park ${SUCCESS}removed${NORMAL}"
  # Outside World
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${OWHOST} "rm -rf OutsideWorld"
  echo -e "Outside World ${SUCCESS}removed${NORMAL}"
  # Repair Area
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${RAHOST} "rm -rf RepairArea"
  echo -e "Repair Area ${SUCCESS}removed${NORMAL}"
  # Supplier Site
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${SSHOST} "rm -rf SupplierSite"
  echo -e "Supplier Site ${SUCCESS}removed${NORMAL}"
  # Manager
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${MANAGERHOST} "rm -rf Manager"
  echo -e "Manager ${SUCCESS}removed${NORMAL}"
  # Mechanic
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${MECHANICHOST} "rm -rf Mechanic"
  echo -e "Mechanic ${SUCCESS}removed${NORMAL}"
  # Customer
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${CUSTOMERHOST} "rm -rf Customer"
  echo -e "Customer ${SUCCESS}removed${NORMAL}"
}

function getVarsLocal {
  echo -e "\nEnter the values for each parameter:"

  GRIHOST="localhost"
  read -p "  General Repository Information port number: " GRIPORT
  LOUNGEHOST="localhost"
  read -p "  Lounge port number: " LOUNGEPORT
  PARKHOST="localhost"
  read -p "  Park port number: " PARKPORT
  OWHOST="localhost"
  read -p "  Outside World port number: " OWPORT
  RAHOST="localhost"
  read -p "  Repair Area port number: " RAPORT
  SSHOST="localhost"
  read -p "  Supplier Site port number: " SSPORT
  MANAGERHOST="localhost"
  MECHANICHOST="localhost"
  CUSTOMERHOST="localhost"
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
}

function getVarsRemote {
  echo -e "\nEnter the values for each parameter:"

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
}
############# END OF FUNCTIONS ############

echo -e "\n${WARNING}WARNING: Please execute as super user and make sure you have sshpass installed!${NORMAL}"

echo -e "\n${SYSTEM}Starting deployment...${NORMAL}"

while true
do
  echo -e "\nChoose local or remote deployment"
  PS3="Choice: "
  options=("Local deployment" "Remote deployment" "Exit")

  select opt in "${options[@]}"
  do
      case $opt in
          "Local deployment")
              ########## LOCAL DEPLOYMENT ##########
              echo -e "\nChoose how you want to define the parameters"
              PS3="Choice: "
              options=("Parameters file" "Interactive" "Default values" "Back")

              select op in "${options[@]}"
              do
                  case $op in
                      "Parameters file")
                          ########## PARAMETERS FILE ##########
                          echo -e "\n${WARNING}WARNING: Please write your parameters in the Parameters.java file, and do not change the variables names!${NORMAL}\n"
                          echo "Continue?"
                          PS3="Choice: "
                          options=("Yes" "No")

                          select op in "${options[@]}"
                          do
                              case $op in
                                  "Yes")
                                      if [[ ! -f Parameters.java ]]; then
                                          echo -e "\n${ERROR}ERROR: File Parameters.java not found!${NORMAL}"
                                          break
                                      else
                                          copyFile
                                          compileCodeLocal
                                          executeCodeLocal

                                          echo -e "\n${SYSTEM}Executing...${NORMAL}"
                                          wait
                                          echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"
                                      fi
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
                          break
                          ;;

                      "Interactive")
                          ############ INTERACTIVE ############
                          getVarsLocal
                          writeFile

                          copyFile
                          compileCodeLocal
                          executeCodeLocal

                          echo -e "\n${SYSTEM}Executing...${NORMAL}"
                          wait
                          echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"

                          break
                          ;;

                      "Default values")
                          ############## DEFAULT ##############
                          echo -e "\nThe default values are:"
                          echo "  General Repository Information:"
                          echo "   -host: localhost"
                          echo "   -port: 22460"
                          echo "  Lounge:"
                          echo "   -host: localhost"
                          echo "   -port: 22461"
                          echo "  Park:"
                          echo "   -host: localhost"
                          echo "   -port: 22462"
                          echo "  Outside World:"
                          echo "   -host: localhost"
                          echo "   -port: 22463"
                          echo "  Repair Area:"
                          echo "   -host: localhost"
                          echo "   -port: 22464"
                          echo "  Supplier Site:"
                          echo "   -host: localhost"
                          echo "   -port: 22465"
                          echo "  Manager:"
                          echo "   -host: localhost"
                          echo "  Mechanic:"
                          echo "   -host: localhost"
                          echo "  Customer:"
                          echo "   -host: localhost"
                          echo "  Number of Customers: 30"
                          echo "  Number of Mechanics: 2"
                          echo "  Number of Replacement Cars: 3"
                          echo "  Number of Car Parts Types: 3"
                          echo "  Car Parts in stock: 0 0 0"
                          echo "  Maximum number of Car Parts: 1 1 1"

                          echo -e "\nDo you still want to continue?"
                          PS3="Choice: "
                          options=("Yes" "No")

                          select op in "${options[@]}"
                          do
                              case $op in
                                  "Yes")
                                      GRIHOST="localhost"
                                      GRIPORT="22460"
                                      LOUNGEHOST="localhost"
                                      LOUNGEPORT="22461"
                                      PARKHOST="localhost"
                                      PARKPORT="22462"
                                      OWHOST="localhost"
                                      OWPORT="22463"
                                      RAHOST="localhost"
                                      RAPORT="22464"
                                      SSHOST="localhost"
                                      SSPORT="22465"
                                      MANAGERHOST="localhost"
                                      MECHANICHOST="localhost"
                                      CUSTOMERHOST="localhost"

                                      NUM_CUSTOMERS="30"
                                      NUM_MECHANICS="2"
                                      NUM_REPLACEMENT="3"
                                      NUM_CAR_TYPES="3"
                                      CAR_PARTS="{0, 0, 0}"
                                      MAX_CAR_PARTS="{1, 1, 1}"

                                      writeFile

                                      copyFile
                                      compileCodeLocal
                                      executeCodeLocal

                                      echo -e "\n${SYSTEM}Executing...${NORMAL}"
                                      wait
                                      echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"

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
                          break
                          ;;

                      "Back")
                          break
                          ;;

                      *)
                          echo "Invalid option $REPLY"
                          ;;
                  esac
              done
              break
              ;;

          "Remote deployment")
              ########## REMOTE DEPLOYMENT ##########
              echo -e "\nChoose how you want to define the parameters"
              PS3="Choice: "
              options=("Parameters file" "Interactive" "Default values" "Back")

              select op in "${options[@]}"
              do
                  case $op in
                      "Parameters file")
                        ########## PARAMETERS FILE ##########
                        echo -e "\n${WARNING}WARNING: Please write your parameters in the Parameters.java file, and do not change the variables names!${NORMAL}\n"
                        echo "Continue?"
                        PS3="Choice: "
                        options=("Yes" "No")

                        select op in "${options[@]}"
                        do
                            case $op in
                                "Yes")
                                    if [[ ! -f Parameters.java ]]; then
                                        echo -e "\n${ERROR}ERROR: File Parameters.java not found!${NORMAL}"
                                        break
                                    else
                                        getMachines

                                        if [[ $GRIHOST == *"localhost"* ]]; then
                                          echo "\n${ERROR}ERROR: File Parameters.java is likely to be run locally!${NORMAL}"
                                          break
                                        else

                                          copyFile
                                          deleteMachine
                                          copyMachine
                                          compileCodeRemote
                                          executeCodeRemote

                                          wait
                                          echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"

                                          echo -e "\nDo you want to download the log file?"
                                          PS3="Choice: "
                                          options=("Yes" "No")
                                          select op in "${options[@]}"
                                          do
                                              case $op in
                                                  "Yes")
                                                    getLog

                                                    echo -e "\nDo you want to delete the code from the machines?"
                                                    PS3="Choice: "
                                                    options=("Yes" "No")
                                                    select op in "${options[@]}"
                                                    do
                                                        case $op in
                                                            "Yes")
                                                                deleteMachine
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
                                        fi
                                    fi
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
                        break
                        ;;

                      "Interactive")
                          getVarsRemote
                          writeFile

                          copyFile
                          getMachines
                          deleteMachine
                          copyMachine
                          compileCodeRemote
                          executeCodeRemote

                          wait
                          echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"

                          echo -e "\nDo you want to download the log file?"
                          PS3="Choice: "
                          options=("Yes" "No")
                          select op in "${options[@]}"
                          do
                              case $op in
                                  "Yes")
                                    getLog

                                    echo -e "\nDo you want to delete the code from the machines?"
                                    PS3="Choice: "
                                    options=("Yes" "No")
                                    select op in "${options[@]}"
                                    do
                                        case $op in
                                            "Yes")
                                                deleteMachine
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
                      break
                      ;;

                      "Default values")
                      ############## DEFAULT ##############
                      echo -e "\nThe default values are:"
                      echo "  General Repository Information:"
                      echo "   -host: l040101-ws01.ua.pt"
                      echo "   -port: 22461"
                      echo "  Lounge:"
                      echo "   -host: l040101-ws02.ua.pt"
                      echo "   -port: 22462"
                      echo "  Park:"
                      echo "   -host: l040101-ws03.ua.pt"
                      echo "   -port: 22463"
                      echo "  Outside World:"
                      echo "   -host: l040101-ws04.ua.pt"
                      echo "   -port: 22464"
                      echo "  Repair Area:"
                      echo "   -host: l040101-ws05.ua.pt"
                      echo "   -port: 22465"
                      echo "  Supplier Site:"
                      echo "   -host: l040101-ws06.ua.pt"
                      echo "   -port: 22466"
                      echo "  Manager:"
                      echo "   -host: l040101-ws07.ua.pt"
                      echo "  Mechanic:"
                      echo "   -host: l040101-ws08.ua.pt"
                      echo "  Customer:"
                      echo "   -host: l040101-ws09.ua.pt"
                      echo "  Number of Customers: 30"
                      echo "  Number of Mechanics: 2"
                      echo "  Number of Replacement Cars: 3"
                      echo "  Number of Car Parts Types: 3"
                      echo "  Car Parts in stock: 0 0 0"
                      echo "  Maximum number of Car Parts: 1 1 1"

                      echo -e "\nDo you still want to continue?"
                      PS3="Choice: "
                      options=("Yes" "No")

                      select op in "${options[@]}"
                      do
                          case $op in
                              "Yes")
                                  GRIHOST="l040101-ws01.ua.pt"
                                  GRIPORT="22461"
                                  LOUNGEHOST="l040101-ws02.ua.pt"
                                  LOUNGEPORT="22462"
                                  PARKHOST="l040101-ws03.ua.pt"
                                  PARKPORT="22463"
                                  OWHOST="l040101-ws04.ua.pt"
                                  OWPORT="22464"
                                  RAHOST="l040101-ws05.ua.pt"
                                  RAPORT="22465"
                                  SSHOST="l040101-ws06.ua.pt"
                                  SSPORT="22466"
                                  MANAGERHOST="l040101-ws07.ua.pt"
                                  MECHANICHOST="l040101-ws08.ua.pt"
                                  CUSTOMERHOST="l040101-ws09.ua.pt"

                                  NUM_CUSTOMERS="30"
                                  NUM_MECHANICS="2"
                                  NUM_REPLACEMENT="3"
                                  NUM_CAR_TYPES="3"
                                  CAR_PARTS="{0, 0, 0}"
                                  MAX_CAR_PARTS="{1, 1, 1}"

                                  writeFile

                                  copyFile
                                  getMachines
                                  deleteMachine
                                  copyMachine
                                  compileCodeRemote
                                  executeCodeRemote

                                  wait
                                  echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"

                                  echo -e "\nDo you want to download the log file?"
                                  PS3="Choice: "
                                  options=("Yes" "No")
                                  select op in "${options[@]}"
                                  do
                                      case $op in
                                          "Yes")
                                            getLog

                                            echo -e "\nDo you want to delete the code from the machines?"
                                            PS3="Choice: "
                                            options=("Yes" "No")
                                            select op in "${options[@]}"
                                            do
                                                case $op in
                                                    "Yes")
                                                        deleteMachine
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

                      break
                      ;;

                      "Back")
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
              break 2
              ;;

          *)
              echo "Invalid option $REPLY"
              ;;

      esac
  done
done
