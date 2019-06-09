#!/bin/bash

echo "Compile the code?"
PS3="Choice: "
options=("Yes" "No")

select op in "${options[@]}"
do
  case $op in
      "Yes")
            # run compile-local-all.sh
            echo "Compiling local code"
            sh compile-local-all.sh
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

# go to registry and run set_rmiregistry_alt.sh + registry_com_alt.sh
echo "Initiating Registry"
cd Register
cp set_rmiregistry_alt.sh ../registry
cp registry_com_alt.sh ../registry
cp java.policy ../registry
cd ..
cd registry/
gnome-terminal -- bash -c "sh set_rmiregistry_alt.sh 22460; read"
gnome-terminal -- bash -c "sh registry_com_alt.sh; read"
cd ..

sleep 2

# go to general repository and run serverSide_com_alt.sh
echo "Initiating General Repository Information"
cd GeneralRepInformation
cp serverSide_com_alt.sh ../gri
cp java.policy ../gri
cd ..
cd gri/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 2


# go to outside world and run serverSide_com_alt.sh
echo "Initiating Outside World"
cd OutsideWorld
cp serverSide_com_alt.sh ../ow
cp java.policy ../ow
cd ..
cd ow/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 2


# go to park and run serverSide_com_alt.sh
echo "Initiating Park"
cd Park
cp serverSide_com_alt.sh ../park
cp java.policy ../park
cd ..
cd park/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 2


# go to repair area and run serverSide_com_alt.sh
echo "Initiating Repair Area"
cd RepairArea
cp serverSide_com_alt.sh ../ra
cp java.policy ../ra
cd ..
cd ra/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 2


# go to repair area and run serverSide_com_alt.sh
echo "Initiating Supplier Site"
cd SupplierSite
cp serverSide_com_alt.sh ../ss
cp java.policy ../ss
cd ..
cd ss/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 2


# go to lounge and run serverSide_com_alt.sh
echo "Initiating Lounge"
cd Lounge
cp serverSide_com_alt.sh ../lounge
cp java.policy ../lounge
cd ..
cd lounge/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 2

# go to manager and run clientSide_com_alt.sh
echo "Initiating Manager"
cd Manager
cp clientSide_com_alt.sh ../manager
cd ..
cd manager/
gnome-terminal -- bash -c "sh clientSide_com_alt.sh; read"
cd ..

sleep 2

# go to mechanic and run clientSide_com_alt.sh
echo "Initiating Mechanic"
cd Mechanic
cp clientSide_com_alt.sh ../mechanic
cd ..
cd mechanic/
gnome-terminal -- bash -c "sh clientSide_com_alt.sh; read"
cd ..

sleep 2

# go to customer and run clientSide_com_alt.sh
echo "Initiating Customer"
cd Customer
cp clientSide_com_alt.sh ../customer
cd ..
cd customer/
gnome-terminal -- bash -c "sh clientSide_com_alt.sh; read"
cd ..

echo "Local deployment complete!"
