DESCRIPTION = "Linux Kernel with patches for Christ Electronic Systems"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

COMPATIBLE_MACHINE = "mx6|pixi-cdlx00|pixi-cdl100|pixi-cdl200|crix-cq100|crix-arq100|pixi-cq100|pixi-cq200|crix-cdl100|4cbox-cdl100|4cbox-cdl200|4cbox-cq100|4cbox-cq200|blix-bdl100|blix-bq100"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ces = "1"

inherit kernel

KBRANCH ?= "linux-4.19.17-ces"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-ces:"

SRC_URI = " \
	git://github.com/software-celo/linux-fslc.git;branch=${KBRANCH} \
	file://0001-bootlogo-align-center.patch \
	file://bootlogo.ppm \
"

LINUX_VERSION ?= "4.19.17"
LINUX_VERSION_EXTENSION_append = "-ces"

SRCREV="ebfb7997cb5de6c1242b3fc7b87ed70d0e36aad7"

PV = "${LINUX_VERSION}+git${SRCPV}"

S = "${WORKDIR}/git"

KERNEL_DEFCONFIG = "imx6_ces_defconfig"
KERNEL_IMAGETYPE = "zImage"

do_configure_prepend() {
	oe_runmake -C ${S} O=${B} ${KERNEL_DEFCONFIG}

	echo "# Set a custom local version" >> ${B}/.config
	echo "CONFIG_LOCALVERSION="\"${LINUX_VERSION_EXTENSION}\" >> ${B}/.config
}

# The kernel bootlogo can be generated as follows:
# Save with gimp indexed with 224 colors as bootlogo.ppm (ASCII)

do_patch_append () {
    bb.build.exec_func('do_insert_bootlogo', d)
}

do_insert_bootlogo () {
    cp ${WORKDIR}/bootlogo.ppm ${S}/drivers/video/logo/logo_custom_clut224.ppm
}
