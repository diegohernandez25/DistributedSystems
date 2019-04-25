#!/bin/bash

#GeneralRep
echo compiling general repository
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws01.ua.pt << EOF
cd GeneralRepInformation/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &
exit 
EOF


#Lounge
echo compiling Lounge
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws02.ua.pt << EOF
cd Lounge/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &
exit 
EOF

#Park
echo compiling Park
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws03.ua.pt << EOF
cd Park/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &
exit
EOF

#Outside World
echo compiling OW
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws04.ua.pt << EOF
cd OutsideWorld/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &

exit
EOF

#Repair Area
echo compiling RA
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws05.ua.pt << EOF
cd RepairArea/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &
exit
EOF

#Supplier Site
echo compiling SS
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws06.ua.pt << EOF
cd SupplierSite/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &
exit
EOF

#Manager
echo compiling Manager
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws07.ua.pt << EOF
cd Manager/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &
exit
EOF


# Mechanic
echo compiling Mechanic
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws08.ua.pt << EOF
cd Mechanic/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt > /dev/null
java -jar Main.jar &
exit
EOF

#Customer
echo compiling Customer
sshpass -p dievalint ssh -tt -o StrictHostKeyChecking=no sd0406@l040101-ws09.ua.pt << EOF
cd Customer/src/
rm Main.jar
find . -name "*.class" > temp.txt
jar -cvfe Main.jar Main @temp.txt >/dev/null
java -jar Main.jar 
exit
EOF
