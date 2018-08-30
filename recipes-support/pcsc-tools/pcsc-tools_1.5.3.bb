DESCRIPTION = "Some tools to be used with smart cards and PC/SC"
HOMEPAGE = "http://ludovic.rousseau.free.fr/softwares/pcsc-tools/"
LICENSE = "GPLv2+"

inherit autotools pkgconfig

SRC_URI = "http://ludovic.rousseau.free.fr/softwares/pcsc-tools/${PN}-${PV}.tar.bz2"

SRC_URI[md5sum] = "c6b59926a6ea3b37503c36a3a419d60d"
SRC_URI[sha256sum] = "daaa011c28daa00653bd8e2a3d8b0b9f8abae00f7344f50b1a94fbd2b01f1d14"
LIC_FILES_CHKSUM = "file://README;beginline=108;endline=128;md5=579c415fc470c8121e2c8164f6947ead"

S = "${WORKDIR}/${PN}-${PV}"

DEPENDS = "pcsc-lite"
RDEPENDS_${PN} = "perl-module-getopt-std pcsc-perl"

FILES_${PN} += " \
    ${datadir}/pcsc \
"

BBCLASSEXTEND = "native"
