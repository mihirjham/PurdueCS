#!/bin/bash

for f in $( ls /var/ ); do
	echo $f
done

COUNT=6

while [ $COUNT -gt 0 ]; do
	echo Count: $COUNT
	let COUNT=COUNT-1
done
