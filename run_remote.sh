#!/bin/bash

########### CONSTANTS DEFINITION ##########
# PASSWORD
PASSWORD='dievalint'

# ENTITIES HOSTS
REGISTRYHOST='l040101-ws01.ua.pt'
GRIHOST='l040101-ws02.ua.pt'
LOUNGEHOST='l040101-ws03.ua.pt'
PARKHOST='l040101-ws04.ua.pt'
OWHOST='l040101-ws05.ua.pt'
RAHOST='l040101-ws06.ua.pt'
SSHOST='l040101-ws07.ua.pt'
MANAGERHOST='l040101-ws08.ua.pt'
MECHANICHOST='l040101-ws09.ua.pt'
CUSTOMERHOST='l040101-ws10.ua.pt'

#ENTITIES PORTS
REGISTRYPORT='22460'
SERVERREGISTRYPORT='22467'
GRIPORT='22466'
LOUNGEPORT='22461'
PARKPORT='22463'
OWPORT='22464'
RAPORT='22462'
SSPORT='22465'
############# END OF CONSTANTS ############

########### FUNCTIONS DEFINITION ##########
function toHost {
    sshpass -p ${PASSWORD} sftp -o StrictHostKeyChecking=no sd0406@$1 << EOF
        put -r $2
        bye
EOF
}
############# END OF FUNCTIONS ############

read -p "Please run as administrator! Press enter to continue."

echo "Compile the code?"
PS3="Choice: "
options=("Yes" "No")

select op in "${options[@]}"
do
  case $op in
      "Yes")
            echo "Compiling the code..."
            sh compile-local-all.sh
            toHost ${REGISTRYHOST} registry
            toHost ${GRIHOST} gri
            toHost ${LOUNGEHOST} lounge
            toHost ${PARKHOST} park
            toHost ${OWHOST} ow
            toHost ${RAHOST} ra
            toHost ${SSHOST} ss
            toHost ${MANAGERHOST} manager
            toHost ${MECHANICHOST} mechanic
            toHost ${CUSTOMERHOST} customer
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

#echo "Initiating Registry"
#cd registry/
#gnome-terminal -- bash -c "sh set_rmiregistry.sh 22460; read"
#gnome-terminal -- bash -c "sh registry_com.sh; read"
#cd ..


#echo "Initiating General Repository Information"
#cd gri/
#gnome-terminal -- bash -c "sh serverSide_com.sh; read"
#cd ..

#echo "Initiating Outside World"
#cd ow/
#gnome-terminal -- bash -c "sh serverSide_com.sh; read"
#cd ..

#echo "Initiating Park"
#cd park/
#gnome-terminal -- bash -c "sh serverSide_com.sh; read"
#cd ..

#echo "Initiating Repair Area"
#cd ra/
#gnome-terminal -- bash -c "sh serverSide_com.sh; read"
#cd ..

#echo "Initiating Supplier Site"
#cd ss/
#gnome-terminal -- bash -c "sh serverSide_com.sh; read"
#cd ..

#echo "Initiating Lounge"
#cd lounge/
#gnome-terminal -- bash -c "sh serverSide_com.sh; read"
#cd ..

#sleep 1

#echo "Initiating Manager"
#cd manager/
#gnome-terminal -- bash -c "sh clientSide_com_alt.sh; read"
#cd ..

#sleep 1

#echo "Initiating Mechanic"
#cd mechanic/
#gnome-terminal -- bash -c "sh clientSide_com.sh; read"
#cd ..

#sleep 1

#echo "Initiating Customer"
#cd customer/
#gnome-terminal -- bash -c "sh clientSide_com.sh; read"
#cd ..

echo "Remote deployment complete!"
