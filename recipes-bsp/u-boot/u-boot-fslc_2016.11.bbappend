FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "3a39c461ac1ca9ef03be180f2c1af6a2b11315f9"
SRCBRANCH = "ces-morty-2016.11+fslc"
SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
    	   file://christ_bootlogo.bmp \
"

do_patch_append () {
    bb.build.exec_func('do_insert_bootlogo', d)
}

do_insert_bootlogo () {
    cp ${WORKDIR}/christ_bootlogo.bmp ${S}/tools/logos/christ.bmp
}
