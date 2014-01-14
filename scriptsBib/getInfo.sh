#!/bin/bash

OPTS=`getopt -o a --long varConv:,outputFile:,varOut:,inputFile: -- "$@"`
if [ $? != 0 ]
then
    exit 1
fi

eval set -- "$OPTS"

while true ; do
    case "$1" in
        --inputFile) echo "Got inputFile, arg: $2"; fileToRead=$2;shift 2;;
        --varConv) echo "Got varConv, arg: $2"; varConv=$2;shift 2;;
        --outputFile) echo "Got outputfile, arg: $2"; fileN=$2; shift 2;;
        --varOut) echo "Got varOut, arg: $2"; varToRead=$2;shift 2;;
        --) shift; break;;
    esac
done

echo "Will read file : $fileToRead and look for : $varToRead vs $varConv"

i=0
k=0
declare -a table
declare -a table2

while read line
do
	((l=k+1))
	((j=i+1))
	if [[ $line == *ndtset* ]]
	then
	ndtset=`echo $line | sed 's/ndtset//g' | sed 's/ //g'`
	fi
	if [[ $line == *$varToRead$j* ]]
	then
	table[$i]=`echo $line | sed "s/$varToRead$j//g" | sed 's/Hartree//g' | sed 's/ //g'`
	((i=i+1))
	fi
	if [[ $line == *$varConv$l* ]]
	then
	
	table2[$k]=`echo $line | sed "s/$varConv$l//g" | sed 's/Hartree//g' | sed 's/ //g'` 
	((k=k+1))
	fi
done < $fileToRead

funName=$fileN

if [ -f $fileN ]
then rm $fileN
fi

echo "$varConv $varToRead" >> $fileN
for ((i=0;i<ndtset;i++)); do
  echo "${table2[${i}]} ${table[${i}]}" >> $fileN
done


