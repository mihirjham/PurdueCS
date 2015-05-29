#!/bin/bash

count=1

while [ $count -lt 201 ] ; do
	echo $count | java NumberOfCalls >> results.txt
	let count=count+1
done

