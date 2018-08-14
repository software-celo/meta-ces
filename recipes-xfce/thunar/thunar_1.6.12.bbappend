DESCRIPTION = "Set run in terminal for *.sh files in thunar"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0001-uca.xml-run-in-terminal.patch \
"
