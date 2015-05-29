	.text
.globl add
	.type	add, @function
add:
	movq	%rdi, %rax	#%rax = a
	addq	%rsi, %rax	#%rax = %rax + b
	addq	%rdx, %rax	#%rax = %rax + c
	ret

