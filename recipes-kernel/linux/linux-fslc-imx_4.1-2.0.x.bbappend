# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Copyright (C) 2017-2018 Christ Electronic Systems GmbH
# Released under the MIT license (see COPYING.MIT for the terms)

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-fslc-imx:"

SRCBRANCH = "ces-rocko-4.1-2.0.x-imx"
SRCREV = "53b69bf402b1645a36113ebb2ca9074029618b96"
SRC_URI = "git://github.com/software-celo/linux-fslc.git;branch=${SRCBRANCH} \
           file://0001-bootlogo-align-center.patch \
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
