SUMMARY = "Image booting a qt5 minimal-browser"

LICENSE = "MIT"

PV = "V2.0"
PR = "r0"

inherit extrausers

IMAGE_FEATURES += " package-management"

CHRIST_UPDATE_INCLUDE_UBOOT = "0"
CHRIST_UPDATE_INCLUDE_CUSTOM = ""

#remove interfering sysv scripts, connman systemd service
do_mkrmscript () {
    echo "for i in ${IMAGE_ROOTFS}/etc/rc0.d ${IMAGE_ROOTFS}/etc/rc1.d ${IMAGE_ROOTFS}/etc/rc2.d ${IMAGE_ROOTFS}/etc/rc3.d ${IMAGE_ROOTFS}/etc/rc4.d ${IMAGE_ROOTFS}/etc/rc5.d ${IMAGE_ROOTFS}/etc/rc6.d ${IMAGE_ROOTFS}/etc/rcS.d ; do" > ${WORKDIR}/rmscript
    echo "    rm -f \$i/*dropbear \$i/*avahi-daemon \$i/*dbus-1 \$i/*lxdm \$i/*ntpd \$i/*syslog \$i/*ofono \$i/*alsa-state \$i/*networking \$i/*udev-late-mount \$i/*sendsigs \$i/*save-rtc.sh \$i/*umountnfs.sh \$i/*portmap \$i/*umountfs \$i/*halt \$i/*rmnologin.sh \$i/*reboot; rm -f \$i/*banner.sh \$i/*sysfs.sh \$i/*checkroot.sh \$i/*alignment.sh \$i/*mountall.sh \$i/*populate-volatile.sh \$i/*devpts.sh \$i/*hostname.sh \$i/*portmap \$i/*mountnfs.sh \$i/*bootmisc.sh" >> ${WORKDIR}/rmscript
    echo "done" >> ${WORKDIR}/rmscript
    chmod +x ${WORKDIR}/rmscript
    readlink -e ${WORKDIR}/rmscript
    cat ${WORKDIR}/rmscript
}

IMAGE_LINGUAS = "de-de en-gb"

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@base_conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES}", d)}'

IMAGE_INSTALL += " \
    packagegroup-basic \
    udev-extra-rules \
    ${ROOTFS_PKGMANAGE_PKGS} \
    packagegroup-base-extended \
    qtbase \
    qtbase-plugins \
    qtsvg-plugins \
    qtgraphicaleffects-qmlplugins \
    qtwebengine-qmlplugins \
    qtdeclarative \
    qtdeclarative-qmlplugins \
    qtquickcontrols-qmlplugins \
    qtwebengine \
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
    minimal-browser \
    can-utils \
    libsocketcan \
    iproute2 \
"


# Copy Licenses to image /usr/share/common-licenses
COPY_LIC_MANIFEST = "1"
COPY_LIC_DIRS = "1"

#create the file /etc/timestamp
IMAGE_PREPROCESS_COMMAND = "rootfs_update_timestamp"

IMAGE_INSTALL += " \
    u-boot-fw-utils \
    libusbg \
    curl \
    file \
    aspell \
    e2fsprogs \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    sqlite3 \
    curl \
    nfs-utils-client \
    openssh-scp \
    openssh-sftp \
    bzip2 \
    gdbserver \
    grep \
    ldd \
    lsof \
    mtd-utils \
    dosfstools \
    util-linux-fstrim \
    devmem2 \
    evtest \
    hdparm \
    iperf \
    i2c-tools \
    memtester \
    lmbench \
    nbench-byte \
    ethtool \
    stress \
    util-linux \
    systemd-bootcheck \
    getting-started \
"

#create the deployment directory-tree
require recipes/images/christ-image-fstype.inc

DEPENDS_append = " virtual/bootloader"

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"

EXTRA_USERS_PARAMS = " usermod -P root123 root; "

export IMAGE_BASENAME = "ces-qt-demoimage"

INHERIT += "buildhistory"

inherit image
