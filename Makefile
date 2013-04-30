# This is the Makefile for local installation of the nsim simulator
# and all the required packages. This Makefile installs everything
# you need to run nsim properly from a local folder. This means
# that you don't need to have root priviledges to use it:
# all will be done inside the directory which contains this
# Makefile. This is useful if you want to run nsim on a cluster
# (and you have not the root password!).
# Remember however that a lot of things will be installed
# even if you already have installed them system-wide.
# This usually takes 20-30 minutes on quite new machines.
# See the file README and INSTALL for more info.
# Matteo Franchin, 16 Mar 2007

# We want bash to be our shell!
SHELL=bash

# Where to find the newest nsim 0.1 core tarball
NSIMCORETARBALL=http://nmag.soton.ac.uk/nmag/0.1/download/nmag-0.1-core.tar.gz

# List of packages we are using at the moment
ATLAS_PKG=pkgs/atlas3.6.0.tar.gz
LAPACK_PKG=pkgs/lapack.tgz
OCAML_PKG=pkgs/ocaml-3.09.3.tar.bz2
OCAMLFINDLIB_PKG=pkgs/findlib-1.1.2pl1.tar.gz
CRYPTOKIT_PKG=pkgs/cryptokit-1.2.tar.gz
QHULL_PKG=pkgs/qhull-2003.1.tar.gz
GSL_PKG=pkgs/gsl-1.8.tar.gz
OCAMLGSL_PKG=pkgs/ocamlgsl-0.4.0.tar.gz
MPICH2_PKG=pkgs/mpich2-1.0.4p1.tar.gz
PETSC_PKG=pkgs/petsc-lite-2.3.3-p15.tar.gz
PARMETIS_PKG=pkgs/ParMetis-3.1.tar.gz
PYTHON_PKG=pkgs/Python-2.6.4.tar.bz2
IPYTHON_PKG=pkgs/ipython-0.10.tar.gz
PYTHON_NUMPY_PKG=pkgs/numpy-1.3.0.tar.gz
PYTHON_PYVTK_PKG=pkgs/PyVTK-0.latest.tar.gz
PYTHON_NUMARRAY_PKG=pkgs/numarray-1.5.2.tar.gz
PYTHON_PYTABLES_PKG=pkgs/tables-2.1.2.tar.gz
PYTHON_PY_PKG=pkgs/py-0.9.1.tar.gz
SUNDIALS_PKG=pkgs/sundials-2.3.0.tar.gz
HDF5_PKG=pkgs/hdf5-1.6.10.tar.bz2
EXPECTED_HLIB_PKG=HLib-1.3p19.tar.gz
EXPECTED_HLIB_VER="1.3p19"

LOCAL_PATH=${PWD}
LOCAL_BIN=$(LOCAL_PATH)/bin
LOCAL_LIB=$(LOCAL_PATH)/lib
LOCAL_INCLUDE=$(LOCAL_PATH)/include
PYTHON=$(LOCAL_BIN)/python
FINDLIB_DESTDIR=$(LOCAL_PATH)/lib/ocaml
MPICH2_PATH=$(LOCAL_PATH)/lib/mpich2
MPICH2_LIB_PATH=$(MPICH2_PATH)/lib
MPICH2_BIN_PATH=$(MPICH2_PATH)/bin
PETSC_LIB_PATH=$(LOCAL_PATH)/lib/petsc
PETSC_LIBRARY_PATH=$(PETSC_LIB_PATH)/my_arch
PETSC_INCLUDE_PATH=$(PETSC_LIB_PATH)/include
PETSC_VERSION=2.3.1
PETSC_DEFINE=PETSC230

EXPORT_PATHS=./export_paths.sh

COPTFLAGS=-O3 #-m32 -march=athlon64 -msse2 -m3dnow -mfpmath=sse"
#PETSC_MORE_CONFIG_OPTS=-COPTFLAGS=$(COPTFLAGS) -CXXOPTFLAGS=$(COPTFLAGS) #--download-c-blas-lapack=1
#--with-blas-lapack-dir=$(LOCAL_PATH)/atlas/lib/Linux_HAMMER32SSE2_2

PETSC_MORE_CONFIG_OPTS=-COPTFLAGS=$(COPTFLAGS) -CXXOPTFLAGS=$(COPTFLAGS) --with-blas-lapack-dir=/usr/lib64/atlas

DIST_FILES=README INSTALL TODO patches/nsimconfigure Makefile bin etc include info lib man \
 share patches nsim pkgs

.PHONY: all uninstall clean ocaml dist python_tools create_bin_links check checkall check-all
.PHONY: export_paths hints update _internal_update_part2 hlib_check

all: hlib_check .deps_nsim_install python_tools create_bin_links hints

hlib_check: nsim/interface/extra/lib/libhmatrix-1.3.so

nsim/interface/extra/lib/libhmatrix-1.3.so:
	bash ./patches/hlib/hlib-untar.sh ./hlib-pkg $(EXPECTED_HLIB_PKG) && \
	  rm -f .deps_hlib_patch && $(MAKE) .deps_hlib_install; true

.deps_hlib_patch:
	cp nsim/hlib/hlibpatch.diff.gz hlib/ && \
	  (cd hlib && gunzip -c hlibpatch.diff.gz | patch -p1) && \
	touch .deps_hlib_patch

.deps_hlib_configure: .deps_hlib_patch
	cd hlib && \
	  ./configure --prefix=$(LOCAL_PATH)/nsim/interface/extra && cd ..
	touch .deps_hlib_configure

.deps_hlib_build: .deps_hlib_configure
	cd hlib && make && cd ..
	touch .deps_hlib_build

.deps_hlib_install: .deps_hlib_build
	cd hlib && make install && cd ..
	touch .deps_hlib_install

$(EXPORT_PATHS):
	export PATH=$(LOCAL_BIN):$(MPICH2_BIN_PATH):\$$PATH > $(EXPORT_PATHS)
	echo export PETSC_DIR=$(PETSC_LIB_PATH) >> $(EXPORT_PATHS)
	echo export PETSC_ARCH= >> $(EXPORT_PATHS)
	echo export LD_LIBRARY_PATH=$(LOCAL_LIB):$(MPICH2_LIB_PATH):\$$LD_LIBRARY_PATH >> $(EXPORT_PATHS)

config.status: patches/nsimconfigure
	$(SHELL) patches/nsimconfigure

.deps_atlas_untar:
	tar xzvf $(ATLAS_PKG)
	[ -d atlas ] || mv ATLAS* atlas
	touch .deps_atlas_untar

.deps_lapack_untar:
	tar xzvf $(LAPACK_PKG)
	mv lapack* lapack
	touch .deps_lapack_untar

.deps_ocaml_untar:
	tar xjvf $(OCAML_PKG)
	mv ocaml* ocaml
	touch .deps_ocaml_untar

.deps_ocaml_patch: .deps_ocaml_untar
	cd ocaml && ../patches/ocaml/patches.sh
	touch .deps_ocaml_patch

.deps_ocaml_configure: .deps_ocaml_patch
	cd ocaml && ./configure -prefix $(LOCAL_PATH) && cd ..
	touch .deps_ocaml_configure

.deps_ocaml_build: .deps_ocaml_configure
	cd ocaml && make world.opt && cd ..
	touch .deps_ocaml_build

.deps_ocaml_install: .deps_ocaml_build
	cd ocaml && make install && cd ..
	touch .deps_ocaml_install

.deps_findlib_untar: .deps_ocaml_install
	tar xzvf $(OCAMLFINDLIB_PKG)
	mv findlib* findlib
	touch .deps_findlib_untar

.deps_findlib_configure: .deps_findlib_untar
	export PATH=$(LOCAL_BIN):$$PATH && cd findlib && ./configure && cd ..
	touch .deps_findlib_configure

.deps_findlib_build: .deps_findlib_configure
	export PATH=$(LOCAL_BIN):$$PATH && cd findlib && make all && cd ..
	touch .deps_findlib_build

.deps_findlib_install: .deps_findlib_build
	export PATH=$(LOCAL_BIN):$$PATH && cd findlib && make install && cd ..
	touch .deps_findlib_install

.deps_findlib_setup: .deps_findlib_install
	sed "s,__DESTDIR,$(FINDLIB_DESTDIR),g" patches/findlib/findlib.conf > etc/findlib.conf
	touch .deps_findlib_setup

.deps_ocaml_all: .deps_findlib_setup
	touch .deps_ocaml_all

.deps_cryptokit_untar: .deps_ocaml_all
	tar xzvf $(CRYPTOKIT_PKG)
	mv cryptokit* cryptokit
	touch .deps_cryptokit_untar

.deps_cryptokit_patch: .deps_cryptokit_untar
	cp patches/cryptokit/* cryptokit
	sh patches/cryptokit/patch.sh $(LOCAL_PATH)/cryptokit
	touch .deps_cryptokit_patch

.deps_cryptokit_build: .deps_cryptokit_patch
	export PATH=$(LOCAL_BIN):$$PATH && cd cryptokit && make all allopt && cd ..
	touch .deps_cryptokit_build

.deps_cryptokit_install: .deps_cryptokit_build
	export PATH=$(LOCAL_BIN):$$PATH && cd cryptokit && \
	 make -f Makefile.CED install && cd ..
	touch .deps_cryptokit_install

.deps_qhull_untar:
	tar xzvf $(QHULL_PKG)
	mv qhull* qhull
	touch .deps_qhull_untar

.deps_qhull_configure: .deps_qhull_untar
	cd qhull && ./configure --prefix=$(LOCAL_PATH) && cd ..
	touch .deps_qhull_configure

.deps_qhull_build: .deps_qhull_configure
	cd qhull && make && cd ..
	touch .deps_qhull_build

.deps_qhull_install: .deps_qhull_build
	cd qhull && make install && cd ..
	touch .deps_qhull_install

.deps_gsl_untar:
	tar xzvf $(GSL_PKG)
	mv gsl* gsl
	touch .deps_gsl_untar

.deps_gsl_configure: .deps_gsl_untar
	cd gsl && ./configure --prefix=$(LOCAL_PATH) && cd ..
	touch .deps_gsl_configure

.deps_gsl_build: .deps_gsl_configure
	cd gsl && make && cd ..
	touch .deps_gsl_build

.deps_gsl_install: .deps_gsl_build
	cd gsl && make install && cd ..
	touch .deps_gsl_install

.deps_ocamlgsl_untar: .deps_gsl_install
	tar xzvf $(OCAMLGSL_PKG)
	mv ocamlgsl* ocamlgsl
	touch .deps_ocamlgsl_untar

.deps_ocamlgsl_patch: .deps_ocamlgsl_untar
	cp patches/ocamlgsl/* ocamlgsl/
	touch .deps_ocamlgsl_patch

.deps_ocamlgsl_build: .deps_ocamlgsl_patch
	export PATH=$(LOCAL_BIN):$$PATH && cd ocamlgsl && make && cd ..
	touch .deps_ocamlgsl_build

.deps_ocamlgsl_install: .deps_ocamlgsl_build
	export PATH=$(LOCAL_BIN):$$PATH && \
	 cd ocamlgsl && make install-findlib && cd ..
	touch .deps_ocamlgsl_install

.deps_mpich2_untar:
	tar xzvf $(MPICH2_PKG)
	mv mpich2* mpich2
	touch .deps_mpich2_untar

.deps_mpich2_configure: .deps_mpich2_untar config.status
	mkdir -p $(MPICH2_PATH)
	. ./config.status && \
	cd mpich2 && \
	 ./configure --prefix=$(MPICH2_PATH) -disable-f77 -disable-f90 \
	  --enable-sharedlibs=$$GCC_FLAVOUR && \
	 cd ..
	touch .deps_mpich2_configure

.deps_mpich2_build: .deps_mpich2_configure
	cd mpich2 && make && cd ..
	touch .deps_mpich2_build

.deps_mpich2_install: .deps_mpich2_build
	cd mpich2 && make install && cd ..
	touch .deps_mpich2_install

.deps_petsc_untar: .deps_python_install
	tar xzvf $(PETSC_PKG)
	mv petsc* $(PETSC_LIB_PATH)
	touch .deps_petsc_untar

.deps_petsc_patch: .deps_petsc_untar
	sh patches/petsc/patches.sh $(PETSC_LIB_PATH)
	touch .deps_petsc_patch

.deps_petsc_configure: .deps_petsc_patch .deps_mpich2_install $(EXPORT_PATHS)
	. $(EXPORT_PATHS) && \
	 cd $(PETSC_LIB_PATH) && \
	 $(PYTHON) ./config/configure.py --with-shared \
	  --with-mpi-dir=$(MPICH2_PATH) $(PETSC_MORE_CONFIG_OPTS) && \
	 cd ..
	touch .deps_petsc_configure

.deps_petsc_build: .deps_petsc_configure
	cd $(PETSC_LIB_PATH) && \
	 export PATH=$(MPICH2_BIN_PATH):$$PATH && \
	 export PETSC_DIR=$(PETSC_LIB_PATH) && \
	 export PETSC_ARCH= && \
	 make all && \
	 cd ..
	touch .deps_petsc_build

set_petsc_arch.sh: .deps_petsc_build
	PETSC_ARCH=`grep PETSC_ARCH $(PETSC_LIB_PATH)/bmake/petscconf | cut -d = -f 2` && \
	echo PETSC_ARCH=$${PETSC_ARCH} > set_petsc_arch.sh


.deps_petsc_setup: set_petsc_arch.sh
	. ./set_petsc_arch.sh; \
	cp $(PETSC_LIB_PATH)/bmake/$${PETSC_ARCH}/petscconf.h $(PETSC_INCLUDE_PATH)/
	touch .deps_petsc_setup

.deps_parmetis_untar:
	tar xzvf $(PARMETIS_PKG)
	mv ParMetis* parmetis
	touch .deps_parmetis_untar

.deps_parmetis_patch: config.status .deps_parmetis_untar
	cat patches/parmetis/Makefile >> parmetis/Makefile
	# This sed scripts won't work if the local path contains commas.
	# Normally this should't happen but one never knows...
	. ./config.status && \
	 sed -e "s/__SHLIB_SUFFIX__/$$SHLIB_SUFFIX/g" \
	     -e "s,__INSTALL_PATH__,$(LOCAL_LIB),g" \
	     -e "s/__SHARED__/$$SHLIB_OPTS/g" \
	     patches/parmetis/metis/Makefile >> parmetis/METISLib/Makefile && \
	 sed -e "s/__SHLIB_SUFFIX__/$$SHLIB_SUFFIX/g" \
	     -e "s,__INSTALL_PATH__,$(LOCAL_LIB),g" \
	     -e "s/__SHARED__/$$SHLIB_OPTS/g" \
	     patches/parmetis/parmetis/Makefile >> parmetis/ParMETISLib/Makefile && \
	 mv parmetis/ParMETISLib/stdheaders.h parmetis/ParMETISLib/stdheaders.h.old && \
	 sed "s,malloc.h,$$MALLOC_INC,g" parmetis/ParMETISLib/stdheaders.h.old \
	     > parmetis/ParMETISLib/stdheaders.h
	cat patches/parmetis/Makefile.in >> parmetis/Makefile.in
	touch .deps_parmetis_patch

.deps_parmetis_build: .deps_parmetis_patch
	cd parmetis && \
	 export PATH=$(MPICH2_BIN_PATH):$$PATH && \
	 export PETSC_DIR=$(PETSC_LIB_PATH) && \
	 export PETSC_ARCH= && \
	 make default shared && cd ..
	touch .deps_parmetis_build

.deps_parmetis_install: .deps_parmetis_build
	cp parmetis/parmetis.h include/
	cp parmetis/lib*metis.* lib/
	touch .deps_parmetis_install

.deps_python_untar:
	tar xjvf $(PYTHON_PKG)
	mv Python* python
	touch .deps_python_untar

.deps_python_configure: .deps_python_untar
	cd python && ./configure --enable-shared --prefix=$(LOCAL_PATH) && cd ..
	touch .deps_python_configure

.deps_python_build: .deps_python_configure
	cd python && make && cd ..
	touch .deps_python_build

.deps_python_install: .deps_python_build
	cd python && make install && cd ..
	touch .deps_python_install

.deps_sundials_untar: .deps_mpich2_install
	tar xzvf $(SUNDIALS_PKG)
	[ -d sundials ] || mv sundials* sundials
	touch .deps_sundials_untar

.deps_sundials_configure: .deps_sundials_untar
	cd sundials && \
	 export PATH=$(MPICH2_BIN_PATH):$$PATH && \
	 ./configure --disable-f77 --enable-shared --prefix=$(LOCAL_PATH) --with-mpi-root=$(MPICH2_PATH) && \
	 cd ..
	touch .deps_sundials_configure

.deps_sundials_build: .deps_sundials_configure
	cd sundials && make && cd ..
	touch .deps_sundials_build

.deps_sundials_install: .deps_sundials_build
	cd sundials && make install && cd .. && \
	sh patches/sundials/adjust_install.sh $(LOCAL_PATH)
	touch .deps_sundials_install

.deps_numpy_untar: .deps_python_install
	tar xzvf $(PYTHON_NUMPY_PKG)
	mv numpy-* numpy
	touch .deps_numpy_untar

.deps_numpy_install: .deps_numpy_untar $(EXPORT_PATHS)
	 . $(EXPORT_PATHS) && cd numpy && \
	 $(PYTHON) setup.py install && \
	 cd ..
	touch .deps_numpy_install

.deps_pyvtk_untar: .deps_python_install .deps_numpy_install
	tar xzvf $(PYTHON_PYVTK_PKG)
	mv PyVTK* pyvtk
	touch .deps_pyvtk_untar

.deps_pyvtk_install: .deps_pyvtk_untar $(EXPORT_PATHS)
	. $(EXPORT_PATHS) && cd pyvtk && \
	 $(PYTHON) setup.py install && \
	 cd ..
	touch .deps_pyvtk_install

.deps_hdf5_untar:: .deps_python_install
	tar xjvf $(HDF5_PKG)
	mv hdf5-* hdf5
	touch .deps_hdf5_untar

.deps_hdf5_configure: .deps_hdf5_untar
	cd hdf5 && \
	 ./configure --prefix=$(LOCAL_PATH) && cd ..
	touch .deps_hdf5_configure

.deps_hdf5_build: .deps_hdf5_configure
	cd hdf5 && make && cd ..
	touch .deps_hdf5_build

.deps_hdf5_install: .deps_hdf5_build
	cd hdf5 && make install && cd ..
	touch .deps_hdf5_install

.deps_numarray_untar: .deps_python_install
	tar xzvf $(PYTHON_NUMARRAY_PKG)
	mv numarray-* numarray
	touch .deps_numarray_untar

.deps_numarray_install: .deps_numarray_untar $(EXPORT_PATHS)
	.  $(EXPORT_PATHS) && cd numarray && \
	 $(PYTHON) setup.py install && \
	 cd ..
	touch .deps_numarray_install

.deps_pytables_untar: .deps_python_install .deps_hdf5_install .deps_numarray_install
	tar xzvf $(PYTHON_PYTABLES_PKG)
	mv tables-* pytables
	touch .deps_pytables_untar

.deps_pytables_install: .deps_pytables_untar $(EXPORT_PATHS)
	.  $(EXPORT_PATHS) && export HDF5_DIR=$(LOCAL_PATH) && \
	cd pytables && \
	 $(PYTHON) setup.py install && \
	 cd ..
	touch .deps_pytables_install

.deps_py_untar: .deps_python_install 
	tar xzvf $(PYTHON_PY_PKG)
	mv py-* py
	touch .deps_py_untar

.deps_py_install: .deps_py_untar $(EXPORT_PATHS)
	.  $(EXPORT_PATHS) && \
	cd py && \
	 $(PYTHON) setup.py install && \
	 cd ..
	touch .deps_py_install

.deps_ipython_untar: .deps_python_install
	tar xzvf $(IPYTHON_PKG)
	rm -rf ipython && mv ipython-* ipython
	touch .deps_ipython_untar

.deps_ipython_install: .deps_ipython_untar $(EXPORT_PATHS)
	. $(EXPORT_PATHS) && cd ipython && $(PYTHON) setup.py install && cd ..
	touch .deps_ipython_install

exports.bash: set_petsc_arch.sh
	echo export PATH=$(LOCAL_BIN):$(MPICH2_BIN_PATH):\$$PATH > exports.bash
	echo export PETSC_DIR=$(PETSC_LIB_PATH) >> exports.bash
	echo export PETSC_ARCH= >> exports.bash
	. ./set_petsc_arch.sh && \
	echo export LD_LIBRARY_PATH=$(LOCAL_PATH)/lib:$(PETSC_LIB_PATH)/lib/$${PETSC_ARCH}:$(MPICH2_LIB_PATH):\$$LD_LIBRARY_PATH >> exports.bash
	cp exports.bash nsim/config/exports.sh

nsim/config/exports.sh: exports.bash
	cp exports.bash nsim/config/exports.sh

nsim/config/configuration.inc: nsim/config/configure.py set_petsc_arch.sh $(EXPORT_PATHS)
	. ./set_petsc_arch.sh && . $(EXPORT_PATHS) && cd nsim/config && \
	 $(PYTHON) configure.py \
	  --libdir=$(LOCAL_LIB) \
	  --includedir=$(LOCAL_INCLUDE) \
	  --full-lib-name \
	  --petsc-libdir=$(PETSC_LIB_PATH)/lib/$$PETSC_ARCH \
	  --mpich-libdir=$(MPICH2_LIB_PATH) \
	  --mpich-includedir=$(MPICH2_PATH)/include \
	  --pmpich-libdir=$(MPICH2_LIB_PATH) \
	  --petsc-includedir=$(LOCAL_LIB)/petsc/include \
	  --metis-includedir=$(LOCAL_PATH)/parmetis/METISLib && \
	 cd ..

.deps_nsim_install: .deps_ocaml_all \
 .deps_qhull_install .deps_ocamlgsl_install \
 .deps_mpich2_install .deps_petsc_setup \
 .deps_parmetis_install .deps_python_install \
 .deps_sundials_install \
 exports.bash nsim/config/exports.sh nsim/config/configuration.inc
	. ./exports.bash && cd nsim && ${MAKE} && cd ..
	touch .deps_nsim_install

python_tools: .deps_numpy_install .deps_pyvtk_install .deps_pytables_install .deps_ipython_install .deps_py_install

create_bin_links:
	@SRC_BINS=`ls nsim/bin`; \
	if [ -d ./bin ]; then \
	  cd bin; \
	  SEP="Creating links in ./bin to the nsim bin directory nsim/bin: "; \
	  for BIN in $$SRC_BINS; do \
	    if [ ! -f $$BIN ]; then \
	      echo -n "$$SEP$$BIN"; \
	      ln -s ../nsim/bin/$$BIN $$BIN; \
	      SEP=", "; \
	    fi; \
	  done; \
	  echo; \
	fi

uninstall: clean
	rm -rf bin/* etc/* lib/* nsim/interface/extra/* include/* share/* man/* \
	 info/* set_petsc_arch.sh exports.bash config.sh config.status

clean:
	rm -rf ocaml findlib cryptokit qhull gsl ocamlgsl mpich2 \
	       parmetis python sundials numpy numarray pyvtk \
	       hdf5 atlas lapack ipython pytables py hlib
	rm -f .deps_*

dist:
	NSIMDIST=$$(basename ${PWD}) && \
	NSIMPATH=$$NSIMDIST && \
	FILES="$(DIST_FILES)" && \
	for FILE in $$FILES; do PRFX_FILES="$$PRFX_FILES $$NSIMPATH/$$FILE"; done && \
	CD=$$(pwd) && cd .. && \
	 tar czvf $$CD/$$NSIMDIST.tar.gz --exclude $$NSIMDIST.tar.gz --exclude 'lib/*' \
	  --exclude 'bin/*' --exclude 'include/*' $$PRFX_FILES && cd $$CD

hints:
	@echo
	@echo "HINT: To be able to launch nsim more easily, append the "
	@echo "following line to the end of your shell configuration file "
	@echo "(for BASH users this file is .bashrc and can be found "
	@echo "on your home directory ~/.bashrc):"
	@echo export PATH='"'`pwd`/nsim/bin:'$$PATH"'
	@echo
	@echo "The nmag directory `pwd` should not be moved to another"
	@echo "location. Doing so will make the package unusable, since"
	@echo "absolute paths are hard-coded inside the nmag executables."
	@echo

update:
	@echo "Will start updating nmag in 5 seconds"
	@sleep 5
	@echo "Downloading the nmag tarball..."
	wget -c $(NSIMCORETARBALL)
	ln -s . nmag-0.1
	(unlink Makefile && tar xzvf nmag-0.1-core.tar.gz)
	rm -f nmag-0.1 nmag-0.1-core.tar.gz

./nsim/bin/pytest_nsim:
	make all

check: ./nsim/bin/pytest_nsim
	cd nsim; make check

checkall: ./nsim/bin/pytest_nsim
	cd nsim; make checkall

checkslow: ./nsim/bin/pytest_nsim
	cd nsim; make checkslow

checkmpi: ./nsim/bin/pytest_nsim
	cd nsim; make checkmpi

checkhlib: ./nsim/bin/pytest_nsim
	cd nsim; make checkhlib

