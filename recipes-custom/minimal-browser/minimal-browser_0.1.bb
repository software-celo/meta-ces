DESCRIPTION = "QtWebengine based bare fullscreen browser"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

inherit qmake5

#PV = "1.1+gitr${SRCPV}"
#SRCREV = "${AUTOREV}"
#SRCBRANCH = "master"
#SRC_URI = "git:///path/to/repo;protocol=file;branch=${SRCBRANCH}"
#S = "${WORKDIR}/git"

PV = "0.1"

S = "${WORKDIR}/minimal-browser"

FILESEXTRAPATHS_prepend := "${THISDIR}/minimal-browser:"

SRC_URI = "file://minimal-browser_0.1.tgz \
           file://minimal-browser.service \
           file://browser.conf"

do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	install -m 0644 ${WORKDIR}/minimal-browser.service ${D}${systemd_unitdir}/system/minimal-browser.service

	ln -sf ${systemd_unitdir}/system/minimal-browser.service \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/minimal-browser.service

	install -m 0644 ${WORKDIR}/browser.conf ${D}${sysconfdir}/browser.conf
}

DEPENDS += "qtwebengine systemd qtquick1 qtdeclarative"

RDEPENDS_${PN} += "qtwebengine qtbase-plugins systemd "
RDEPENDS_${PN} += "qtsvg-plugins qtgraphicaleffects-qmlplugins qtwebengine-qmlplugins qtquickcontrols-qmlplugins qtquick1 qtdeclarative qtdeclarative-qmlplugins "

FILES_${PN} = "${systemd_unitdir}/system/*.service ${sysconfdir} ${sbindir}"

BBCLASSEXTEND = "native"
