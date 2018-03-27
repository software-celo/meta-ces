DESCRIPTION = "QtWebengine based bare fullscreen browser"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

inherit qmake5

FILESEXTRAPATHS_prepend := "${THISDIR}/ces-qt-browser:"

PV = "0.2+gitr${SRCPV}"

S = "${WORKDIR}/git"

SRCREV = "0eb8117efb4c1e35c3defe852eb65a574d73c8be"
SRCBRANCH = "ces-qt-browser"

SRC_URI = "git://github.com/software-celo/ces-qt-browser.git;branch=${SRCBRANCH} \
	   file://qtbrowser-autostart.service \
	   file://browser.conf \
	   file://error.html \
	   file://header_r.png \
	   file://index.css \
	   file://Tux.png \
"

# Systempath for error landing page
ERL_PATH = "${datadir}/minimalbrowser/erl"



do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	install -m 0644 ${WORKDIR}/qtbrowser-autostart.service ${D}${systemd_unitdir}/system/qtbrowser-autostart.service

	ln -sf ${systemd_unitdir}/system/qtbrowser-autostart.service \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants/qtbrowser-autostart.service

	install -m 0644 ${WORKDIR}/browser.conf ${D}${sysconfdir}/browser.conf

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
	install -m 0644 ${WORKDIR}/header_r.png ${D}${ERL_PATH}/header_r.png
	install -m 0644 ${WORKDIR}/index.css ${D}${ERL_PATH}/index.css
	install -m 0644 ${WORKDIR}/Tux.png ${D}${ERL_PATH}/Tux.png
}



DEPENDS += "qtwebengine systemd qtquick1 qtdeclarative"

RDEPENDS_${PN} += "qtwebengine qtbase-plugins systemd qtvirtualkeyboard"
RDEPENDS_${PN} += "qtsvg-plugins qtgraphicaleffects-qmlplugins qtwebengine-qmlplugins qtquickcontrols-qmlplugins qtquick1 qtdeclarative qtdeclarative-qmlplugins "
RDEPENDS_${PN} += "qtquickcontrols2 qtquickcontrols2-qmlplugins qtquick1-qmlplugins qtquickcontrols-qmlplugins"

FILES_${PN} = "${systemd_unitdir}/system/*.service ${sysconfdir} ${sbindir} ${datadir} ${ERL_PATH}"

BBCLASSEXTEND = "native"
