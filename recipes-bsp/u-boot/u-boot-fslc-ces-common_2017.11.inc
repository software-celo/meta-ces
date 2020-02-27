# Freescale U-Boot LOCALVERSION extension
#
# This allow to easy reuse of code between different U-Boot recipes
#
# The following options are supported:
#
#  SCMVERSION        Puts the Git hash in U-Boot local version
#  LOCALVERSION      Value used in LOCALVERSION (default to '+fslc')
#
# Copyright 2014 (C) O.S. Systems Software LTDA.
# Copyright (C) 2012-2019 O.S. Systems Software LTDA.
# Copyright (C) 2019-2019 Steffen Kothe <skothe@christ-es.de>  Christ Electronic Systems GmbH
# Released under the MIT license (see COPYING.MIT for the terms)
#
#
# This recipe structure was extracted from meta-fslc layer
#

LOCALVERSION ??= "+fslc"

UBOOT_LOCALVERSION = "${LOCALVERSION}"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

DEPENDS += "bison-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc-ces:"

SRCREV = "6ef3c19fbbaa91eb27ebbb1b11ecc1ba5a55764a"
SRCBRANCH = "ces-mainline-2017.11+fslc"
SRC_URI = " \
	git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
"


PV = "v2017.11+git${SRCPV}"

S = "${WORKDIR}/git"

do_compile_prepend() {

	# Add GIT revision to the local version
	head=`cd ${S} ; git rev-parse --verify --short HEAD 2> /dev/null`

	#Count all commits backwards from HEAD
	commitcnt=`cd ${S} ; git rev-list --count HEAD`
	printf "%s%s%s%s" "${UBOOT_LOCALVERSION}" "+ces-${DISTRO_CODENAME}-${DISTRO_VERSION}+c${commitcnt}" +g $head > ${S}/.scmversion
	printf "%s%s%s%s" "${UBOOT_LOCALVERSION}" "+ces-${DISTRO_CODENAME}-${DISTRO_VERSION}+c${commitcnt}" +g $head > ${B}/.scmversion
}

BBCLASSEXTEND = "native"
