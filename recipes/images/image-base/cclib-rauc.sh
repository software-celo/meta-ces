#!/bin/sh

LOGFILE="/tmp/curt.log"


partition_custom () {

    umount -l /dev/mmcblk${MMCDEV}p* 2>/dev/null

    # create 2x1GB (forA/B) and 1,6GB (data) on 4GB eMMC
    sfdisk --force /dev/mmcblk${MMCDEV} << EOF
,1024M,83
,1024M,83
,,83
EOF
    local RET=$?

    if [ 0 -eq $RET ] ; then
        echo "partition_emmc=1" >> $LOGFILE
    else
        echo "partition_emmc=0" >> $LOGFILE
    fi

}


format_custom () {

    mkfs.ext3 -q /dev/mmcblk${MMCDEV}p3
    local RET=$?

    if [ 0 -eq $RET ] ; then
        echo "format_fat=1" >> $LOGFILE
    else
        echo "format_fat=0" >> $LOGFILE
    fi

    mkfs.ext3 /dev/mmcblk${MMCDEV}p1
    local RET=$?

    if [ 0 -eq $RET ] ; then
        echo "format_ext=1" >> $LOGFILE
    else
        echo "format_ext=0" >> $LOGFILE
    fi

}

mount_custom () {

    umount /dev/mmcblk${MMCDEV}p*

    if [ 1 ] ; then
        echo "mount_fat=1" >> $LOGFILE
    else
        echo "mount_fat=0" >> $LOGFILE
    fi

    mkdir /home/root/ext
    mount /dev/mmcblk${MMCDEV}p1 /home/root/ext
    local RET=$?

    if [ 0 -eq $RET ] ; then
        echo "mount_ext=1" >> $LOGFILE
    else
        echo "mount_ext=0" >> $LOGFILE
    fi

}

uboot_custom_pre () {

    # create hw-partition gp0 with 4M/ENH/WRREL
    mmc gp create -c 4096 1 1 0 /dev/mmcblk${MMCDEV}
    mmc write_reliability set -y 1 /dev/mmcblk${MMCDEV}

    # reset boot configuration
    mmc bootpart enable 1 0 /dev/mmcblk${MMCDEV}
    mmc bootbus set single_hs x1 x8 /dev/mmcblk${MMCDEV}
}

uboot_custom_post () {

    # this will fail on the first run if hw-partitions were created in the same
    # boot cycle, but it doesn't matter, because it is empty anyway
    dd if=/dev/zero of=/dev/mmcblk${MMCDEV}gp0 bs=1k seek=3072 conv=fsync count=8
}

kerneldtb_custom () {

    # can't do anything here because kernel and dtb are included in rootfs
    echo "kernel=1" >> $LOGFILE
    echo "dtb=1" >> $LOGFILE

}


