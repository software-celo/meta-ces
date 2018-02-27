FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "f5161d0e53381ebe80d18941cbfba8a870b727b0"
SRCBRANCH ="ces-rocko-2017.11+fslc"
PV = "v2017.11+git${SRCPV}"

SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
    file://0002-fw-env-config.patch \
"
