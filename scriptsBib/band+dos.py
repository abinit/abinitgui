#!python

import abipy;
import os, sys;
import argparse;

def bsanddos(inputDos,inputBand,outputDos,outputBand,outputBoth,titleBS,titleDOS,backend):

    import matplotlib;
    matplotlib.use(backend);
    import matplotlib.pyplot as plt;

    fig = plt.figure();

    ebands = abipy.ebands.ElectronBands.from_ncfile(inputBand)
    ebandsDos = abipy.ebands.ElectronBands.from_ncfile(inputDos)

    dos = ebandsDos.get_dos();

    dos.plot(title=titleDOS,fig=fig)
    
    fig.savefig(outputDos);

    plt.close(fig);

    fig = plt.figure();
    ebands.plot(title=titleBS,fig=fig,klabels={(0.0,0.0,0.0) : "$\Gamma$", (0.5,0.0,0.0) : "L", (0.5,0.25,0.0) : "B", (0.5,0.5,0.0) : "X"})

    fig.savefig(outputBand);

    plt.close(fig);

    fig = plt.figure();
    ebands.plot_with_dos(dos,fig=fig,klabels={(0.0,0.0,0.0) : "$\Gamma$", (0.5,0.0,0.0) : "L"});

    fig.savefig(outputBoth);
    plt.close(fig);

def usage():
    """usage de la ligne de commande"""
    print "usage: ", sys.argv[0], "--inputFile=file --outputFile=file"

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('-b', '--inputBand')
    parser.add_argument('-d', '--inputDos')
    parser.add_argument('-B', '--outputBand')
    parser.add_argument('-D', '--outputDos')
    parser.add_argument('-A', '--outputBoth')
    parser.add_argument('-t', '--titleBS')
    parser.add_argument('-u', '--titleDOS')
    parser.add_argument('-f', '--backend')
    args = parser.parse_args()
    bsanddos(args.inputDos,args.inputBand,args.outputDos,args.outputBand,args.outputBoth,args.titleBS,args.titleDOS,args.backend);
