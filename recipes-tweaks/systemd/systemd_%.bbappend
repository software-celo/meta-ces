PACKAGECONFIG_append += "xz ldconfig networkd sysusers resolved iptc hostnamed myhostname binfmt timedated timesyncd localed backlight firstboot pam polkit utmp pam"
PACKAGECONFIG_remove += "rfkill randomseed quotacheck bootchart hibernate ima smack logind"

do_install_append() {
#	rm ${D}${sysconfdir}/systemd/system/getty.target.wants/getty@tty1.service
#	rm ${D}${systemd_unitdir}/system/getty@.service
	sed -i "s/MountFlags=slave/MountFlags=shared/g" ${D}${systemd_unitdir}/system/systemd-udevd.service
}
