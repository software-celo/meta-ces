SUMMARY = "Image booting a chromium browser in fullscreen"

LICENSE = "MIT"

PV = "V1.0"
PR = "r0"

inherit extrausers

IMAGE_FEATURES += " package-management"

CHRIST_UPDATE_INCLUDE_UBOOT = "0"
CHRIST_UPDATE_INCLUDE_CUSTOM = ""
CHRIST_UPDATE_ONLY_CUSTOM = "0"

IMAGE_LINGUAS = "de-de en-gb"

SOUND_PKGS_INSTALL = '${@bb.utils.contains("DISTRO_FEATURES", "alsa", "${SOUND_PKGS}", "",d)}'
SOUND_PKGS = " \
    packagegroup-fsl-gstreamer1.0-full \
    alsa-plugins \
    alsa-tools \
    alsa-utils \
"

IMAGE_INSTALL_append = " \
    packagegroup-basic \
    udev-extra-rules \
    packagegroup-base-extended \
    packagegroup-core-x11-base \
    mmc-utils \
    tmux \
    picocom \
    openssl \
    openssl-misc \
    ca-certificates \
    userconf \ 
    nano \
    logrotate \
    liberation-fonts \
    tzdata \
    tzdata-misc \
    tzdata-posix \ 
    tzdata-right \ 
    tzdata-europe \ 
    can-utils \
    libsocketcan \
    iproute2 \
    ${SOUND_PKGS_INSTALL} \
    u-boot-fw-utils \
    libusbg \
    e2fsprogs \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    nfs-utils-client \
    openssh-scp \
    openssh-sftp \
    bzip2 \
    grep \
    dosfstools \
    util-linux-fstrim \
    evtest \
    hdparm \
    iperf \
    i2c-tools \
    ethtool \
    stress \
    util-linux \
    systemd-bootcheck \
    getting-started \
    chromium \
"

# Copy Licenses to image /usr/share/common-licenses
COPY_LIC_MANIFEST = "1"
COPY_LIC_DIRS = "1"

#create the file /etc/timestamp
IMAGE_PREPROCESS_COMMAND = "rootfs_update_timestamp"

#create the deployment directory-tree
require recipes/images/christ-image-fstype.inc

DEPENDS_append = " virtual/bootloader"

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

EXTRA_USERS_PARAMS = " usermod -P root123 root; "

export IMAGE_BASENAME = "ces-x11-chromeimage"

inherit image
