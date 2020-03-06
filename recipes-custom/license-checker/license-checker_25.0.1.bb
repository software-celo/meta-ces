SUMMARY  = "Recipe for license-checker on npm modules"
AUTHOR = "Steffen Kothe <skothe@christ-es.de>"
HOMEPAGE = "https://www.christ-es.de"
LICENSE = "BSD-3-Clause"
SECTION = "web"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5ce085733d14fd58b61bcf9e27bc020d"
PR = "r0"
FILESEXTRAPATHS_prepend := "${THISDIR}/license-checker:"

inherit npm

SRCREV = "de6e9a42513aa38a58efc6b202ee5281ed61f486"
SRCBRANCH = "master"

SRC_URI = " \
	git://github.com/davglass/license-checker.git;branch=${SRCBRANCH} \
"

S = "${WORKDIR}/git"

do_compile (){
	npm install -g license-checker
}

RDEPENDS_${PN} += " \
	nodejs (>= 10.15.3) \
"

#Dependencies for compiling/building
DEPENDS += "nodejs-native"

BBCLASSEXTEND += "native nativesdk"
