# Copyright Steffen Kothe <skothe@christ-es.de> Christ Electronic Systems GmbH
#
# This recipe structure was reused from meta-fslc layer
#

require u-boot.inc
require u-boot-fslc-ces-common_${PV}.inc

DESCRIPTION = "U-Boot based on mainline U-Boot used by FSL Community BSP in \
order to provide support for some backported features and fixes, or because it \
was submitted for revision and it takes some time to become part of a stable \
version, or because it is not applicable for upstreaming."

DEPENDS_append = " bc-native dtc-native"

PROVIDES += "u-boot"

SRC_URI += " \
	file://bootlogo.bmp \
	file://0001-Fix-build-with-warrior.patch \
	file://0003-whitelist-kconfig-options.patch  \
	${@bb.utils.contains('DISTRO_FEATURES', 'rauc', 'file://0004-UBOOT-RAUC.patch', '', d)} \
"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7|vf|use-mainline-bsp)"

inherit deploy

BUILDDIR="${WORKDIR}/build"
UBOOT_IMX_FILE="u-boot.imx"
UBOOT_IMX_RAUC_PREFIX="u-boot"
UBOOT_IMX_RAUC_SUFFIX="rauc"
UBOOT_IMX_RAUC_FILE="${UBOOT_IMX_RAUC_PREFIX}.${UBOOT_IMX_RAUC_SUFFIX}"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'rauc', 'do_rauc_uboot_emmc', '', d)}"

do_rauc_uboot_emmc() {

	for machine in ${UBOOT_MACHINE};do
		if [ -f ${BUILDDIR}/${UBOOT_IMX_FILE} ];then

			dd if=${BUILDDIR}/${UBOOT_IMX_FILE} \
			   of=${BUILDDIR}/${UBOOT_IMX_RAUC_FILE} \
			   bs=1k seek=1

			if [ $? -ne 0 ];then
				bberror "rauc/dd: failed with error code $?"
			fi

			size_origin=`stat --printf="%s" ${BUILDDIR}/${UBOOT_IMX_FILE}`
			size_rauc=`stat --printf="%s" ${BUILDDIR}/${UBOOT_IMX_RAUC_FILE}`
			checksize=`expr ${size_rauc} - ${size_origin}`

			if ! [ ${checksize} -eq 1024 ];then
				bberror "rauc u-boot creation failed! Checksize: ${checksize}"
			fi
		else
			bberror "Missing ${UBOOT_IMX_FILE}!"
		fi
	done
}

do_deploy_ces-fb-rauc_append() {
	for machine in ${UBOOT_MACHINE}; do
		install -m 0644 ${BUILDDIR}/${UBOOT_IMX_RAUC_FILE} ${DEPLOYDIR}/${UBOOT_IMX_RAUC_PREFIX}-${PV}-${PR}.${UBOOT_IMX_RAUC_SUFFIX}
		cd ${DEPLOYDIR}
		ln -sf ${UBOOT_IMX_RAUC_PREFIX}-${PV}-${PR}.${UBOOT_IMX_RAUC_SUFFIX} ${DEPLOYDIR}/${UBOOT_IMX_RAUC_PREFIX}.${UBOOT_IMX_RAUC_SUFFIX}
	done
}
