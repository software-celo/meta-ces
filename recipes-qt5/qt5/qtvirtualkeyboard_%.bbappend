# Copyright (c) 2018  Christ Electronic Systems GmbH

FILESEXTRAPATHS_prepend := "${THISDIR}/qtvirtualkeyboard:"
PACKAGECONFIG = " lang-de_DE lang-en_GB"


SRC_URI += "file://0002-Disable-Language-Popuplist.patch"
