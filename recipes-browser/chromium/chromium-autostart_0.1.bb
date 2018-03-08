SUMMARY = "This service starts the chromium on system startup"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit systemd

SRC_URI = "file://browser.conf \
	   file://chromium-autostart.service"

do_install() {
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/systemd/system/graphical.target.wants
	install -m 0644 ${WORKDIR}/chromium-autostart.service ${D}${systemd_unitdir}/system/chromium-autostart.service
	install -m 0644 ${WORKDIR}/browser.conf ${D}${sysconfdir}/browser.conf

	# Do activate chromium-autostart by default
	ln -sf ${systemd_unitdir}/system/chromium-autostart.service \
		${D}${sysconfdir}/systemd/system/graphical.target.wants/chromium-autostart.service
}

RPROVIDES_${PN} += "xserver-nodm-init-systemd"
RREPLACES_${PN} += "xserver-nodm-init-systemd"
RCONFLICTS_${PN} += "xserver-nodm-init-systemd"
SYSTEMD_SERVICE_${PN} = "chromium-autostart.service"

FILES_${PN} += "${systemd_unitdir}/system/*.service ${sysconfdir}/browser.conf ${sysconfdir}/systemd/system/graphical.target.wants/chromium-autostart.service"
