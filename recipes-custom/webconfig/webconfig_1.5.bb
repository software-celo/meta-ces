# Copyright (c) Steffen Kothe <skothe@christ-es.de> Christ Electronic Systems GmbH

require webconfig.inc

LIC_FILES_CHKSUM = "file://LICENSE;md5=e65f866ca692a5385dd4ebea623fa3fa"

INC_PR = "r0"
SRCREV = "9b3b5ec8a61315e8e326bed25affa5355a8dbe1b"

SRC_URI += " \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"
