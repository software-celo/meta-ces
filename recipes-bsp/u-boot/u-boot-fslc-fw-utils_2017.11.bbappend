FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "e515ca338bd6d0b968b853135f0c9b33add998fb"
SRCBRANCH ="ces-rocko-2017.11+fslc"
PV = "v2017.11+git${SRCPV}"

SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
    file://0002-fw-env-config.patch \
"
