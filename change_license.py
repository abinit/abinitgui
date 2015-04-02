#!/usr/bin/env python

import os
import sys
import glob
import codecs

if __name__ == "__main__":
  
  license_file = './LICENSE'

  with codecs.open(license_file,encoding='utf-8') as lf:
    license = lf.readlines()

  for file in glob.glob('src/abinitgui/*/*.java'):

    tmp_file = file+'.tmp'
    #with codecs.open(file,encoding="ISO-8859-1") as f:
    with codecs.open(file,encoding="utf-8") as f:
      all_lines = f.readlines()

      is_beginning = True
      with codecs.open(tmp_file,"w",encoding='utf-8') as ft:
        for iline,line in enumerate(all_lines):
          if is_beginning and line.strip().startswith("*/"):
            is_beginning = False
            continue
          if not is_beginning:
            try:
              ft.write(line.encode('utf-8'))
            except:
              print("error in line ",iline+1,"of file : ",file, "line : ",line)
              try:
                ft.write(line)
              except:
                print("error in encode utf-8 ",iline+1,"of file : ",file, "line : ",line)
        if is_beginning:
          print("!!!!!!!!!!!!!! ERROR !!!!!!!!!!!!!!!! : ",file)

    with codecs.open(tmp_file,encoding='utf-8') as ft:
      all_lines = ft.readlines()

      with codecs.open(file,"w",encoding='utf-8') as f:
        f.write("/*\n")
        f.writelines(license)
        f.write(" */\n")
        f.writelines(all_lines)

    os.remove(tmp_file)
    

