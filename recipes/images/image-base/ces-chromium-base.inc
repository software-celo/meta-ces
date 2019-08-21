require ces-base/ces-base-settings.inc

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
    iperf3 \
    i2c-tools \
    ethtool \
    stress \
    util-linux \
    sysinfo \
    systemd-bootcheck \
    getting-started \
    enable-update \
    chromium-x11 \
    chromium-autostart \
"
SYSTEMD_DEFAULT_TARGET = "graphical.target"
