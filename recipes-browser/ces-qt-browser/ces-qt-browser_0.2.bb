DESCRIPTION = "QtWebengine based bare fullscreen browser"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

inherit qmake5

FILESEXTRAPATHS_prepend := "${THISDIR}/ces-qt-browser:"

PV = "0.2+gitr${SRCPV}"

S = "${WORKDIR}/git"

SRCREV = "daec321533f001a07ec872e3c77598a11413f6d2"
SRCBRANCH = "ces-qt-browser"

SRC_URI = "git://github.com/software-celo/ces-qt-browser.git;branch=${SRCBRANCH} \
           file://qtbrowser-autostart.service \
           file://browser.conf"

do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	install -m 0644 ${WORKDIR}/qtbrowser-autostart.service ${D}${systemd_unitdir}/system/qtbrowser-autostart.service

	ln -sf ${systemd_unitdir}/system/qtbrowser-autostart.service \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/qtbrowser-autostart.service

	install -m 0644 ${WORKDIR}/browser.conf ${D}${sysconfdir}/browser.conf
}

DEPENDS += "qtwebengine systemd qtquick1 qtdeclarative"

RDEPENDS_${PN} += "qtwebengine qtbase-plugins systemd "
RDEPENDS_${PN} += "qtsvg-plugins qtgraphicaleffects-qmlplugins qtwebengine-qmlplugins qtquickcontrols-qmlplugins qtquick1 qtdeclarative qtdeclarative-qmlplugins "

FILES_${PN} = "${systemd_unitdir}/system/*.service ${sysconfdir} ${sbindir}"

BBCLASSEXTEND = "native"
