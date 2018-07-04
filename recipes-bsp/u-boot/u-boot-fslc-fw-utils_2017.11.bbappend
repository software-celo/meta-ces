FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "7a380b3216ef9df5d0112e621878f95e3a93ba59"
SRCBRANCH ="ces-rocko-2017.11+fslc"
PV = "v2017.11+git${SRCPV}"

SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
    file://0002-fw-env-config.patch \
"
