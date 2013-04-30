#!/bin/bash

LOG=AbinitGUI_src_ver.log

OPTS='-Rf'

#Version='0.1.0.7'
#PackageFolder='AbinitGUI_v.'$Version
#BIN_FOLDER=bin
#TEST_FOLDER=bin

PackageFolder=gui
BIN_FOLDER=precompiled
TEST_FOLDER=tests

# Création du dossier contenant le Package AbinitGUI
[ -d $PackageFolder ] && echo 'Folder '$PackageFolder' exists!' || mkdir $PackageFolder

# Copy JSCH
echo "Copy of the JSCH library..."
MAIN=src/com/jcraft/jsch
mkdir -p ./$PackageFolder/$MAIN 2>> $LOG
cp $OPTS ./$MAIN/*.java $PackageFolder/$MAIN/. 2> $LOG
mkdir ./$PackageFolder/$MAIN/jce
cp $OPTS ./$MAIN/jce/*.java $PackageFolder/$MAIN/jce/. 2>> $LOG
mkdir ./$PackageFolder/$MAIN/jcraft
cp $OPTS ./$MAIN/jcraft/*.java $PackageFolder/$MAIN/jcraft/. 2>> $LOG
mkdir ./$PackageFolder/$MAIN/jgss
cp $OPTS ./$MAIN/jgss/*.java $PackageFolder/$MAIN/jgss/. 2>> $LOG
echo "JSCH library copied."

# Copy JZLIB
echo "Copy of the JZLIB library..."
MAIN=src/com/jcraft/jzlib
mkdir -p ./$PackageFolder/$MAIN 2>> $LOG
cp $OPTS ./$MAIN/*.java $PackageFolder/$MAIN/. 2> $LOG
echo "JZLIB library copied."

# Copy swing-layout
echo "Copy of the swing-layout library..."
MAIN=src/org/jdesktop/layout
mkdir -p ./$PackageFolder/$MAIN 2>> $LOG
cp $OPTS ./$MAIN/*.java ./$PackageFolder/$MAIN/. 2>> $LOG
echo "Swing-layout library copied."

# Copy JDOM
echo "Copy of the JDOM library..."
MAIN=src/org/jdom
mkdir -p ./$PackageFolder/$MAIN 2>> $LOG
cp $OPTS ./$MAIN/*.java $PackageFolder/$MAIN/. 2>> $LOG
mkdir ./$PackageFolder/$MAIN/adapters
cp $OPTS ./$MAIN/adapters/*.java $PackageFolder/$MAIN/adapters/. 2>> $LOG
mkdir ./$PackageFolder/$MAIN/filter
cp $OPTS ./$MAIN/filter/*.java $PackageFolder/$MAIN/filter/. 2>> $LOG
mkdir ./$PackageFolder/$MAIN/input
cp $OPTS ./$MAIN/input/*.java $PackageFolder/$MAIN/input/. 2>> $LOG
mkdir ./$PackageFolder/$MAIN/output
cp $OPTS ./$MAIN/output/*.java $PackageFolder/$MAIN/output/. 2>> $LOG
mkdir ./$PackageFolder/$MAIN/transform
cp $OPTS ./$MAIN/transform/*.java $PackageFolder/$MAIN/transform/. 2>> $LOG
echo "JDOM library copied."

# Copy AbinitGUI
echo "Copy of the AbinitGUI program..."
MAIN=src/abinitgui
mkdir -p ./$PackageFolder/$MAIN 2>> $LOG
cp $OPTS ./$MAIN/*.java $PackageFolder/$MAIN/. 2>> $LOG
cp $OPTS ./src/Main.java $PackageFolder/src/Main.java 2>> $LOG
echo "AbinitGUI program copied."

echo 'Copy of other files...'
#cp $OPTS MakeGUI $PackageFolder/MakeGUI 2>> $LOG
cp $OPTS README_srcVer.txt $PackageFolder/README 2>> $LOG
echo 'Files copied'

./MakeGUI

echo 'Make bin folder and put AbinitGUI.jar inside...'
mkdir -p ./$PackageFolder/$BIN_FOLDER
cp $OPTS AbinitGUI.jar ./$PackageFolder/$BIN_FOLDER/. 2>> $LOG
cp $OPTS generic_config.xml $PackageFolder/$BIN_FOLDER/config.xml 2>> $LOG
#cp $OPTS PSP $PackageFolder/$BIN_FOLDER/. 2>> $LOG
mkdir -p ./$PackageFolder/$TEST_FOLDER
cp $OPTS t41.in $PackageFolder/$TEST_FOLDER/t41.in 2>> $LOG
echo 'bin folder created and AbinitGUI.jar was copied inside.'

find $PackageFolder -name .DS_Store -type f -print0 | xargs -0 /bin/rm -f

#echo 'Creating AbinitGUI.tar.gz file...'
#tar -zcvf AbinitGUI.tar.gz $PackageFolder >> $LOG
#echo 'AbinitGUI.tar.gz file created.'
#echo 'Creating AbinitGUI.zip file...'
#zip -r AbinitGUI $PackageFolder >> $LOG
#echo 'AbinitGUI.zip file created.'

