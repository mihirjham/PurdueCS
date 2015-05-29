	.text
.globl addarray
	.type	addarray, @function
addarray:
	mov	$0, %edx	#i: %edx, i=0
	mov	$0, %eax	#sum: %eax, sum=0
while:
	cmp	%edi, %edx	#i<n
	je	end
	
	add	(%esi), %eax	#sum = sum + *array
	add	$1, %edx	#i++
	add	$4, %esi	#array = array + 4
	jmp	while
end:
	ret
