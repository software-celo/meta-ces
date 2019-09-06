PACKAGECONFIG_append = "gles2 icu accessibility fontconfig dbus libinput "

FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

OE_QMAKE_PATH_QT_FONTS = "${datadir}/fonts"

SRC_URI += " \
    file://eglfs_kms.json \
"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/eglfs_kms.json ${D}${sysconfdir}/eglfs_kms.json
}
