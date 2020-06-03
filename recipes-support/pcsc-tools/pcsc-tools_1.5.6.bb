DESCRIPTION = "Some tools to be used with smart cards and PC/SC"
HOMEPAGE = "http://ludovic.rousseau.free.fr/softwares/pcsc-tools/"
LICENSE = "GPLv2+"

inherit autotools pkgconfig

SRC_URI = "http://ludovic.rousseau.free.fr/softwares/pcsc-tools/${BPN}-${PV}.tar.bz2"

SRC_URI[md5sum] = "71cc9d0290ed2ada189330d5f3da4959"
SRC_URI[sha256sum] = "5edb41c783e05446e13e2d6bb4a430afc017887c01a0522fc59a68c30c685fa8"
LIC_FILES_CHKSUM = "file://README;beginline=111;endline=127;md5=4c621c41acd1bbbaa987b21e13fedb51"

S = "${WORKDIR}/${BPN}-${PV}"

DEPENDS = "pcsc-lite"
RDEPENDS_${PN} = "perl-module-getopt-std pcsc-perl"

FILES_${PN} += " \
    ${datadir}/pcsc \
"

BBCLASSEXTEND = "native"
