FILESEXTRAPATHS_prepend := "${THISDIR}/base-files:"

do_install_append() {

    MMCDEV=${@bb.utils.contains_any('MACHINE', 'medix-rq100 medix-tdl100', '0', '1', d)}

    if ${@bb.utils.contains('DISTRO_FEATURES', 'rauc', 'false', 'true', d)}; then
        echo "/dev/mmcblk${MMCDEV}p1  /boot  vfat  ro  0  0" >> ${D}/${sysconfdir}/fstab
    else
        install -d -m 0755 ${D}/persistent
        install -d -m 0755 ${D}/ofs.base
        install -d -m 0755 ${D}/overlay

	echo "/dev/mmcblk1p3 /persistent/ auto defaults 0 0" >> ${D}/${sysconfdir}/fstab

        echo "overlay /overlay overlay noauto,x-systemd.automount,x-systemd.requires=/dev/mmcblk1p3,lowerdir=/ofs.base,upperdir=/persistent/ofs.persistent,workdir=/persistent/ofs.work 0 0" >> ${D}/${sysconfdir}/fstab
    fi
}
