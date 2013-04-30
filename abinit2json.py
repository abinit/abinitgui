#!/home/naps/ygillet/utils/bin/python

import ctypes
ctypes.PyDLL("ab6_invars.so", mode=ctypes.RTLD_GLOBAL)
import abipy
import sys
import locale
import os

if(len(sys.argv) == 1):
   print "You should provide a file name"
   sys.exit(1)
else:
   fname=sys.argv[1]

fsuffix = os.path.basename(fname)

print "Filename = ",fname
print "baseName = ",fsuffix

dtset = abipy.parser.Datasets(fname)

print "dtset(acell_orig) = ",dtset.dtsets[1]["acell_orig"]

json_name="./"+fsuffix+".json.txt"

dtset.export_json(json_name);


