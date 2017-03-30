#!/bin/sh
echo 0 > /sys/block/mmcblk3boot0/force_ro
/sbin/fw_setenv bootcheck 1
RET=$?
echo 1 > /sys/block/mmcblk3boot0/force_ro
exit $RET
