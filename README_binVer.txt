-----------------------------------------------
******   AbinitGUI v. 0.3 (April 2013)   ******
-----------------------------------------------

***************************
1. How to run AbinitGUI.jar
***************************

Before you start the AbiniGUI.jar executable you need the Java JRE version 1.7+ (mandatory).

The jar file distributed in this package can be executed on the following ARCH/OS:

x86_64 (64bits) / Mac OS X (R) 10.6+ (intel based)
x86 (32bits) and x86_64 (64bits) / Linux (kernel 2.6+)
x86 (32bits) and x86_64 (64bits) / Windows (R) (XP, Vista, 7)

The best way to run AbinitGUI.jar is to open a Terminal (on UNIX [Linux or Mac] or DOS [Windows XP, Vista, 7] systems) and to type the following command:

prompt# java -jar AbinitGUI.jar

This ensures that the current directory will be the one in which lies the AbinitGUI.jar file. Furthermore, it allows to display additional error messages that are not displayed in the GUI.

*****************************
2. How to build AbinitGUI.jar
*****************************

Go to the "gui" folder inside the abinit package (the folder where you can find this README file).

Execute the following commands at the command line (terminal):

prompt# ./bootstrap.sh

Please refer to the INSTALL file for more details!

prompt# ./configure
prompt# make

Now you should have the AbinitGUI.jar executable in the "src/Prog" subdirectory.

You can also install the GUI in your system, but you need administrator rights on the machine.

prompt# make install

To clean the compiled files and the configurations type:

prompt# make clean
prompt# ./wipeout.sh

ATTENTION! The Abinit GUI is known to work with:
  * Sun JDK
  * OpenJDK
But at present, the build fails with Eclipse JDK.

***********************
3. Error and bug report
***********************

Please report any incompatibility or bug to abinitgui@gmail.com.