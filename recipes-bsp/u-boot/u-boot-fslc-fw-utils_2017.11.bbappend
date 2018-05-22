FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-fslc:"

SRCREV = "0963debcadf629fad38a9e1e45660de43dbfe066"
SRCBRANCH ="ces-rocko-2017.11+fslc"
PV = "v2017.11+git${SRCPV}"

SRC_URI = "git://github.com/software-celo/u-boot-fslc.git;branch=${SRCBRANCH} \
    file://0002-fw-env-config.patch \
"
