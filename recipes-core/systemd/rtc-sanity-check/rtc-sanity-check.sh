#!/bin/sh

/sbin/hwclock -r 2>/dev/null
RET=$?

if [ "$RET" -eq 1 ]; then
    /sbin/hwclock -w --noadjfile --utc
    exit $?
else
    exit $RET
fi
