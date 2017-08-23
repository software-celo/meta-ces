# Base this distro on fslc-base which in turn is based on poky
require conf/distro/include/fslc-base.inc

DISTRO = "ces-base"
DISTRO_NAME = "Christ Electronic Systems GmbH Distro Base"

MAINTAINER = "Christ Electronic Systems Embedded Linux Team <linux@christ-es.de>"

# Log information on images and packages
INHERIT += "buildhistory"
BUILDHISTORY_COMMIT = "1"

# Disable sysvinit for recipes with systemd support
DISTRO_FEATURES_BACKFILL_CONSIDERED += "sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"

# Default features (for packagegroup(-core)-base)
DISTRO_FEATURES_DEFAULT = "argp ext2 vfat largefile usbgadget usbhost xattr ipv4 ipv6 systemd vfat pam"
