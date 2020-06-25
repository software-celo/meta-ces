SUMMARY = "RAUC bundle for rootfs, kernel, bootloader & devictree"

LICENSE = "MIT"

PV = "V1.0"
PR = "r0"

inherit bundle

RAUC_BUNDLE_COMPATIBLE = "${MACHINE}"
RAUC_BUNDLE_VERSION = "${PV}-${PR}"

RAUC_BUNDLE_SLOTS = "rootfs bootloader"

RAUC_SLOT_rootfs ?= "ces-console-image"
RAUC_SLOT_rootfs[type] = "image"

RAUC_SLOT_bootloader = "u-boot-fslc-ces"
RAUC_SLOT_bootloader[type] = "file"
RAUC_SLOT_bootloader[file] = "u-boot.rauc"

RAUC_KEY_FILE := "${THISDIR}/rauc-dev-certs/development-1.key.pem"
RAUC_CERT_FILE := "${THISDIR}/rauc-dev-certs/development-1.cert.pem"
