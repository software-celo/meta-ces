SUMMARY = "cssselect2 is a straightforward implementation of CSS3 Selectors for markup documents (HTML, XML, etc.)"
HOMEPAGE = "https://pypi.org/project/cssselect2"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=aa7228954285c7398bb6711fee73b4ac \
"

inherit pypi

DEPENDS += " \
	${PYTHON_PN}-pytest-runner-native \
"

RDEPENDS_${PN} += " \
	${PYTHON_PN}-tinycss2 \
	${PYTHON_PN}-webencodings \
"

DEPENDS += " \
	${PYTHON_PN}-pytest-runner \
"

SRC_URI[md5sum] = "2e7836cbc8ac19e155ec123df91904db"
SRC_URI[sha256sum] = "5c2716f06b5de93f701d5755a9666f2ee22cbcd8b4da8adddfc30095ffea3abc"

BBCLASSEXTEND = "native nativesdk"
