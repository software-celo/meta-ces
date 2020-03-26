SUMMARY = "Pyphen is a pure Python module to hyphenate text using existing Hunspell hyphenation dictionaries."
HOMEPAGE = "https://github.com/Kozea/Pyphen"
SECTION = "devel/python"
LICENSE = "GPLv2 & LGPLv2.1+ & MPLv1.1"
LIC_FILES_CHKSUM = " \
	file://COPYING;md5=e41ef0a4d42c598fa1acb43dc5a6b1e0 \
	file://COPYING.GPL;md5=c0f324f2415fa9a1e896ee42694eab06 \
	file://COPYING.LGPL;md5=d8045f3b8f929c1cb29a1e3fd737b499 \
	file://COPYING.MPL;md5=5f430e30765925d76de3a9a302a1d982 \
"

inherit pypi

PYPI_PACKAGE = "Pyphen"

SRC_URI[md5sum] = "e53a31be2189636252e5e3d830bd7236"
SRC_URI[sha256sum] = "3b633a50873156d777e1f1075ba4d8e96a6ad0a3ca42aa3ea9a6259f93f18921"

BBCLASSEXTEND = "native nativesdk"
