#!/bin/bash

MOUNTPOINTS=sd*

cd /media/

shopt -s nullglob

for i in $MOUNTPOINTS
do
	mnt=`mount | grep $i`
	ret=$?
	if [ "$ret" -eq 0 ]; then
		echo -e "Mountpoint $i: \e[32mOK\e[0m"
	else
		echo -e "Mountpoint $i: \e[33mnot OK\e[0m"
		echo "removing mountpoint $i"
		rmdir $i
	fi
done
