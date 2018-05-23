SUMMARY = "fast settings interface"
DESCRIPTION = "fastConfig is a lightweight settings app launched before the real app"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=23801156fd0d1dec6581a01b1e337fb2"

inherit qmake5

PV = "1.0+gitr${SRCPV}"

S = "${WORKDIR}/git"

FILESEXTRAPATHS_prepend := "${THISDIR}/fastconfig:"

SRCREV = "1c3ace3df323150ff748bdb7619f876e5394b42e"
SRCBRANCH = "master"

SRC_URI = " \
           git://github.com/software-celo/fastConfig.git;branch=${SRCBRANCH} \
           file://fastconfig.service \
           file://fast.conf \
"

do_install_append () {

    install -d ${D}${systemd_unitdir}/system
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
    install -m 0644 ${WORKDIR}/fastconfig.service ${D}${systemd_unitdir}/system/fastconfig.service
    install -m 0644 ${WORKDIR}/fast.conf ${D}${sysconfdir}/fast.conf

    ln -sf ${systemd_unitdir}/system/fastconfig.service \
        ${D}${sysconfdir}/systemd/system/multi-user.target.wants/fastconfig.service
}

DEPENDS += "systemd qtquick1 qtdeclarative"
RDEPENDS_${PN} += "qtbase-plugins systemd qtsvg-plugins qtgraphicaleffects-qmlplugins \
                   qtquickcontrols-qmlplugins qtquick1 qtdeclarative qtdeclarative-qmlplugins \
"

FILES_${PN} = " \
               ${systemd_unitdir}/system/*.service \
               ${sysconfdir} \
               ${sbindir} \
"

BBCLASSEXTEND = "native"
