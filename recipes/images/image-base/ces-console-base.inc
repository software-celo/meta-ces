require ces-base/ces-base-settings.inc

SOUND_PACKAGES_INSTALL = "${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${SOUND_PACKAGES}', '', d)}"
SOUND_PACKAGES = " \
	alsa-plugins \
	alsa-tools \
	alsa-utils \
"

BLUETOOTH_PACKAGES_INSTALL = "${@bb.utils.contains('DISTRO_FEATURES', 'bluez5', '${BLUETOOTH_PACKAGES}', '', d)}"
BLUETOOTH_PACKAGES = " \
	bluez5 \
"

HARDWARE_TOOL_PACKAGES_INSTALL = " \
	can-utils \
	cpufrequtils \
	devmem2 \
	ethtool \
	evtest \
	hdparm \
	htop \
	i2c-tools \
	iperf3 \
	iproute2 \
	iptables \
	lmbench \
	memtester \
	mmc-utils \
	mtd-utils \
	mtdev \
	nbench-byte \
	nfs-utils-client \
	picocom \
	stress \
	sysfsutils \
"

SOFTWARE_TOOL_PACKAGES_INSTALL = " \
	aspell \
	curl \
	file \
	gdbserver \
	gpgme \
	ldd \
	logrotate \
	lsof \
	nano \
	tmux \
	bzip2 \
	tzdata-europe \
	tzdata-misc \
	tzdata-posix \
	tzdata-right \
"

CES_MINIMAL_PACKAGES_INSTALL = " \
	sysinfo \
	systemd-bootcheck \
	userconf \
	busybox \
	packagegroup-base \
	packagegroup-core-ssh-openssh \
	openssh-sftp \
	openssh-sftp-server \
	openssh-scp \
	openssl \
	openssl-misc \
	kernel-modules \
	fontconfig-utils \
	liberation-fonts \
	enable-update \
	ca-certificates \
	udev-extra-rules \
	u-boot-fw-utils \
"

IMAGE_INSTALL_append = " \
	${CES_MINIMAL_PACKAGES_INSTALL} \
	${SOUND_PACKAGES_INSTALL} \
	${BLUETOOTH_PACKAGES_INSTALL} \
	${SOFTWARE_TOOL_PACKAGES_INSTALL} \
	${HARDWARE_TOOL_PACKAGES_INSTALL} \
"
