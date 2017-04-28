FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "d4f327460a30103a081a0992c5f75ed69ef83d61"
SRCBRANCH = "ces-morty-2016.11+fslc"
SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
    	   file://christ_bootlogo.bmp \
"

# The bootlogo can be generated as follows:
# convert logo.png -type Palette -colors 126 -compress none -verbose BMP3:christ_bootlogo.bmp

do_patch_append () {
    bb.build.exec_func('do_insert_bootlogo', d)
}

do_insert_bootlogo () {
    cp ${WORKDIR}/christ_bootlogo.bmp ${S}/tools/logos/christ.bmp
}
