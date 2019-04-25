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

# ENTITIES PORTS
GRIPORT=''
LOUNGEPORT=''
PARKPORT=''
OWPORT=''
RAPORT=''
SSPORT=''
############# END OF CONSTANTS ############

########### FUNCTIONS DEFINITION ##########
function writeLocalDefault {
  > test.java
  echo "test" >> test.java
}

function writeLocalInteractive {
  > test.java
  echo "test" >> test.java
}

function writeRemoteDefault {
  > test.java
  echo "test" >> test.java
}

function writeRemoteInteractive {
  > test.java
  echo "test" >> test.java
}

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
      # Get General Repository Information port
      if [[ $line == *"griPort"* ]]; then
        IFS='= ' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            GRIPORT=${i::-1}
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
      # Get Lounge port
      if [[ $line == *"loungePort"* ]]; then
        IFS='= ' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            LOUNGEPORT=${i::-1}
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
      # Get Park port
      if [[ $line == *"parkPort"* ]]; then
        IFS='= ' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            PARKPORT=${i::-1}
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
      # Get Outside World port
      if [[ $line == *"owPort"* ]]; then
        IFS='= ' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            OWPORT=${i::-1}
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
      # Get Repair Area port
      if [[ $line == *"raPort"* ]]; then
        IFS='= ' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            RAPORT=${i::-1}
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
      # Get Supplier Site port
      if [[ $line == *"ssPort"* ]]; then
        IFS='= ' read -ra MACHINE <<< "$line"
        for i in "${MACHINE[@]}"; do
          if [[ $i == *"224"* ]]; then
            SSPORT=${i::-1}
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
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${LOUNGEHOST} << EOF
    cd Lounge/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Lounge ${SUCCESS}compiled${NORMAL}"
  # Park
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${PARKHOST} << EOF
    cd Park/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Park ${SUCCESS}compiled${NORMAL}"
  # Outside World
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${OWHOST} << EOF
    cd OutsideWorld/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Outside World ${SUCCESS}compiled${NORMAL}"
  # Repair Area
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${RAHOST} << EOF
    cd RepairArea/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Repair Area ${SUCCESS}compiled${NORMAL}"
  # Supplier Site
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${SSHOST} << EOF
    cd SupplierSite/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Supplier Site ${SUCCESS}compiled${NORMAL}"
  # Manager
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${MANAGERHOST} << EOF
    cd Manager/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Manager ${SUCCESS}compiled${NORMAL}"
  # Mechanic
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${MECHANICHOST} << EOF
    cd Mechanic/
    find . -name "*.java" > temp.txt
  	javac @temp.txt
  	rm temp.txt
EOF
  echo -e "Mechanic ${SUCCESS}compiled${NORMAL}"
  # Customer
  sshpass -p ${PASSWORD} ssh -t -o StrictHostKeyChecking=no sd0406@${CUSTOMERHOST} << EOF
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
  cd Mechanic/src/
  java Main &
  cd ../..
  echo -e "Mechanic ${SUCCESS}running${NORMAL}"
  cd Customer/src/
  java Main &
  cd ../..
  echo -e "Customer ${SUCCESS}running${NORMAL}"
}

function executeCodeRemote {
  echo -e "\n${SYSTEM}Executing the code...${NORMAL}\n"

  # General Repository Information
  sshpass -p ${PASSWORD} ssh  -o StrictHostKeyChecking=no sd0406@${GRIHOST} << EOF
    cd GeneralRepInformation/src/
    nohup java Main &
EOF
  echo -e "General Repository Information ${SUCCESS}running${NORMAL}"
  # Lounge
  sshpass -p ${PASSWORD} ssh  -o StrictHostKeyChecking=no sd0406@${LOUNGEHOST} << EOF
    cd Lounge/src/
    nohup java Main &
EOF
  echo -e "Lounge ${SUCCESS}running${NORMAL}"
  # Park
  sshpass -p ${PASSWORD} ssh  -o StrictHostKeyChecking=no sd0406@${PARKHOST} << EOF
    cd Park/src/
    nohup java Main &
EOF
  echo -e "Park ${SUCCESS}running${NORMAL}"
  # Outside World
  sshpass -p ${PASSWORD} ssh  -o StrictHostKeyChecking=no sd0406@${OWHOST} << EOF
    cd OutsideWorld/src/
    nohup java Main &
EOF
  echo -e "Outside World ${SUCCESS}running${NORMAL}"
  # Repair Area
  sshpass -p ${PASSWORD} ssh  -o StrictHostKeyChecking=no sd0406@${RAHOST} << EOF
    cd RepairArea/src/
    nohup java Main &
EOF
  echo -e "Repair Area ${SUCCESS}running${NORMAL}"
  # Supplier Site
  sshpass -p ${PASSWORD} ssh  -o StrictHostKeyChecking=no sd0406@${SSHOST} << EOF
    cd SupplierSite/src/
    nohup java Main &
EOF
  echo -e "Supplier Site ${SUCCESS}running${NORMAL}"
  # Manager
  sshpass -p ${PASSWORD} ssh  -o StrictHostKeyChecking=no sd0406@${MANAGERHOST} << EOF
    cd Manager/src/
    nohup java Main &
EOF
  echo -e "Manager ${SUCCESS}running${NORMAL}"
  # Mechanic
  sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no sd0406@${MECHANICHOST} << EOF
    cd Mechanic/src/
    nohup java Main &
EOF
  echo -e "Mechanic ${SUCCESS}running${NORMAL}"
  # Customer
  sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no sd0406@${CUSTOMERHOST} << EOF
    cd Customer/src/
    nohup java Main &
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
############# END OF FUNCTIONS ############

# TODO: back/exit; get log file

echo -e "\n${WARNING}WARNING: Please execute as super user and make sure you have sshpass installed!${NORMAL}"

echo -e "\n${SYSTEM}Starting deployment...${NORMAL}"

echo -e "\nChoose local or remote deployment"
PS3="Choice: "
options=("Local deployment" "Remote deployment")

select opt in "${options[@]}"
do
    case $opt in
        "Local deployment")
            ########## LOCAL DEPLOYMENT ##########
            echo -e "\nChoose how you want to define the parameters"
            PS3="Choice: "
            options=("Parameters file" "Interactive" "Default values")

            select op in "${options[@]}"
            do
                case $op in
                    "Parameters file")
                        ########## PARAMETERS FILE ##########
                        echo -e "\n${WARNING}WARNING: Please write your parameters in the Parameters.java class!${NORMAL}\n"
                        echo "Continue?"
                        PS3="Choice: "
                        options=("Yes" "No")

                        select op in "${options[@]}"
                        do
                            case $op in
                                "Yes")
                                    if [ ! -f Parameters.java ]; then
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
                        #TODO
                        break
                        ;;

                    "Default values")
                        ############## DEFAULT ##############
                        echo -e "\nThe default values are:"
                        echo "  Lounge:"
                        echo "   -host: localhost"
                        echo "   -port: 22460"
                        echo "  Park:"
                        echo "   -host: localhost"
                        echo "   -port: 22461"
                        echo "  Outside World:"
                        echo "   -host: localhost"
                        echo "   -port: 22462"
                        echo "  Repair Area:"
                        echo "   -host: localhost"
                        echo "   -port: 22463"
                        echo "  Supplier Site:"
                        echo "   -host: localhost"
                        echo "   -port: 22464"
                        echo "  General Repository Information:"
                        echo "   -host: localhost"
                        echo "   -port: 22465"
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
                                    #TODO WRITE DEFAULT ON PARAMETERS.JAVA, COPY AND CONTINUE
                                    break
                                    ;;

                                "No")
                                    break
                                    ;;
                            esac
                        done

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
            options=("Parameters file" "Interactive" "Default values")

            select op in "${options[@]}"
            do
                case $op in
                    "Parameters file")
                      ########## PARAMETERS FILE ##########
                      echo -e "\n${WARNING}WARNING: Please write your parameters in the Parameters.java class!${NORMAL}\n"
                      echo "Continue?"
                      PS3="Choice: "
                      options=("Yes" "No")

                      select op in "${options[@]}"
                      do
                          case $op in
                              "Yes")
                                  if [ ! -f Parameters.java ]; then
                                      echo -e "\n${ERROR}ERROR: File Parameters.java not found!${NORMAL}"
                                      break
                                  else
                                      copyFile
                                      getMachines
                                      deleteMachine
                                      copyMachine
                                      compileCodeRemote
                                      executeCodeRemote

                                      echo -e "\n${SYSTEM}Executing...${NORMAL}"
                                      wait
                                      echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"

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
                        #TODO
                        break
                        ;;

                    "Default values")
                        echo -e "\nThe default values are: "
                        #TODO
                        echo -e "\nDo you still want to continue?"
                        PS3="Choice: "
                        options=("Yes" "No")

                        select op in "${options[@]}"
                        do
                            case $op in
                                "Yes")
                                    #TODO
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

                    *)
                        echo "Invalid option $REPLY"
                        ;;
                esac
            done

            break
            ;;

        *)
            echo "Invalid option $REPLY"
            ;;

    esac
done
