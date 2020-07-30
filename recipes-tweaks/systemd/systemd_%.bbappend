PACKAGECONFIG_append += "xz ldconfig networkd sysusers resolved iptc hostnamed myhostname binfmt timedated timesyncd localed backlight firstboot pam polkit utmp pam"
PACKAGECONFIG_remove += "rfkill randomseed quotacheck bootchart hibernate ima smack logind"

do_install_append() {
	sed -i "s/enable getty\@.service/disable getty\@.service/g" \
		 ${D}${systemd_unitdir}/system-preset/90-systemd.preset

	sed -i "s/PrivateMounts=yes/PrivateMounts=no/g" ${D}${systemd_unitdir}/system/systemd-udevd.service

	if ${@bb.utils.contains('DISTRO_FEATURES', 'rauc', 'true', 'false', d)}; then
		sed -i "s/^#RuntimeWatchdogSec.*/RuntimeWatchdogSec=20/g" ${D}${sysconfdir}/systemd/system.conf
		sed -i "s/^#ShutdownWatchdogSec.*/ShutdownWatchdogSec=5min/g" ${D}${sysconfdir}/systemd/system.conf
	fi
}
