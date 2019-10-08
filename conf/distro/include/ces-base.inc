DISTRO ?= "ces-base"
DISTRO_NAME = "Christ Electronic Systems GmbH Distro Base"
DISTRO_VERSION = "2.7 / Warrior"

MAINTAINER = "Christ Electronic Systems Embedded Linux Team <linux@christ-es.de>"

# Log information on images and packages
INHERIT += "buildhistory"
BUILDHISTORY_COMMIT = "1"

DISTROOVERRIDES = "ces"
INHERIT += "distrooverrides"

# Disable sysvinit for recipes with systemd support
DISTRO_FEATURES_BACKFILL_CONSIDERED += "sysvinit pulseaudio bluez5"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"
VIRTUAL-RUNTIME_login_manager = "shadow-base"
VIRTUAL-RUNTIME_dev_manager = "udev"

# Default features (for packagegroup(-core)-base)
DISTRO_FEATURES_DEFAULT = "ipv6 pam systemd usbgadget usbhost vfat xattr ext2"
DISTRO_FEATURES += "${DISTRO_FEATURES_DEFAULT}"

# Disable python usage in opkg-utils
PACKAGECONFIG_remove_pn-opkg-utils = "python"

# Customize SDK variables according to the selected distro
SDK_NAME_PREFIX = "${DISTRO}"
SDK_VERSION ?= "${DISTRO_VERSION}"
