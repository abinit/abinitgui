#!/bin/bash

OPTS=`getopt -o a --long long-key:,outputFile: -- "$@"`
if [ $? != 0 ]
then
    exit 1
fi

eval set -- "$OPTS"

while true ; do
    case "$1" in
        --long-key) echo "Got long-key, arg: $2"; shift 2;;
        --outputFile) echo "Got outputfile, arg: $2"; outputFile=$2; shift 2;;
        --) shift; break;;
    esac
done

qstat -u "*" > $outputFile