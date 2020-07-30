#!/bin/sh

if [ -f "/sys/block/mmcblk3boot0/force_ro" ] ; then
    # kernel 4.1 maps USDHC4 to mmcblk3
    : ${MMCDEV:=3}
else
    # kernel 4.19 maps USDHC4 to mmcblk1 if some other USDHC device is configured
    # and mmcblk0 if this is the only one
    # so use the number of usdhc interfaces to determine the correct mmc device
    MMCCOUNT=$(for i in /proc/device-tree/soc/aips-bus@*/usdhc\@* ; do cat $i/status; echo ""; done | grep -a okay | wc -l)
    if [ "$MMCCOUNT" -gt 1 ] ; then
        : ${MMCDEV:=1}
    else
        : ${MMCDEV:=0}
    fi
fi

# try to update /etc/fw_env.config with the device number we found out above
mv /etc/fw_env.config /etc/fw_env.config.bak 2>/dev/null
head -n -1 /etc/fw_env.config.bak > /etc/fw_env.config 2>/dev/null
echo "/dev/mmcblk${MMCDEV}boot0      0x300000        0x2000" >> /etc/fw_env.config 2>/dev/null
if [ -e /etc/fw_env.config ]; then
	rm /etc/fw_env.config.bak 2>/dev/null
fi

# if we failed here somehow (e.g. if /etc/ is mounted RO) try to continue anyway


for boot0dev in /sys/block/mmcblk*boot0; do
    echo 0 > $boot0dev/force_ro
done

/sbin/fw_setenv bootcheck 1
RET=$?
sync

for boot0dev in /sys/block/mmcblk*boot0; do
    echo 1 > $boot0dev/force_ro
done

exit $RET
