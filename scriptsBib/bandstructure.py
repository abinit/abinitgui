#!/usr/bin/env python

import os, sys;
import argparse;
from abipy.electrons.ebands import ElectronBands

def bandstructure(inputFile,outputFile,title,show):

    ebands = ElectronBands.from_file(inputFile)

    fig = ebands.plot(title=title,show=show,savefig=outputFile)

def usage():
    """usage de la ligne de commande"""
    print "usage: ", sys.argv[0], "--inputFile=file --outputFile=file --title=title --show=show"

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('-o', '--outputFile')
    parser.add_argument('-i', '--inputFile')
    parser.add_argument('-t', '--title')
    parser.add_argument('-s', '--show')
    args = parser.parse_args()
    bandstructure(args.inputFile,args.outputFile,args.title,args.show=='True')
