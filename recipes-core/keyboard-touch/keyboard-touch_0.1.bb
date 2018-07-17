SUMMARY = "Files regarding touch and keyboard"
DESCRIPTION = "This package provides touch calibration and keyboard configuration."
PR = "r2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://00-keyboard.conf \
           "
S = "${WORKDIR}/"

do_install () {
	install -m 0775 -d ${D}${sysconfdir}/X11/xorg.conf.d
	install -m 0644 ${WORKDIR}/00-keyboard.conf ${D}${sysconfdir}/X11/xorg.conf.d/00-keyboard.conf

        #install -m 0775 -d ${D}${datadir}/X11/xorg.conf.d
	#install -m 0644 ${WORKDIR}/99-calibration.conf ${D}${datadir}/X11/xorg.conf.d/99-calibration.conf
}

FILES_${PN} = " ${sysconfdir}/X11/xorg.conf.d/00-keyboard.conf \
"

