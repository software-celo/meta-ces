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
"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7|vf|use-mainline-bsp)"
