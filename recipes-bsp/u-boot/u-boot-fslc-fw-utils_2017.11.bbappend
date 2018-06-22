FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "afe734f7bcdbe1b1f1b8f1fca1416523807f5cc6"
SRCBRANCH ="ces-rocko-2017.11+fslc"
PV = "v2017.11+git${SRCPV}"

SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
    file://0002-fw-env-config.patch \
"
