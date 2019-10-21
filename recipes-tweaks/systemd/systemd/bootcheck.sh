#!/bin/sh

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
