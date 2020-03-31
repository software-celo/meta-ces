do_install_append() {
    echo "/dev/mmcblk1p1  /boot  vfat  ro  0  0" >> ${D}/${sysconfdir}/fstab
}
