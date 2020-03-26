SUMMARY = "cairocffi is a CFFI-based drop-in replacement for Pycairo, a set of Python bindings and object-oriented API for cairo."
HOMEPAGE = "https://pypi.org/project/cairocffi/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=e7c7639c2f7e3d6103414416614bfaac \
"

inherit pypi

DEPENDS += " \
	${PYTHON_PN}-pytest-runner-native \
	${PYTHON_PN}-cffi-native \
"

RDEPENDS_${PN} += " \
	${PYTHON_PN}-cffi \
"

SRC_URI[md5sum] = "3fc4be01911348278c9ef308b77443e4"
SRC_URI[sha256sum] = "f1c0c5878f74ac9ccb5d48b2601fcc75390c881ce476e79f4cfedd288b1b05db"

BBCLASSEXTEND = "native nativesdk"
