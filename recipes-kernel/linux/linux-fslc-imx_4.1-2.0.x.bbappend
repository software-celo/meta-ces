# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Copyright (C) 2017 Christ Electronic Systems GmbH
# Released under the MIT license (see COPYING.MIT for the terms)

SRCBRANCH = "ces-morty-4.1-2.0.x-imx"
SRCREV = "e77c6a827330c6b6c95614b8106a58478d85d2a5"
SRC_URI = "git://github.com/software-celo/linux-fslc.git;branch=${SRCBRANCH} \
           file://defconfig \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-fslc-imx:"
