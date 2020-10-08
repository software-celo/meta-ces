LICENSE = "MIT"

inherit image extrausers image-buildinfo

IMAGE_FEATURES += " package-management"
IMAGE_LINGUAS = "de-de en-gb"

# Copy Licenses to image /usr/share/common-licenses
COPY_LIC_MANIFEST = "1"
COPY_LIC_DIRS = "1"
                                                     
#create the file /etc/timestamp
IMAGE_POSTPROCESS_COMMAND = "rootfs_update_timestamp"

BUILDNAME = "${PV}"
                                                     
#create the deployment directory-tree
require ces-image-fstype.inc
require ces-license-xml-manifest.inc
                                                     
DEPENDS_append = " virtual/bootloader"
                                                     
IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"
                                                     
EXTRA_USERS_PARAMS = " usermod -P root123 root; "

DATE := "${@time.strftime('%Y%m%d',time.localtime())}"
TIME := "${@time.strftime('%H%M%S',time.localtime())}"
