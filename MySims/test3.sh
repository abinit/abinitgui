#!/bin/bash


MPI=mpirun
${MPI} -np 1 /home/naps/ygillet/MAPR2451-v2/7.3.1-public/build/src/98_main/abinit < /home/users/t/u/tuto04/MySims/test3.files >& /home/users/t/u/tuto04/MySims/logfiles/test3.log


