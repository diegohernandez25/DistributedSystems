#!/bin/bash
CUR_PATH=$(pwd)
cd Register/src/
sh compile-local.sh
cd $CUR_PATH
cd Park/src/
sh compile-local.sh
cd $CUR_PATH
cd OutsideWorld/src/
sh compile-local.sh
cd $CUR_PATH
cd Lounge/src/
sh compile-local.sh
cd $CUR_PATH
cd SupplierSite/src/
sh compile-local.sh
cd $CUR_PATH
cd RepairArea/src/
sh compile-local.sh
cd $CUR_PATH
cd Mechanic/src/
sh compile-local.sh
cd $CUR_PATH
cd Manager/src/
sh compile-local.sh
cd $CUR_PATH
cd Customer/src/
sh compile-local.sh
