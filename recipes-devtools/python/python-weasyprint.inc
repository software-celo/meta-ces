SUMMARY = "WeasyPrint converts web documents"
DESCRIPTION = " \
WeasyPrint is a smart solution helping web developers to create PDF documents. \
It turns simple HTML pages into gorgeous statistical reports, invoices, tickets…\
\
From a technical point of view, WeasyPrint is a visual rendering engine for HTML \
and CSS that can export to PDF and PNG. \
It aims to support web standards for printing. \
WeasyPrint is free software made available under a BSD license.\
"
HOMEPAGE = "https://weasyprint.org/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=3d346c875b773a1e6251d417aee89c7d \
"

inherit pypi python3native

PYPI_PACKAGE = "WeasyPrint"

DEPENDS += " \
	${PYTHON_PN}-pytest-runner-native \
"

RDEPENDS_${PN} += " \
	${PYTHON_PN}-cairosvg \
	${PYTHON_PN}-pyphen \
	${PYTHON_PN}-cairocffi \
	${PYTHON_PN}-cffi \
	${PYTHON_PN}-cssselect2 \
	${PYTHON_PN}-html5lib \
	${PYTHON_PN}-tinycss2 \
	${PYTHON_PN}-setuptools \
"

SRC_URI[md5sum] = "177a9603187403c70275f63b601c39d7"
SRC_URI[sha256sum] = "b3e971973a4f03c1430e6b838b75b5b57630415fcae8666d2be1347630ff6d6a"

BBCLASSEXTEND = "native nativesdk"
