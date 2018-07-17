SUMMARY = "Miscellaneous files for the base system provided by christ elektronic systems."
DESCRIPTION = "This package provides network configuration files and alike."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://wired0.network \
           file://wired1.network \
          "

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install () {

	# install network configuration
	install -d -m 0775 ${D}${sysconfdir}/systemd/network
	install -m 0644 ${WORKDIR}/wired0.network ${D}${sysconfdir}/systemd/network/wired0.network
	install -m 0644 ${WORKDIR}/wired1.network ${D}${sysconfdir}/systemd/network/wired1.network
}


FILES_${PN} = " ${sysconfdir}/systemd/network/wired0.network \
		${sysconfdir}/systemd/network/wired1.network \
		"

DIRFILES = "1"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
