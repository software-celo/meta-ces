SUMMARY = "This service removes empty usb device mountpoints"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://usbmountpoints.service \
           file://usbmountpoints.sh \
"

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend := "${THISDIR}/systemd:"

do_install() {
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	install -m 0644 ${WORKDIR}/usbmountpoints.service ${D}${systemd_unitdir}/system/usbmountpoints.service
	# enable the service
	ln -sf ${systemd_unitdir}/system/usbmountpoints.service \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/usbmountpoints.service
        install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/usbmountpoints.sh ${D}${sbindir}/usbmountpoints.sh
}

RDEPENDS_${PN} = "systemd bash"

FILES_${PN} = "${systemd_unitdir}/system/*.service ${sysconfdir} ${sbindir}/usbmountpoints.sh"
PACKAGE_ARCH = "${MACHINE_ARCH}"
