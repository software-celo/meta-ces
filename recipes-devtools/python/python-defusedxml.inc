SUMMARY = "Defusing XML bombs and other exploits"
HOMEPAGE = "https://github.com/tiran/defusedxml"
SECTION = "devel/python"
LICENSE = "PSFL"
LIC_FILES_CHKSUM = " \
	file://LICENSE;md5=056fea6a4b395a24d0d278bf5c80249e \
"

inherit pypi

SRC_URI[md5sum] = "a59741f675c4cba649de40a99f732897"
SRC_URI[sha256sum] = "f684034d135af4c6cbb949b8a4d2ed61634515257a67299e5f940fbaa34377f5"

BBCLASSEXTEND = "native nativesdk"
