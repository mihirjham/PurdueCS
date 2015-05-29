#!/bin/bash

if [ ! -f $1 ] ; then
	echo "Password file does not exist."
	exit
fi

password=$(head -n 1 $1)
length=${#password}
strength=$length

if [ $length -lt 6 ]; then
	echo "Error: Password length invalid."
	exit
fi

if [ $length -gt 32 ]; then
	echo "Error: Password length invalid."
	exit
fi

if egrep -q '[#$\+%@]' $1 ; then
	let strength=strength+5
fi

if egrep -q [0-9] $1 ; then
	let strength=strength+5
fi

if egrep -q [A-Za-z] $1 ; then
	let strength=strength+5
fi

if egrep -q '([A-Za-z0-9])\1+' $1 ; then
	let strength=strength-10
fi

if egrep -q '([a-z]){3,}' $1 ; then
	let strength=strength-3
fi

if egrep -q '([A-Z]){3,}' $1 ; then
	let strength=strength-3
fi

if egrep -q '([0-9]){3,}' $1 ; then
	let strength=strength-3
fi

echo "Password Score:" $strength
