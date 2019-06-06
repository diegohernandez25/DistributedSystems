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
cd registry/
gnome-terminal -- bash -c "sh set_rmiregistry_alt.sh 22460; read"
gnome-terminal -- bash -c "sh registry_com_alt.sh; read"
cd ..

sleep 1

# go to general repository and run serverSide_com_alt.sh
echo "Initiating General Repository Information"
cd gri/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 1


# go to outside world and run serverSide_com_alt.sh
echo "Initiating Outside World"
cd ow/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 1


# go to park and run serverSide_com_alt.sh
echo "Initiating Park"
cd park/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 1


# go to repair area and run serverSide_com_alt.sh
echo "Initiating Repair Area"
cd ra/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 1


# go to repair area and run serverSide_com_alt.sh
echo "Initiating Supplier Site"
cd ss/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 1


# go to lounge and run serverSide_com_alt.sh
echo "Initiating Lounge"
cd lounge/
gnome-terminal -- bash -c "sh serverSide_com_alt.sh; read"
cd ..

sleep 1

# go to manager and run clientSide_com_alt.sh
echo "Initiating Manager"
cd manager/
gnome-terminal -- bash -c "sh clientSide_com_alt.sh; read"
cd ..

sleep 1

# go to mechanic and run clientSide_com_alt.sh
echo "Initiating Mechanic"
cd mechanic/
gnome-terminal -- bash -c "sh clientSide_com_alt.sh; read"
cd ..

sleep 1

# go to customer and run clientSide_com_alt.sh
echo "Initiating Customer"
cd customer/
gnome-terminal -- bash -c "sh clientSide_com_alt.sh; read"
cd ..

echo "Local deployment complete!"
