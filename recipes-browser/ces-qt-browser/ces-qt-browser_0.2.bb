DESCRIPTION = "QtWebengine based bare fullscreen browser"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

inherit qmake5

FILESEXTRAPATHS_prepend := "${THISDIR}/ces-qt-browser:"

PV = "2.0+gitr${SRCPV}"

S = "${WORKDIR}/git"

SRCREV = "cc09fb44341563af48a8a8593ffd21a58cf7ac1c"
SRCBRANCH = "ces-qt-browser"

SRC_URI = "git://github.com/software-celo/ces-qt-browser.git;branch=${SRCBRANCH} \
	   file://qtbrowser-autostart.service \
	   file://browser.conf \
	   file://error.html \
	   file://index.css \
	   file://erlfunc.js \
	   file://ces.conf \
"

# Systempath for error landing page
ERL_PATH = "${datadir}/ces-qt-browser/erl"


do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	install -m 0644 ${WORKDIR}/qtbrowser-autostart.service ${D}${systemd_unitdir}/system/qtbrowser-autostart.service

	ln -sf ${systemd_unitdir}/system/qtbrowser-autostart.service \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/qtbrowser-autostart.service

	install -m 0644 ${WORKDIR}/browser.conf ${D}${sysconfdir}/browser.conf
	install -m 0644 ${WORKDIR}/ces.conf ${D}${sysconfdir}/ces.conf

	inst_erldir
	inst_erlpage
}


# Install erl directory
inst_erldir() {
	echo "Install erl directory"
	install -d ${D}${datadir}
	install -d ${D}${ERL_PATH}
}


# Install standard error landing page
inst_erlpage() {
	echo "Install standard error landing page"
        install -m 0644 ${WORKDIR}/error.html ${D}${ERL_PATH}/error.html
	install -m 0644 ${WORKDIR}/index.css ${D}${ERL_PATH}/index.css
	install -m 0644 ${WORKDIR}/erlfunc.js ${D}${ERL_PATH}/erlfunc.js
}



DEPENDS += "qtwebengine systemd qtquick1 qtdeclarative"

RDEPENDS_${PN} += "qtwebengine qtbase-plugins systemd qtvirtualkeyboard"
RDEPENDS_${PN} += "qtsvg-plugins qtgraphicaleffects-qmlplugins qtwebengine-qmlplugins qtquickcontrols-qmlplugins qtquick1 qtdeclarative qtdeclarative-qmlplugins "
RDEPENDS_${PN} += "qtquickcontrols2 qtquickcontrols2-qmlplugins qtquick1-qmlplugins qtquickcontrols-qmlplugins"

FILES_${PN} = "${systemd_unitdir}/system/*.service ${sysconfdir} ${sbindir} ${datadir} ${ERL_PATH}"

BBCLASSEXTEND = "native"
