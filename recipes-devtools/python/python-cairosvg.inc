SUMMARY = "CairoSVG is a SVG converter based on Cairo. It can export SVG files to PDF, PostScript and PNG files."
HOMEPAGE = "https://cairosvg.org/"
SECTION = "devel/python"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=bb3ca60759f3202f1ae42e3519cd06bc \
"

inherit pypi python3native

PYPI_PACKAGE = "CairoSVG"

DEPENDS += " \
	${PYTHON_PN}-pytest-runner-native \
"

RDEPENDS_${PN} += " \
	${PYTHON_PN}-cairocffi \
	${PYTHON_PN}-cssselect2 \
	${PYTHON_PN}-defusedxml \
	${PYTHON_PN}-pillow \
	${PYTHON_PN}-tinycss2 \
"

SRC_URI[md5sum] = "a5134fa331a2ac6fc7b0050c34e09dba"
SRC_URI[sha256sum] = "4e668f96653326780036ebb0a9ff2bb59a8443d7bcfc51a14aab77b57a8e67ad"

BBCLASSEXTEND = "native nativesdk"
