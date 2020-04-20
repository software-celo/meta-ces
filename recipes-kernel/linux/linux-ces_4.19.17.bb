DESCRIPTION = "Linux Kernel with patches for Christ Electronic Systems GmbH"

AUTHOR = " \
	Peter Fink <pfink@christ-es.de> \
	Steffen Kothe <skothe@christ-es.de> \
"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

COMPATIBLE_MACHINE = "ces-imx"

inherit kernel-yocto kernel

KMACHINE = "${MACHINE}"
LINUX_KERNEL_TYPE = "standard"
KERNEL_IMAGETYPE = "zImage"

KBRANCH ?= "linux-${LINUX_VERSION}-ces"
KCONFIG_MODE ?= "alldefconfig"

KBUILD_DEFCONFIG_ces-imx ?= "imx6_ces_defconfig"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-4.19.17-ces:"

SRC_URI = " \
	git://github.com/software-celo/linux-fslc.git;branch=${KBRANCH} \
	file://0001-bootlogo-align-center.patch \
	file://bootlogo.ppm \
	file://defconfig \
"

LINUX_VERSION = "4.19.17"
LINUX_VERSION_EXTENSION_append = "-ces"

SRCREV = "44d277e993159a76dbae8840a8accef60feed378"

PV = "${LINUX_VERSION}+git${SRCPV}"

S = "${WORKDIR}/git"


# The kernel bootlogo can be generated as follows:
# Save with gimp indexed with 224 colors as bootlogo.ppm (ASCII)

do_patch_append () {
	do_insert_bootlogo
}

do_insert_bootlogo () {
	cp ${WORKDIR}/bootlogo.ppm ${S}/drivers/video/logo/logo_custom_clut224.ppm
}
