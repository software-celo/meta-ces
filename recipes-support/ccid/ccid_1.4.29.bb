SUMMARY = "Generic USB CCID smart card reader driver"
HOMEPAGE = "https://ccid.apdu.fr/"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"

DEPENDS = "virtual/libusb0 pcsc-lite"
RDEPENDS_${PN} = "pcsc-lite"

SRC_URI = "https://ccid.apdu.fr/files/ccid-${PV}.tar.bz2 \
    file://no-dep-on-libfl.patch \
"

SRC_URI[md5sum] = "899bce5ff791128cda6f854318cde547"
SRC_URI[sha256sum] = "a5432ae845730493c04e59304b5c0c6103cd0e2c8827df57d69469a3eaaab84d"

inherit autotools pkgconfig

FILES_${PN} += "${libdir}/pcsc/"
FILES_${PN}-dbg += "${libdir}/pcsc/drivers/*/*/*/.debug"
