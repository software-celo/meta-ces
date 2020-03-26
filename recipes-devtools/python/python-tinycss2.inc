SUMMARY = "tinycss2 is a low-level CSS parser and generator"
HOMEPAGE = "http://tinycss2.readthedocs.io/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=c6b0b4185f20de17d177d689832e2d6d \
"

inherit pypi

DEPENDS += " \
	${PYTHON_PN}-pytest-runner-native \
"

RDEPENDS_${PN} += " \
	${PYTHON_PN}-webencodings \
"

SRC_URI[md5sum] = "6eddf5d3d1b0cd14676ecc03a9caa3fc"
SRC_URI[sha256sum] = "6427d0e3faa0a5e0e8c9f6437e2de26148a7a197a8b0992789f23d9a802788cf"

BBCLASSEXTEND = "native nativesdk"
