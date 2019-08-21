require ces-base/ces-base-settings.inc

SOUND_PKGS_INSTALL = '${@bb.utils.contains("DISTRO_FEATURES", "alsa", "${SOUND_PKGS}", "",d)}'
SOUND_PKGS = " \
    packagegroup-fsl-gstreamer1.0-full \
    alsa-plugins \
    alsa-tools \
    alsa-utils \
"

IMAGE_INSTALL_append = " \
    udev-extra-rules \
    packagegroup-base-extended \
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
    \
    packagegroup-core-boot \
    packagegroup-core-x11 \
    packagegroup-xfce-base \
    kernel-modules \
    thunar-media-tags-plugin \
    thunar-volman \
    xfce4-cpugraph-plugin \
    xfceuserconf \
    \
    htop \
    procps \
    mmc-utils \
    florence3 \
    pcmanfm \
    gtk-theme-clearlooks \
    systemd-usbmountpoints \
    gedit \
    vim \
    curl \
    packagegroup-core-ssh-openssh \
    openssh-sftp-server \
"

SYSTEMD_DEFAULT_TARGET = "graphical.target"
