ARM_INSTRUCTION_SET_fslc = "arm"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwebengine:"

SRC_URI_append = " \
    file://quicknanobrowser.desktop \
"

do_install_append (){
    # install quicknanobrowser.desktop file if x11 is set in DISTRO_FEATURE
    if ${@bb.utils.contains('DISTRO_FEATURES','x11','true','false',d)}; then
        install -d ${D}${datadir}/applications
        install -m 0644 ${WORKDIR}/quicknanobrowser.desktop ${D}${datadir}/applications/quicknanobrowser.desktop
    fi
}
