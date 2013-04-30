#!/home/naps/ygillet/utils/bin/python

import pydft.abinit as pa
import sys
import os

if(len(sys.argv) == 1):
   fname="/home/naps/ygillet/MAPR2451-v2/7.3.1-public/tests/tutorial/Input/tbs_1.in"
else:
   fname=sys.argv[1]

fsuffix = os.path.basename(fname)

print "Filename = ",fname
print "baseName = ",fsuffix

inp=pa.inputvars(fname)

print "inp = ",inp

#print "dtset(acell_orig) = ",dtset.dtsets[1]["acell_orig"]

json_name="./"+fsuffix+".json.txt"

inp.export_json(json_name);


