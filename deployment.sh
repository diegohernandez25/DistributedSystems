#!/bin/bash

echo -e "Starting deployment..."

echo -e "\nChoose local or remote deployment"
PS3="Choice: "
options=("Local deployment" "Remote deployment")

select opt in "${options[@]}"
do
    case $opt in
        "Local deployment")
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
                        # TODO
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

#echo -e "\n${bold}* Copiar parâmetros de simulação *${normal}"
#cp SimulationParameters/remote_SimulationParameters.java BettingCenter/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java Broker/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java ControlCenter/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java Horses/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java Logger/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java Paddock/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java RacingTrack/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java Spectators/src/main/java/MainPackage/SimulationParameters.java
#cp SimulationParameters/remote_SimulationParameters.java Stable/src/main/java/MainPackage/SimulationParameters.java


###

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
