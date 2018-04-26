SUMMARY = "Activate update mode after reboot."
DESCRIPTION = "This package provides a script to activate CURT after the next reboot."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://enable-update"

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend := "${THISDIR}/enable-update:"

do_install () {
	install -d -m 0775 ${D}${base_sbindir}
	install -m 0775 ${WORKDIR}/enable-update ${D}${base_sbindir}/enable-update
}

FILES_${PN} = "${base_sbindir}/enable-update"

RDEPENDS_${PN} = "u-boot-fslc-fw-utils"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
