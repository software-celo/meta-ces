FILES_${PN} += "${sysconfdir}/locale.conf"

do_install_append() {
	# Commonly systemd-firstboot service is supposed to set a locale,
	# however, because systemd-conf package brings /etc/machine-id file
	# to the root filesystem, the systemd-firstboot service is NOT run
	# on the first boot and machine locale setting remains unconfigured.
	#
	# There are various options to configure a wanted locale in runtime,
	# for instance by running "localectl", but at build time the best
	# way is to install /etc/machine-id and /etc/locale.conf together.

	echo "LANG=de_DE.utf8" > ${D}${sysconfdir}/locale.conf
}
