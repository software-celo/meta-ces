FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "e515ca338bd6d0b968b853135f0c9b33add998fb"
SRCBRANCH = "ces-rocko-2017.11+fslc"
SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
           file://bootlogo.bmp \
"

# The bootlogo can be generated as follows:
# convert logo.png -type Palette -colors 126 -compress none -verbose BMP3:bootlogo.bmp

do_patch_append () {
    bb.build.exec_func('do_insert_bootlogo', d)
}

do_insert_bootlogo () {
    cp ${WORKDIR}/bootlogo.bmp ${S}/tools/logos/christ.bmp
}
