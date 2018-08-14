DESCRIPTION = "Set pcmanfm as default filemanager instead thunar"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0001-helpers.rc-Set-pcmanfm-as-filemanager.patch \
"

