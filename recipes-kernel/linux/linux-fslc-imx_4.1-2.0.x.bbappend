# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Copyright (C) 2017 Christ Electronic Systems GmbH
# Released under the MIT license (see COPYING.MIT for the terms)

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-fslc-imx:"

SRCBRANCH = "ces-morty-4.1-2.0.x-imx"
SRCREV = "a4f5e5f9c03435c106dbb52d4578a87df4ceecda"
SRC_URI = "git://github.com/software-celo/linux-fslc.git;branch=${SRCBRANCH} \
           file://defconfig \
           file://bootlogo.ppm \
"

# The kernel bootlogo can be generated as follows:
# Save with gimp indexed with 224 colors as bootlogo.ppm (ASCII)

do_patch_append () {
    bb.build.exec_func('do_insert_bootlogo', d)
}

do_insert_bootlogo () {
    cp ${WORKDIR}/bootlogo.ppm ${S}/drivers/video/logo/logo_custom_clut224.ppm
}
