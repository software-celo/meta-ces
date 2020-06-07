SUMMARY = "Miscellaneous files for the base system provided by Christ Electronic Systems GmbH."
DESCRIPTION = "Provides currently only network settings"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://10-wired.network \
    file://20-wired.network \
"

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install () {

	# install network configuration
	install -d -m 0775 ${D}${sysconfdir}/systemd/network
	install -m 0644 ${WORKDIR}/10-wired.network ${D}${sysconfdir}/systemd/network/10-wired.network
	install -m 0644 ${WORKDIR}/20-wired.network ${D}${sysconfdir}/systemd/network/20-wired.network
}

RDEPENDS_${PN} = "systemd"

FILES_${PN} = " \
    ${sysconfdir}/systemd/network/10-wired.network \
    ${sysconfdir}/systemd/network/20-wired.network \
"

DIRFILES = "1"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
