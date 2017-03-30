SUMMARY = "Miscellaneous files for the base system provided by christ elektronic systems."
DESCRIPTION = "This package provides network configuration files and alike."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \                     
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://wired0.network \
          "

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend = "${THISDIR}/files:"

do_install () {

	# install network configuration
	install -d -m 0775 ${D}${sysconfdir}/systemd/network
	install -m 0644 ${WORKDIR}/wired0.network ${D}${sysconfdir}/systemd/network/wired0.network
}


FILES_${PN} = "${sysconfdir}/systemd/network/wired0.network \
		      "

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
