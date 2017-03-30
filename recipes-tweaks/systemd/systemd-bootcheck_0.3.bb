SUMMARY = "This service sets the bootcheck variable after a clean system start"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

SRC_URI = "file://bootcheck.service \
	   file://bootcheck.sh \
	   file://COPYING.LGPLv3"

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend := "${THISDIR}/systemd:"

do_install() {
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/bootcheck.sh ${D}${sbindir}/bootcheck.sh
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	install -m 0644 ${WORKDIR}/bootcheck.service ${D}${systemd_unitdir}/system/bootcheck.service
	# enable the service
	ln -sf ${systemd_unitdir}/system/bootcheck.service \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/bootcheck.service
}

RDEPENDS_${PN} = "systemd"

FILES_${PN} = "${systemd_unitdir}/system/*.service ${sysconfdir} ${sbindir}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
