SUMMARY = "Fixup rtc invalid state after boot without rtc battery"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://rtc-sanity-check.service \
    file://rtc-sanity-check.sh \
"

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend := "${THISDIR}/rtc-sanity-check:"

inherit features_check systemd
REQUIRED_DISTRO_FEATURES = "systemd"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/rtc-sanity-check.sh ${D}${sbindir}/rtc-sanity-check.sh

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/rtc-sanity-check.service ${D}${systemd_system_unitdir}
}

SYSTEMD_SERVICE_${PN} = "rtc-sanity-check.service"

RDEPENDS_${PN} = "systemd"

FILES_${PN} = " \
    ${systemd_system_unitdir}/*.service \
    ${sbindir} \
"

#PACKAGE_ARCH = "${MACHINE_ARCH}"
