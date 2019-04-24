#!/bin/bash

########### CONSTANTS DEFINITION ##########
# COLORS
NORMAL='\033[0m'
SYSTEM='\033[0;34m'
WARNING='\033[1;33m'
ERROR='\033[0;31m'
SUCCESS='\033[0;32m'

########### FUNCTIONS DEFINITION ##########
function writeLocalDefault {
  > test.java
  echo "test1" >> test.java
  echo "test2" >> test.java
}

function writeLocalInteractive {
  > test.java
  echo "test1" >> test.java
  echo "test2" >> test.java
}

function writeRemoteDefault {
  > test.java
  echo "test1" >> test.java
  echo "test2" >> test.java
}

function writeRemoteInteractive {
  > test.java
  echo "test1" >> test.java
  echo "test2" >> test.java
}

function copyFile {
  echo -e "\n${SYSTEM}Copying Parameters.java file...${NORMAL}"
  cp Parameters.java Manager/src/Main/Parameters.java
  cp Parameters.java Mechanic/src/Main/Parameters.java
  cp Parameters.java Customer/src/Main/Parameters.java
  cp Parameters.java Lounge/src/Main/Parameters.java
  cp Parameters.java OutsideWorld/src/Main/Parameters.java
  cp Parameters.java Park/src/Main/Parameters.java
  cp Parameters.java RepairArea/src/Main/Parameters.java
  cp Parameters.java SupplierSite/src/Main/Parameters.java
  cp Parameters.java GeneralRepInformation/src/Main/Parameters.java
  echo -e "\n${SUCCESS}Parameters.java copied${NORMAL}"
}

function compileCode {
  echo -e "\n${SYSTEM}Compiling the code...${NORMAL}\n"

  cd GeneralRepInformation/
  javac $(find . -name '*.java')
  cd ..
  echo -e "General Repository Information ${SUCCESS}done${NORMAL}"
  cd Lounge/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Lounge ${SUCCESS}done${NORMAL}"
  cd OutsideWorld/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Outside World ${SUCCESS}done${NORMAL}"
  cd Park/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Park ${SUCCESS}done${NORMAL}"
  cd RepairArea/
  javac $(find . -name '*.java')
  cd ..
  echo -e "RepairArea ${SUCCESS}done${NORMAL}"
  cd SupplierSite/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Supplier Site ${SUCCESS}done${NORMAL}"
  cd Manager/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Manager ${SUCCESS}done${NORMAL}"
  cd Mechanic/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Mechanic ${SUCCESS}done${NORMAL}"
  cd Customer/
  javac $(find . -name '*.java')
  cd ..
  echo -e "Customer ${SUCCESS}done${NORMAL}"
}

function executeCode {
  echo -e "\n${SYSTEM}Executing the code...${NORMAL}\n"
  cd GeneralRepInformation/src/
  java Main &
  cd ../..
  echo -e "General Repository Information ${SUCCESS}done${NORMAL}"
  cd Lounge/src/
  java Main &
  cd ../..
  echo -e "Lounge ${SUCCESS}done${NORMAL}"
  cd OutsideWorld/src/
  java Main &
  cd ../..
  echo -e "Outside World ${SUCCESS}done${NORMAL}"
  cd Park/src/
  java Main &
  cd ../..
  echo -e "Park ${SUCCESS}done${NORMAL}"
  cd RepairArea/src/
  java Main &
  cd ../..
  echo -e "RepairArea ${SUCCESS}done${NORMAL}"
  cd SupplierSite/src/
  java Main &
  cd ../..
  echo -e "Supplier Site ${SUCCESS}done${NORMAL}"

  sleep 1

  cd Manager/src/
  java Main &
  cd ../..
  echo -e "Manager ${SUCCESS}done${NORMAL}"
  cd Mechanic/src/
  java Main &
  cd ../..
  echo -e "Mechanic ${SUCCESS}done${NORMAL}"
  cd Customer/src/
  java Main &
  cd ../..
  echo -e "Customer ${SUCCESS}done${NORMAL}"

}
############# END OF FUNCTIONS ############
echo -e "\n${WARNING}WARNING: Please execute as super user${NORMAL}"

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
                                        compileCode
                                        executeCode

                                        echo -e "\n${SYSTEM}Executing...${NORMAL}"
                                        wait
                                        echo -e "\n${SUCCESS}Finished successfully!${NORMAL}"
                                    fi
                                    break
                                    ;;

                                "No")
                                    break
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
            echo -e "\nChoose how you want to define the parameters"
            PS3="Choice: "
            options=("Parameters file" "Interactive" "Default values")

            select op in "${options[@]}"
            do
                case $op in
                    "Parameters file")
                        #TODO
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



#echo -e "\n${bold}* Cópia do código a executar em cada nó *${normal}"

#echo -e "\n${bold}->${normal} A mover Logger para a máquina ${bold}1${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws01.ua.pt << !
#	put -r Logger/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover BettingCenter para a máquina ${bold}2${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws02.ua.pt << !
#	put -r BettingCenter/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover ControlCenter para a máquina ${bold}3${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws03.ua.pt << !
#	put -r ControlCenter/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover Paddock para a máquina ${bold}4${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws04.ua.pt << !
#	put -r Paddock/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover RacingTrack para a máquina ${bold}5${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws05.ua.pt << !
#	put -r RacingTrack/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover Stable para a máquina ${bold}6${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws06.ua.pt << !
#	put -r Stable/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover Broker para a máquina ${bold}7${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws07.ua.pt << !
#	put -r Broker/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover Horses para a máquina ${bold}8${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws08.ua.pt << !
#	put -r Horses/
#	bye
#!

#echo -e "\n${bold}->${normal} A mover Spectators para a máquina ${bold}9${normal}"
#sshpass -e sftp -o StrictHostKeyChecking=no sd0403@l040101-ws09.ua.pt << !
#	put -r Spectators/
#	bye
#!


###

#echo -e "\n${bold}* Compilação do código em cada nó *${normal}"

#echo -e "\n${bold}->${normal} A compilar Logger na máquina ${bold}1${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws01.ua.pt << EOF
#	cd Logger/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar BettingCenter na máquina ${bold}2${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws02.ua.pt << EOF
#	cd BettingCenter/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar ControlCenter na máquina ${bold}3${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws03.ua.pt << EOF
#	cd ControlCenter/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar Paddock na máquina ${bold}4${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws04.ua.pt << EOF
#	cd Paddock/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar RacingTrack na máquina ${bold}5${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws05.ua.pt << EOF
#	cd RacingTrack/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar Stable na máquina ${bold}6${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws06.ua.pt << EOF
#	cd Stable/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar Broker na máquina ${bold}7${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws07.ua.pt << EOF
#	cd Broker/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar Horses na máquina ${bold}8${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws08.ua.pt << EOF
#	cd Horses/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

#echo -e "\n${bold}->${normal} A compilar Spectators na máquina ${bold}9${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws09.ua.pt << EOF
#	cd Spectators/
#	find . -name "*.java" > files.txt
#	javac @files.txt
#	rm files.txt
#EOF

###

#echo -e "\n${bold}* Execução do código em cada nó *${normal}"

#echo -e "\n${bold}->${normal} A executar Logger na máquina ${bold}1${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws01.ua.pt << EOF
#	cd Logger/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/Logger/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

#echo -e "\n${bold}->${normal} A executar BettingCenter na máquina ${bold}2${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws02.ua.pt << EOF
#	cd BettingCenter/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/BettingCenter/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

#echo -e "\n${bold}->${normal} A executar ControlCenter na máquina ${bold}3${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws03.ua.pt << EOF
#	cd ControlCenter/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/ControlCenter/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

#echo -e "\n${bold}->${normal} A executar Paddock na máquina ${bold}4${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws04.ua.pt << EOF
#	cd Paddock/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/Paddock/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

#echo -e "\n${bold}->${normal} A executar RacingTrack na máquina ${bold}5${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws05.ua.pt << EOF
#	cd RacingTrack/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/RacingTrack/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

#echo -e "\n${bold}->${normal} A executar Stable na máquina ${bold}6${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws06.ua.pt << EOF
#	cd Stable/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/Stable/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

# Wait for the shared regions to be launched before lanching the intervening enities

#sleep 1

#echo -e "\n${bold}->${normal} A executar Broker na máquina ${bold}7${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws07.ua.pt << EOF
#	cd Broker/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/Broker/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

#echo -e "\n${bold}->${normal} A executar Horses na máquina ${bold}8${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws08.ua.pt << EOF
#	cd Horses/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/Horses/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF

#echo -e "\n${bold}->${normal} A executar Spectators na máquina ${bold}9${normal}"
#sshpass -e ssh -o StrictHostKeyChecking=no sd0403@l040101-ws09.ua.pt << EOF
#	cd Spectators/src/main/java/MainPackage/
#	nohup java -cp /home/sd0403/Spectators/src/main/java MainPackage.MainProgram > /dev/null 2>&1 &
#EOF
