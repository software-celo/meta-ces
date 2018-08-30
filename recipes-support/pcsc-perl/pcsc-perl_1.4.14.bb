DESCRIPTION = "This library allows to communicate with a smart card using PC/SC (Microsoft version or pcsc-lite for Unixes) from a Perl script."
HOMEPAGE = "http://ludovic.rousseau.free.fr/softwares/pcsc-perl/"
SECTION = "libs"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENCE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

FILESEXTRAPATHS_append:= "${THISDIR}/${PN}:"

SRC_URI = " \
    http://ludovic.rousseau.free.fr/softwares/pcsc-perl/${PN}-${PV}.tar.bz2 \
    file://0001-pcsc-perl-fix-incorrect-perl-shebang.patch \
"

SRC_URI[md5sum] = "45601505dbb7b27329811ac9bad35fab"
SRC_URI[sha256sum] = "2722b7e5543e4faf3ba1ec6b29a7dfec6d92be1edec09d0a3191992d4d88c69d"

S = "${WORKDIR}/${PN}-${PV}"

inherit cpan pkgconfig

BBCLASSEXTEND = "native"

#RDEPENDS_${PN}_class-native = ""
RDEPENDS_${PN} = "pcsc-lite perl-module-dynaloader perl-module-file-stat"
DEPENDS = "pcsc-lite perl-native"
# perl
