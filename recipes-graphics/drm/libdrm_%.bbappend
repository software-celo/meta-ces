FILESEXTRAPATHS_prepend := "${THISDIR}/libdrm:"

SRC_URI += " \
        file://libdrm-etnaviv-Fix-double-free-in-etna_bo_cache_free.patch \
"
