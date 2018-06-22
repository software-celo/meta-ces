FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "afe734f7bcdbe1b1f1b8f1fca1416523807f5cc6"
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
