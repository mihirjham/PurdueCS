#!/bin/bash

if [ ! -f $1 ] ; then
	echo "$1 does not exist."
	exit
fi

filename=$(date +%Y%m%d-%H%M%S)
filename="$filename-$1"

if [ ! -d $2 ] ; then
	mkdir $2
fi

cp $1 $2/$filename

while [ 1 ] ; do
	sleep $3
	diffOP=$(diff $1 $2/$filename)
	if [ "$diffOP" != "" ] ; then
		count=$(ls -l $2 | wc -l)

		if [ $count -gt $4 ] ; then
			oldestFile=$(ls -t -r $2 | head -n 1)
			rm $2/$oldestFile
		fi

		filename=$(date +%Y%m%d-%H%M%S)
		filename="$filename-$1"
		cp $1 $2/$filename
		echo $diffOP > tmp_msg
		/usr/bin/mailx -s "$1 backed up at $(date)" $USER < tmp_msg
		rm tmp_msg
	fi
done
