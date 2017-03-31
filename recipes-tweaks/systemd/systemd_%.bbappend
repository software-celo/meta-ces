PACKAGECONFIG_append += "xz ldconfig networkd sysusers resolved iptc hostnamed myhostname binfmt timedated timesyncd localed backlight kdbus firstboot pam polkit utmp pam"
PACKAGECONFIG_remove += "rfkill randomseed quotacheck bootchart hibernate ima smack logind"

do_install_append() {
	rm ${D}${sysconfdir}/systemd/system/getty.target.wants/getty@tty1.service
}
